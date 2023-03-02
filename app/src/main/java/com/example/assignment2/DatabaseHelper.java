package com.example.assignment2;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private Context context = null;
//TODO: make everything on database
    private SQLiteDatabase database;

    public DatabaseHelper(Context context) {
        super(context, Config.DATABASE_NAME, null, DATABASE_VERSION);
        database = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_STUDENT_TABLE = "CREATE TABLE " + Config.STUDENT_TABLE + " ( "
                + Config.COLUMN_COUNT + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Config.COLUMN_NAME + " TEXT NOT NULL,"
                + Config.COLUMN_SURNAME + " TEXT NOT NULL,"
                + Config.COLUMN_ID + " INTEGER NOT NULL,"
                + Config.COLUMN_GPA + " DOUBLE NOT NULL,"
                + Config.COLUMN_DATECREATED + " TEXT NOT NULL)";
        sqLiteDatabase.execSQL(CREATE_STUDENT_TABLE);

        String CREATE_ACCESS_TABLE = "CREATE TABLE " + Config.ACCESS_TABLE + " ( "
                + Config.COLUMN_ACCESSID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Config.COLUMN_ID + " INTEGER NOT NULL,"
                + Config.COLUMN_ACCCESSTYPE + " TEXT NOT NULL,"
                + Config.COLUMN_ACCESSTIME + " TEXT NOT NULL)";
        sqLiteDatabase.execSQL(CREATE_ACCESS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Config.STUDENT_TABLE +";");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Config.ACCESS_TABLE + ";");
        onCreate(sqLiteDatabase);
    }

    public long insertStudent(Student student){
        database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Config.COLUMN_NAME, student.getName());
        contentValues.put(Config.COLUMN_SURNAME, student.getSurName());
        contentValues.put(Config.COLUMN_ID, student.getID());
        contentValues.put(Config.COLUMN_GPA, student.getGPA());
        contentValues.put(Config.COLUMN_DATECREATED, student.getDateCreated());
        try {
            database.insertOrThrow(Config.STUDENT_TABLE, null, contentValues);
            return 1;
        }catch (Exception e){
            Toast.makeText(this.context, "DB Error: "+ e.getMessage(), Toast.LENGTH_LONG).show();
            System.out.println("DB error in insertStudents");
            return 0;
        }finally {
            database.close();
        }
    }
    public List<Student> getAllStudents(){
        List<Student> students = new ArrayList<>();
        database = getReadableDatabase();
        Cursor cursor = null;
        try{
            cursor = database.query(Config.STUDENT_TABLE, null, null, null, null, null, null);
            if (cursor.moveToFirst()){
                do{
                    @SuppressLint("Range")int count = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_COUNT));
                    @SuppressLint("Range")String firstName = cursor.getString(cursor.getColumnIndex(Config.COLUMN_SURNAME));
                    @SuppressLint("Range")String lastName = cursor.getString(cursor.getColumnIndex(Config.COLUMN_NAME));
                    @SuppressLint("Range")int ID = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_ID));
                    @SuppressLint("Range")double GPA = cursor.getDouble(cursor.getColumnIndex(Config.COLUMN_GPA));
                    @SuppressLint("Range")String todaysDate = cursor.getString(cursor.getColumnIndex(Config.COLUMN_DATECREATED));

                    students.add(new Student(count, firstName, lastName, ID, GPA, todaysDate));
                }while (cursor.moveToNext());
            }
        }catch (Exception e){
            Toast.makeText(this.context, "DB Error: "+ e.getMessage(), Toast.LENGTH_LONG).show();
            System.out.println("DB error in getAllStudents");
            return Collections.emptyList();
        }finally {
            database.close();
        }
        return students;
    }
    public long insertAccess(Student student, String type){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd @ hh:mm:ss");
        Date todaysDate = new Date();
        database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Config.COLUMN_ID, student.getID());
        contentValues.put(Config.COLUMN_ACCCESSTYPE, type);
        contentValues.put(Config.COLUMN_ACCESSTIME, formatter.format(todaysDate));
        try {
            database.insertOrThrow(Config.ACCESS_TABLE, null, contentValues);
            return 1;
        }catch (Exception e){
            Toast.makeText(this.context, "DB Error: "+ e.getMessage(), Toast.LENGTH_LONG).show();
            System.out.println("DB error in insertStudents");
            return 0;
        }finally {
            database.close();
        }
    }
    public List<AccessRecord> getAllAccessRecords(){
        List<AccessRecord> records =  new ArrayList<>();
        database = getReadableDatabase();
        Cursor cursor = null;
        try{
            cursor = database.query(Config.ACCESS_TABLE, null, null, null, null, null, null);
            if (cursor.moveToFirst()){
                do{
                    @SuppressLint("Range")int accessID = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_ACCESSID));
                    @SuppressLint("Range")int studentID = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_ID));
                    @SuppressLint("Range")String type = cursor.getString(cursor.getColumnIndex(Config.COLUMN_ACCCESSTYPE));
                    @SuppressLint("Range")String accessTime = cursor.getString(cursor.getColumnIndex(Config.COLUMN_ACCESSTIME));
                    records.add(new AccessRecord(accessID, studentID, type, accessTime));
                }while (cursor.moveToNext());
            }
        }catch (Exception e){
            Toast.makeText(this.context, "DB Error: "+ e.getMessage(), Toast.LENGTH_LONG).show();
            System.out.println("DB error in getAllAccessRecords");
            return Collections.emptyList();
        }finally {
            database.close();
        }
        return records;
    }

    public void deleteStudent(Student student){
        insertAccess(student, "Deleted");
        Integer id = student.getID();
        database = getReadableDatabase();
        database.delete(Config.STUDENT_TABLE,Config.COLUMN_ID+" = ?",new String[]{id.toString()});
    }
    public Student findStudent(String id){
        List<Student> studentList = getAllStudents();
        int idInteger = 0;
        try{
            idInteger = Integer.parseInt(id);
        }
        catch(final NumberFormatException e){
            Toast.makeText(this.context, "DB Error: "+ e.getMessage(), Toast.LENGTH_LONG).show();
            System.out.println("ERROR: COULD NOT PARSE INT");
        }
        for (int i = 0; i < studentList.size(); i++){

            if( idInteger == studentList.get(i).getID()){
                return studentList.get(i);
            }
        }
        //Returns first ID in database if ID does not exist
        return studentList.get(0);
    }
    public boolean IDExist(int id){
        List<Student> studentList = getAllStudents();
        for (int i = 0; i < studentList.size(); i++){
            if(id == studentList.get(i).getID()){
                return true;
            }
        }
        return false;
    }
}

