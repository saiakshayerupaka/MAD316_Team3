package com.cegep.lenbow.activities.admin;

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
import android.widget.ListView;

import com.cegep.lenbow.R;
import com.cegep.lenbow.adapters.MessagelistAdapter;
import com.cegep.lenbow.models.Message;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Inbox Activity
 *
 * @author dipmal lakhani
 * @author Sai Akshay
 * @author Gopichand
 * @author HarshaVardhan
 * @author Vinay
 * @author prashant
 * @author Amandeep singh
 */


public class MessageList extends AppCompatActivity {

    /**
     * list view for messages
     */
    private ListView listView;
    /**
     * Edittext view for search
     */
    private EditText search;
    /**
     * back button view
     */
    private ImageView backbtn;
    /**
     * List of Message
     */
    private List<Message> messages = new ArrayList<>();
    /**
     * Firebase database object
     */
    private FirebaseDatabase database;
    /**
     * Message list adapter object
     */
    private MessagelistAdapter messagelistAdapter;

    /**
     * activity oncreate method
     * @param savedInstanceState
     */
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

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                messagelistAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        listView = findViewById(R.id.item_list);

        database.getReference().child("Messages").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messages.clear();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    Message msg = snap.getValue(Message.class);
                    msg.setMessageId(snap.getKey());
                    messages.add(msg);
                }


                messagelistAdapter = new MessagelistAdapter(messages, MessageList.this);
                listView.setAdapter(messagelistAdapter);


                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(MessageList.this, MessageInfo.class);
                        intent.putExtra("data", messagelistAdapter.getItem(position));
                        startActivity(intent);
                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}