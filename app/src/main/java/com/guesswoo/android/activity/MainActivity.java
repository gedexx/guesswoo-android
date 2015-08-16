package com.guesswoo.android.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.guesswoo.android.R;
import com.guesswoo.android.fragment.MainFragment_;
import com.guesswoo.android.fragment.NavigationDrawerFragment;
import com.guesswoo.android.fragment.NotificationDrawerFragment;
import com.guesswoo.android.fragment.ProfileInformationsFragment_;
import com.guesswoo.android.fragment.ProfilePreferencesFragment_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.FragmentById;
import org.androidannotations.annotations.ViewById;

/**
 * Ecran présentant les fonctionnalités principales de l'application
 */
@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, NotificationDrawerFragment
        .OnFragmentInteractionListener {

    /**
     * Fragment de navigation (s'affiche en swipe de la gauche vers la droite)
     */
    @FragmentById(R.id.navigation_drawer)
    protected NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Fragment de notification (s'affiche en swipe de la droite vers la gauche)
     */
    @FragmentById(R.id.notification_drawer)
    protected NotificationDrawerFragment mNotificationDrawerFragment;

    /**
     * Composant permettant de gérer les swipe
     */
    @ViewById(R.id.drawer_layout)
    protected DrawerLayout drawerLayout;

    /**
     * Permet de stocker le titre des différents fragments ouverts via le fragment de navigation
     */
    private CharSequence mTitle;

    @AfterViews
    protected void init() {
        mTitle = getTitle();

        // Permet d'initialiser le fragment de navigation
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer, drawerLayout);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // Permet de mettre à jour le fragment à afficher en fonction de l'item choisi dans le fragment de navigation
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment;

        switch (position) {
            default:
            case 0:
                fragment = new MainFragment_();
                break;
            case 1:
                fragment = new ProfileInformationsFragment_();
                break;
            case 2:
                fragment = new ProfilePreferencesFragment_();
                break;
        }

        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();

        onSectionAttached(position);
    }

    public void onSectionAttached(int number) {
        switch (number) {
            default:
            case 0:
                mTitle = getString(R.string.title_home);
                break;
            case 1:
                mTitle = getString(R.string.title_profile_informations);
                break;
            case 2:
                mTitle = getString(R.string.title_profile_preferences);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Permet de gérer les éléments à afficher dans la barre de menu (les 3 petits points en haut à droite de
            // l'écran) en fonction de l'ouverture ou non du fragment de navigation
            getMenuInflater().inflate(R.menu.menu_main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Permet de gérer les interactions entre l'activity principale et le fragment de notification
     *
     * @param id Identifiant de l'item sélectionné
     */
    @Override
    public void onFragmentInteraction(String id) {

    }
}
