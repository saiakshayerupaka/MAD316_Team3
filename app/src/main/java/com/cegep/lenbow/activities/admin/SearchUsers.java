package com.cegep.lenbow.activities.admin;

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
import android.widget.LinearLayout;
import android.widget.ListView;

import com.cegep.lenbow.R;
import com.cegep.lenbow.adapters.UserlistAdapter;
import com.cegep.lenbow.models.Student;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Activity for List of Users
 * @author dipmal lakhani
 * @author Sai Akshay
 * @author Gopichand
 * @author HarshaVardhan
 * @author Vinay
 * @author prashant
 * @author Amandeep singh
 */


public class SearchUsers extends AppCompatActivity {

    /**
     * List view to showcase list of users
     */
    private ListView listView;
    /**
     * Firebase database object
     */
    private FirebaseDatabase database;
    /**
     * List of Student
     */
    private List<Student> students = new ArrayList<>();
    /**
     * Edittext view for search input
     */
    private EditText search;
    /**
     * User list adapter object
     */
    private UserlistAdapter userlistAdapter;
    /**
     * back btn imageview
     */
    private ImageView back;
    /**
     * Layout view
     */
    private LinearLayout noresult;


    /**
     * activity oncreate method
     * @param savedInstanceState
     */
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
        noresult = findViewById(R.id.noresult);

        noresult.setVisibility(View.GONE);


        listView = findViewById(R.id.listview);
        search = findViewById(R.id.search);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                userlistAdapter.getFilter().filter(s);
                if(userlistAdapter.getCount()==0){
                    noresult.setVisibility(View.VISIBLE);
                }
                else{
                    noresult.setVisibility(View.GONE);
                }
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

                if(students.size()==0){
                    noresult.setVisibility(View.VISIBLE);
                }
                else{
                    noresult.setVisibility(View.GONE);
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
