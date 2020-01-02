package com.lucas.calendarview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private CalendarView calendario;
    private MaterialCalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //calendario = findViewById(R.id.calendarView);

        calendarView = findViewById(R.id.calendarView);

       /* calendarView.state().edit()
                .setMinimumDate(CalendarDay.from(2015,1,1))
                .setMaximumDate(CalendarDay.from(2020,1,1))
                .commit();

        */

      /*  calendario.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Log.i("DATA: ", "Valor: "+ dayOfMonth + "/" + month + "/" + year);
            }
        });*/

      /*calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
          @Override
          public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
              Log.i("DATA: ", "Valor: "+ date);
          }
      });

       */

      calendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
          @Override
          public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
              Log.i("DATA: ", "Valor: "+ (date.getMonth()+1) + "/" + date.getYear());
          }
      });

    }
}
