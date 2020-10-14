package com.cegep.lanbow.activities.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.cegep.lanbow.R;
import com.cegep.lanbow.adapters.UserlistAdapter;
import com.cegep.lanbow.models.Student;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SearchUsers extends AppCompatActivity {

    private ListView listView;
    private FirebaseDatabase database;
    private List<Student> students = new ArrayList<>();
    private EditText search;
    private UserlistAdapter userlistAdapter;
    private ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_users);

        database = FirebaseDatabase.getInstance();

        back = findViewById(R.id.backbtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        listView = findViewById(R.id.listview);
        search = findViewById(R.id.search);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                userlistAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        database.getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot snap: snapshot.getChildren()){

                    Student s = snap.getValue(Student.class);
                    s.setKey(snap.getKey());

                    Log.d("ew",s.getKey());

                    students.add(s);
                }

                if(students!=null) {
                    userlistAdapter = new UserlistAdapter(SearchUsers.this, students);
                    listView.setAdapter(userlistAdapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent in = new Intent(SearchUsers.this,UserProfile.class);


                            in.putExtra("data", (Serializable) students.get(position));
                            startActivity(in);
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}
