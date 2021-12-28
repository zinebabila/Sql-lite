package com.example.sqlmobil;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.util.Log;
import android.widget.Toast;


import java.util.ArrayList;

class MyDatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_TABLE_NAME = "mydatabase1";
    private static final String PKEY = "pkey";
    private static final String COL1 = "col1";
    private static final String COL2 = "col2";

    MyDatabase(Context context) {
        super(context, DATABASE_TABLE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String DATABASE_TABLE_CREATE = "CREATE TABLE " + DATABASE_TABLE_NAME + " (" +
                PKEY + " INTEGER PRIMARY KEY, " +
                COL1 + " TEXT, " + COL2 + " TEXT);";
        db.execSQL(DATABASE_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void insertData(String s,String f)
    {
        Log.i("JFL"," Insert in database");
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        ContentValues values = new ContentValues();
        values.put(COL1,s);
        values.put(COL2,f);
        db.insertOrThrow(DATABASE_TABLE_NAME,null, values);
        db.setTransactionSuccessful();
        db.endTransaction();
    }


    @SuppressLint("Range")
    public void readData(ArrayList<String> a,ArrayAdapter b)
    {
        // List itemIds = new ArrayList<String>();
        String select = new String("SELECT * from " + DATABASE_TABLE_NAME);
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(select, null);
        while(cursor.moveToNext()) {
            String itemId = cursor.getString(cursor.getColumnIndex(COL1))+"     "+cursor.getString(cursor.getColumnIndex(COL2));
            a.add(itemId);
            b.notifyDataSetChanged();
            Log.i("JFL", "Reading: " + cursor.getString(cursor.getColumnIndex(COL1)));

        }
        cursor.close();

       /*Log.i("JFL", "Reading database...");
        String select = new String("SELECT * from " + DATABASE_TABLE_NAME);
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(select, null);
        Log.i("JFL", "Number of entries: " + cursor.getCount());
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
               Log.i("JFL", "Reading: " + cursor.getString(cursor.getColumnIndex(COL1)));
            } while (cursor.moveToNext());

        }*/
    }
public void delete(){
    String delete = new String("DELETE  from " + DATABASE_TABLE_NAME);
    SQLiteDatabase db = getWritableDatabase();
    db.execSQL(delete);
}

}

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyDatabase mydb = new MyDatabase(this);
       // mydb.delete();
        Button button = findViewById(R.id.button);
        ListView textView1= findViewById(R.id.liste) ;
        EditText input1 = findViewById(R.id.editTextTextPersonName);
        EditText input2 = findViewById(R.id.editTextTextPersonName2);
        ArrayList<String> item = new ArrayList<String >();
        ArrayAdapter a=new ArrayAdapter(this,android.R.layout.simple_list_item_1,item);
        textView1.setAdapter(a);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.clear();
                String W = input1.getText().toString();
                String W1 = input2.getText().toString();
                if(W.isEmpty()||W1.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Vous avez rien saisie",Toast.LENGTH_LONG).show();
                    return;
                }
                mydb.insertData(W,W1);
                mydb.readData(item,a);
                input1.setText("");
                input2.setText("");



            }


        });
    }
    }
