package com.example.assignment2;

import android.os.Bundle;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Popup extends DialogFragment {

    private EditText firstNameEditText, lastNameEditText, IDEditText, GPAEditText;
    private Button saveButton, cancelButton;
    private final Context context = null;

    public Popup() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_popup, container, false);

        firstNameEditText = view.findViewById(R.id.surnameEditText);
        lastNameEditText = view.findViewById(R.id.lastnameEditText);
        IDEditText = view.findViewById(R.id.editTextID);
        GPAEditText = view.findViewById(R.id.editTextGPA);
        saveButton = view.findViewById(R.id.saveButton);
        cancelButton = view.findViewById(R.id.cancelButton);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper dbhelper = new DatabaseHelper(getActivity());
                String firstName = firstNameEditText.getText().toString();
                String lastName = lastNameEditText.getText().toString();
                String temp = IDEditText.getText().toString();
                String temp2 = GPAEditText.getText().toString();
                int ID = Integer.parseInt(temp);
                double GPA = Double.parseDouble(temp2);
                if(firstName.isEmpty() || lastName.isEmpty() || ID < 10000000 || ID > 99999999 || GPA < 0 || GPA > 4.30){
                    Toast.makeText(getActivity(), "Fields cannot be blank or Invalid Entries", Toast.LENGTH_SHORT).show();
                }
                else{
                    //getting creation date
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy @ HH:mm:ss");
                    Date todaysDate = new Date();
                    //Inserting new student
                    Student stud = new Student(-1, firstName, lastName, ID, GPA, formatter.format(todaysDate));
                    dbhelper.insertStudent(stud);
                    ((MainActivity)getActivity()).listStudents();
                    //TODO: Create Insert
                    dbhelper.insertAccess(stud, "Created");
                    dismiss();
                }
            }
        });
        return view;
    }
}
