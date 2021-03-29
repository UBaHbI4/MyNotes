package softing.ubah4ukdev.mynotes.ui.notes;

/****
 Project MyNotes
 Package softing.ubah4ukdev.mynotes.ui.notes

 Created by Ivan Sheynmaer

 2021.03.25
 v1.0
 */

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import softing.ubah4ukdev.mynotes.domain.Note;
import softing.ubah4ukdev.mynotes.domain.NotesRepository;

public class NotesViewModel extends ViewModel {

    public final NotesRepository notesRepository = NotesRepository.INSTANCE;

    private final MutableLiveData<List<Note>> notesLiveData = new MutableLiveData<>();

    public NotesViewModel() {
    }

    public void fetchNotes() {
        notesLiveData.setValue(notesRepository.getNotes());
    }

    public LiveData<List<Note>> getNotesLiveData() {
        return notesLiveData;
    }
}
