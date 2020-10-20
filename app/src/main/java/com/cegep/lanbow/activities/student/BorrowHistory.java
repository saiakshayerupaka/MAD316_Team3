package com.cegep.lanbow.activities.student;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.cegep.lanbow.R;

public class BorrowHistory extends AppCompatActivity {

    private ListView listView;
    private EditText Search;
    private ImageView backbtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrow_history);

        backbtn = findViewById(R.id.backbtn);
        listView = findViewById(R.id.listview);
        Search = findViewById(R.id.search);



        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}