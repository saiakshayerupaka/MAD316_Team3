package com.cegep.lenbow.activities.student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cegep.lenbow.R;
import com.cegep.lenbow.models.Item;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * Item description activity
 * @author dipmal lakhani
 */

public class  ItemDescription extends AppCompatActivity {
    /**
     * Item name Input attribute
     */

    private TextView ItemName;
    /**
     *Item Description
     */
    private TextView ItemDes;
    private TextView ItemType;
    private TextView ItemUpdatedOn;
    private Button Reserve;
    private ImageView Itemimg;
    private FirebaseStorage firebaseStorage;
    private ImageView backbtn;
    private SimpleDateFormat df;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_description);

        final Item item = (Item) getIntent().getSerializableExtra("data");

        firebaseStorage = FirebaseStorage.getInstance();

        df = new SimpleDateFormat("MMM, d yyyy");


        ItemName = findViewById(R.id.itemName);
        ItemDes = findViewById(R.id.itemDes);
        Reserve = findViewById(R.id.reserve);
        ItemType = findViewById(R.id.item_type);

        ItemUpdatedOn = findViewById(R.id.itemUpdatedOn);

        if(item.getUpdatedOn()!=0){
            ItemUpdatedOn.setText(df.format(new Date(item.getUpdatedOn())));
        }

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
                finish();

            }
        });



    }
}