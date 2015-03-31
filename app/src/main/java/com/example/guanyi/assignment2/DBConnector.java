package com.example.guanyi.assignment2;
import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBConnector {

    // Database fields
    private SQLiteDatabase database;
    private MySqliteHelper dbHelper;
    private String[] allColumns = { MySqliteHelper.COLUMN_FIRSTNAME,
                                    MySqliteHelper.COLUMN_LASTNAME,
                                    MySqliteHelper.COLUMN_EMAIL,
                                    MySqliteHelper.COLUMN_STUDENTNUM };

    public DBConnector(Context context) {
        dbHelper = new MySqliteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Student createStudent(int student_number, String first_name, String last_name, String email_address) {
        ContentValues values = new ContentValues();
        values.put(MySqliteHelper.COLUMN_FIRSTNAME, first_name);
        values.put(MySqliteHelper.COLUMN_LASTNAME, last_name);
        values.put(MySqliteHelper.COLUMN_EMAIL, email_address);
        values.put(MySqliteHelper.COLUMN_STUDENTNUM, student_number);

        long insertId = database.insert(MySqliteHelper.TABLE_NAME, null, values);
        Cursor cursor = database.query(MySqliteHelper.TABLE_NAME, allColumns,
                MySqliteHelper.COLUMN_STUDENTNUM + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        Student student = cursorToStudent(cursor);
        cursor.close();
        return student;
    }

    public void deleteStudent(Student student) {
        long id = student.getId();
        System.out.println("Student deleted with id: " + id);
        database.delete(MySqliteHelper.TABLE_NAME, MySqliteHelper.COLUMN_STUDENTNUM + " = " + id, null);
    }

    public ArrayList<Student> getAllStudents() {
        ArrayList<Student> students = new ArrayList<>();
        Cursor cursor = database.query(MySqliteHelper.TABLE_NAME, allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Student student = cursorToStudent(cursor);
            students.add(student);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return students;
    }

    private Student cursorToStudent(Cursor cursor) {
        Student student = new Student();
        student.setFirstName(cursor.getString(0));
        student.setLastName(cursor.getString(1));
        student.setEmail(cursor.getString(2));
        student.setId(cursor.getInt(3));
        return student;
    }
}
