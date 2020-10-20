package com.cegep.lanbow.activities.student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.cegep.lanbow.R;
import com.cegep.lanbow.adapters.BorrowListAdapter;
import com.cegep.lanbow.models.Reserve;
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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrow_history);


        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();


        backbtn = findViewById(R.id.backbtn);
        listView = findViewById(R.id.listview);
        Search = findViewById(R.id.search);
        Toast.makeText(BorrowHistory.this,auth.getCurrentUser().getUid(),Toast.LENGTH_LONG).show();

        database.getReference().child("Reserve").orderByChild("userId").equalTo(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                reserveList.clear();
                for(DataSnapshot snap : snapshot.getChildren()){
                    reserveList.add(snap.getValue(Reserve.class));
                }
                Toast.makeText(BorrowHistory.this,String.valueOf(reserveList.size()),Toast.LENGTH_LONG).show();
                borrowadapter = new BorrowListAdapter(BorrowHistory.this,reserveList);
                listView.setAdapter(borrowadapter);

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