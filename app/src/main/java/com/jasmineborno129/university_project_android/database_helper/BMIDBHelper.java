package com.jasmineborno129.university_project_android.database_helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jasmineborno129.university_project_android.model.BMIRecord;

import java.util.ArrayList;

public class BMIDBHelper extends SQLiteOpenHelper {

    //database name
    public static final String DATABASE_NAME = "mohanad_computing";
    //database version
    public static final int DATABASE_VERSION = 3;
    public static final String TABLE_NAME = "tbl_records";

    public BMIDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query;
        //creating table
        query = "CREATE TABLE " + TABLE_NAME + "(ID INTEGER PRIMARY KEY, Weight Text,Length Text,Date Text,Status Text,UID String,BMI String)";
        db.execSQL(query);
    }

    //upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //add the new note
    public void addRecord(String weight, String length , String date, String status,String uid,String BMI) {
        SQLiteDatabase sqLiteDatabase = this .getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("weight", weight);
        values.put("length", length);
        values.put("date", date);
        values.put("status", status);
        values.put("uid", uid);
        values.put("bmi",BMI);

        //inserting new row
        sqLiteDatabase.insert(TABLE_NAME, null , values);
        //close database connection
        sqLiteDatabase.close();
    }

    //get the all notes
    public ArrayList<BMIRecord> getRecords() {
        ArrayList<BMIRecord> arrayList = new ArrayList<>();

        // select all query
        String select_query= "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this .getWritableDatabase();
        Cursor cursor = db.rawQuery(select_query, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                BMIRecord record = new BMIRecord();
                record.setID(cursor.getString(0));
                record.setWeight(cursor.getString(1));
                record.setLength(cursor.getString(2));
                record.setDate(cursor.getString(3));
                record.setStatus(cursor.getString(4));
                record.setUID(cursor.getString(5));
                record.setBMI(cursor.getString(6));
                arrayList.add(record);
            }while (cursor.moveToNext());
        }
        return arrayList;
    }

    //delete the note
    public void delete(String ID) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        //deleting row
        sqLiteDatabase.delete(TABLE_NAME, "ID=" + ID, null);
        sqLiteDatabase.close();
    }




}
