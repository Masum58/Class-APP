package com.example.mac.healthconsultingapp;

import java.util.Calendar;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mac.healthconsultingapp.Database.HealthDB;
import com.example.mac.healthconsultingapp.Model.Exercise;
import com.example.mac.healthconsultingapp.Utils.Common;

import java.util.ArrayList;
import java.util.List;

public class Daily_Training extends AppCompatActivity {

    Button btnStart;
    ImageView ex_image;
    TextView txtGetReady, txtCountdown, txtTimer, ex_name;
    ProgressBar progressBar;
    LinearLayout layoutGetReady;

    int ex_id=0,limit_time = 0;

    List<Exercise> list = new ArrayList<>();

    HealthDB healthDB;

    public int counter = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily__training);

        initData();

        healthDB = new HealthDB(this);


        btnStart = findViewById(R.id.btnStart);
        ex_image = findViewById(R.id.detail_image);
        txtCountdown = findViewById(R.id.txtCountdown);
        txtGetReady = findViewById(R.id.txtGetReady);
        txtTimer = findViewById(R.id.timer);
        ex_name = findViewById(R.id.title);

        layoutGetReady = findViewById(R.id.layout_get_ready);

        progressBar = findViewById(R.id.progressBar);

        //Set Data
        progressBar.setMax(list.size());

        setExerciseInformation(ex_id);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnStart.getText().toString().toLowerCase().equals("start"))
                {
                    showGetReady();
                    btnStart.setText("done");
                }
                else if (btnStart.getText().toString().toLowerCase().equals("done"))
                {
                    if (healthDB.getSettingMode() == 0)
                        exerciseEasyModeCountDown.cancel();
                    else if (healthDB.getSettingMode() == 1)
                        exerciseMediumModeCountDown.cancel();
                    else if (healthDB.getSettingMode() == 2)
                        exerciseHardModeCountDown.cancel();

                    restTimeCountDown.cancel();

                    if (ex_id < list.size())
                    {
                        showRestTime();
                        ex_id++;
                        progressBar.setProgress(ex_id);
                        txtTimer.setText("");
                    }
                    else
                        showFinished();
                }
                else
                {

                    if (healthDB.getSettingMode() == 0)
                        exerciseEasyModeCountDown.cancel();
                    else if (healthDB.getSettingMode() == 1)
                        exerciseMediumModeCountDown.cancel();
                    else if (healthDB.getSettingMode() == 2)
                        exerciseHardModeCountDown.cancel();

                    restTimeCountDown.cancel();

                    if (ex_id < list.size())
                        setExerciseInformation(ex_id);
                    else
                        showFinished();

                }
            }
        });


    }

    private void showRestTime() {
        ex_image.setVisibility(View.INVISIBLE);
        txtTimer.setVisibility(View.INVISIBLE);
        btnStart.setText("Skip");
        btnStart.setVisibility(View.VISIBLE);

        layoutGetReady.setVisibility(View.VISIBLE);

        restTimeCountDown.start();

        txtGetReady.setText("REST TIME");
    }

    private void showGetReady() {
        ex_image.setVisibility(View.INVISIBLE);
        btnStart.setVisibility(View.INVISIBLE);
        txtTimer.setVisibility(View.VISIBLE);

        layoutGetReady.setVisibility(View.VISIBLE);

        txtGetReady.setText("GET READY");

        new CountDownTimer(6000, 1000)
        {

            @Override
            public void onTick(long l) {
                txtCountdown.setText(String.valueOf(counter));
                counter--;
            }

            @Override
            public void onFinish() {
                showExercise();
            }
        }.start();

    }

    private void showExercise() {
        if (ex_id < list.size()) //List Size Contains All Exercise
        {
            ex_image.setVisibility(View.VISIBLE);
            btnStart.setVisibility(View.VISIBLE);
            layoutGetReady.setVisibility(View.INVISIBLE);

            if (healthDB.getSettingMode() == 0)
                exerciseEasyModeCountDown.start();
            else if (healthDB.getSettingMode() == 1)
                exerciseMediumModeCountDown.start();
            else if (healthDB.getSettingMode() == 2)
                exerciseHardModeCountDown.start();

            //Set Data
            ex_image.setImageResource(list.get(ex_id).getImage_id());
            ex_name.setText(list.get(ex_id).getName());
        }
        else
            showFinished();
    }

    private void showFinished() {
        ex_image.setVisibility(View.INVISIBLE);
        btnStart.setVisibility(View.INVISIBLE);
        txtTimer.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        layoutGetReady.setVisibility(View.VISIBLE);

        txtGetReady.setText("FINISHED");
        txtCountdown.setText("Congratulation ! \n You're done with today Exercise");
        txtCountdown.setTextSize(20);

        //Save Done Workouts To DB
        healthDB.saveDay(""+ Calendar.getInstance().getTimeInMillis());
    }

    //CountDown
    CountDownTimer exerciseEasyModeCountDown = new CountDownTimer(Common.TIME_LIMIT_EASY,1000) {
        public int count = 10;

        @Override
        public void onTick(long l) {
            txtTimer.setText(String.valueOf(count));
            count--;
        }

        @Override
        public void onFinish() {
            if (ex_id < list.size()-1)
            {
                ex_id++;
                progressBar.setProgress(ex_id);
                txtTimer.setText("");

                setExerciseInformation(ex_id);
                btnStart.setText("START");
            }
            else
                showFinished();

        }
    };
    CountDownTimer exerciseMediumModeCountDown = new CountDownTimer(Common.TIME_LIMIT_MEDIUM,1000) {


        public int count = 15;

        @Override
        public void onTick(long l) {
            txtTimer.setText(String.valueOf(count));
            count--;
        }

        @Override
        public void onFinish() {
            if (ex_id < list.size()-1)
            {
                ex_id++;
                progressBar.setProgress(ex_id);
                txtTimer.setText("");

                setExerciseInformation(ex_id);
                btnStart.setText("START");
            }
            else
                showFinished();

        }
    };
    CountDownTimer exerciseHardModeCountDown = new CountDownTimer(Common.TIME_LIMIT_HARD,1000) {

        public int count = 20;

        @Override
        public void onTick(long l) {
            txtTimer.setText(String.valueOf(count));
            count--;
        }

        @Override
        public void onFinish() {
            if (ex_id < list.size()-1)
            {
                ex_id++;
                progressBar.setProgress(ex_id);
                txtTimer.setText("");

                setExerciseInformation(ex_id);
                btnStart.setText("START");
            }
            else
                showFinished();

        }
    };


    CountDownTimer restTimeCountDown = new CountDownTimer(6000,1000) {
        @Override
        public void onTick(long l) {
           // txtCountdown.setText(String.valueOf(counter));
            counter--;
        }

        @Override
        public void onFinish() {
            setExerciseInformation(ex_id);
            showExercise();

        }
    };

    private void setExerciseInformation(int id) {
        ex_image.setImageResource(list.get(id).getImage_id());
        ex_name.setText(list.get(id).getName());
        btnStart.setText("Start");

        ex_image.setVisibility(View.VISIBLE);
        btnStart.setVisibility(View.VISIBLE);
        txtTimer.setVisibility(View.VISIBLE);

        layoutGetReady.setVisibility(View.INVISIBLE);
    }

    private void initData() {

        list.add(new Exercise(R.drawable.easy_pose, "Easy Pose"));
        list.add(new Exercise(R.drawable.cobra_pose, "Cobra Pose"));
        list.add(new Exercise(R.drawable.downward_facing_dog, "Downward Facing Dog"));
        list.add(new Exercise(R.drawable.half_pigeon, "Half Pigeon"));
        list.add(new Exercise(R.drawable.low_lunge, "Low Lunge"));
        list.add(new Exercise(R.drawable.upward_bow, "Upward Pose"));
        list.add(new Exercise(R.drawable.crescent_lunge, "Crescent Lunge"));
        list.add(new Exercise(R.drawable.warrior_pose, "Warrior Pose"));
        list.add(new Exercise(R.drawable.bow_pose, "Bow Pose"));
        list.add(new Exercise(R.drawable.warrior_pose_2, "Warrior pose 2"));
    }
}
