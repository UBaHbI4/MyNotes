package softing.ubah4ukdev.mynotes.ui.notes;

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
import softing.ubah4ukdev.mynotes.model.Notes;
import softing.ubah4ukdev.mynotes.servicess.DataHelper;
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
    private Notes myNotes;
    private View root;
    private RecyclerView recyclerView;
    private boolean isLandscape;
    private Note currentNote;
    private NotesAdapter adapter;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_notes, container, false);
        Publisher.getInstance().add(this);
        Init();
        adapter.notifyDataSetChanged();
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
            currentNote = myNotes.getNotes().get(0);
        }
        if (isLandscape) {
            showNotesLand(currentNote);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putSerializable(CURRENT_NOTE, currentNote);
        super.onSaveInstanceState(outState);
    }

    private void Init() {
        if (root != null) {
            recyclerView = root.findViewById(R.id.recyclerViewNotes);
            DataHelper data = new DataHelper();
            myNotes = data.load();
            currentNote = myNotes.getNotes().get(myNotes.getNotes().size() - 1);
            adapter = new NotesAdapter(myNotes, this);
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void onNoteClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(CURRENT_NOTE, myNotes.getNotes().get(position));
        currentNote = myNotes.getNotes().get(position);
        if (isLandscape) {
            showNotesLand(myNotes.getNotes().get(position));
        } else {
            showNotes(myNotes.getNotes().get(position));
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
        DataHelper data = new DataHelper();
        myNotes = data.load();
        adapter.notifyDataSetChanged();
    }
}