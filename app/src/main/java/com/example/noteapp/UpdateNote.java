package com.example.noteapp;

import android.app.Dialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class UpdateNote extends AppCompatActivity {

    EditText UpdateTitle, updateNoteET;
    Button UpdateBtn,UpdateNoteHomeBTN;

    DatabaseHelper databaseHelper;
    SQLiteDatabase sqLiteDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_note);

        setTitle("Update You Note");

        UpdateTitle = findViewById(R.id.UpdateTitle);
        updateNoteET = findViewById(R.id.UpdateNote);
        UpdateBtn = findViewById(R.id.UpdateBtn);
        UpdateNoteHomeBTN = findViewById(R.id.UpdateNoteHomeBTN);

        databaseHelper =  new DatabaseHelper(this);

        Intent intent = getIntent();
        final String id = intent.getStringExtra("ID");
        updateNoteET.setText(intent.getStringExtra("Note"));
        UpdateTitle.setText(intent.getStringExtra("Title"));



        UpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String noteUpdate = updateNoteET.getText().toString();
                String titleUpdate = UpdateTitle.getText().toString();

                databaseHelper.updateData(id,titleUpdate, noteUpdate);

                Intent intent1 = new Intent(UpdateNote.this, MainActivity.class);
                startActivity(intent1);
                finish();


            }
        });

        UpdateNoteHomeBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(UpdateNote.this, MainActivity.class);
                startActivity(intent1);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {

        final Dialog dialog = new Dialog(UpdateNote.this);

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
                UpdateNote.super.onBackPressed();
            }
        });

        dialog.show();

    }
}
