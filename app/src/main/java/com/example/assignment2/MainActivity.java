package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    protected FloatingActionButton addUserClick;
    protected ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addUserClick = findViewById(R.id.addUser);
        listView = findViewById(R.id.studentList);
        listStudents();



        addUserClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Opening popup
                Popup pop = new Popup();
                pop.show(getSupportFragmentManager(), "Add Person Dialog");
                listStudents();
            }
        });
    }


    public void listStudents(){
        //get list From database
        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
        List<Student> studentList = dbHelper.getAllStudents();
        //Add Just names to list to be displayed
        List<String> outputNameList = new ArrayList<String>();
        for (Student s : studentList){
            outputNameList.add( s.getCount() + ". " + s.getSurName() + ", " + s.getName() );
        }

        //Display sorted list
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, sortListAlphabetical(outputNameList));
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String firstName = "SurName: " + studentList.get(position).getSurName();
                String lastName = "Name: " + studentList.get(position).getName();
                String ID = "ID: " + studentList.get(position).getID();
                String GPA = "GPA: " + studentList.get(position).getGPA();
                String dateCreated = "Profile created: " + studentList.get(position).getDateCreated();
                //TODO: access history (new access)
                dbHelper.insertAccess(studentList.get(position), "Opened");

                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                intent.putExtra(Config.COLUMN_SURNAME, firstName);
                intent.putExtra(Config.COLUMN_NAME, lastName);
                intent.putExtra(Config.COLUMN_ID, ID);
                intent.putExtra(Config.COLUMN_GPA, GPA);
                intent.putExtra(Config.COLUMN_DATECREATED, dateCreated);
                startActivity(intent);
            }
        });


    }

    private List<String> sortListAlphabetical(List<String> outputNameList) {
        Collections.sort(outputNameList);
        return outputNameList;
    }
}