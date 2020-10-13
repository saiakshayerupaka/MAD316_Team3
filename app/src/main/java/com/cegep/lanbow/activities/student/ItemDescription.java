package com.cegep.lanbow.activities.student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cegep.lanbow.R;
import com.cegep.lanbow.models.Item;

public class ItemDescription extends AppCompatActivity {

    private TextView ItemName;
    private TextView ItemDes;
    private Button Reserve;
    private ImageView backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_description);

        final Item item = (Item) getIntent().getSerializableExtra("data");

        ItemName = findViewById(R.id.itemName);
        ItemDes = findViewById(R.id.itemDes);
        Reserve = findViewById(R.id.reserve);
        backbtn = findViewById(R.id.backbtn);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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