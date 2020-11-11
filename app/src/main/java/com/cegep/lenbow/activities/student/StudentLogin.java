package com.cegep.lenbow.activities.student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.cegep.lenbow.R;
import com.cegep.lenbow.models.Student;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Student Login activity
 * @author dipmal lakhani
 * @author Sai Akshay
 * @author Gopichand
 * @author HarshaVardhan
 * @author Vinay
 * @author prashant
 * @author Amandeep singh
 */

public class StudentLogin extends AppCompatActivity {
    /**
     * StudentLogin back ImageView attribute
     */
    private ImageView backbtn;
    private FirebaseAuth mAuth;
    /**
     * StudentLogin emailInput EditText attribute
     * StudentLogin passInput EditText attribute
     */
    private EditText emailInput, passInput;
    /**
     * StudentLogin studentLogin Button attribute
     */
    private Button studentLogin;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        if(mAuth.getCurrentUser()!=null){

            database.getReference().child("Users").child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Student s = snapshot.getValue(Student.class);
                    if(s.getProfileStatus().equals("block")){
                        Toast.makeText(StudentLogin.this,"Your Account is Blocked!! Please contact Admin",Toast.LENGTH_LONG).show();
                        finish();
                    }
                    else{
                        startActivity(new Intent(StudentLogin.this, UserHome.class));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

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
                            database.getReference().child("Users").child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    Student s = snapshot.getValue(Student.class);
                                    if(s.getProfileStatus().equals("block")){
                                        Toast.makeText(StudentLogin.this,"Your Account is Blocked!! Please contact Admin",Toast.LENGTH_LONG).show();
                                        finish();
                                    }
                                    else{
                                        startActivity(new Intent(StudentLogin.this, UserHome.class));
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

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
