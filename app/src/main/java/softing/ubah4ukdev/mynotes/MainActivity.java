package softing.ubah4ukdev.mynotes;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import softing.ubah4ukdev.mynotes.ui.notes.Publisher;
import softing.ubah4ukdev.mynotes.ui.notes.PublisherGetter;
import softing.ubah4ukdev.mynotes.ui.profile.ProfileFragment;

public class MainActivity extends AppCompatActivity implements PublisherGetter {

    private Publisher publisher = new Publisher();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NavigationView navigationView = findViewById(R.id.nav_view);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public Publisher getPublisher() {
        return publisher;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment navHostFragment = getSupportFragmentManager().getPrimaryNavigationFragment();
        Fragment currentFragment = navHostFragment.getChildFragmentManager().getFragments().get(0);
        if (currentFragment instanceof ProfileFragment) {
            currentFragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}