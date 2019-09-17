package com.example.noteapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;

    LinearLayout WriteANote;

    EditText NoteBox, TitleBox;
    Button SaveData;

    CardView flotingBtn;
    GridView gridView;
    RelativeLayout NoteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<NoteModel> noteModelArrayList =  new ArrayList<>();

        flotingBtn =  findViewById(R.id.flotingBtn);
        gridView = findViewById(R.id.gridView);

        WriteANote = findViewById(R.id.WriteANote);
        NoteList = findViewById(R.id.NoteList);

        NoteBox = findViewById(R.id.Note);
        TitleBox = findViewById(R.id.Title);
        SaveData =  findViewById(R.id.SaveData);

        databaseHelper =  new DatabaseHelper(this);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        SaveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String note = NoteBox.getText().toString();
                String title = TitleBox.getText().toString();

                long rowNumber = databaseHelper.saveData(title, note);
                if (rowNumber>-1){
                    Toast.makeText(MainActivity.this, "Ok", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, "Not Ok", Toast.LENGTH_SHORT).show();
                }

                WriteANote.setVisibility(View.GONE);
                NoteList.setVisibility(View.VISIBLE);
            }
        });

        flotingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WriteANote.setVisibility(View.VISIBLE);
            }
        });

        CustomAdapter customAdapter = new CustomAdapter(this, noteModelArrayList);
        gridView.setAdapter(customAdapter);
        Cursor cursor = databaseHelper.showAllData();
        noteModelArrayList.clear();
        while (cursor.moveToNext()) {
            String id = cursor.getString(0);
            String title = cursor.getString(1);
            String note = cursor.getString(2);

            noteModelArrayList.add(new NoteModel(id, title, note));
        }
        customAdapter.notifyDataSetChanged();
    }
}
