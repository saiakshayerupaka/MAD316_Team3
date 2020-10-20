package com.cegep.lanbow.activities.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cegep.lanbow.R;
import com.cegep.lanbow.activities.BorrowHistory;
import com.cegep.lanbow.models.Student;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfile extends AppCompatActivity {

    private TextView name,email,studentid,phone,address;
    private Button block;
    private MenuItem blck;
    private ImageView back;
    private FirebaseDatabase database;
    private ImageView more;
    private Student s;
    private Button borrowhistory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile2);

        database = FirebaseDatabase.getInstance();


        s = (Student) getIntent().getSerializableExtra("data");



        back = findViewById(R.id.backbtn);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        name = findViewById(R.id.username);
        email = findViewById(R.id.email);
        studentid = findViewById(R.id.studentid);
        phone = findViewById(R.id.phonenumber);
        address = findViewById(R.id.address);
        more = findViewById(R.id.more);
        borrowhistory = findViewById(R.id.borrowhistory);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(v);
            }
        });

        name.setText(s.getName());
        email.setText(s.getEmail());
        studentid.setText(s.getStudentId());
        phone.setText(s.getPhonenumber());
        address.setText(s.getAddress());

        borrowhistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(UserProfile.this, BorrowHistory.class);
                in.putExtra("data",s);
                startActivity(in);
            }
        });





    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.adminuserprofile, popup.getMenu());
        Menu menu = popup.getMenu();
         blck = menu.getItem(0);
        database.getReference().child("Users").child(s.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Student s = snapshot.getValue(Student.class);


                if(s.getProfileStatus().equals("active")){
                    blck.setTitle("Block User");
                }
                else{
                    blck.setTitle("Unblock User");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch ((item.getItemId())){

                    case R.id.block:
                        Block();
                        return true;
                }
                return false;
            }
        });
        popup.show();
    }

    public void Block(){
        database.getReference().child("Users").child(s.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Student s = snapshot.getValue(Student.class);

                if(s.getProfileStatus()!=null && s.getProfileStatus().equals("active")) {
                    database.getReference().child("Users").child(snapshot.getKey()).child("profileStatus").setValue("block");
                    blck.setTitle("Unblock User");

                }
                else if(s.getProfileStatus()!=null && s.getProfileStatus().equals("block")){
                    database.getReference().child("Users").child(snapshot.getKey()).child("profileStatus").setValue("active");
                    blck.setTitle("Block User");

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}