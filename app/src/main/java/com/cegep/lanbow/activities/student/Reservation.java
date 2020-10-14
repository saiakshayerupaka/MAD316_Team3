package com.cegep.lanbow.activities.student;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cegep.lanbow.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.savvi.rangedatepicker.CalendarPickerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Reservation extends AppCompatActivity {

    private CalendarPickerView calendar;
    private TextView borrowdate,returndate;
    private FirebaseDatabase database;
    private FirebaseAuth auth;
    private Button reserve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        database = FirebaseDatabase.getInstance();

        calendar = findViewById(R.id.calendar_view);
        borrowdate = findViewById(R.id.borrowdate);
        returndate = findViewById(R.id.returndate);

        reserve = findViewById(R.id.makereservation);

        reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(calendar.getSelectedDates().size()>1){

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
                DateFormat df = new SimpleDateFormat("MMM, d yyyy");

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
}
