package com.cegep.lanbow.activities.student;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cegep.lanbow.R;
import com.cegep.lanbow.activities.admin.AdminLogin;
import com.google.firebase.auth.FirebaseAuth;

public class GettingStarted extends AppCompatActivity {

    private Button studentLogin;
    private TextView studentRegister;
    private Button adminLogin;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getting_started);

        auth = FirebaseAuth.getInstance();

        if(auth.getCurrentUser()!=null){
            startActivity(new Intent(GettingStarted.this,UserProfile.class));
        }



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
