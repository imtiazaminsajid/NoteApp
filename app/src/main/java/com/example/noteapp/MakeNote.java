package com.example.noteapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MakeNote extends AppCompatActivity {

    EditText MakeNoteTitle, MakeNoteNote;
    Button MakeNoteSaveBtn;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_note);

        MakeNoteNote = findViewById(R.id.MakeNoteNote);
        MakeNoteTitle = findViewById(R.id.MakeNoteTitle);
        MakeNoteSaveBtn = findViewById(R.id.MakeNoteSaveData);

        databaseHelper =  new DatabaseHelper(this);

        Intent intent = getIntent();
        String id = intent.getStringExtra("ID");
        MakeNoteNote.setText(intent.getStringExtra("Note"));
        MakeNoteTitle.setText(intent.getStringExtra("Title"));

        MakeNoteSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String note = MakeNoteNote.getText().toString();
                String title = MakeNoteTitle.getText().toString();

                long rowNumber = databaseHelper.saveData(title, note);
                if (rowNumber>-1){
                    Toast.makeText(MakeNote.this, "Ok", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MakeNote.this, MainActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(MakeNote.this, "Not Ok", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
