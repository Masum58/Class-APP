package com.example.mac.healthconsultingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mac.healthconsultingapp.Custom.WorkoutDoneDecorator;
import com.example.mac.healthconsultingapp.Database.HealthDB;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class Calendar extends AppCompatActivity {

    MaterialCalendarView materialCalendarView;
    HashSet<CalendarDay> list = new HashSet<>();

    HealthDB healthDB;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        healthDB = new HealthDB(this);

        materialCalendarView = (MaterialCalendarView) findViewById(R.id.calendar);
        List<String> workoutDay = healthDB.getWorkoutDays();
        HashSet<CalendarDay> convertedList = new HashSet<>();
        for(String value:workoutDay) {
            convertedList.add(CalendarDay.from(new Date(Long.parseLong(value))));
            materialCalendarView.addDecorator(new WorkoutDoneDecorator(convertedList));
        }
    }
}
