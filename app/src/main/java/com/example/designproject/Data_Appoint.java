package com.example.designproject;

public class Data_Appoint {

    private String doc_name,note,date,time;
    public Data_Appoint(String doc_name, String note, String date, String time){
        this.doc_name=doc_name;
        this.note=note;
        this.date=date;
        this.time=time;
    }
    public String getDoc_name() {
        return doc_name;
    }

    public String getNote() {
        return note;
    }

    public String getDate() {
        return date;
    }
    public String getTime(){
        return time;
    }
}
