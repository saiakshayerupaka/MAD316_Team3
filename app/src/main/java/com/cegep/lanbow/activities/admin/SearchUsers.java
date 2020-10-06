package com.cegep.lanbow.activities.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.cegep.lanbow.R;
import com.cegep.lanbow.adapters.UserlistAdapter;
import com.cegep.lanbow.models.Student;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchUsers extends AppCompatActivity {

    private ListView listView;
    private FirebaseDatabase database;
    private List<Student> students = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_users);

        database = FirebaseDatabase.getInstance();

        listView = findViewById(R.id.listview);

        database.getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot snap: snapshot.getChildren()){
                    Student s = snap.getValue(Student.class);
                    students.add(s);
                }

                if(students!=null) {
                    UserlistAdapter userlistAdapter = new UserlistAdapter(SearchUsers.this, students);
                    listView.setAdapter(userlistAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}
