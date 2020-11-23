package com.example.designproject;


import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(@Nullable Context context) {
        super(context, "medicare.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE user(user_id Integer PRIMARY KEY AUTOINCREMENT ,name text , email text  , password text)");
        db.execSQL("CREATE TABLE medicine(med_id Integer PRIMARY KEY AUTOINCREMENT ,user_id int ,name text , pills_AAT INTEGER  ,time1 TEXT,time2 TEXT,time3 TEXT , total INTEGER )");
        db.execSQL("CREATE TABLE appointment(user_id int, doctor TEXT, date TEXT, time TEXT,note TEXT)");
        db.execSQL("CREATE TABLE report(user_id int, name text,date text,time text,swallow text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists user");
        db.execSQL("DROP TABLE IF EXISTS medicine");
        db.execSQL("DROP TABLE IF EXISTS appointment");
        db.execSQL("DROP TABLE IF EXISTS report");
    }
    public boolean emailpassword(String email , String password){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM user  WHERE email=? AND password=?" , new String[]{email, password});
        if(cursor.getCount()>0){
            return true;
        }
        else return false;
    }
    public int getID(String email , String password){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM user  WHERE email=? AND password=?" , new String[]{email, password});
        cursor.moveToFirst();
        int id=cursor.getInt(0);
        return id;
    }


    // INSERTION IN THE LOGIN TABLE
    public boolean insert(String name ,String email, String password){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("Name", name);
        contentValues.put("Email", email);
        contentValues.put("Password", password);
        long ins= db.insert("user",null, contentValues);

        if(ins==-1){
            return false;
        }
        else {
            return true;
        }
    }

    // INSERTION IN THE APPOINTMENT TABLE
    public boolean insert_appoint(int user_id,String doctor, String date, String time ,String note){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("user_id",user_id);
        contentValues.put("doctor",doctor);
        contentValues.put("date",date);
        contentValues.put("time",time);
        contentValues.put("note",note);
        long ins=db.insert("appointment",null,contentValues);

        if(ins==-1){
            return false;
        }
        else {
            return true;
        }

    }

    // INSERTION IN THE MEDICINE TABLE
    public boolean insert_med(int user_id,String name , Integer pills_AAT, String time1,String time2, String time3 , Integer total){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("user_id",user_id);
        contentValues.put("name",name);
        contentValues.put("pills_AAT",pills_AAT);
        contentValues.put("time1",time1);
        contentValues.put("time2",time2);
        contentValues.put("time3",time3);
        contentValues.put("Total",total);

        long ins= db.insert("medicine",null,contentValues);

        if(ins==-1){
            return  false;
        }
        else {
            return true;
        }
    }

    // INSERTION IN THE REPORT TABLE
    public boolean insert_report(int user_id, String name , String date , String time,String swallow){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("user_id",user_id);
        contentValues.put("name",name);
        contentValues.put("date",date);
        contentValues.put("time",time);
        contentValues.put("swallow",swallow);

        long ins=db.insert("report",null,contentValues);

        if(ins==-1){
            return false;
        }
        else {
            return true;
        }
    }

    public void updatetakenmed(int user_id, String name){
        String id=Integer.toString(user_id);
        SQLiteDatabase db =this.getWritableDatabase();
        Cursor cursor= db.rawQuery("SELECT total FROM medicine WHERE user_id=? and name=? ",new String[]{id,name});
        cursor.moveToFirst();
        int quant=cursor.getInt(0);
        cursor= db.rawQuery("SELECT pills_AAT FROM medicine WHERE user_id=? and name=? ",new String[]{id,name});
        cursor.moveToFirst();
        int atatime=cursor.getInt(0);
        quant=quant-atatime;
        ContentValues values= new ContentValues();
        values.put("total", quant);
        db.update("medicine",values,"user_id=? AND name=?",new String[]{id,name});
    }


    public ArrayList<String> getmedicines(int user_id){
        String id=Integer.toString(user_id);
        SQLiteDatabase db=this.getWritableDatabase();
        ArrayList<String> medicines= new ArrayList<>();
        Cursor cursor= db.rawQuery("SELECT name FROM medicine WHERE user_id=? ",new String[]{id});
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            do{
                String random=cursor.getString(0);
                medicines.add(random);
            }
            while(cursor.moveToNext());
        }
        return medicines;
    }
    public ArrayList<String> gettotalmedines(int user_id){
        String id=Integer.toString(user_id);
        SQLiteDatabase db=this.getWritableDatabase();
        ArrayList<String> totalmedicines= new ArrayList<>();
        Cursor cursor= db.rawQuery("SELECT total FROM medicine WHERE user_id=? ",new String[]{id});
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            do{
                String random=cursor.getString(0);
                totalmedicines.add(random);
            }
            while(cursor.moveToNext());
        }
        return totalmedicines;
    }


    public ArrayList<String> gettime1(int user_id){
        String id=Integer.toString(user_id);
        SQLiteDatabase db=this.getWritableDatabase();
        ArrayList<String> timeone= new ArrayList<>();
        Cursor cursor= db.rawQuery("SELECT time1 FROM medicine WHERE user_id=?",new String [] {id});
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            do{
                String random=cursor.getString(0);
                timeone.add(random);
            }
            while(cursor.moveToNext());
        }
        return timeone;
    }

    public ArrayList<String> gettime2(int user_id){
        String id=Integer.toString(user_id);
        SQLiteDatabase db=this.getWritableDatabase();
        ArrayList<String> timetwo= new ArrayList<>();
        Cursor cursor= db.rawQuery("SELECT time2 FROM medicine WHERE user_id=?",new String [] {id});
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            do{
                String random=cursor.getString(0);
                timetwo.add(random);
            }
            while(cursor.moveToNext());
        }
        return timetwo;
    }

    public ArrayList<String> gettime3(int user_id){
        String id=Integer.toString(user_id);
        SQLiteDatabase db=this.getWritableDatabase();
        ArrayList<String> timethree= new ArrayList<>();
        Cursor cursor= db.rawQuery("SELECT time3 FROM medicine WHERE user_id=?",new String [] {id});
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            do{
                String random=cursor.getString(0);
                timethree.add(random);
            }
            while(cursor.moveToNext());
        }
        return timethree;
    }

    // DETAILS OF THE MEDICINE OF A USER
//    public String[] showmedicinesname(String a){
//       SQLiteDatabase db=this.getWritableDatabase();
//       Cursor cursor= db.rawQuery("SELECT * FROM medicine WHERE user_id=? ",new String[] {a});
//        String medname[]= new String[cursor.getCount()];
//        int i=0;
//        cursor.moveToFirst();
//        while(!cursor.isAfterLast()){
//            medname[i]=cursor.getString(1);
//            i++;
//            cursor.moveToNext();
//        }
//        return medname;
//    }
//-----------------------------------------------------------------------------------------------------------------------------
    // THIS WHOLE SECTION WILL GIVE DATA ADDED IN THE APPOINTMENT TABLE

    // THIS WILL GIVE THE LIST OF THE NAMES OF THE DOCTORS
    public ArrayList<String> getdocname(int user_id){
        String id=Integer.toString(user_id);
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor =db.rawQuery("SELECT doctor FROM appointment where user_id=? ", new String[]{id});
        ArrayList<String> doctors= new ArrayList<>();
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            do {
                String x=cursor.getString(0);
                doctors.add(x);
            }
            while (cursor.moveToNext());
        }
        return doctors;
    }


    // THIS WILL GIVE THE LIST OF THE NOTES ADDED BY THAT USER
    public ArrayList<String> getnotes(int user_id){
        String id=Integer.toString(user_id);
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor =db.rawQuery("SELECT note FROM appointment where user_id=? ", new String[]{id});
        ArrayList<String> notes= new ArrayList<>();
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            do {
                String x=cursor.getString(0);
                notes.add(x);
            }
            while (cursor.moveToNext());
        }
        return notes;
    }


    // THIS WILL GIVE THE LIST OF THE DATES ADDED BY THE USER FOR THE APPOINTMENT TO THE DOCTOR
    public ArrayList<String> getdate(int user_id){
        String id=Integer.toString(user_id);
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor =db.rawQuery("SELECT date FROM appointment where user_id=? ", new String[]{id});
        ArrayList<String> dates= new ArrayList<>();
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            do {
                String x=cursor.getString(0);
                dates.add(x);
            }
            while (cursor.moveToNext());
        }
        return dates;
    }


    // THIS WILL GIVE THE LIST OF THE TIME ADDED BY THE USER TO MEET THE DOCTOR
    public ArrayList<String> gettime(int user_id){
        String id=Integer.toString(user_id);
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor =db.rawQuery("SELECT time FROM appointment where user_id=? ", new String[]{id});
        ArrayList<String> times= new ArrayList<>();
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            do {
                String x=cursor.getString(0);
                times.add(x);
            }
            while (cursor.moveToNext());
        }
        return times;
    }
    //END OF THE DETAILS SENDING PAGE FOR TH APPOINTMENTS
    // -----------------------------------------------------------------------------------------------------------------------------------

    // CHECKING  IF EMAIL EXISTS;
    public boolean chkemail(String email){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM user WHERE email=?" , new String[] {email});
         if(cursor.getCount()>0){
            return false;
        }
        else {
            return true;
        }
    }

    //==============================================================================================================================================
    //THIS WHOLE SECTION IS ABOUT GETTING THE DATA FROM THE REPORT TABLE
    //------------- FUNCTIONS ARE DESCRIBES BELOW AND THEY ARE BEING CALLED IN THE WEEKLY_REPORT.JAVA FILE ------------------------------------------

    public ArrayList<String> getmedfromreport(int user_id){
        String id=Integer.toString(user_id);
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor =db.rawQuery("SELECT name FROM report where user_id=? ", new String[]{id});
        ArrayList<String> medarray= new ArrayList<>();
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            do {
                String x=cursor.getString(0);
                medarray.add(x);
            }
            while (cursor.moveToNext());
        }
        return medarray;
    }

    public ArrayList<String> getdatefromreport(int user_id){
        String id=Integer.toString(user_id);
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor =db.rawQuery("SELECT date FROM report where user_id=? ", new String[]{id});
        ArrayList<String> datearray= new ArrayList<>();
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            do {
                String x=cursor.getString(0);
                datearray.add(x);
            }
            while (cursor.moveToNext());
        }
        return datearray;
    }

    public ArrayList<String> gettimefromreport(int user_id){
        String id=Integer.toString(user_id);
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor =db.rawQuery("SELECT time FROM report where user_id=? ", new String[]{id});
        ArrayList<String> timearray= new ArrayList<>();
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            do {
                String x=cursor.getString(0);
                timearray.add(x);
            }
            while (cursor.moveToNext());
        }
        return timearray;
    }

    public ArrayList<String> getswafromreport(int user_id){
        String id=Integer.toString(user_id);
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor =db.rawQuery("SELECT swallow FROM report where user_id=? ", new String[]{id});
        ArrayList<String> swarray= new ArrayList<>();
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            do {
                String x=cursor.getString(0);
                swarray.add(x);
            }
            while (cursor.moveToNext());
        }
        return swarray;
    }

}
