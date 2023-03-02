package com.example.assignment2;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    private TextView firstname, lastname, id, gpa, dateCreated;
    ListView accessList;
    private String IDSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ActionBar actionBar = getSupportActionBar();
        // showing back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        setupUI();
    }
    public boolean onCreateOptionsMenu(Menu eventViewToggle) {
        eventViewToggle.add("Delete Current Student");
        return super.onCreateOptionsMenu(eventViewToggle);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        //Use Database to get Current User ID
        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
        Intent intent = getIntent();
        IDSearch = intent.getStringExtra(Config.COLUMN_ID).substring(4);
        if(item.getItemId() == 0)
        {
            //IF user selects delete User
            dbHelper.deleteStudent(dbHelper.findStudent(IDSearch));
        }
        else if(item.getItemId() == android.R.id.home){
            //IF user selects back button, will insert closed in AccessList
            dbHelper.insertAccess(dbHelper.findStudent(IDSearch), "Closed");
        }
        Intent intent2 = new Intent(ProfileActivity.this, MainActivity.class);
        startActivity(intent2);
        return super.onOptionsItemSelected(item);
    }

    private void setupUI() {
        firstname = findViewById(R.id.textViewSurname);
        lastname = findViewById(R.id.textViewName);
        id = findViewById(R.id.textViewID);
        gpa = findViewById(R.id.textViewGPA);
        dateCreated = findViewById(R.id.textViewDateCreated);
        accessList = findViewById(R.id.accessLists);


        Intent intent = getIntent();
        firstname.setText(intent.getStringExtra(Config.COLUMN_SURNAME));
        lastname.setText(intent.getStringExtra(Config.COLUMN_NAME));
        id.setText(intent.getStringExtra(Config.COLUMN_ID));
        gpa.setText(intent.getStringExtra(Config.COLUMN_GPA));
        dateCreated.setText(intent.getStringExtra(Config.COLUMN_DATECREATED));
        createAccessList(intent.getStringExtra(Config.COLUMN_ID));
    }
    protected void createAccessList(String selectedID){
        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
        List<AccessRecord> accessRecordList = dbHelper.getAllAccessRecords();
        ArrayList<String> selectedUsersAccessRecords = new ArrayList<>();

        for(int index = 0; index < accessRecordList.size(); index++){
            String recordListID = "ID: " + accessRecordList.get(index).getID();
            if(selectedID.equals(recordListID) ){
                String timeStamp = accessRecordList.get(index).getDateTime() + " " + accessRecordList.get(index).getType();
                //Adding to the front of list
                selectedUsersAccessRecords.add(0, timeStamp);
            }
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, selectedUsersAccessRecords);
        accessList.setAdapter(arrayAdapter);
    }
}
