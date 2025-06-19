package com.example.DBStorage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    // Database information
    private static final String DATABASE_NAME = "UserDatabase.db";
    private static final int DATABASE_VERSION = 1;

    // Table and column names
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_ID = "_id"; // Convention for primary key
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_EMAIL = "email";

    // SQL statement to create the users table
    private static final String SQL_CREATE_TABLE_USERS =
            "CREATE TABLE " + TABLE_USERS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_NAME + " TEXT NOT NULL," +
                    COLUMN_EMAIL + " TEXT NOT NULL);";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Called when the database is created for the first time.
        // Create the users table.
        db.execSQL(SQL_CREATE_TABLE_USERS);
        Log.d("DBHelper", "Database created with table: " + TABLE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Called when the database needs to be upgraded.
        // This is where you would handle schema changes.
        // For simplicity in this lab, we just drop the table and recreate it.
        Log.w("DBHelper", "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db); // Recreate the table
    }
}