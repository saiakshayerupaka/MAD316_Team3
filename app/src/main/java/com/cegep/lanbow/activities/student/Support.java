package com.cegep.lanbow.activities.student;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.cegep.lanbow.R;
import com.cegep.lanbow.models.Message;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class Support extends AppCompatActivity {

    private EditText messageTitle,message;
    private Spinner spinner;
    private FirebaseDatabase database;
    private FirebaseAuth firebaseAuth;
    private Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);

        database = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();


        messageTitle = findViewById(R.id.titleInput);
        spinner = findViewById(R.id.spinner);
        message = findViewById(R.id.msg);

        send = findViewById(R.id.send);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!messageTitle.getText().equals("") && !message.getText().equals("") && spinner.getSelectedItem()!=null){
                    database.getReference().child("Messages").child(UUID.randomUUID().toString()).setValue(new Message(messageTitle.getText().toString(),))
                }


            }
        });



    }
}