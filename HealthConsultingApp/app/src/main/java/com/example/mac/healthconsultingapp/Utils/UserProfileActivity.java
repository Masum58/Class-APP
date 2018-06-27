package com.example.mac.healthconsultingapp.Utils;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mac.healthconsultingapp.Database.Database;
import com.example.mac.healthconsultingapp.R;

public class UserProfileActivity extends AppCompatActivity {
    EditText e3,e4,e5,e6,e7;
    Button b3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        e3 = findViewById(R.id.editText3);
        e4 = findViewById(R.id.editText4);
        e5 = findViewById(R.id.editText5);
        e6 = findViewById(R.id.editText6);
        e7 = findViewById(R.id.editText7);
        b3 = findViewById(R.id.button3);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveData();
            }
        });
    }

    private void SaveData() {
        try {
            // validation
            if(TextUtils.isEmpty(e3.getText().toString())){
                e3.setError("Email is required");
                return;
            }
            if(TextUtils.isEmpty(e4.getText().toString())){
                e4.setError("Gender is required");
                return;
            }
            if(TextUtils.isEmpty(e5.getText().toString())){
                e5.setError("Age is required");
                return;
            }
            if(TextUtils.isEmpty(e5.getText().toString())){
                e6.setError("Height is required");
                return;
            }
            if(TextUtils.isEmpty(e5.getText().toString())){
                e7.setError("Weight is required");
                return;
            }

            // sqlite init
            Database database = new Database(this);
            SQLiteDatabase userRegistration = database.getWritableDatabase();

            // set data using (key, value) format
            ContentValues contentValues = new ContentValues();
            contentValues.put("Email", e3.getText().toString());
            contentValues.put("Gender", e4.getText().toString());
            contentValues.put("Age", e5.getText().toString());
            contentValues.put("Height", e6.getText().toString());
            contentValues.put("Weight", e7.getText().toString());

            long newUserId = userRegistration.insert("user", null, contentValues);
            if(newUserId>0){
                Toast.makeText(this, "Data successfully save into database.", Toast.LENGTH_LONG).show();
                onBackPressed();
            }else{
                Toast.makeText(this, "Data insert failed.", Toast.LENGTH_LONG).show();
            }
        }catch (Exception ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
    }

