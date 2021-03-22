package softing.ubah4ukdev.mynotes.ui.detail;

import android.app.DatePickerDialog;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import softing.ubah4ukdev.mynotes.R;
import softing.ubah4ukdev.mynotes.model.Note;
import softing.ubah4ukdev.mynotes.servicess.DataHelper;
import softing.ubah4ukdev.mynotes.ui.notes.Publisher;

public class DetailFragment extends Fragment {
    private final String CURRENT_NOTE = "CURRENT_NOTE";

    private TextView titleView;
    private TextView noteView;
    private TextView dateCreatedView;
    private LinearLayoutCompat rect;
    private Note current;

    View root;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_detail, container, false);
        if (root != null) {
            if (getArguments() != null) {
                Note note = (Note) getArguments().getSerializable(CURRENT_NOTE);
                current = note;
                titleView = root.findViewById(R.id.titleTV);
                noteView = root.findViewById(R.id.noteTV);
                dateCreatedView = root.findViewById(R.id.dateCreatedTV);
                rect = root.findViewById(R.id.rect);
                titleView.setText(note.getTitle());
                noteView.setText(note.getNote());
                dateCreatedView.setText(new SimpleDateFormat("dd.MM.yyyy").format(new Date(note.getDateCreated())));
                rect.setBackgroundColor(Color.parseColor(note.getColor()));

                dateCreatedView.setOnClickListener(v -> {
                    callDatePicker();
                });
            }
        }
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(CURRENT_NOTE, current);
    }

    private boolean isLandscape() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if (isLandscape()) {
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
            navController.navigateUp();
        }
    }

    private void callDatePicker() {
        // получаем текущую дату
        final Calendar cal = Calendar.getInstance();
        int myYear = cal.get(Calendar.YEAR);
        int myMonth = cal.get(Calendar.MONTH);
        int myDay = cal.get(Calendar.DAY_OF_MONTH);

        // инициализируем диалог выбора даты текущими значениями
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String editTextDateParam = dayOfMonth + "." + (monthOfYear + 1) + "." + year;
                        SimpleDateFormat f = new SimpleDateFormat("dd.MM.yyyy");
                        Date d = null;
                        long milliseconds = 0;
                        try {
                            d = f.parse(editTextDateParam);
                            milliseconds = d.getTime();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        DataHelper data = new DataHelper();
                        data.saveDate(current, milliseconds);
                        dateCreatedView.setText(new SimpleDateFormat("dd.MM.yyyy").format(new Date(current.getDateCreated())));
                        Publisher.getInstance().startUpdate();
                    }
                }, myYear, myMonth, myDay);
        datePickerDialog.show();
    }
}