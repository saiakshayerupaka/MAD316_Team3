package com.cegep.lanbow.activities.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.cegep.lanbow.R;
import com.cegep.lanbow.models.Item;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateItem extends AppCompatActivity {

    private ImageView itemImg;
    private EditText itemName,itemDescription;
    private Button upload;
    private Button updateItem;
    private Spinner spinner;

    private FirebaseDatabase database;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_item);

        Item item = (Item) getIntent().getSerializableExtra("data");

        itemImg = findViewById(R.id.itemImg);
        itemName = findViewById(R.id.itemTitle);
        itemDescription = findViewById(R.id.itemDes);

        updateItem = findViewById(R.id.UpdateItem);

        updateItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}