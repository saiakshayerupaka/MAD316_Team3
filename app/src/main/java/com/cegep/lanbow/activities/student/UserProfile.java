package com.cegep.lanbow.activities.student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cegep.lanbow.R;
import com.cegep.lanbow.models.Student;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

public class UserProfile extends AppCompatActivity {

    private TextView name,email,studentid,phone,address;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private Button resetpassword;
    private Button editProfile;
    private ImageView backbtn;
    private Student student;
    private ImageView profilepic;
    private FirebaseStorage storage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

        backbtn = findViewById(R.id.backbtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        profilepic = findViewById(R.id.profilePic);


        editProfile = findViewById(R.id.editProfile);


        name = findViewById(R.id.username);
        email = findViewById(R.id.email);
        studentid = findViewById(R.id.studentid);
        phone = findViewById(R.id.phonenumber);
        address = findViewById(R.id.address);

        resetpassword = findViewById(R.id.changePass);

        resetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                auth.sendPasswordResetEmail(auth.getCurrentUser().getEmail());
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(UserProfile.this);
                alertDialogBuilder.setMessage("Password reset link has been set to your registered email.");
                alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();

                alertDialog.show();
            }
        });

        email.setText(auth.getCurrentUser().getEmail());

        database.getReference().child("Users").child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                student = snapshot.getValue(Student.class);
                if(student.getProfilepic()!=null){
                    profilepic.setColorFilter(null);
                    storage.getReference().child(student.getProfilepic()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Glide.with(UserProfile.this).load(uri).into(profilepic);
                        }
                    });
                }
                name.setText(student.getName().toString());
                studentid.setText(student.getStudentId().toString());
                address.setText(student.getAddress().toString());
                phone.setText(student.getPhonenumber().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfile.this,EditProfile.class);
                intent.putExtra("data",student);
                startActivity(intent);
            }
        });




    }
}