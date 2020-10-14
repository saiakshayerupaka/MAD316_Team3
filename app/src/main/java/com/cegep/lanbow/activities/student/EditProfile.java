package com.cegep.lanbow.activities.student;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.cegep.lanbow.R;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Pattern;

import java.util.List;

public class EditProfile extends AppCompatActivity implements Validator.ValidationListener {

    private ImageView backbtn;
    @Email
    private EditText email;
    private EditText name;
    @Pattern(regex = "^[0-9-]+$",message = "Enter valid phone number")
    private EditText phone;
    private EditText address;
    private Button save;
    private Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        backbtn = findViewById(R.id.backbtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        validator = new Validator(this);
        validator.setValidationListener(this);

        email = findViewById(R.id.emailInput);
        name = findViewById(R.id.nameInput);
        phone = findViewById(R.id.phoneInput);
        address = findViewById(R.id.addressInput);

        save = findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               validator.validate();
            }
        });


    }

    @Override
    public void onValidationSucceeded() {
        Toast.makeText(EditProfile.this,"Validation success",Toast.LENGTH_LONG).show();

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
        }    }
}