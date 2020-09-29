package com.cegep.lanbow.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cegep.lanbow.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StudentRegister extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private ImageView backbtn;
    private LinearLayout Step1,Step2;
    private Button next,signup;

    private EditText nameInput,emailInput,passInput,idInput,phoneInput,addressInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_register);

        mAuth = FirebaseAuth.getInstance();

        Step1 = findViewById(R.id.Step1);
        Step2 = findViewById(R.id.Step2);
        Step1.setVisibility(View.VISIBLE);
        Step2.setVisibility(View.GONE);

        next = findViewById(R.id.next);
        signup = findViewById(R.id.studentSignup);
        nameInput = findViewById(R.id.nameInput);
        emailInput = findViewById(R.id.emailInput);
        passInput = findViewById(R.id.passInput);

        idInput = findViewById(R.id.idInput);
        phoneInput = findViewById(R.id.phoneInput);
        addressInput = findViewById(R.id.addressInput);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!nameInput.getText().equals("")&&!emailInput.getText().equals("")&&!passInput.getText().equals("")){
                    Step1.setVisibility(View.GONE);
                    Step2.setVisibility(View.VISIBLE);
                }
                else{

                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!idInput.getText().equals("")&&!phoneInput.getText().equals("")&&!addressInput.getText().equals("")){
                    mAuth.createUserWithEmailAndPassword(emailInput.getText().toString(), passInput.getText().toString())
                            .addOnCompleteListener(StudentRegister.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Toast.makeText(StudentRegister.this, "success",
                                                Toast.LENGTH_SHORT).show();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(StudentRegister.this, "Authentication failed."+task.getException(),
                                                Toast.LENGTH_SHORT).show();

                                    }

                                    // ...
                                }
                            });
                }

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
