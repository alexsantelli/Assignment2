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
                String tempID = IDEditText.getText().toString();
                String tempGPA = GPAEditText.getText().toString();
                if(firstName.isEmpty() || lastName.isEmpty() || tempID.isEmpty() || tempGPA.isEmpty()){
                    Toast.makeText(getActivity(), "Fields cannot be blank!", Toast.LENGTH_SHORT).show();
                }
                else if( dbhelper.IDExist(Integer.parseInt(tempID))){
                    Toast.makeText(getActivity(), "ID Already Exists! Try Again", Toast.LENGTH_SHORT).show();
                }
                else if (Integer.parseInt(tempID) < 10000000 || Integer.parseInt(tempID) > 99999999 || Double.parseDouble(tempGPA) < 0 || Double.parseDouble(tempGPA) > 4.30) {
                    Toast.makeText(getActivity(), "Invalid Entry! Try Again", Toast.LENGTH_SHORT).show();
                }
                else{
                    int ID = Integer.parseInt(tempID);
                    double GPA = Double.parseDouble(tempGPA);
                    //getting creation date
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd @ HH:mm:ss");
                    Date todaysDate = new Date();
                    //Inserting new student
                    Student stud = new Student(-1, firstName, lastName, ID, GPA, formatter.format(todaysDate));
                    dbhelper.insertStudent(stud);
                    ((MainActivity)getActivity()).listStudentsbyName();
                    dbhelper.insertAccess(stud, "Created");
                    dismiss();
                }
            }
        });
        return view;
    }
}
