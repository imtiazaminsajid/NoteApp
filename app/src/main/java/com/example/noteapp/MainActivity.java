package com.example.noteapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;

    CardView flotingBtn;
    GridView gridView;
    RelativeLayout NoteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ArrayList<NoteModel> noteModelArrayList =  new ArrayList<>();

        flotingBtn =  findViewById(R.id.flotingBtn);
        gridView = findViewById(R.id.gridView);

        NoteList = findViewById(R.id.NoteList);


        databaseHelper =  new DatabaseHelper(this);
        final SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();


        flotingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                WriteANote.setVisibility(View.VISIBLE);

                Intent intent = new Intent(MainActivity.this, MakeNote.class);
                startActivity(intent);
            }
        });

        final Cursor cursor = databaseHelper.showAllData();
        noteModelArrayList.clear();

        while (cursor.moveToNext()) {
            String id = cursor.getString(0);
            String title = cursor.getString(1);
            String note = cursor.getString(2);

            noteModelArrayList.add(new NoteModel(id, title, note));
        }
        CustomAdapter customAdapter = new CustomAdapter(this, noteModelArrayList);
        customAdapter.notifyDataSetChanged();
        gridView.invalidateViews();
        gridView.setAdapter(customAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String ClickID = noteModelArrayList.get(position).getId();
                String ClickTitle = noteModelArrayList.get(position).getTitle();
                String ClickNote = noteModelArrayList.get(position).getNote();

                Intent intent = new Intent(MainActivity.this, UpdateNote.class);
                intent.putExtra("ID", ClickID);
                intent.putExtra("Title", ClickTitle);
                intent.putExtra("Note", ClickNote);

                startActivity(intent);
            }
        });

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder =  new AlertDialog.Builder(MainActivity.this);

                builder.setMessage("Want to Delete?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                String deleteID = noteModelArrayList.get(position).getId();
                                databaseHelper.deleteData(deleteID);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .create().show();

                return true;
            }
        });


    }
}
