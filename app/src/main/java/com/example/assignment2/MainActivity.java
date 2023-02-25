package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    protected Button save, cancel;
    protected FloatingActionButton addUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addUser = findViewById(R.id.addUser);
        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Popup.class));
            }
        });

        SQLiteDatabase myDB = this.openOrCreateDatabase("Profiles", MODE_PRIVATE, null);
        myDB.execSQL("CREATE TABLE IF NOT EXISTS profiles (Surname String, Lastname String, id int(8), GPA float)");
        //Inserting content into DB
        //myDB.execSQL("INSERT INTO profiles (Surname, Lastname, id, GPA) VALUES ('Alex', 'Santelli', 40164629, 4.1)");
        //Pulling someone from DB

        Cursor c = myDB.rawQuery("SELECT * FROM profiles", null);
    }
}