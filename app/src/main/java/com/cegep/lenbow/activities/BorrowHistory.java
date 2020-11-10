package com.cegep.lenbow.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.cegep.lenbow.R;
import com.cegep.lenbow.adapters.BorrowListAdapter;
import com.cegep.lenbow.models.Reserve;
import com.cegep.lenbow.models.Student;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * borrow/reservation history page
 * @author dipmal lakhani
 */

public class BorrowHistory extends AppCompatActivity {

    /**
     * listview attribute
     */
    private ListView listView;
    /**
     * Edittext attribute
     */
    private EditText Search;
    /**
     * Imageview attribute
     */
    private ImageView backbtn;
    /**
     * Firebase database object
     */
    private FirebaseDatabase database;
    /**
     * Firebase authentication object
     */
    private FirebaseAuth auth;
    /**
     * List of Reserve type attribute
     */
    private List<Reserve> reserveList = new ArrayList<>();
    /**
     * BorrowLostAapter object
     */
    private BorrowListAdapter borrowadapter;
    /**
     * Id of the user
     */
    private String userId;
    /**
     * object of Student class
     */
    private Student s;
    /**
     * Linearlayout attribute
     */
    private LinearLayout noresult;
    private boolean studentSide = true;


    /**
     * activity on create method
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrow_history);


        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        s = (Student) getIntent().getSerializableExtra("data");

        if(s!=null){
            userId = s.getKey();
            studentSide = false;
        }
        else{
            userId = auth.getCurrentUser().getUid();

        }


        noresult = findViewById(R.id.noresult);
        backbtn = findViewById(R.id.backbtn);
        listView = findViewById(R.id.listview);
        Search = findViewById(R.id.search);
        noresult.setVisibility(View.GONE);

        Search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(borrowadapter.getCount()==0){
                    noresult.setVisibility(View.VISIBLE);
                }
                else{
                    noresult.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                borrowadapter.getFilter().filter(s);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        database.getReference().child("Reserve").orderByChild("userId").equalTo(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                reserveList.clear();
                for(DataSnapshot snap : snapshot.getChildren()){
                    Reserve reserve = snap.getValue(Reserve.class);
                    if(studentSide) {
                        reserve.setReserveId(snap.getKey());
                    }
                    reserveList.add(reserve);

                }

                if(reserveList.size() == 0){
                    noresult.setVisibility(View.VISIBLE);
                }
                else {
                    Toast.makeText(BorrowHistory.this, String.valueOf(reserveList.size()), Toast.LENGTH_LONG).show();
                    borrowadapter = new BorrowListAdapter(BorrowHistory.this, reserveList);
                    listView.setAdapter(borrowadapter);
                    noresult.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}