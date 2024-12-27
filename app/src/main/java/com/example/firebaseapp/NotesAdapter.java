package com.example.firebaseapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {

    private List<String> noteList;
    private NoteClickListener noteClickListener;

    public NotesAdapter(List<String> noteList, NoteClickListener noteClickListener) {
        this.noteList = noteList;
        this.noteClickListener = noteClickListener;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        String note = noteList.get(position);
        holder.noteTextView.setText(note);

        // Set click listener for each item in the RecyclerView
        holder.itemView.setOnClickListener(v -> noteClickListener.onNoteClick(note));
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public interface NoteClickListener {
        void onNoteClick(String note);
    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder {

        public TextView noteTextView;

        public NoteViewHolder(View view) {
            super(view);
            noteTextView = view.findViewById(android.R.id.text1);
        }
    }
}
