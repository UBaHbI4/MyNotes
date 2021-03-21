package softing.ubah4ukdev.mynotes.ui.notes;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import softing.ubah4ukdev.mynotes.R;
import softing.ubah4ukdev.mynotes.model.Note;
import softing.ubah4ukdev.mynotes.model.Notes;

/****
 Project MyNotes
 Package softing.ubah4ukdev.mynotes.ui.notes

 Created by Ivan Sheynmaer

 2021.03.21
 v1.0
 */

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {
    private List<Note> notes;
    private INotesClickable iNotesClickable;

    NotesAdapter(Notes notesList, INotesClickable iNotesClickable) {
        this.notes = notesList.getNotes();
        this.iNotesClickable = iNotesClickable;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(view, iNotesClickable);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.myTextView.setText(note.getTitle());
        holder.note.setText(note.getNote());
        holder.dateCreated.setText(new SimpleDateFormat("dd.MM.yyyy")
                .format(new Date(note.getDateCreated())));

        holder.rect.setBackgroundColor(Color.parseColor(note.getColor()));
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;
        private TextView note;
        private TextView dateCreated;
        private LinearLayoutCompat rect;

        ViewHolder(View itemView, INotesClickable iNotesClickable) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.title);
            note = itemView.findViewById(R.id.noteText);
            dateCreated = itemView.findViewById(R.id.dateCreated);
            rect = itemView.findViewById(R.id.rect);

            NotesAdapter.this.iNotesClickable = iNotesClickable;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            iNotesClickable.onNoteClick(getAdapterPosition());
        }
    }
}