package com.cegep.lanbow.activities.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cegep.lanbow.R;
import com.cegep.lanbow.models.Message;

public class MessageInfo extends AppCompatActivity {

    private TextView messageTitle,studentId,issueType,message;
    private ImageView backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_info);

        Message msg = (Message) getIntent().getSerializableExtra("data");

        messageTitle = findViewById(R.id.messageTitle);
        studentId = findViewById(R.id.studentId);
        issueType = findViewById(R.id.issue_type);
        message = findViewById(R.id.msg);

        backbtn = findViewById(R.id.backbtn);

        messageTitle.setText(msg.getMessageTitle());
        message.setText(msg.getMessage());
        issueType.setText(msg.getMessageType());
        studentId.setText(msg.getStudentId());

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}