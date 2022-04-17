package com.sahilhans0605.notemakercom;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sahilhans0605.notemakercom.databinding.ActivityMakeNoteBinding;

public class makeNote extends AppCompatActivity {
    ActivityMakeNoteBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMakeNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        binding.addToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String heading = binding.headingEdit.getText().toString();
                String noteText = binding.headingNote.getText().toString();
                DatabaseReference dbRef = database.getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("Note");
                String noteId = dbRef.push().getKey();

                note notes = new note(noteText, heading, noteId);
                if (heading != "" || noteText != "") {

                    database.getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("Note").child(noteId).setValue(notes).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(makeNote.this, "Note Added:)", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(makeNote.this, MainActivity.class);
                            startActivity(intent);
                            finishAffinity();
                        }
                    });

                } else {
                    Toast.makeText(makeNote.this, "Add something to the note!", Toast.LENGTH_LONG).show();
                }


            }
        });

    }

}
