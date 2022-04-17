package com.sahilhans0605.notemakercom;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sahilhans0605.notemakercom.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    NoteAdapter adapter;
    ArrayList<note> notes;
    FirebaseDatabase database;
    ProgressDialog dialog;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.newnote, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.newNote:
                Intent intent = new Intent(MainActivity.this, makeNote.class);
                startActivity(intent);

                break;

            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                Intent intent1 = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent1);
        }
        return true;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database = FirebaseDatabase.getInstance();
        dialog = new ProgressDialog(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage("Fetching your notes...");
        dialog.show();
        notes = new ArrayList<note>();
        adapter = new NoteAdapter(this, notes);
        binding.recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.recyclerView.setLayoutManager(layoutManager);
        database.getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("Note").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                notes.clear();
//                 DataSnapshot instance contains data from a Firebase Database location.
//                Any time you read Database data, you receive the data as a DataSnapshot.
                for (DataSnapshot snapshotUsers : snapshot.getChildren()) {
                    note user = snapshotUsers.getValue(note.class);
                    notes.add(user);
                }
                dialog.dismiss();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });

    }
}
