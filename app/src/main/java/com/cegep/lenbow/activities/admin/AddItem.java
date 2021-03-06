package com.cegep.lenbow.activities.admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.cegep.lenbow.R;
import com.cegep.lenbow.models.Item;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

/**
 * Add Item activity for Admin
 * @author dipmal lakhani
 * @author Sai Akshay
 * @author Gopichand
 * @author HarshaVardhan
 * @author Vinay
 * @author prashant
 * @author Amandeep singh
 */


public class AddItem extends AppCompatActivity {
    /**
     * filepath URI object
     */
    private Uri filePath;
    /**
     * Firebase storage object
     */
    private FirebaseStorage storage;
    /**
     * Firebase storage reference object
     */
    private StorageReference storageReference;
    /**
     * upload button view
     */
    private Button upload;
    /**
     * Firebase database object
     */
    private FirebaseDatabase database;
    /**
     * Item Image imageview
     */
    private ImageView itemImg;

    /**
     * Item url
     */
    private String imgurl;
    /**
     * Item Title,Item Description
     */
    private EditText itemTitle,itemDescription;
    /**
     * Add Item button view
     */
    private Button AddItem;
    /**
     * Spinner view
     */
    private Spinner spinner;
    /**
     * Item type
     */
    private String itemType;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 234 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePath = data.getData();
            uploadImage(filePath);

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(
                                getContentResolver(),
                                filePath);
                itemImg.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * activity on create method
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        database = FirebaseDatabase.getInstance();

        itemTitle = findViewById(R.id.itemTitle);
        itemDescription = findViewById(R.id.itemDes);
        AddItem = findViewById(R.id.AddItem);
        itemImg = findViewById(R.id.itemImg);

      spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.item_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);





        AddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(imgurl!=null && !itemTitle.getText().toString().equals("") && !itemDescription.getText().toString().equals(""))

                database.getReference().child("Items").child(UUID.randomUUID().toString()).setValue(new Item(imgurl,itemTitle.getText().toString(),itemDescription.getText().toString(),spinner.getSelectedItem().toString(),new Date().getTime())).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(AddItem.this,"Item Added successfully",Toast.LENGTH_LONG).show();
                            finish();
                        }
                        else{
                            Toast.makeText(AddItem.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();

                        }

                    }
                });
            }
        });




        upload = findViewById(R.id.upload);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imgurl = null;
                upload();
            }
        });


    }

    /**
     * This method will start new Intent to select Image
     */

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


    /**
     * This method will upload the image to firebase storage
     * @param filePath
     */

    private void uploadImage(Uri filePath) {

            final ProgressDialog progressDialog
                    = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());



            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    progressDialog.dismiss();
                                    imgurl = taskSnapshot.getMetadata().getPath();
                                    Toast.makeText(AddItem.this, "Image Uploaded!!"+                                    taskSnapshot.getMetadata().getPath()
                                            , Toast.LENGTH_SHORT).show();
                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            progressDialog.dismiss();
                            Toast.makeText(AddItem.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
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
