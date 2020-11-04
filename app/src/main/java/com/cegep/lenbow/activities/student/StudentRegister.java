package com.cegep.lenbow.activities.student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cegep.lenbow.R;
import com.cegep.lenbow.models.Student;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.mobsandgeeks.saripaar.annotation.Pattern;

import java.util.List;

public class StudentRegister extends AppCompatActivity implements Validator.ValidationListener {

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private ImageView backbtn;
    private LinearLayout Step1,Step2;
    private Button next,signup;
    @NotEmpty
    private EditText nameInput;
    @NotEmpty
    @Email
    private EditText emailInput;
    @Password(min = 6, scheme = Password.Scheme.ALPHA_NUMERIC_MIXED_CASE_SYMBOLS)
    private EditText passInput;

    @NotEmpty(message = "Student id shouldn't be empty")
    @Length(min = 7,max = 7,message = "Student Id should be 7 digit")
    private EditText idInput;

    @NotEmpty(message = "Phone number shouldn't be empty")
    @Pattern(regex = "^[0-9-]+$",message = "Enter valid phone number")
    private EditText phoneInput;
    @NotEmpty
    private EditText addressInput;

    private Validator validator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_register);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        validator = new Validator(this);
        validator.setValidationListener(this);

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
                validator.validate();

            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validator.validate();

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

    @Override
    public void onValidationSucceeded() {
        if(Step1.getVisibility()== View.VISIBLE){
            Step1.setVisibility(View.GONE);
            Step2.setVisibility(View.VISIBLE);
        }
        else{
            RegisterUser();
        }
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }

    public void RegisterUser(){
        mAuth.createUserWithEmailAndPassword(emailInput.getText().toString(), passInput.getText().toString())
                .addOnCompleteListener(StudentRegister.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();

                            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(StudentRegister.this, "Email Verification link Sent!!"+task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
                            Student student = new Student(nameInput.getText().toString(),user.getEmail(),idInput.getText().toString(),phoneInput.getText().toString(),addressInput.getText().toString(),"activel");

                            database.getReference().child("Users").child(mAuth.getCurrentUser().getUid()).setValue(student).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    startActivity(new Intent(StudentRegister.this,UserHome.class));
                                    finish();
                                }
                            });
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
