package com.example.noteappjava;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.noteappjava.adapters.NotesClickListener;
import com.example.noteappjava.adapters.NotesListAdapter;
import com.example.noteappjava.database.NotesDatabase;
import com.example.noteappjava.databinding.ActivityMainBinding;
import com.example.noteappjava.model.NotesModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

//    RecyclerView recyclerView;
    NotesListAdapter notesListAdapter;
    List<NotesModel> notes = new ArrayList<>();
    NotesDatabase database;
//    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        database = NotesDatabase.getInstance(this);
        notes = database.notesDAO().getAllNotes();

        updateRecyclerView(notes);

        binding.fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,CreateNoteActivity.class);
                startActivityForResult(intent,101);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==101){
            if (resultCode == Activity.RESULT_OK){
                NotesModel new_notes = (NotesModel) data.getSerializableExtra("note");
                database.notesDAO().insertNote(new_notes);
                notes.clear();
                notes.addAll(database.notesDAO().getAllNotes());
                notesListAdapter.notifyDataSetChanged();
            }
        }
    }

    private void updateRecyclerView(List<NotesModel> notes) {
        binding.rvNotes.setHasFixedSize(true);
        binding.rvNotes.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        notesListAdapter = new NotesListAdapter(MainActivity.this,notes,notesClickListener);
        binding.rvNotes.setAdapter(notesListAdapter);
    }

    private final NotesClickListener notesClickListener = new NotesClickListener() {
        @Override
        public void onClick(NotesModel notes) {

        }

        @Override
        public void onLongClick(NotesModel notes, CardView cardView) {

        }
    };
}