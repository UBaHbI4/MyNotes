package softing.ubah4ukdev.mynotes.ui.notes;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import softing.ubah4ukdev.mynotes.domain.FirestoreNotesRepository;

/****
 Project MyNotes
 Package softing.ubah4ukdev.mynotes.ui.notes

 Created by Ivan Sheynmaer

 2021.03.31
 v1.0
 */
public class NotesViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new NotesViewModel(FirestoreNotesRepository.INSTANCE);
    }
}
