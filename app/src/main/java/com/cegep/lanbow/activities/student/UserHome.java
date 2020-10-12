package com.cegep.lanbow.activities.student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.cegep.lanbow.R;
import com.cegep.lanbow.adapters.HomeItemListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserHome extends AppCompatActivity {
    private ImageView profile;
    private FirebaseAuth mAuth;
    private ListView listview;
    private HomeItemListAdapter homeItemListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();

        if(!user.isEmailVerified()){
            startActivity(new Intent(UserHome.this,EmailVerify.class));
            finish();
        }

        profile = findViewById(R.id.profile);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserHome.this,UserProfile.class));
            }
        });

    }
}