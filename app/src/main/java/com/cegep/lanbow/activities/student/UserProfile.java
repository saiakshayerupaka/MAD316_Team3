package com.cegep.lanbow.activities.student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cegep.lanbow.R;
import com.cegep.lanbow.models.Student;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfile extends AppCompatActivity {

    private TextView name,email,studentid,phone,address;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private Button editprofile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();


        name = findViewById(R.id.username);
        email = findViewById(R.id.email);
        studentid = findViewById(R.id.studentid);
        phone = findViewById(R.id.phonenumber);
        address = findViewById(R.id.address);

        editprofile = findViewById(R.id.editProfile);

        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfile.this,EditProfile.class);
                startActivity(intent);
            }
        });


        email.setText(auth.getCurrentUser().getEmail());

        database.getReference().child("Users").child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Student student = snapshot.getValue(Student.class);
                name.setText(student.getName().toString());
                studentid.setText(student.getStudentId().toString());
                address.setText(student.getAddress().toString());
                phone.setText(student.getPhonenumber().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}