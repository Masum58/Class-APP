package com.example.mac.healthconsultingapp;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mac.healthconsultingapp.Database.HealthDB;
import com.example.mac.healthconsultingapp.Utils.Common;

public class ViewExercise extends AppCompatActivity {

    int image_id;
    String name;

    TextView timer,title;
    ImageView detail_image;

    Button btnStart;


    boolean isRunning = false;

    HealthDB healthDB;

    public int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_exercise);

        healthDB = new HealthDB(this);

        timer = (TextView) findViewById(R.id.timer);
        title = (TextView) findViewById(R.id.title);
        detail_image = (ImageView) findViewById(R.id.detail_image);

        btnStart = (Button) findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isRunning)
                {
                    btnStart.setText("DONE");
                    int timeLimit = 0;
                    if (healthDB.getSettingMode() == 0) {
                        counter = 10;
                        timeLimit = Common.TIME_LIMIT_EASY;
                    }
                    else if (healthDB.getSettingMode() == 1) {
                        counter = 15;
                        timeLimit = Common.TIME_LIMIT_MEDIUM;
                    }
                    else if (healthDB.getSettingMode() == 2) {
                        counter = 20;
                        timeLimit = Common.TIME_LIMIT_HARD;
                    }

                    new CountDownTimer(timeLimit,1000){

                        @Override
                        public void onTick(long l) {
                            timer.setText(String.valueOf(counter));
                            counter--;


                        }

                        @Override
                        public void onFinish() {
                            Toast.makeText(ViewExercise.this, "Finish!!!",Toast.LENGTH_SHORT).show();
                            finish();

                        }
                    }.start();
                }
                else
                {
                    Toast.makeText(ViewExercise.this, "Finish!!!",Toast.LENGTH_SHORT).show();
                    finish();

                }

                isRunning = !isRunning;
            }
        });

        timer.setText("");

        if (getIntent() != null)
        {
            image_id = getIntent().getIntExtra("image_id", -1);
            name = getIntent().getStringExtra("name");

            detail_image.setImageResource(image_id);
            title.setText(name);
        }


    }
}
