package com.cegep.lenbow.activities.student;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.cegep.lenbow.R;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Password reset activity
 * @author dipmal lakhani
 * @author Sai Akshay
 * @author Gopichand
 * @author HarshaVardhan
 * @author Vinay
 * @author prashant
 * @author Amandeep singh
 */

public class PasswordReset extends AppCompatActivity {
    /**
     * PasswordReset password EditText attribute
     * PasswordReset confirmpass EditText attribute
     */
    private EditText pass,confirmpass;
    /**
     * PasswordReset reset Button attribute
     */
    private Button reset;
    /**
     * PasswordReset back Button attribute
     */
    private ImageView back;
    /**
     * FirebaseAuth authentication variable
     */
    private FirebaseAuth auth;

    /**
     * activity on create method
     * @param PasswordReset
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);

        back = findViewById(R.id.backbtn);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        pass = findViewById(R.id.pass);
        confirmpass = findViewById(R.id.confirmpass);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!pass.getText().equals("")){
                    if(pass.getText().toString().equals(confirmpass.getText().toString())){


                    }
                }
            }
        });
    }
}
