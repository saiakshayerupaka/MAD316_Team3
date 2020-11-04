package com.cegep.lenbow.activities.student;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.cegep.lenbow.R;
import com.cegep.lenbow.models.Student;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Pattern;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class EditProfile extends AppCompatActivity implements Validator.ValidationListener {

    private ImageView backbtn;
    @Email
    @NotEmpty
    private EditText email;
    @NotEmpty
    private EditText name;
    @Pattern(regex = "^[0-9-]+$", message = "Enter valid phone number")
    private EditText phone;
    @NotEmpty
    private EditText address;
    private Button save;
    private Validator validator;
    private Uri uploadFilePath;
    private String profileurl;

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
                    upload();
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
        Toast.makeText(EditProfile.this, "Validation success", Toast.LENGTH_LONG).show();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("email", email.getText().toString());
if(!email.getText().equals(s.getEmail())){
    auth.getCurrentUser().updateEmail(email.getText().toString());
}
        childUpdates.put("name", name.getText().toString());
        childUpdates.put("address", address.getText().toString());
        childUpdates.put("phonenumber", phone.getText().toString());
        if(!profileurl.equals("")) {
            childUpdates.put("profilepic", profileurl);
        }

        database.getReference().child("Users").child(auth.getCurrentUser().getUid()).updateChildren(childUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(EditProfile.this, "Profile updated", Toast.LENGTH_LONG).show();
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
        }
    }

    private void upload(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                234);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 234 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            uploadFilePath = data.getData();
            uploadImage(uploadFilePath);


        }
    }

    private void uploadImage(Uri uploadFilePath) {

        final ProgressDialog progressDialog
                = new ProgressDialog(this);
        progressDialog.setTitle("Uploading...");
        progressDialog.show();

        StorageReference ref = storage.getReference().child("profilepic/" + UUID.randomUUID().toString());


        ref.putFile(uploadFilePath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressDialog.dismiss();
                        profileurl = taskSnapshot.getMetadata().getPath();
                        Toast.makeText(EditProfile.this, "Image Uploaded!!" + taskSnapshot.getMetadata().getPath()
                                , Toast.LENGTH_SHORT).show();
                    }
                })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        progressDialog.dismiss();
                        Toast.makeText(EditProfile.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnProgressListener(
                        new OnProgressListener<UploadTask.TaskSnapshot>() {


                            @Override
                            public void onProgress(
                                    UploadTask.TaskSnapshot taskSnapshot) {
                                double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                progressDialog.setMessage("Uploaded " + (int) progress + "%");
                            }
                        });
    }
}