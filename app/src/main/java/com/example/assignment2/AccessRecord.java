package com.example.assignment2;

public class AccessRecord {
    private int ID, accessID;
    private String type, dateTime;

    public AccessRecord(int accessID, int id, String type, String dateTime) {
        this.accessID = accessID;
        this.ID = id;
        this.type = type;
        this.dateTime = dateTime;
    }
    public AccessRecord(int id, String type){
        this.ID = id;
        this.type = type;
    }

    public int getAccessID() {
        return accessID;
    }

    public void setAccessID(int accessID) {
        this.accessID = accessID;
    }

    public int getID(){return ID;}
    public void setID(int id){this.ID = id;}

    public String getType(){return type;}
    public void setType(String type){this.type = type;}

    public String getDateTime(){return dateTime;};
    public void setDateTime(String dateTime){this.dateTime = dateTime;}

}
