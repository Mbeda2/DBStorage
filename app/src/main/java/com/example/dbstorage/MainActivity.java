package com.example.DBStorage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.dbstorage.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button openSettingsButton = findViewById(R.id.openSettingsButton);
        Button openRegistrationButton = findViewById(R.id.openRegistrationButton);

        // Set up click listener for the "Open Settings" button
        openSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start SettingsActivity
                Intent intent = new Intent(MainActivity.this, com.example.DBstorage.SettingsActivity.class);
                startActivity(intent); // Start the activity
            }
        });

        // Set up click listener for the "Open User Registration" button
        openRegistrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start UserRegistrationActivity
                Intent intent = new Intent(MainActivity.this, com.example.DBStorage.UserRegistrationActivity.class);
                startActivity(intent); // Start the activity
            }
        });
    }
}