package com.cegep.lenbow.activities.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cegep.lenbow.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Admin home screen activity
 * @author dipmal lakhani
 * @author Sai Akshay
 * @author Gopichand
 * @author HarshaVardhan
 * @author Vinay
 * @author prashant
 * @author Amandeep singh
 */

public class AdminHome extends AppCompatActivity {

    /**
     * back button view
     */
    private ImageView backbtn;
    /**
     * User count text view , Item count textview
     */
    private TextView countUser,countItem;
    /**
     * Firebase database obect
     */
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
