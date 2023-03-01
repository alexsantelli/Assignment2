package com.example.assignment2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    protected FloatingActionButton addUserClick;
    protected ListView listView;
    private boolean isDisplayUser = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addUserClick = findViewById(R.id.addUser);
        listView = findViewById(R.id.studentList);
        listStudentsbyName();

        addUserClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Opening popup
                Popup pop = new Popup();
                pop.show(getSupportFragmentManager(), "Add Person Dialog");

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu eventViewToggle) {
        eventViewToggle.add("Toggle Name/ID");
        return super.onCreateOptionsMenu(eventViewToggle);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(item.getItemId() == 0)
        {
            if(!isDisplayUser) {
                listStudentsbyName();
                isDisplayUser = true;
            }
            else
            {
                listStudentsbyID();
                isDisplayUser = false;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void listStudentsbyID(){
        //get list From database
        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
        List<Student> studentList = dbHelper.getAllStudents();
        //Add Just names to list to be displayed
        List<String> outputNameList = new ArrayList<String>();
        for (Student s : studentList){
            outputNameList.add(s.getCount() + ". " + s.getID());
        }

        //Display sorted list
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, sortList(outputNameList));
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String firstName = "SurName: " + studentList.get(position).getSurName();
                String lastName = "Name: " + studentList.get(position).getName();
                String ID = "ID: " + studentList.get(position).getID();
                String GPA = "GPA: " + studentList.get(position).getGPA();
                String dateCreated = "Profile created: " + studentList.get(position).getDateCreated();
                //Adding Opened access
                dbHelper.insertAccess(studentList.get(position), "Opened");
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                intent.putExtra(Config.COLUMN_COUNT, position);
                intent.putExtra(Config.COLUMN_SURNAME, firstName);
                intent.putExtra(Config.COLUMN_NAME, lastName);
                intent.putExtra(Config.COLUMN_ID, ID);
                intent.putExtra(Config.COLUMN_GPA, GPA);
                intent.putExtra(Config.COLUMN_DATECREATED, dateCreated);
                startActivity(intent);
            }
        });
    }
    public void listStudentsbyName(){
        //get list From database
        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
        List<Student> studentList = dbHelper.getAllStudents();
        //Add Just names to list to be displayed
        List<String> outputNameList = new ArrayList<String>();
        for (Student s : studentList){
            outputNameList.add(s.getCount() + ". " + s.getSurName() + ", " + s.getName());
        }

        //Display sorted list
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, sortList(outputNameList));
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String firstName = "SurName: " + studentList.get(position).getSurName();
                String lastName = "Name: " + studentList.get(position).getName();
                String ID = "ID: " + studentList.get(position).getID();
                String GPA = "GPA: " + studentList.get(position).getGPA();
                String dateCreated = "Profile created: " + studentList.get(position).getDateCreated();
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
    private List<String> sortList(List<String> outputNameList) {
        Collections.sort(outputNameList, new Comparator<String>() {
            @Override
            //SORT by 3rd character
            public int compare(String o1, String o2) {
                return Character.compare(o1.charAt(3), o2.charAt(3));
            }
        });
        return outputNameList;
    }
}