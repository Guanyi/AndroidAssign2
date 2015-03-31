package com.example.guanyi.assignment2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Guanyi on 3/23/2015.
 */
public class MySqliteHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "students";
    public static final String COLUMN_FIRSTNAME = "first_name";
    public static final String COLUMN_LASTNAME = "last_name";
    public static final String COLUMN_EMAIL = "email_address";
    public static final String COLUMN_STUDENTNUM = "student_number";

    private static final String DATABASE_NAME = "students.db";
    private static final int DATABASE_VERSION = 3;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table " + TABLE_NAME + " ( " + COLUMN_FIRSTNAME + " text, " + COLUMN_LASTNAME + " text, " + COLUMN_EMAIL + " text, " + COLUMN_STUDENTNUM + " integer);";

    public MySqliteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySqliteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
