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
import softing.ubah4ukdev.mynotes.model.NotesRepository;

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
    private INotesLongClickable iNotesLongClickable;

    NotesAdapter(NotesRepository notesRepositoryList, INotesClickable iNotesClickable, INotesLongClickable iNotesLongClickable) {
        this.notes = notesRepositoryList.getNotes();
        this.iNotesClickable = iNotesClickable;
        this.iNotesLongClickable = iNotesLongClickable;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(view, iNotesClickable, iNotesLongClickable);
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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView myTextView;
        private TextView note;
        private TextView dateCreated;
        private LinearLayoutCompat rect;

        ViewHolder(View itemView, INotesClickable iNotesClickable, INotesLongClickable iNotesLongClickable) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.title);
            note = itemView.findViewById(R.id.noteText);
            dateCreated = itemView.findViewById(R.id.dateCreated);
            rect = itemView.findViewById(R.id.rect);

            NotesAdapter.this.iNotesClickable = iNotesClickable;
            NotesAdapter.this.iNotesLongClickable = iNotesLongClickable;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            iNotesClickable.onNoteClick(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            iNotesLongClickable.onNoteLongClick(getAdapterPosition());
            return true;
        }
    }
}