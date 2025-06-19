package com.example.DBstorage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dbstorage.R;

public class SettingsActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText emailEditText;
    private Button saveSettingsButton;
    private SharedPreferences sharedPreferences;

    // Keys for SharedPreferences
    private static final String PREF_NAME = "UserSettings";
    private static final String KEY_USERNAME = "username_key";
    private static final String KEY_EMAIL = "email_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Initialize UI elements
        usernameEditText = findViewById(R.id.usernameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        saveSettingsButton = findViewById(R.id.saveSettingsButton);

        // Get SharedPreferences instance
        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        // Read saved settings and populate EditText fields
        loadSettings();

        // Set up click listener for the Save Settings button
        saveSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSettings(); // Call the method to save settings
            }
        });
    }

    /**
     * Loads the saved username and email from SharedPreferences and displays them in the EditText fields.
     */
    private void loadSettings() {
        // Retrieve username and email. Provide a default empty string if not found.
        String savedUsername = sharedPreferences.getString(KEY_USERNAME, "");
        String savedEmail = sharedPreferences.getString(KEY_EMAIL, "");

        // Set the text of the EditText fields
        usernameEditText.setText(savedUsername);
        emailEditText.setText(savedEmail);

        Toast.makeText(this, "Settings loaded!", Toast.LENGTH_SHORT).show();
    }

    /**
     * Saves the current text from the EditText fields into SharedPreferences.
     */
    private void saveSettings() {
        // Get the text from the EditText fields
        String username = usernameEditText.getText().toString();
        String email = emailEditText.getText().toString();

        // Get a SharedPreferences editor to make changes
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Put the string values with their respective keys
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_EMAIL, email);

        // Apply the changes asynchronously
        editor.apply();

        Toast.makeText(this, "Settings saved successfully!", Toast.LENGTH_SHORT).show();
    }
}