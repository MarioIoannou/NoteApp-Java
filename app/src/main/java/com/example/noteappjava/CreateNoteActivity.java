package com.example.noteappjava;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.noteappjava.databinding.ActivityCreateNoteBinding;
import com.example.noteappjava.databinding.ActivityMainBinding;
import com.example.noteappjava.model.NotesModel;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateNoteActivity extends AppCompatActivity {
    private ActivityCreateNoteBinding binding;
    NotesModel notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateNoteBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.imgSaveToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = binding.etTitle.getText().toString();
                String description = binding.etNotes.getText().toString();

                if(description.isEmpty()){
                    Toast.makeText(CreateNoteActivity.this, "Please provide some notes", Toast.LENGTH_SHORT).show();
                }
                SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy HH:mm a");
                Date date = new Date();

                notes = new NotesModel();
                notes.setTitle(title);
                notes.setNotes(description);
                notes.setDatetime(formatter.format(date));

                Intent intent = new Intent();
                intent.putExtra("note",notes);
                setResult(Activity.RESULT_OK,intent);
                finish();
            }
        });
    }
}