package com.example.assignment2;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private Context context = null;

    public DatabaseHelper(@Nullable Context context) {
        super(context, Config.DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE = "CREATE TABLE " + Config.STUDENT_TABLE + " ("
                + Config.COLUMN_SURNAME + "TEXT NOT NULL,"
                + Config.COLUMN_NAME + "TEXT NOT NULL,"
                + Config.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Config.COLUMN_GPA + " DOUBLE NOT NULL"
                + ")";

        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Config.STUDENT_TABLE +";");
        onCreate(sqLiteDatabase);
    }

    public long insertCourse(Student student){
        String firstName = student.getSurName();
        String lastName = student.getName();
        int ID = student.getID();
        double GPA = student.getGPA();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Config.COLUMN_SURNAME, firstName);
        contentValues.put(Config.COLUMN_NAME, lastName);
        contentValues.put(Config.COLUMN_ID, ID);
        contentValues.put(Config.COLUMN_GPA, GPA);
        try {
            db.insertOrThrow(Config.STUDENT_TABLE, null, contentValues);
            return 1;
        }catch (Exception e){
            Toast.makeText(this.context, "DB Error: "+ e.getMessage(), Toast.LENGTH_SHORT).show();
            System.out.println("DB error in insertCourse");
            return 0;
        }finally {
            db.close();
        }
    }

    public List<Student> getAllStudents(){
        List<Student> students = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        try{
            cursor = db.query(Config.STUDENT_TABLE, null, null, null, null, null, null);
            if (cursor.moveToFirst()){
                do{
                    @SuppressLint("Range") String firstName = cursor.getString(cursor.getColumnIndex(Config.COLUMN_SURNAME));
                    @SuppressLint("Range") String lastName = cursor.getString(cursor.getColumnIndex(Config.COLUMN_NAME));
                    @SuppressLint("Range") int ID = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_ID));
                    @SuppressLint("Range") double GPA = cursor.getDouble(cursor.getColumnIndex(Config.COLUMN_GPA));
                    Student student = new Student(firstName, lastName, ID, GPA);
                    students.add(student);
                }while (cursor.moveToNext());
            }
        }catch (Exception e){
            Toast.makeText(this.context, "DB Error: "+ e.getMessage(), Toast.LENGTH_SHORT).show();
            System.out.println("DB error in getAllCourses");
            return Collections.emptyList();
        }finally {
            db.close();
        }
        return students;
    }

}
