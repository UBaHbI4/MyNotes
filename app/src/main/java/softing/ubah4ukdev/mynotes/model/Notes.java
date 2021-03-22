package softing.ubah4ukdev.mynotes.model;

import java.util.ArrayList;
import java.util.List;

/****
 Project MyNotes
 Package softing.ubah4ukdev.mynotes.model

 Created by Ivan Sheynmaer

 2021.03.20
 v1.0
 */
public class Notes {
    private List<Note> notes;

    public Notes() {
        notes = new ArrayList<>();
    }

    public boolean add(Note note) {
        try {
            note.setId(notes.size());
            notes.add(note);
            return true;
        } catch (Exception err) {
            return false;
        }
    }

    public boolean remove(Note note) {
        try {
            notes.remove(note);
            return true;
        } catch (Exception err) {
            return false;
        }
    }

    public boolean remove(int index) {
        try {
            notes.remove(index);
            return true;
        } catch (Exception err) {
            return false;
        }
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public Note findByID(int id) {
        for (Note note : notes) {
            if (note.getId() == id) {
                return note;
            }
        }
        return null;
    }
}
