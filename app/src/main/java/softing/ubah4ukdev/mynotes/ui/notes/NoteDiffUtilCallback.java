package softing.ubah4ukdev.mynotes.ui.notes;

import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

import softing.ubah4ukdev.mynotes.domain.Note;

/****
 Project MyNotes
 Package softing.ubah4ukdev.mynotes.ui.notes

 Created by Ivan Sheynmaer

 2021.03.26
 v1.0
 */
public class NoteDiffUtilCallback extends DiffUtil.Callback {

    private final List<Note> oldList;
    private final List<Note> newList;

    public NoteDiffUtilCallback(List<Note> oldList, List<Note> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        Note oldNote = oldList.get(oldItemPosition);
        Note newNote = newList.get(newItemPosition);
        return oldNote.getId() == newNote.getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        Note oldNote = oldList.get(oldItemPosition);
        Note newNote = newList.get(newItemPosition);
        return oldNote.getTitle().equals(newNote.getTitle())
                && oldNote.getNote().equals(newNote.getNote())
                && oldNote.getDateCreated() == newNote.getDateCreated()
                && oldNote.getColor() == newNote.getColor();
    }
}