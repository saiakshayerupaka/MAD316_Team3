package com.cegep.lanbow.activities.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.cegep.lanbow.R;
import com.google.firebase.database.FirebaseDatabase;

public class MessageList extends AppCompatActivity {

    private ListView listView;
    private EditText search;
    private ImageView backbtn;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);

        database = FirebaseDatabase.getInstance();

        backbtn = findViewById(R.id.backbtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        search = findViewById(R.id.search);
        listView = findViewById(R.id.listview);


    }
}