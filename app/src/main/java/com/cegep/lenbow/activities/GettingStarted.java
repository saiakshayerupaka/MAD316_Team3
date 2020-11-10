package com.cegep.lenbow.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cegep.lenbow.R;
import com.cegep.lenbow.activities.admin.AdminLogin;
import com.cegep.lenbow.activities.student.StudentLogin;
import com.cegep.lenbow.activities.student.StudentRegister;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Getting started Screen
 * @author dipmal lakhani
 *
 */

public class GettingStarted extends AppCompatActivity {

    /**
     * Button view attribute
     */
    private Button studentLogin;
    /**
     * Textview attribute
     */
    private TextView studentRegister;
    /**
     * Button view attribute
     */
    private Button adminLogin;
    /**
     * Firebase authentication object
     */
    private FirebaseAuth auth;

    /**
     * activity on create method
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getting_started);

        auth = FirebaseAuth.getInstance();

//        if(auth.getCurrentUser()!=null){
//            startActivity(new Intent(GettingStarted.this, UserProfile.class));
//        }



        studentLogin = findViewById(R.id.studentLogin);
        studentRegister = findViewById(R.id.studentRegister);
        adminLogin = findViewById(R.id.adminLogin);


        studentLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GettingStarted.this, StudentLogin.class));
            }
        });

        studentRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GettingStarted.this, StudentRegister.class));
            }
        });
        adminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GettingStarted.this, AdminLogin.class));
            }
        });

    }
}
