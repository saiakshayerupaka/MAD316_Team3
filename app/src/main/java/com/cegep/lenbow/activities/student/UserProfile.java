package com.cegep.lenbow.activities.student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cegep.lenbow.R;
import com.cegep.lenbow.models.Student;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

/**
 * User profile activity
 * @author dipmal lakhani
 * @author Sai Akshay
 * @author Gopichand
 * @author HarshaVardhan
 * @author Vinay
 * @author prashant
 * @author Amandeep singh
 */

public class UserProfile extends AppCompatActivity {
    /**
     * Textview name,email,studentid,phone,address inputattribute
     */

    private TextView name,email,studentid,phone,address;
    /**
     * firebase database authentication
     */
    private FirebaseAuth auth;
    /**
     * firebase database object
     */
    private FirebaseDatabase database;
    /**
     * buuton view attribute
     */
    private Button resetpassword;
    /**
     * button view attribute
     */
    private Button editProfile;
    /**
     * imageview backbtn attribute
     */
    private ImageView backbtn;
    private Student student;
    /**
     * imageview profilepic
     */
    private ImageView profilepic;
    /**
     * firebase databse storage
     */
    private FirebaseStorage storage;

    /**
     * activity oncreate activity userprofile
     * @param savedInstanceState
     */

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
                else {
                    profilepic.setColorFilter(getResources().getColor(R.color.light));
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

/**
 * clicklistner for editprofile
 */
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