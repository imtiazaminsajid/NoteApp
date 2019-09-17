package com.example.noteapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "note.db";
    private static final String TABLE_NAME = "notedetails";
    private static final String ID = "_id";
    private static final String NOTE = "note";
    private static final String TITLE = "title";
    private static final int VERSION_NUMBER = 1;
    Context context;

    private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+" ("+ID+" INTEGER PRIMARY KEY, "+TITLE+" VARCHAR(30), "+NOTE+" TEXT);";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION_NUMBER);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {

            db.execSQL(CREATE_TABLE);
            Toast.makeText(context, "Table Created", Toast.LENGTH_SHORT).show();

        } catch (Exception e){
            Toast.makeText(context, "Error  "+e, Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        try {
            Toast.makeText(context, "Table Created", Toast.LENGTH_SHORT).show();
            db.execSQL(TABLE_NAME);
            onCreate(db);

        } catch (Exception e){
            Toast.makeText(context, "Error  "+e, Toast.LENGTH_SHORT).show();

        }
    }

    public long saveData(String title, String note){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TITLE, title);
        contentValues.put(NOTE, note);

        long rowNumber = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        return rowNumber;
    }

    public Cursor showAllData(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE_NAME,null);
        return cursor;
    }
}
