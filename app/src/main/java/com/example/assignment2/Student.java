package com.example.assignment2;

public class Student {

    private String surName, name;
    private int ID;
    private double GPA;

    public Student(String surName, String name, int ID, double GPA){
        this.surName = surName;
        this.name = name;
        this.ID = ID;
        this.GPA = GPA;
    }
    public String getSurName() {return surName;}
    public void setSurName(String surName){this.surName = surName;}

    public String getName() {return name;}
    public void setName(String name){this.name = name;}

    public int getID() {return ID;}
    public void setID(int ID){this.ID = ID;}

    public double getGPA() {return GPA;}
    public void setGPA(double GPA){this.GPA = GPA;}
}
