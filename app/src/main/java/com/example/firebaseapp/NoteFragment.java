package com.example.firebaseapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NoteFragment extends Fragment {

    private EditText noteInput;
    private Button saveNoteButton, editNoteButton, deleteNoteButton, retrieveNoteButton;
    private RecyclerView notesRecyclerView;
    private NotesAdapter notesAdapter;
    private List<String> noteList = new ArrayList<>();
    private String selectedNote = "";  // To keep track of the selected note

    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "NotePrefs";
    private static final String NOTES_KEY = "savedNotes";

    public NoteFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_note, container, false);

        // Initialize UI components
        noteInput = view.findViewById(R.id.noteInput);
        saveNoteButton = view.findViewById(R.id.saveNoteButton);
        editNoteButton = view.findViewById(R.id.editNoteButton);
        deleteNoteButton = view.findViewById(R.id.deleteNoteButton);
        retrieveNoteButton = view.findViewById(R.id.retrieveNoteButton);
        notesRecyclerView = view.findViewById(R.id.notesRecyclerView);

        // Initialize SharedPreferences
        sharedPreferences = requireActivity().getSharedPreferences(PREF_NAME, requireContext().MODE_PRIVATE);

        // Set up RecyclerView
        notesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        notesAdapter = new NotesAdapter(noteList, new NotesAdapter.NoteClickListener() {
            @Override
            public void onNoteClick(String note) {
                // Set the selected note when clicked
                selectedNote = note;
                noteInput.setText(note); // Display it in the input field
            }
        });
        notesRecyclerView.setAdapter(notesAdapter);

        // Load saved notes from SharedPreferences
        loadNotes();

        // Set up button actions
        saveNoteButton.setOnClickListener(v -> saveNote());
        editNoteButton.setOnClickListener(v -> editNote());
        deleteNoteButton.setOnClickListener(v -> deleteNote());
        retrieveNoteButton.setOnClickListener(v -> retrieveNote());

        return view;
    }

    private void saveNote() {
        String note = noteInput.getText().toString().trim();
        if (!note.isEmpty()) {
            // Save the new note to the list
            noteList.add(note);
            notesAdapter.notifyDataSetChanged();

            // Save the updated list to SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putStringSet(NOTES_KEY, new HashSet<>(noteList));
            editor.apply();

            noteInput.setText("");  // Clear the input field
            Toast.makeText(getContext(), "Note saved successfully!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Please enter a note to save.", Toast.LENGTH_SHORT).show();
        }
    }

    private void editNote() {
        if (!selectedNote.isEmpty()) {
            String updatedNote = noteInput.getText().toString().trim();
            if (!updatedNote.isEmpty()) {
                int position = noteList.indexOf(selectedNote);
                if (position != -1) {
                    noteList.set(position, updatedNote);
                    notesAdapter.notifyItemChanged(position);

                    // Save the updated list to SharedPreferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putStringSet(NOTES_KEY, new HashSet<>(noteList));
                    editor.apply();

                    Toast.makeText(getContext(), "Note updated successfully!", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Toast.makeText(getContext(), "No note selected for editing.", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteNote() {
        if (!selectedNote.isEmpty()) {
            int position = noteList.indexOf(selectedNote);
            if (position != -1) {
                noteList.remove(position);
                notesAdapter.notifyItemRemoved(position);

                // Save the updated list to SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putStringSet(NOTES_KEY, new HashSet<>(noteList));
                editor.apply();

                noteInput.setText(""); // Clear the input field
                Toast.makeText(getContext(), "Note deleted successfully!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "No note selected for deletion.", Toast.LENGTH_SHORT).show();
        }
    }

    private void retrieveNote() {
        if (!selectedNote.isEmpty()) {
            noteInput.setText(selectedNote); // Display the selected note in the input field
            Toast.makeText(getContext(), "Note retrieved successfully!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "No note selected for retrieval.", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadNotes() {
        // Load saved notes from SharedPreferences
        Set<String> savedNotes = sharedPreferences.getStringSet(NOTES_KEY, new HashSet<>());
        if (savedNotes != null) {
            noteList.clear();
            noteList.addAll(savedNotes);
            notesAdapter.notifyDataSetChanged();
        }
    }
}
