package com.cegep.lenbow.activities.student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.cegep.lenbow.R;
import com.cegep.lenbow.activities.BorrowHistory;
import com.cegep.lenbow.adapters.HomeItemListAdapter;
import com.cegep.lenbow.models.Item;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;

public class UserHome extends AppCompatActivity {
    private ImageView profile;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private ListView listview;
    private EditText search;
    private HomeItemListAdapter homeItemListAdapter;
    private List<Item> itemlist = new ArrayList<>();
    private ImageView menu;
    private LinearLayout noresult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        noresult = findViewById(R.id.noresult);
        noresult.setVisibility(View.GONE);

        menu = findViewById(R.id.menu);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(v);
            }
        });


        listview = findViewById(R.id.listview);
        search = findViewById(R.id.search);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {



            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(homeItemListAdapter.getCount()==0){
                    noresult.setVisibility(View.VISIBLE);
                }
                else{
                    noresult.setVisibility(View.GONE);
                }
                homeItemListAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


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

        database.getReference().child("Items").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                itemlist.clear();
                for(DataSnapshot snap : snapshot.getChildren()){
                    Item item = snap.getValue(Item.class);
                    item.setItemId(snap.getKey());
                    itemlist.add(item);
                }

                if(itemlist.size()==0){
                    noresult.setVisibility(View.VISIBLE);
                }
                else {
                    homeItemListAdapter = new HomeItemListAdapter(UserHome.this, itemlist);
                    listview.setAdapter(homeItemListAdapter);
                    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent in = new Intent(UserHome.this, ItemDescription.class);
                            in.putExtra("data", homeItemListAdapter.getItem(position));
                            startActivity(in);
                        }
                    });
                    noresult.setVisibility(View.GONE);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch ((item.getItemId())){
                    case R.id.borrowhistory :
                        startActivity(new Intent(UserHome.this, BorrowHistory.class));
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(UserHome.this,UserProfile.class));
                        return true;
                    case R.id.support:
                        startActivity(new Intent(UserHome.this,Support.class));
                        return true;
                    case R.id.logout:
                        finish();
                        mAuth.signOut();
                        return true;
                }
                return false;
            }
        });
        inflater.inflate(R.menu.homemenu, popup.getMenu());
        popup.show();
    }
}