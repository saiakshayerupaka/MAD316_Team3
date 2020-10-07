package com.cegep.lanbow.activities.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cegep.lanbow.R;
import com.cegep.lanbow.models.Student;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfile extends AppCompatActivity {

    private TextView name,email,studentid,phone,address;
    private Button block;
    private ImageView back;
    private FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile2);

        database = FirebaseDatabase.getInstance();


        final Student s = (Student) getIntent().getSerializableExtra("data");



        back = findViewById(R.id.backbtn);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        name = findViewById(R.id.username);
        email = findViewById(R.id.email);
        studentid = findViewById(R.id.studentid);
        phone = findViewById(R.id.phonenumber);
        address = findViewById(R.id.address);

        name.setText(s.getName());
        email.setText(s.getEmail());
        studentid.setText(s.getStudentId());
        phone.setText(s.getPhonenumber());
        address.setText(s.getAddress());

        block = findViewById(R.id.block);

        database.getReference().child("Users").child(s.getKey()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Student s = snapshot.getValue(Student.class);

                if(s.getProfileStatus().equals("active")){
                    block.setText("Block User");
                }
                else{
                    block.setText("Unblock User");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        block.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                database.getReference().child("Users").child(s.getKey()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        Student s = snapshot.getValue(Student.class);

                        if(s.getProfileStatus()!=null && s.getProfileStatus().equals("active")) {
                            database.getReference().child("Users").child(s.getKey()).child("profileStatus").setValue("block");
                        }
                        else if(s.getProfileStatus()!=null && s.getProfileStatus().equals("block")){
                            database.getReference().child("Users").child(s.getKey()).child("profileStatus").setValue("active");
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });


    }
}