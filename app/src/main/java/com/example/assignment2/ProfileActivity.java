package com.example.assignment2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private TextView firstname, lastname, id, gpa, dateCreated;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        setupUI();
    }

    private void setupUI() {
        firstname = findViewById(R.id.textViewSurname);
        lastname = findViewById(R.id.textViewName);
        id = findViewById(R.id.textViewID);
        gpa = findViewById(R.id.textViewGPA);
        dateCreated = findViewById(R.id.textViewDateCreated);



        Intent intent = getIntent();
        firstname.setText(intent.getStringExtra(Config.COLUMN_SURNAME));
        lastname.setText(intent.getStringExtra(Config.COLUMN_NAME));
        id.setText(intent.getStringExtra(Config.COLUMN_ID));
        gpa.setText(intent.getStringExtra(Config.COLUMN_GPA));
        dateCreated.setText(intent.getStringExtra(Config.COLUMN_DATECREATED));

    }
}
