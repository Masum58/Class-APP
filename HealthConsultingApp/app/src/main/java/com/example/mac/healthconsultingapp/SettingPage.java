package com.example.mac.healthconsultingapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.mac.healthconsultingapp.Database.HealthDB;

import java.util.Calendar;
import java.util.Date;

public class SettingPage extends AppCompatActivity {

    Button btnSave;
    RadioButton rdiEasy, rdiMedium, rdiHard;
    RadioGroup rdiGroup;
    HealthDB healthDB;
    ToggleButton switchAlarm;
    TimePicker timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_page);

        btnSave = findViewById(R.id.btnSave);
        rdiGroup = findViewById(R.id.rdiGroup);
        rdiEasy = findViewById(R.id.rdiEasy);
        rdiMedium = findViewById(R.id.rdiMedium);
        rdiHard = findViewById(R.id.rdiHard);

        switchAlarm = findViewById(R.id.switchAlarm);
        timePicker = findViewById(R.id.timePicker);

        healthDB = new HealthDB(this);

        int mode = healthDB.getSettingMode();
        setRadioButton(mode);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                saveWorkoutMode();
                saveAlarm(switchAlarm.isChecked());
                Toast.makeText(SettingPage.this, "SAVED ! !", Toast.LENGTH_SHORT ).show();
                finish();
            }
        });
    }

    private void saveAlarm(boolean checked) {
      if (checked)
      {
          AlarmManager manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
          Intent intent;
          PendingIntent pendingIntent;

          intent = new Intent(SettingPage.this,AlarmNotificationReciever.class);
          pendingIntent = PendingIntent.getBroadcast(this,0,intent,0);

          //Set Alarm Time
          Calendar calendar = Calendar.getInstance();
          Date toDay =  Calendar.getInstance().getTime();
          calendar.set(toDay.getYear(),toDay.getMonth(),toDay.getDay(),timePicker.getCurrentHour(),timePicker.getCurrentMinute());

          manager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);

          Log.d("DEBUG","Alarm is set at : "+timePicker.getCurrentHour()+":"+timePicker.getCurrentMinute());

      }
      else
      {
          //Cancell Alarm
          Intent intent = new Intent(SettingPage.this,AlarmNotificationReciever.class);
          PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,intent,0);
          AlarmManager manager = (AlarmManager)getSystemService(ALARM_SERVICE);
          manager.cancel(pendingIntent);
      }
    }

    private void saveWorkoutMode() {

        int selectedID = rdiGroup.getCheckedRadioButtonId();
        if (selectedID == rdiEasy.getId())
            healthDB.saveSettingMode(0);
        else if (selectedID == rdiMedium.getId())
            healthDB.saveSettingMode(1);
        else if (selectedID == rdiHard.getId())
            healthDB.saveSettingMode(2);
    }

    private void setRadioButton(int mode) {
        if (mode == 0)
            rdiGroup.check(R.id.rdiEasy);
        else if (mode == 1)
            rdiGroup.check(R.id.rdiMedium);
        else if (mode == 2)
            rdiGroup.check(R.id.rdiHard);
    }
}





