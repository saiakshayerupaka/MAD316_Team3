package com.cegep.lanbow.activities.student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cegep.lanbow.R;
import com.cegep.lanbow.models.Item;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.UUID;

import com.cegep.lanbow.models.*;

public class Reservation extends AppCompatActivity {

    private CalendarPickerView calendar;
    private TextView borrowdate,returndate;
    private FirebaseDatabase database;
    private FirebaseAuth auth;
    private DateFormat df;

    private Button reserve;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        df = new SimpleDateFormat("MMM, d yyyy");;

        final Item item = (Item) getIntent().getSerializableExtra("data");

        calendar = findViewById(R.id.calendar_view);
        borrowdate = findViewById(R.id.borrowdate);
        returndate = findViewById(R.id.returndate);

        reserve = findViewById(R.id.makereservation);

        database.getReference().child("Reserve").orderByChild("itemId").equalTo(item.getItemId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot sna : snapshot.getChildren()) {
                    Reserve reserve = sna.getValue(Reserve.class);
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
                if(calendar.getSelectedDates().size()>1){

                   Reserve reserve = new Reserve(auth.getCurrentUser().getUid(),item.getItemId(),convertDatestoTimestamp(calendar.getSelectedDates()),new Date().getTime(),calendar.getSelectedDates().get(0).getTime(),calendar.getSelectedDates().get(calendar.getSelectedDates().size()-1).getTime());
                   database.getReference().child("Reserve").child(UUID.randomUUID().toString()).setValue(reserve).addOnCompleteListener(new OnCompleteListener<Void>() {
                       @Override
                       public void onComplete(@NonNull Task<Void> task) {
                           if(task.isSuccessful()){

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


                if(calendar.getSelectedDates().size()==0){
                    return true;
                }
                else {

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
                        Toast.makeText(Reservation.this,"You can select max 5 days",Toast.LENGTH_LONG).show();

                        return false;
                    }
                }
            }
        });




        ;
// deactivates given dates, non selectable
// highlight dates in red color, mean they are aleady used.
// add subtitles to the dates pass a arrayList of SubTitle objects with date and text
    }

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

    private List<Date> convertTimestamptoDates(List<Long> dates){

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
}
