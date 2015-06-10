package com.example.seanredmond.youtubedatabaseexample;

import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    DBAdapter myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openDB();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        closeDB();
    }


    private void openDB(){
        myDb = new DBAdapter(this);
        myDb.open();
    }

    private void closeDB(){

        myDb.close();
    }

    private void displayText(String message){
        TextView textView = (TextView) findViewById(R.id.textDisplay);
        textView.setText(message);
    }


    public void onClick_AddRecord(View v){
        displayText("Clicked add record");

        long newId = myDb.insertRow("Waldo", 9897, "blue");

        //Query for the record just added
        // Use the id

        Cursor cursor = myDb.getRow(newId);
        displayRecordSet(cursor);
    }

    public void onClick_ClearAll(View v){
        displayText("Clicked clear all");
        myDb.deleteAll();
    }

    public void onClick_DisplayRecords(View v){
        displayText("clicked display record");

        Cursor cursor = myDb.getAllRows();
        displayRecordSet(cursor);
    }

    private void displayRecordSet(Cursor cursor){
        String message = "";

        if(cursor.moveToFirst()){
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                int studentNumber = cursor.getInt(2);

                //append data to the message
                message += "id=" + id
                        + ",name " + name
                        + ",Student Num " + studentNumber
                        + "\n";
            }while(cursor.moveToNext());

        }
        //close cursor to avoid a resource leak
        cursor.close();
        displayText(message);

    }
}
