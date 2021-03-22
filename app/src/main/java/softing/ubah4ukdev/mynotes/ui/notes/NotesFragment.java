package softing.ubah4ukdev.mynotes.ui.notes;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import softing.ubah4ukdev.mynotes.R;
import softing.ubah4ukdev.mynotes.model.Note;
import softing.ubah4ukdev.mynotes.model.NotesRepository;
import softing.ubah4ukdev.mynotes.servicess.NoteService;
import softing.ubah4ukdev.mynotes.ui.detail.DetailFragment;

/****
 Project MyNotes
 Package softing.ubah4ukdev.mynotes.ui.notes

 Created by Ivan Sheynmaer

 2021.03.21
 v1.0
 */

public class NotesFragment extends Fragment implements INotesClickable, INoteObserver {
    private final String CURRENT_NOTE = "CURRENT_NOTE";
    private NotesRepository myNotesRepository;
    private View root;
    private RecyclerView recyclerView;
    private boolean isLandscape;
    private Note currentNote;
    private NotesAdapter adapter;
    private Publisher publisher;

    public void onAttach(Context context) {
        super.onAttach(context);
        publisher = ((PublisherGetter) context).getPublisher(); // получим обработчика подписок
    }

    @Override
    public void onDetach() {
        super.onDetach();
        publisher.unsubscribe(this);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_notes, container, false);

        publisher.subscribe(this);
        init();
        isLandscape = isLandscape();
        return root;
    }

    private boolean isLandscape() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            currentNote = (Note) savedInstanceState.getSerializable(CURRENT_NOTE);
        } else {
            if (myNotesRepository != null) {
                currentNote = myNotesRepository.getNotes().get(0);
            }
        }
        if (isLandscape) {
            showNotesLand(currentNote);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (currentNote != null) {
            outState.putSerializable(CURRENT_NOTE, currentNote);
        }
    }

    private void init() {
        if (root != null) {
            recyclerView = root.findViewById(R.id.recyclerViewNotes);
            NoteService data = new NoteService();
            myNotesRepository = data.getNotes();
            if (myNotesRepository == null) {
                return;
            }
            currentNote = myNotesRepository.getNotes().get(myNotesRepository.getNotes().size() - 1);
            adapter = new NotesAdapter(myNotesRepository, this);
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void onNoteClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(CURRENT_NOTE, myNotesRepository.getNotes().get(position));
        currentNote = myNotesRepository.getNotes().get(position);
        if (isLandscape) {
            showNotesLand(myNotesRepository.getNotes().get(position));
        } else {
            showNotes(myNotesRepository.getNotes().get(position));
        }
    }

    private void showNotesLand(Note current) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(CURRENT_NOTE, current);
        DetailFragment detailFragment = new DetailFragment();
        detailFragment.setArguments(bundle);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.detail_land, detailFragment);  // замена фрагмента
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }

    private void showNotes(Note current) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(CURRENT_NOTE, current);
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.nav_detail, bundle);
    }

    @Override
    public void update() {
        NoteService data = new NoteService();
        myNotesRepository = data.getNotes();
        adapter.notifyDataSetChanged();
    }

}