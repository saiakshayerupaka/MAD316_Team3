package com.cegep.lanbow.activities.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.cegep.lanbow.R;
import com.cegep.lanbow.adapters.ItemlistAdapter;
import com.cegep.lanbow.models.Item;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Items extends AppCompatActivity {

    private Listview itemlist;
    private FirebaseDatabase database;
    private List<Item> items = new ArrayList<>();
    private ItemlistAdapter itemlistAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        itemlist = findViewById(R.id.item_list);

        database = FirebaseDatabase.getInstance();

        database.getReference().child("Items").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot snap : snapshot.getChildren()){
                    Items item = snap.getValue(Item.class);
                    items.add(item);

                    itemlistAdapter = new ItemlistAdapter(Items.this,items);
                    itemlist.setAdapter(itemlistAdapter);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}