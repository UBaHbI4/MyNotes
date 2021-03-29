package softing.ubah4ukdev.mynotes.ui.detail;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.card.MaterialCardView;

import java.text.SimpleDateFormat;
import java.util.Date;

import softing.ubah4ukdev.mynotes.R;
import softing.ubah4ukdev.mynotes.domain.Note;
import softing.ubah4ukdev.mynotes.domain.NotesRepository;
import softing.ubah4ukdev.mynotes.ui.notes.Publisher;
import softing.ubah4ukdev.mynotes.ui.notes.PublisherGetter;

public class DetailFragment extends Fragment {
    private final String CURRENT_NOTE = "CURRENT_NOTE";
    public final NotesRepository notesRepository = NotesRepository.INSTANCE;
    private TextView titleView;
    private TextView noteView;
    private TextView dateCreatedView;
    private LinearLayoutCompat rect;
    private Note current;
    private MaterialCardView detailCard;
    private Publisher publisher;
    private NavController navController;

    public void onAttach(Context context) {
        super.onAttach(context);
        publisher = ((PublisherGetter) context).getPublisher(); // получим обработчика подписок
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        DetailViewModel detailViewModel = new ViewModelProvider(this).get(DetailViewModel.class);
        View root = inflater.inflate(R.layout.fragment_detail, container, false);
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);

        init(root);

        if (getArguments() != null) {
            Note note = (Note) getArguments().getSerializable(CURRENT_NOTE);
            titleView = root.findViewById(R.id.titleTV);
            noteView = root.findViewById(R.id.noteTV);
            dateCreatedView = root.findViewById(R.id.dateCreatedTV);
            rect = root.findViewById(R.id.rect);
            detailCard = root.findViewById(R.id.detailCard);
            if (note != null) {
                detailCard.setVisibility(View.VISIBLE);
                current = note;
                titleView.setText(note.getTitle());
                noteView.setText(note.getNote());
                dateCreatedView.setText(new SimpleDateFormat("dd.MM.yyyy").format(new Date(note.getDateCreated())));
                rect.setBackgroundColor(note.getColor());
            } else {
                detailCard.setVisibility(View.GONE);
            }
        }
        return root;
    }

    private void init(View view) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        //В альбомном режиме нету тулбара у детайлфрагмент
        if (toolbar != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
                ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
                ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
            }

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    navController.navigate(R.id.nav__item_notes);
                }
            });
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (current != null) {
            outState.putSerializable(CURRENT_NOTE, current);
        }
    }

    private boolean isLandscape() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isLandscape()) {
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
            navController.navigateUp();
        }
    }
}