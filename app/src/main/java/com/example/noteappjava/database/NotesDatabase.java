package com.example.noteappjava.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.noteappjava.model.NotesModel;
import com.example.noteappjava.utils.Constants;

@Database(entities = NotesModel.class,version = 1,exportSchema = false)
public abstract class NotesDatabase extends RoomDatabase {

    private static NotesDatabase database;
    private static String DATABASE_NAME = Constants.DATABASE_NAME;

    public synchronized static NotesDatabase getInstance(Context context){
        if (database == null){
            database = Room.databaseBuilder(
                    context.getApplicationContext(),
                    NotesDatabase.class,
                    DATABASE_NAME
                    ).allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }
    public abstract NotesDAO notesDAO();
}
