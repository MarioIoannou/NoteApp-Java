package com.example.noteappjava;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.example.noteappjava.adapters.NotesClickListener;
import com.example.noteappjava.adapters.NotesListAdapter;
import com.example.noteappjava.database.NotesDatabase;
import com.example.noteappjava.databinding.ActivityMainBinding;
import com.example.noteappjava.model.NotesModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private ActivityMainBinding binding;
    NotesModel selectedNote;
    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    int activityResult = result.getResultCode();
                    Intent data = result.getData();
                    if (activityResult == RESULT_OK){
                        NotesModel new_notes = (NotesModel) data.getSerializableExtra("note");
                        database.notesDAO().insertNote(new_notes);
                        notes.clear();
                        notes.addAll(database.notesDAO().getAllNotes());
                        notesListAdapter.notifyDataSetChanged();
                    } else if (activityResult == 102) {
                        
                    }
                }
            }
    );

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
                activityResultLauncher.launch(intent);
            }
        });
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode==101){
//            if (resultCode == Activity.RESULT_OK){
//                NotesModel new_notes = (NotesModel) data.getSerializableExtra("note");
//                database.notesDAO().insertNote(new_notes);
//                notes.clear();
//                notes.addAll(database.notesDAO().getAllNotes());
//                notesListAdapter.notifyDataSetChanged();
//            }
//        }
//
//    }

    private void updateRecyclerView(List<NotesModel> notes) {
        binding.rvNotes.setHasFixedSize(true);
        binding.rvNotes.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        notesListAdapter = new NotesListAdapter(MainActivity.this,notes,notesClickListener);
        binding.rvNotes.setAdapter(notesListAdapter);
    }

    private final NotesClickListener notesClickListener = new NotesClickListener() {
        @Override
        public void onClick(NotesModel notes) {
            Intent intent = new Intent(MainActivity.this, CreateNoteActivity.class);
            intent.putExtra("old_note",notes);
            activityResultLauncher.launch(intent);
        }

        @Override
        public void onLongClick(NotesModel notes, CardView cardView) {
            selectedNote = new NotesModel();
            selectedNote = notes;
            showPopUpDialog(cardView);
        }
    };

    private void showPopUpDialog(CardView cardView) {
        PopupMenu popupMenu = new PopupMenu(this,cardView);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.pin:
                if(selectedNote.getPinned()){
                    database.notesDAO().pinned(selectedNote.getId(),false);
                }else{
                    database.notesDAO().pinned(selectedNote.getId(),true);
                }
                notes.clear();
                notes.addAll(database.notesDAO().getAllNotes());
                notesListAdapter.notifyDataSetChanged();
                return true;

            case R.id.delete:
                database.notesDAO().deleteNote(selectedNote);
                notes.remove(selectedNote);
                notesListAdapter.notifyDataSetChanged();
        }
        return false;
    }
}