package com.example.noteapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    Context context;
    ArrayList<NoteModel> noteModels;

    public CustomAdapter(Context context, ArrayList<NoteModel> noteModels) {
        this.context = context;
        this.noteModels = noteModels;
    }

    @Override
    public int getCount() {
        return noteModels.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView TitleID, NoteID;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.card_item, parent, false);

        TextView titleID = view.findViewById(R.id.titleID);
        TextView noteID = view.findViewById(R.id.noteID);

        NoteModel noteModel = noteModels.get(position);

        titleID.setText(noteModel.getTitle());
        noteID.setText(noteModel.getNote());

        return view;
    }
}
