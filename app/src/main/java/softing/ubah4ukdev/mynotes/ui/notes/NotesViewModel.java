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

import softing.ubah4ukdev.mynotes.domain.Callback;
import softing.ubah4ukdev.mynotes.domain.INotesRepository;
import softing.ubah4ukdev.mynotes.domain.Note;

public class NotesViewModel extends ViewModel {

    private final MutableLiveData<List<Note>> notesLiveData = new MutableLiveData<>();

    public final INotesRepository repository;

    public NotesViewModel(INotesRepository repository) {
        this.repository = repository;
    }

    public void fetchNotes() {
        repository.getNotes(new Callback<List<Note>>() {
            @Override
            public void onResult(List<Note> value) {
                notesLiveData.setValue(value);
            }
        });
    }

    public LiveData<List<Note>> getNotesLiveData() {
        return notesLiveData;
    }
}
