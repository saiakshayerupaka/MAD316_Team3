package com.cegep.lanbow.activities.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

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

    private ListView itemlist;
    private FirebaseDatabase database;
    private List<Item> items = new ArrayList<>();
    private ItemlistAdapter itemlistAdapter;
    private TextView additem;
    private ImageView back;
    private EditText search;
    private LinearLayout noresult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        itemlist = findViewById(R.id.item_list);

        database = FirebaseDatabase.getInstance();
        noresult = findViewById(R.id.noresult);
        noresult.setVisibility(View.GONE);


        additem = findViewById(R.id.additem);
        additem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Items.this,AddItem.class));
            }
        });
        back = findViewById(R.id.backbtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        search = findViewById(R.id.search);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(itemlistAdapter.getCount()==0){
                    noresult.setVisibility(View.VISIBLE);
                }
                else{
                    noresult.setVisibility(View.GONE);
                }

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
itemlistAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        database.getReference().child("Items").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                items.clear();

                for(DataSnapshot snap : snapshot.getChildren()){
                    Item item = snap.getValue(Item.class);
                    item.setItemId(snap.getKey());
                    items.add(item);

                }

                if(items.size()==0){
                    noresult.setVisibility(View.VISIBLE);
                }
                else {
                    noresult.setVisibility(View.GONE);
                    itemlistAdapter = new ItemlistAdapter(Items.this, items);
                    itemlist.setAdapter(itemlistAdapter);

                    itemlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent in = new Intent(Items.this, UpdateItem.class);
                            in.putExtra("data", itemlistAdapter.getItem(position));
                            startActivity(in);
                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}