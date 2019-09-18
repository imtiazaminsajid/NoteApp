package com.example.noteapp;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MakeNote extends AppCompatActivity {

    EditText MakeNoteTitle, MakeNoteNote;
    Button MakeNoteSaveBtn, MakeNoteHomeBTN;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_note);

        setTitle("Make A Note");

        MakeNoteNote = findViewById(R.id.MakeNoteNote);
        MakeNoteTitle = findViewById(R.id.MakeNoteTitle);
        MakeNoteSaveBtn = findViewById(R.id.MakeNoteSaveData);
        MakeNoteHomeBTN = findViewById(R.id.MakeNoteHomeBTN);

        databaseHelper =  new DatabaseHelper(this);

        final Intent intent = getIntent();
        String id = intent.getStringExtra("ID");
        MakeNoteNote.setText(intent.getStringExtra("Note"));
        MakeNoteTitle.setText(intent.getStringExtra("Title"));

        MakeNoteSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String note = MakeNoteNote.getText().toString();
                String title = MakeNoteTitle.getText().toString();

                if (title.isEmpty()){
                    MakeNoteTitle.setError("Enter Title");
                    MakeNoteTitle.requestFocus();
                    return;
                }
                if (note.isEmpty()){
                    MakeNoteNote.setError("Enter Note");
                    MakeNoteNote.requestFocus();
                    return;
                }

                long rowNumber = databaseHelper.saveData(title, note);
                if (rowNumber>-1){
                    Toast.makeText(MakeNote.this, "Ok", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MakeNote.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(MakeNote.this, "Not Ok", Toast.LENGTH_SHORT).show();
                }
            }
        });

        MakeNoteHomeBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MakeNote.this, MainActivity.class);
                startActivity(intent1);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {

        final Dialog dialog = new Dialog(MakeNote.this);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(R.layout.custom_exit);
        dialog.setCancelable(false);

        Button no = dialog.findViewById(R.id.dialogNo);
        Button yes = dialog.findViewById(R.id.dialogYes);

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MakeNote.super.onBackPressed();
            }
        });

        dialog.show();

    }
}
