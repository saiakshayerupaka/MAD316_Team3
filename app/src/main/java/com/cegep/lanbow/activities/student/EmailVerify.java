package com.cegep.lanbow.activities.student;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cegep.lanbow.R;
import com.google.firebase.auth.FirebaseAuth;

public class EmailVerify extends AppCompatActivity {

    public Button resend;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_verify);

        auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser().isEmailVerified()){
            finish();
        }

        resend = findViewById(R.id.resend);
        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.getCurrentUser().sendEmailVerification();
                Toast.makeText(EmailVerify.this,"Email Verication link sent!!",Toast.LENGTH_LONG).show();

            }
        });


    }
}