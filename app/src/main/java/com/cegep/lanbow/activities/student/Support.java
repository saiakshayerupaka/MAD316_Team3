package com.cegep.lanbow.activities.student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.cegep.lanbow.R;
import com.cegep.lanbow.models.Message;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
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

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.message_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        message = findViewById(R.id.msg);

        send = findViewById(R.id.send);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!messageTitle.getText().equals("") && !message.getText().equals("") && spinner.getSelectedItem()!=null){
                    database.getReference().child("Messages").child(UUID.randomUUID().toString()).setValue(new Message(messageTitle.getText().toString(),spinner.getSelectedItem().toString(),message.getText().toString(),"unread",firebaseAuth.getCurrentUser().getUid(),new Date().getTime())).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(Support.this,"Message Sent successfully",Toast.LENGTH_LONG).show();
                                finish();
                            }
                            else{
                                Toast.makeText(Support.this,"Something went wrong!!!",Toast.LENGTH_LONG).show();

                            }
                        }
                    });
                }

                else{
                    Toast.makeText(Support.this,"Something Missing!!",Toast.LENGTH_LONG).show();

                }


            }
        });



    }
}