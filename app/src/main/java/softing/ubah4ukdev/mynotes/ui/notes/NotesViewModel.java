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

import softing.ubah4ukdev.mynotes.model.NotesRepository;
import softing.ubah4ukdev.mynotes.servicess.NoteService;

public class NotesViewModel extends ViewModel {

    public final NoteService noteService = NoteService.INSTANCE;

    private final MutableLiveData<NotesRepository> notesLiveData = new MutableLiveData<>();

    public NotesViewModel() {
    }

    public void fetchNotes() {
        notesLiveData.setValue(noteService.getNotes());
    }

    public LiveData<NotesRepository> getNotesLiveData() {
        return notesLiveData;
    }
}
