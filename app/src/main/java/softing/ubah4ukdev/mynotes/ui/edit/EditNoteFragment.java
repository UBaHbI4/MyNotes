package softing.ubah4ukdev.mynotes.ui.edit;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import softing.ubah4ukdev.mynotes.R;
import softing.ubah4ukdev.mynotes.domain.Callback;
import softing.ubah4ukdev.mynotes.domain.FirestoreNotesRepository;
import softing.ubah4ukdev.mynotes.domain.Note;
import softing.ubah4ukdev.mynotes.ui.notes.INoteObserver;
import softing.ubah4ukdev.mynotes.ui.notes.Publisher;
import softing.ubah4ukdev.mynotes.ui.notes.PublisherGetter;
import top.defaults.colorpicker.ColorPickerPopup;

public class EditNoteFragment extends Fragment implements INoteObserver {
    private TextInputEditText dateInput;
    private final String CURRENT_NOTE = "CURRENT_NOTE";
    //public final MockNotesRepository mockNotesRepository = MockNotesRepository.INSTANCE;
    public final FirestoreNotesRepository firestoreNotesRepository = FirestoreNotesRepository.INSTANCE;
    private TextInputEditText titleInput;
    private TextInputEditText noteInput;
    private NavController navController;
    private MaterialButton btnSave;
    private Publisher publisher;
    private LinearLayoutCompat colorSelector;

    public EditNoteFragment() {
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        publisher = ((PublisherGetter) context).getPublisher(); // получим обработчика подписок
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_edit_note, container, false);
        init(root);

        if (getArguments() != null) {
            Note note = (Note) getArguments().getSerializable(CURRENT_NOTE);
            if (note != null) {
                titleInput.setText(note.getTitle());
                noteInput.setText(note.getNote());
                dateInput.setText(new SimpleDateFormat("dd.MM.yyyy").format(new Date(note.getDateCreated())));
                colorSelector.setBackgroundColor(note.getColor());
            } else {
            }

            btnSave.setOnClickListener(v -> {
                String titleSave = titleInput.getText().toString();
                String noteSave = noteInput.getText().toString();
                int color = ((ColorDrawable) colorSelector.getBackground()).getColor();
                long dateSave = 0;
                Date date = null;
                try {
                    date = new SimpleDateFormat("dd.MM.yyyy").parse(dateInput.getText().toString());
                    dateSave = date.getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Note saveNote = new Note(titleSave, noteSave, dateSave, color);
                if (note != null) {
                    firestoreNotesRepository.updateNote(note, saveNote, new Callback<Boolean>() {
                        @Override
                        public void onResult(Boolean value) {
                            publisher.startUpdate();
                            navController.navigateUp();
                        }
                    });
                } else {
                    firestoreNotesRepository.addNote(saveNote, new Callback<Boolean>() {
                        @Override
                        public void onResult(Boolean value) {
                            publisher.startUpdate();
                            navController.navigateUp();
                        }
                    });
                }
            });
        }
        return root;
    }

    private void init(View view) {
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        titleInput = view.findViewById(R.id.titleInput);
        noteInput = view.findViewById(R.id.noteInput);
        btnSave = view.findViewById(R.id.btnSave);
        colorSelector = view.findViewById(R.id.colorSelector);
        dateInput = view.findViewById(R.id.dateInput);
        colorSelector.setOnClickListener(v -> {
            new ColorPickerPopup.Builder(getContext())
                    .initialColor(Color.RED)
                    .enableBrightness(false)
                    .enableAlpha(false)
                    .okTitle("OK")
                    .cancelTitle("CANCEL")
                    .showIndicator(true)
                    .showValue(false)
                    .build()
                    .show(colorSelector, new ColorPickerPopup.ColorPickerObserver() {
                        @Override
                        public void onColorPicked(int color) {
                            colorSelector.setBackgroundColor(color);
                        }
                    });
        });

        dateInput.setOnClickListener(v -> {
            callDatePicker();
        });

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
    }

    private void callDatePicker() {
        // получаем текущую дату
        Calendar cal = Calendar.getInstance();
        int myYear = cal.get(Calendar.YEAR);
        int myMonth = cal.get(Calendar.MONTH);
        int myDay = cal.get(Calendar.DAY_OF_MONTH);

        // инициализируем диалог выбора даты текущими значениями
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.clear();
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, (monthOfYear));
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        dateInput.setText(new SimpleDateFormat("dd.MM.yyyy").format(calendar.getTimeInMillis()));
                    }
                }, myYear, myMonth, myDay);
        datePickerDialog.show();
    }

    @Override
    public void updateAllNotes() {
    }
}