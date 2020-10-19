package com.cegep.lanbow.activities.student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.cegep.lanbow.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class StudentLogin extends AppCompatActivity {

    private ImageView backbtn;
    private FirebaseAuth mAuth;
    private EditText emailInput, passInput;
    private Button studentLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);

        mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser()!=null){
            startActivity(new Intent(StudentLogin.this, UserHome.class));
        }

        emailInput = findViewById(R.id.emailInput);
        passInput = findViewById(R.id.passInput);
        studentLogin = findViewById(R.id.studentLogin);

        studentLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAuth.signInWithEmailAndPassword(emailInput.getText().toString(), passInput.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if(!mAuth.getCurrentUser().isEmailVerified()){
                                mAuth.getCurrentUser().sendEmailVerification();
                            }
                            startActivity(new Intent(StudentLogin.this, UserHome.class));
                        } else {
                            Toast.makeText(StudentLogin.this, "Wrong credential", Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });

        backbtn = findViewById(R.id.backbtn);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
