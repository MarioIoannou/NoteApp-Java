package com.example.noteappjava.adapters;

import androidx.cardview.widget.CardView;

import com.example.noteappjava.model.NotesModel;

public interface NotesClickListener {

    void onClick(NotesModel notes);
    void onLongClick(NotesModel notes, CardView cardView);

}
