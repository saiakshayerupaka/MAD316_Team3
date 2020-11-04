package com.cegep.lenbow.activities.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cegep.lenbow.R;
import com.cegep.lenbow.models.Message;
import com.cegep.lenbow.models.Student;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MessageInfo extends AppCompatActivity {

    private TextView messageTitle,studentId,issueType,message,createdOn;
    private ImageView backbtn;
    private FirebaseDatabase database;
    private SimpleDateFormat df;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_info);

        database = FirebaseDatabase.getInstance();
        df = new SimpleDateFormat("MMM, d yyyy");


        final Message msg = (Message) getIntent().getSerializableExtra("data");

        messageTitle = findViewById(R.id.messageTitle);
        studentId = findViewById(R.id.studentId);
        issueType = findViewById(R.id.issue_type);
        message = findViewById(R.id.msg);
        createdOn = findViewById(R.id.createdOn);


        database.getReference().child("Messages").child(msg.getMessageId()).child("messageStatus").setValue("read");

        backbtn = findViewById(R.id.backbtn);

        messageTitle.setText(msg.getMessageTitle());
        message.setText(msg.getMessage());
        issueType.setText(msg.getMessageType());
        studentId.setText(msg.getStudentId());

        studentId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.getReference().child("Users").child(msg.getMessageBy()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Student s = snapshot.getValue(Student.class);
                        s.setKey(snapshot.getKey());
                        Intent in = new Intent(MessageInfo.this,UserProfile.class);
                        in.putExtra("data",s);
                        startActivity(in);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        createdOn.setText(df.format(new Date(msg.getCreatedOn())));
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}