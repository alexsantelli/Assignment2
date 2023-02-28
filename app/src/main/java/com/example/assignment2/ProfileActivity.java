package com.example.assignment2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    private TextView firstname, lastname, id, gpa, dateCreated;
    ListView accessList;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setupUI();
    }
    //back button
    //TODO: Add Close indicator
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class );
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, selectedUsersAccessRecords);
        accessList.setAdapter(arrayAdapter);
    }
}
