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

import com.bumptech.glide.Glide;
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
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UpdateItem extends AppCompatActivity {

    private ImageView itemImg;
    private Uri filePath;
    private EditText itemName,itemDescription;
    private Button upload;
    private Button updateItem;
    private Spinner spinner;
    private String imgurl;

    private FirebaseStorage storage;
    private FirebaseDatabase database;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_item);

        database = FirebaseDatabase.getInstance();

        final Item item = (Item) getIntent().getSerializableExtra("data");
        storage = FirebaseStorage.getInstance();
        imgurl = item.getItemUrl();




        itemImg = findViewById(R.id.itemImg);
        itemName = findViewById(R.id.itemTitle);
        itemDescription = findViewById(R.id.itemDes);
        spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.item_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(adapter.getPosition(item.getItemType()));

        updateItem = findViewById(R.id.UpdateItem);

        storage.getReference().child(item.getItemUrl()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(UpdateItem.this).load(uri).into(itemImg);
            }
        });


        itemName.setText(item.getItemName());
        itemDescription.setText(item.getItemDes());


        upload = findViewById(R.id.upload);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                upload();
            }
        });


        updateItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> childUpdates = new HashMap<>();
                childUpdates.put("itemName", itemName.getText().toString());

                childUpdates.put("itemType", spinner.getSelectedItem());
                childUpdates.put("itemDes", itemDescription.getText().toString());
                childUpdates.put("itemUrl", imgurl);
                childUpdates.put("updatedOn",new Date().getTime());


                database.getReference().child("Items").child(item.getItemId()).updateChildren(childUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(UpdateItem.this, "Item updated", Toast.LENGTH_LONG).show();
                        }
                    }
                });


            }
        });
    }

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



    private void uploadImage(Uri filePath) {

        final ProgressDialog progressDialog
                = new ProgressDialog(this);
        progressDialog.setTitle("Uploading...");
        progressDialog.show();

        StorageReference ref = storage.getReference().child("images/" + UUID.randomUUID().toString());



        ref.putFile(filePath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressDialog.dismiss();
                        imgurl = taskSnapshot.getMetadata().getPath();
                        Toast.makeText(UpdateItem.this, "Image Uploaded!!"+                                    taskSnapshot.getMetadata().getPath()
                                , Toast.LENGTH_SHORT).show();
                    }
                })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        progressDialog.dismiss();
                        Toast.makeText(UpdateItem.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
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