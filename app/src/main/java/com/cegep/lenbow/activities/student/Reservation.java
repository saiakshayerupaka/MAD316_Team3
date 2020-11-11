package com.cegep.lenbow.activities.student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cegep.lenbow.R;
import com.cegep.lenbow.activities.BorrowHistory;
import com.cegep.lenbow.models.Item;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.savvi.rangedatepicker.CalendarPickerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.UUID;

import com.cegep.lenbow.models.*;

/**
 * Item Reservation activity
 * @author dipmal lakhani
 * @author Sai Akshay
 * @author Gopichand
 * @author HarshaVardhan
 * @author Vinay
 * @author prashant
 * @author Amandeep singh
 */

public class Reservation extends AppCompatActivity {
    /**
     * Reservation calendar CalendarPickerView attribute
     */
    private CalendarPickerView calendar;
    /**
     * Reservation borrow date TextView attribute
     * Reservation return date TextView attribute
     */
    private TextView borrowdate,returndate;
    private FirebaseDatabase database;
    private FirebaseAuth auth;
    /**
     * Used to format the date in required format
     */
    private DateFormat df;
    /**
     * ItemDescription backbutton button attribute
     */
    private ImageView backbtn;
    /**
     * Used to display notification
     */
    private AlertDialog.Builder builder1;
    /**
     * this lst contains all dates when the product is borrowed
     */
    private List<Date> borrowdates = new ArrayList<>();
    /**
     * ItemDescription reserve button attribute
     */
    private Button reserve;
    /*
    * contains details of the Item
     */
    private Item item;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        df = new SimpleDateFormat("MMM, d yyyy");

        backbtn = findViewById(R.id.backbtn);

        builder1 = new AlertDialog.Builder(Reservation.this);
        builder1.setMessage("Confirm your Reservation");
        builder1.setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
makeReserve();

            }
        });
        builder1.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
dialog.dismiss();
            }
        });


        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

         item = (Item) getIntent().getSerializableExtra("data");

        calendar = findViewById(R.id.calendar_view);
        borrowdate = findViewById(R.id.borrowdate);
        returndate = findViewById(R.id.returndate);

        reserve = findViewById(R.id.makereservation);

        database.getReference().child("Reserve").orderByChild("itemId").equalTo(item.getItemId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                borrowdates.clear();
                for(DataSnapshot sna : snapshot.getChildren()) {
                    Reserve reserve = sna.getValue(Reserve.class);

                    borrowdates.add(new Date(reserve.getBorrowDate()));
                    Log.d("dates",convertTimestamptoDates(reserve.getSelectedDates()).toString());
                    calendar.highlightDates(convertTimestamptoDates(reserve.getSelectedDates()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Reservation.this,error.getMessage(),Toast.LENGTH_LONG).show();


            }
        });




        reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });

        final Calendar cal = Calendar.getInstance();
        Date today = cal.getTime();
        cal.add(Calendar.MONTH, 3);
        Date nextyear = cal.getTime();

        calendar.init(today, nextyear) //
                .inMode(CalendarPickerView.SelectionMode.RANGE);
        calendar.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {

                if(calendar.getSelectedDates().size()<=1){
                    Toast.makeText(Reservation.this,"Borrow Date selected. Please select return date",Toast.LENGTH_LONG).show();
                    borrowdate.setText(df.format(date));
                    returndate.setText("");

                }
                else{
                    returndate.setText(df.format(date));

                    Toast.makeText(Reservation.this,"Return date selected.select other date to change",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onDateUnselected(Date date) {

            }
        });


        calendar.setDateSelectableFilter(new CalendarPickerView.DateSelectableFilter() {
            @Override
            public boolean isDateSelectable(Date date) {

                if(checkMonths(date)) {


                    if (calendar.getSelectedDates().size() == 0) {
                        return true;
                    } else {

                        Calendar c1 = Calendar.getInstance();
                        c1.setTime(calendar.getSelectedDates().get(0));
                        c1.add(Calendar.DATE, 5);


                        Calendar c2 = Calendar.getInstance();
                        c2.setTime(date);
                        if (c2.getTimeInMillis() < c1.getTimeInMillis()) {
                            return true;
                        } else {
                            if (calendar.getSelectedDates().size() > 1) {
                                return true;
                            }
                            Toast.makeText(Reservation.this, "You can select max 5 days", Toast.LENGTH_LONG).show();

                            return false;
                        }
                    }
                }
                else{
                    Toast.makeText(Reservation.this,"Sorry You can reserve item 2 times in a month",Toast.LENGTH_LONG).show();

                    return false;
                }
            }
        });




        ;
// deactivates given dates, non selectable
// highlight dates in red color, mean they are aleady used.
// add subtitles to the dates pass a arrayList of SubTitle objects with date and text
    }

    /**
     *
     * @param dates
     * @return List of converted dates in timestamp
     */
    private List<Long> convertDatestoTimestamp(List<Date> dates){

       ListIterator<Date> iterator = dates.listIterator();

        List<Long> newdates = new ArrayList<>();
        int i = 0;

        while (iterator.hasNext()){
            Date date = iterator.next();
            newdates.add(i,date.getTime());
            i++;
        }

        return newdates;

    }

    /**
     *
     * @param dates in timestamp
     * @return list of dates converted from timestamp
     */
    private List<Date> convertTimestamptoDates(List<Long> dates){
        Collections.sort(dates);
        ListIterator<Long> iterator = dates.listIterator();

        List<Date> newdates = new ArrayList<>();
        int i = 0;

        while (iterator.hasNext()){
            long date = iterator.next();
            newdates.add(i,new Date(date));
            i++;
        }

        return newdates;

    }

    /**
     * This method is executed when the item is to be reserved
     */
    private void makeReserve(){
        if(calendar.getSelectedDates().size()>1){

            Reserve reserve = new Reserve(item.getItemName(),auth.getCurrentUser().getUid(),item.getItemId(),convertDatestoTimestamp(calendar.getSelectedDates()),new Date().getTime(),calendar.getSelectedDates().get(0).getTime(),calendar.getSelectedDates().get(calendar.getSelectedDates().size()-1).getTime());
            database.getReference().child("Reserve").child(UUID.randomUUID().toString()).setValue(reserve).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        startActivity(new Intent(Reservation.this, BorrowHistory.class));
                        finish();
                    }
                    else{
                        Toast.makeText(Reservation.this,task.getException().toString(),Toast.LENGTH_LONG).show();
                    }
                }
            });

        }
        else{
            Toast.makeText(Reservation.this,"Please select borrow and return date!!!",Toast.LENGTH_LONG).show();
        }
    }

    /**
     * this method is called to check if the user has made reservation of the item more then 2 times.
     */
    private boolean checkMonths(Date dd){
        int count =0;
        if(borrowdates.size()!=0){
           ListIterator<Date> dates = borrowdates.listIterator();

           while (dates.hasNext()){
               Date date = dates.next();
               if(date.getMonth() == dd.getMonth()){
                   count = count + 1;
               }
           }
        }
//        Toast.makeText(Reservation.this,String.valueOf(count),Toast.LENGTH_LONG).show();

        if(count>=2){
            return false;
        }
        else{
            return true;
        }
    }
}
