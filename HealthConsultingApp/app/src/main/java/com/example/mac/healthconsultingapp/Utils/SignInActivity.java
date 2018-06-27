package com.example.mac.healthconsultingapp.Utils;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mac.healthconsultingapp.R;

public class SignInActivity extends AppCompatActivity {
    EditText e1,e2;
    Button b1,b2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        e1 = findViewById(R.id.editText);
        e2 = findViewById(R.id.editText2);
        b1 = findViewById(R.id.button);
        b2 = findViewById(R.id.button2);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateInputs();

            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void validateInputs() {
        if(e1.getText().toString().isEmpty()){
            e1.setError("Please enter your username");
            return;
        }
        if(e2.getText().toString().isEmpty()){
            e2.setError("Please enter your password");
            return;
        }

        // call server for authentication
        Toast.makeText(SignInActivity.this,"Hello All", Toast.LENGTH_SHORT).show();

        Intent homeActivity = new Intent(this, UserProfileActivity.class);
        startActivity(homeActivity);
    }
}
