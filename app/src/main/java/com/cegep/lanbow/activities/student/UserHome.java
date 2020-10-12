package com.cegep.lanbow.activities.student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.cegep.lanbow.R;
import com.cegep.lanbow.adapters.HomeItemListAdapter;
import com.cegep.lanbow.adapters.ItemlistAdapter;
import com.cegep.lanbow.models.Item;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserHome extends AppCompatActivity {
    private ImageView profile;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private ListView listview;
    private HomeItemListAdapter homeItemListAdapter;
    private List<Item> itemlist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        listview = findViewById(R.id.listview);


        FirebaseUser user = mAuth.getCurrentUser();

        if(!user.isEmailVerified()){
            startActivity(new Intent(UserHome.this,EmailVerify.class));
            finish();
        }

        profile = findViewById(R.id.profile);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserHome.this,UserProfile.class));
            }
        });

        database.getReference().child("Items").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                itemlist.clear();
                for(DataSnapshot snap : snapshot.getChildren()){
                    itemlist.add(snap.getValue(Item.class));
                }
                homeItemListAdapter = new HomeItemListAdapter(UserHome.this,itemlist);
                listview.setAdapter(homeItemListAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}