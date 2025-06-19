package com.example.DBStorage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dbstorage.R;

public class UserRegistrationActivity extends AppCompatActivity {

    private EditText nameEditText;
    private EditText userEmailEditText; // Renamed to avoid confusion with SettingsActivity's emailEditText
    private Button registerUserButton;
    private Button viewUsersButton;
    private TextView usersDisplayTextView; // For displaying users directly in the UI

    private com.example.DBStorage.DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

        // Initialize UI elements
        nameEditText = findViewById(R.id.nameEditText);
        userEmailEditText = findViewById(R.id.userEmailEditText);
        registerUserButton = findViewById(R.id.registerUserButton);
        viewUsersButton = findViewById(R.id.viewUsersButton);
        usersDisplayTextView = findViewById(R.id.usersDisplayTextView);

        // Enable scrolling for the TextView
        usersDisplayTextView.setMovementMethod(new ScrollingMovementMethod());

        // Initialize the database helper
        dbHelper = new com.example.DBStorage.DBHelper(this);

        // Set up click listener for Register User button
        registerUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser(); // Call the method to register a new user
            }
        });

        // Set up click listener for View Users button
        viewUsersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewUsers(); // Call the method to view all registered users
            }
        });
    }

    /**
     * Registers a new user into the SQLite database.
     */
    private void registerUser() {
        String name = nameEditText.getText().toString().trim();
        String email = userEmailEditText.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Name and Email cannot be empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = null; // Declare db outside try to ensure it's accessible in finally
        try {
            // Get a writable database instance
            db = dbHelper.getWritableDatabase();

            // Create a ContentValues object to store key-value pairs (column_name, value)
            ContentValues values = new ContentValues();
            values.put(com.example.DBStorage.DBHelper.COLUMN_NAME, name);
            values.put(com.example.DBStorage.DBHelper.COLUMN_EMAIL, email);

            // Insert the new row, returning the primary key value of the new row
            long newRowId = db.insert(com.example.DBStorage.DBHelper.TABLE_USERS, null, values);

            if (newRowId != -1) {
                Toast.makeText(this, "User registered successfully! ID: " + newRowId, Toast.LENGTH_SHORT).show();
                // Clear the input fields after successful registration
                nameEditText.setText("");
                userEmailEditText.setText("");
            } else {
                Toast.makeText(this, "Error registering user.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e("UserRegistration", "Error registering user: " + e.getMessage());
            Toast.makeText(this, "Database error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            if (db != null) {
                db.close(); // Always close the database connection
            }
        }
    }

    /**
     * Retrieves all registered users from the SQLite database and displays them.
     */
    private void viewUsers() {
        SQLiteDatabase db = null; // Declare db outside try to ensure it's accessible in finally
        Cursor cursor = null; // Declare cursor outside try to ensure it's accessible in finally
        StringBuilder usersList = new StringBuilder();
        usersList.append("Registered Users:\n"); // Initial header for display

        try {
            // Get a readable database instance
            db = dbHelper.getReadableDatabase();

            // Perform a query to get all users from the 'users' table
            // query(table, columns, selection, selectionArgs, groupBy, having, orderBy)
            cursor = db.query(com.example.DBStorage.DBHelper.TABLE_USERS,
                    new String[]{com.example.DBStorage.DBHelper.COLUMN_ID, com.example.DBStorage.DBHelper.COLUMN_NAME, com.example.DBStorage.DBHelper.COLUMN_EMAIL}, // Columns to return
                    null, // The columns for the WHERE clause
                    null, // The values for the WHERE clause
                    null, // don't group the rows
                    null, // don't filter by row groups
                    null); // The sort order

            // Check if the cursor has any data
            if (cursor.moveToFirst()) {
                // Loop through the cursor to read each user's data
                do {
                    // Get column indices
                    int idIndex = cursor.getColumnIndex(com.example.DBStorage.DBHelper.COLUMN_ID);
                    int nameIndex = cursor.getColumnIndex(com.example.DBStorage.DBHelper.COLUMN_NAME);
                    int emailIndex = cursor.getColumnIndex(com.example.DBStorage.DBHelper.COLUMN_EMAIL);

                    // Check if column exists before trying to retrieve value
                    long id = (idIndex != -1) ? cursor.getLong(idIndex) : -1;
                    String name = (nameIndex != -1) ? cursor.getString(nameIndex) : "N/A";
                    String email = (emailIndex != -1) ? cursor.getString(emailIndex) : "N/A";

                    // Log the user data to Logcat
                    Log.d("User Data", "ID: " + id + ", Name: " + name + ", Email: " + email);

                    // Append to the StringBuilder for UI display
                    usersList.append("ID: ").append(id)
                            .append(", Name: ").append(name)
                            .append(", Email: ").append(email).append("\n");

                } while (cursor.moveToNext()); // Move to the next row
            } else {
                usersList.append("No users registered yet.");
                Log.d("User Data", "No users registered yet.");
            }
        } catch (Exception e) {
            Log.e("UserRegistration", "Error viewing users: " + e.getMessage());
            Toast.makeText(this, "Database error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            usersList.append("\nError loading users: ").append(e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close(); // Always close the cursor
            }
            if (db != null) {
                db.close(); // Always close the database connection
            }
        }
        // Update the TextView with the list of users
        usersDisplayTextView.setText(usersList.toString());
    }
}