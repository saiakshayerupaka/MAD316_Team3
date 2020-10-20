package com.cegep.lanbow.activities;

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

import com.cegep.lanbow.R;
import com.cegep.lanbow.adapters.BorrowListAdapter;
import com.cegep.lanbow.models.Reserve;
import com.cegep.lanbow.models.Student;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BorrowHistory extends AppCompatActivity {

    private ListView listView;
    private EditText Search;
    private ImageView backbtn;
    private FirebaseDatabase database;
    private FirebaseAuth auth;
    private List<Reserve> reserveList = new ArrayList<>();
    private BorrowListAdapter borrowadapter;
    private String userId;
    private Student s;
    private LinearLayout noresult;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrow_history);


        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        s = (Student) getIntent().getSerializableExtra("data");

        if(s!=null){
            userId = s.getKey();
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
                    reserveList.add(snap.getValue(Reserve.class));
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