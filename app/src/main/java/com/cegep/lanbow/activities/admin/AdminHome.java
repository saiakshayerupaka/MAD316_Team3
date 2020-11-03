package com.cegep.lanbow.activities.admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.resource.bitmap.BitmapEncoder;
import com.cegep.lanbow.R;
import com.cegep.lanbow.models.Admin;
import com.cegep.lanbow.models.Item;
import com.cegep.lanbow.models.Message;
import com.cegep.lanbow.models.Student;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminHome extends AppCompatActivity {

    private ImageView backbtn;
    private TextView countUser,countItem;
    private FirebaseDatabase database;
    private LinearLayout item,searchUser;
    private TextView countMessage,countNewMessage;
    private TextView admin;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        database = FirebaseDatabase.getInstance();


        countUser = findViewById(R.id.countUser);
        countItem = findViewById(R.id.countItem);




        countMessage = findViewById(R.id.countMessage);
        countNewMessage = findViewById(R.id.countNewMessage);

        searchUser = findViewById(R.id.searchUser);


        searchUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHome.this,SearchUsers.class));
            }
        });

        item = findViewById(R.id.item);

        countUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHome.this,SearchUsers.class));

            }
        });

        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHome.this,Items.class));
            }
        });

        database.getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int i = (int) snapshot.getChildrenCount();
                countUser.setText(String.valueOf(i));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        database.getReference().child("Items").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int i = (int) snapshot.getChildrenCount();
                countItem.setText(String.valueOf(i));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        database.getReference().child("Messages").orderByChild("messageStatus").equalTo("unread").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int i = (int) snapshot.getChildrenCount();
                countNewMessage.setText(String.valueOf(i) + " new");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        countMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in =new Intent(AdminHome.this, MessageList.class);
                startActivity(in);
            }
        });


        database.getReference().child("Messages").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int i = (int) snapshot.getChildrenCount();
                countMessage.setText(String.valueOf(i));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        backbtn = findViewById(R.id.backbtn);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
