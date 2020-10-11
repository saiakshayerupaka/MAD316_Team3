package com.cegep.lanbow.activities.student;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.cegep.lanbow.R;
import com.savvi.rangedatepicker.CalendarPickerView;

import java.util.Calendar;
import java.util.Date;

public class Reservation extends AppCompatActivity {

    CalendarPickerView calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        calendar = findViewById(R.id.calendar_view);

        Calendar cal = Calendar.getInstance();
        Date today = cal.getTime();
        cal.add(Calendar.MONTH, 3);
        Date nextyear = cal.getTime();

        calendar.init(today, nextyear) //
                .inMode(CalendarPickerView.SelectionMode.RANGE)
                .withSelectedDate(new Date());
// deactivates given dates, non selectable
// highlight dates in red color, mean they are aleady used.
// add subtitles to the dates pass a arrayList of SubTitle objects with date and text
    }
}
