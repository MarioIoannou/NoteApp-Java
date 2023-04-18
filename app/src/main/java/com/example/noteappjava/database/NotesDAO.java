package com.example.noteappjava.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.noteappjava.model.NotesModel;

import java.util.List;

@Dao
public interface NotesDAO{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNote(NotesModel note);

    @Query("SELECT * FROM notesTable ORDER BY id DESC")
    List<NotesModel> getAllNotes();

    @Query("UPDATE notesTable SET title = :title, notes = :notes WHERE id = :id")
    void updateNote(int id,String title, String notes);

    @Delete
    void deleteNote(NotesModel note);

    @Query("UPDATE notesTable SET pinned = :pin WHERE ID = :id")
    void pinned(int id,boolean pin);
}
