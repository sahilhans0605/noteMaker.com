package com.sahilhans0605.notemakercom;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.sahilhans0605.notemakercom.databinding.SampleNoteBinding;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.chatViewHolder> {
    Context context;
    ArrayList<note> notes;
    FirebaseDatabase database;
    AlertDialog.Builder alertDialog;

    public NoteAdapter(Context context, ArrayList<note> notes) {
        this.context = context;
        this.notes = notes;
    }

    @NonNull
    @Override
    public chatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_note, parent, false);
        return new chatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull chatViewHolder holder, int position) {
        alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle("Alert!!");
        alertDialog.setMessage("Are you sure you want to delete this note?");
        note noteText = notes.get(position);
        holder.binding.heading.setText(noteText.getHeading());
        holder.binding.note.setText(noteText.getNote());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.binding.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditNote.class);
                intent.putExtra("heading", noteText.getHeading());
                intent.putExtra("note", noteText.getNote());
                intent.putExtra("noteId", noteText.getNoteId());
                context.startActivity(intent);
            }
        });
        holder.binding.deleteButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                database.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("Note").child(noteText.getNoteId()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(context, "Note Deleted!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(context, "Note not deleted!", Toast.LENGTH_LONG).show();
                    }
                });
                alertDialog.create().show();


            }
        });

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class chatViewHolder extends RecyclerView.ViewHolder {

        SampleNoteBinding binding;

        public chatViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = SampleNoteBinding.bind(itemView);

        }
    }
}
