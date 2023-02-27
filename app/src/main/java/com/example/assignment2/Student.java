package com.example.assignment2;

public class Student {

    private String surName, name, dateCreated;
    private int ID, count;
    private double GPA;

    public Student(int count, String surName, String name, int ID, double GPA, String dateCreated){
        this.count = count;
        this.surName = surName;
        this.name = name;
        this.ID = ID;
        this.GPA = GPA;
        this.dateCreated = dateCreated;
    }
    public String getSurName() {return surName;}
    public void setSurName(String surName){this.surName = surName;}

    public String getName() {return name;}
    public void setName(String name){this.name = name;}

    public int getID() {return ID;}
    public void setID(int ID){this.ID = ID;}

    public double getGPA() {return GPA;}
    public void setGPA(double GPA){this.GPA = GPA;}

    public int getCount(){return count;}

    public void setCount(int count){this.count = count;}

    public void setDateCreated(String dateCreated){ this.dateCreated = dateCreated;}
    public String getDateCreated(){return dateCreated;}


}


