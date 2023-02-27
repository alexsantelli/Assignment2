package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    protected Button save, cancel;
    protected FloatingActionButton addUser;
    protected ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addUser = findViewById(R.id.addUser);
        listView = findViewById(R.id.studentList);

        listStudents();
        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Opening popup
                Popup pop = new Popup();
                pop.show(getSupportFragmentManager(), "Add Person Dialog");
            }
        });
    }

    public void listStudents(){
        //get list From database
        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
        List<Student> studentList = dbHelper.getAllStudents();
        //Add Just names to list to be displayed
        List<String> outputNameList = new ArrayList<String>();
        int index = 1;
        for (Student s : studentList){
            outputNameList.add( (index++) + ". " + s.getName() + ", " + s.getSurName());
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, outputNameList);
        listView.setAdapter(arrayAdapter);
    }
}