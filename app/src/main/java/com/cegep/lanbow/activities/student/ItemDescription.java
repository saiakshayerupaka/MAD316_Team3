package com.cegep.lanbow.activities.student;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cegep.lanbow.R;
import com.cegep.lanbow.models.Item;

public class ItemDescription extends AppCompatActivity {

    private TextView ItemName;
    private TextView ItemDes;
    private Button Reserve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_description);

        Item item = (Item) getIntent().getSerializableExtra("data");

        ItemName = findViewById(R.id.itemName);
        ItemDes = findViewById(R.id.itemDes);
        Reserve = findViewById(R.id.reserve);

        ItemName.setText(item.getItemName());
        ItemDes.setText(item.getItemDes());

        Reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



    }
}