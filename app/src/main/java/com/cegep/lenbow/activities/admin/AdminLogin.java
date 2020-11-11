package com.cegep.lenbow.activities.admin;

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
import com.cegep.lenbow.models.Admin;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Admin login activity
 * @author dipmal lakhani
 * @author Sai Akshay
 * @author Gopichand
 * @author HarshaVardhan
 * @author Vinay
 * @author prashant
 * @author Amandeep singh
 */


public class AdminLogin extends AppCompatActivity {

    private ImageView backbtn;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private EditText emailInput, passInput;
    private Button adminLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        database = FirebaseDatabase.getInstance();

        backbtn = findViewById(R.id.backbtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        emailInput = findViewById(R.id.emailInput);
        passInput = findViewById(R.id.passInput);
        adminLogin = findViewById(R.id.adminLogin);

        adminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.getReference().child("Admin").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Admin admin = snapshot.getValue(Admin.class);
                        if (emailInput.getText().toString().equals(admin.getUser_name()) && passInput.getText().toString().equals(admin.getUser_pass())) {
                            Toast.makeText(AdminLogin.this, "sucess", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(AdminLogin.this, AdminHome.class));
                            finish();
                        } else {
                            Toast.makeText(AdminLogin.this, "wrong credentials", Toast.LENGTH_LONG).show();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(AdminLogin.this, error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });
            }
        });

    }
}