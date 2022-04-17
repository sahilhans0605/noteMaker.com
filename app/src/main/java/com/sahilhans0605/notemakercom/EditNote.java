package com.sahilhans0605.notemakercom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.sahilhans0605.notemakercom.databinding.ActivityEditNoteBinding;

import java.util.HashMap;

public class EditNote extends AppCompatActivity {
    ActivityEditNoteBinding binding;
    FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database = FirebaseDatabase.getInstance();
        String noteHeading = getIntent().getStringExtra("heading");
        String note = getIntent().getStringExtra("note");
        String noteId = getIntent().getStringExtra("noteId");
        binding.editNote.setText(note);
        binding.editNoteHeading.setText(noteHeading);

        binding.updateNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateNote(noteHeading, note, noteId);
            }
        });
    }

    private void updateNote(String noteHeading, String note, String noteId) {

        HashMap<String, Object> map = new HashMap<>();
        map.put("heading", binding.editNoteHeading.getText().toString());
        map.put("note", binding.editNote.getText().toString());
        database.getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("Note").child(noteId).updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

                Toast.makeText(EditNote.this, "Note Updated", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(EditNote.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}