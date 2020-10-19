package com.cegep.lanbow.activities.student;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.cegep.lanbow.R;
import com.cegep.lanbow.models.Student;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Pattern;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditProfile extends AppCompatActivity implements Validator.ValidationListener {

    private ImageView backbtn;
    @Email
    @NotEmpty
    private EditText email;
    @NotEmpty
    private EditText name;
    @Pattern(regex = "^[0-9-]+$",message = "Enter valid phone number")
    private EditText phone;
    @NotEmpty
    private EditText address;
    private Button save;
    private Validator validator;
    private Uri uploadFilePath;

    private FirebaseStorage storage;

    private FirebaseDatabase database;
    private FirebaseAuth auth;
    private Student s;

    private Button uploadpic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();

        s = (Student) getIntent().getSerializableExtra("data");

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

        uploadpic = findViewById(R.id.uploadpic);

        uploadpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        email.setText(s.getEmail());
        phone.setText(s.getPhonenumber());
        name.setText(s.getName());
        address.setText(s.getAddress());

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
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("email", email.getText().toString());

        childUpdates.put("name", name.getText().toString());
        childUpdates.put("address",address.getText().toString());
        childUpdates.put("phonenumber",phone.getText().toString());

        database.getReference().child("Users").child(auth.getCurrentUser().getUid()).updateChildren(childUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(EditProfile.this,"Profile updated",Toast.LENGTH_LONG).show();
                }
            }
        });

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 234 && resultCode == RESULT_OK && data != null && data.getData() != null) {

           uploadFilePath = data.getData();
            uploadImage(uploadFilePath);



        }
    }

    private void uploadImage(String uploadFilePath) {
    }
}