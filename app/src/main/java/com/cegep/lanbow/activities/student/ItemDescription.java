package com.cegep.lanbow.activities.student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cegep.lanbow.R;
import com.cegep.lanbow.models.Item;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;

public class  ItemDescription extends AppCompatActivity {

    private TextView ItemName;
    private TextView ItemDes;
    private TextView ItemType;
    private Button Reserve;
    private ImageView Itemimg;
    private FirebaseStorage firebaseStorage;
    private ImageView backbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_description);

        final Item item = (Item) getIntent().getSerializableExtra("data");

        firebaseStorage = FirebaseStorage.getInstance();

        ItemName = findViewById(R.id.itemName);
        ItemDes = findViewById(R.id.itemDes);
        Reserve = findViewById(R.id.reserve);
        ItemType = findViewById(R.id.item_type);

        ItemType.setText(item.getItemType());

        Itemimg = findViewById(R.id.itemImg);

        backbtn = findViewById(R.id.backbtn);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        firebaseStorage.getReference().child(item.getItemUrl()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(ItemDescription.this).load(uri).into(Itemimg);
            }
        });

        ItemName.setText(item.getItemName());
        ItemDes.setText(item.getItemDes());

        Reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(ItemDescription.this,Reservation.class);
                in.putExtra("data",item);
                startActivity(in);

            }
        });



    }
}