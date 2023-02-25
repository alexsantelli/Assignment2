package com.example.assignment2;

import android.os.Bundle;
import android.util.DisplayMetrics;

public class Popup  extends MainActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_popup);

        //Setting popup size
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        double width = dm.widthPixels * 0.75;
        double height = dm.heightPixels* 0.5;

        getWindow().setLayout((int)width, (int)height);


    }
}
