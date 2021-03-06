package com.cegep.lenbow.activities.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cegep.lenbow.R;
import com.cegep.lenbow.activities.BorrowHistory;
import com.cegep.lenbow.models.Student;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

/**
 * user profile for admin
 * @author dipmal lakhani
 * @author Sai Akshay
 * @author Gopichand
 * @author HarshaVardhan
 * @author Vinay
 * @author prashant
 * @author Amandeep singh
 */


public class UserProfile extends AppCompatActivity {

    /**
     * User name, Email, student Id, phone number, Address textview
     */
    private TextView name,email,studentid,phone,address;
    /**
     * block button view
     */
    private Button block;
    /**
     * MenuItem attribute
     */
    private MenuItem blck;
    /**
     * backbtn image view
     */
    private ImageView back;
    /**
     * Firebase database object
     */
    private FirebaseDatabase database;
    /**
     * more imageview
     */
    private ImageView more;
    /**'
     * Student class object
     */
    private Student s;
    /**
     * borrowhistory button view
     */
    private Button borrowhistory;
    /**'
     * profile pic imageview
     */
    private ImageView profilepic;
    /**
     * Firebase stirage object
     */
    private FirebaseStorage storage;

    /**
     * activity on create method
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile2);

        database = FirebaseDatabase.getInstance();
        profilepic = findViewById(R.id.profilepic);
        storage = FirebaseStorage.getInstance();


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


        if(s.getProfilepic()!=null){
            storage.getReference().child(s.getProfilepic()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(UserProfile.this).load(uri).into(profilepic);
                }
            });
        }
        else{
            profilepic.setColorFilter(getResources().getColor(R.color.light));

        }

        borrowhistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(UserProfile.this, BorrowHistory.class);
                in.putExtra("data",s);
                startActivity(in);
            }
        });





    }

    /**
     * This method will show the popup menu on top
     * @param v
     */
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

    /**
     * this method will block/unblock the user
     */

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