package softing.ubah4ukdev.mynotes;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import softing.ubah4ukdev.mynotes.ui.notes.Publisher;
import softing.ubah4ukdev.mynotes.ui.notes.PublisherGetter;

public class MainActivity extends AppCompatActivity implements PublisherGetter {

    private AppBarConfiguration mAppBarConfiguration;
    private Publisher publisher = new Publisher();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav__item_notes, R.id.nav_detail, R.id.nav_item_about, R.id.nav__item_settings)
                .setOpenableLayout(drawer)
                .build();

//        toolbar.findViewById(R.id.action_find).setOnClickListener(v -> {
//            Toast.makeText(this, "Поиск заметок...", Toast.LENGTH_LONG).show();
//        });

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_find:
                Toast.makeText(this, "Поиск заметок пока не работает", Toast.LENGTH_LONG).show();
                return false;
            case R.id.action_sort:
                Toast.makeText(this, "Сортировка заметок пока не работает", Toast.LENGTH_LONG).show();
                return false;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public Publisher getPublisher() {
        return publisher;
    }
}