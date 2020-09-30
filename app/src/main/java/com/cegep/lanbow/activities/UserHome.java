package com.cegep.lanbow.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.cegep.lanbow.R;

public class UserHome extends AppCompatActivity {
    private ImageView backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        backbtn = findViewById(R.id.backbtn);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}