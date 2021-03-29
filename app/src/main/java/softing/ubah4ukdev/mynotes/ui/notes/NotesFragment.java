package softing.ubah4ukdev.mynotes.ui.notes;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import softing.ubah4ukdev.mynotes.R;
import softing.ubah4ukdev.mynotes.domain.Note;
import softing.ubah4ukdev.mynotes.domain.NotesRepository;
import softing.ubah4ukdev.mynotes.ui.detail.DetailFragment;

/****
 Project MyNotes
 Package softing.ubah4ukdev.mynotes.ui.notes

 Created by Ivan Sheynmaer

 2021.03.21
 v1.0
 */

public class NotesFragment extends Fragment implements INotesClickable, INotesLongClickable, INoteObserver {
    private final String CURRENT_NOTE = "CURRENT_NOTE";
    private RecyclerView recyclerView;
    private boolean isLandscape;
    private Note currentNote;
    private NotesAdapter adapter;
    private Publisher publisher;
    private NotesViewModel notesViewModel;
    private NavController navController;
    private FloatingActionButton fabAdd;
    public final NotesRepository notesRepository = NotesRepository.INSTANCE;

    public void onAttach(Context context) {
        super.onAttach(context);
        // получим обработчика подписок
        publisher = ((PublisherGetter) context).getPublisher();
        publisher.subscribe(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        publisher.unsubscribe(this);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new NotesAdapter(this, this);
        notesViewModel = new ViewModelProvider(this).get(NotesViewModel.class);
        notesViewModel.fetchNotes();
    }

    private void init(View view) {
        recyclerView = view.findViewById(R.id.recyclerViewNotes);
        recyclerView.setAdapter(adapter);
        fabAdd = view.findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(v -> {
            currentNote = null;
            Bundle bundle = new Bundle();
            bundle.putSerializable(CURRENT_NOTE, currentNote);
            navController.navigate(R.id.nav__item_edit, bundle);
        });
        isLandscape = isLandscape();
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);

        DrawerLayout drawerLayout = getActivity().findViewById(R.id.drawer_layout);
        Toolbar toolbar = view.findViewById(R.id.toolbar);

        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
        //делаем бургер
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_find:
                    Toast.makeText(getActivity(), "Поиск заметок пока не работает", Toast.LENGTH_LONG).show();
                    return false;
                case R.id.action_sort:
                    Toast.makeText(getActivity(), "Сортировка заметок пока не работает", Toast.LENGTH_LONG).show();
                    return false;
            }
            return super.onOptionsItemSelected(item);
        });
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notes, container, false);
        init(root);
        notesViewModel.getNotesLiveData()
                .observe(getViewLifecycleOwner(), new Observer<List<Note>>() {
                    @Override
                    public void onChanged(List<Note> notes) {
                        //adapter.clear();
                        //adapter.addItems(notes);
                        //adapter.notifyDataSetChanged();

                        NoteDiffUtilCallback noteDiffUtilCallback =
                                new NoteDiffUtilCallback(adapter.getData(), notes);
                        DiffUtil.DiffResult noteDiffResult = DiffUtil.calculateDiff(noteDiffUtilCallback);
                        adapter.clear();
                        adapter.addItems(notes);
                        noteDiffResult.dispatchUpdatesTo(adapter);
                    }
                });
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
            Note note = notesViewModel.notesRepository.getNote(0);
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

    //Показать заметку в альбомном режиме
    private void showNotesLand(Note current) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(CURRENT_NOTE, current);
        DetailFragment detailFragment = new DetailFragment();
        detailFragment.setArguments(bundle);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.detail_land, detailFragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }

    //Показать заметку в портретном режиме
    private void showNotes(Note current) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(CURRENT_NOTE, current);
        navController.navigate(R.id.nav_detail, bundle);
    }

    //Обновить данные в ресайкле
    @Override
    public void updateAllNotes() {
        //adapter.notifyDataSetChanged();
        notesViewModel.fetchNotes();
    }

    //Показать подробности заметки
    @Override
    public void onNoteClick(int position) {
        currentNote = notesViewModel.notesRepository.getNotes().get(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable(CURRENT_NOTE, currentNote);
        if (isLandscape) {
            showNotesLand(currentNote);
        } else {
            showNotes(currentNote);
        }
    }

    //Показать контекстное меню при длительном нажатии по карточке
    @Override
    public void onNoteLongClick(int position) {
        String[] items = {"Изменить", "Удалить", "Подробнее.."};
        String title = notesViewModel.notesRepository.getNotes().get(position).getTitle();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        currentNote = notesViewModel.notesRepository.getNotes().get(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable(CURRENT_NOTE, currentNote);

        builder.setTitle(title);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                switch (item) {
                    case 0:
                        navController.navigate(R.id.nav__item_edit, bundle);
                        break;
                    case 1:
                        notesRepository.deleteNote(currentNote);
                        notesViewModel.fetchNotes();
                        break;
                    case 2:
                        if (isLandscape) {
                            showNotesLand(currentNote);
                        } else {
                            showNotes(currentNote);
                        }
                        break;
                }
            }
        });
        builder.show();
    }
}