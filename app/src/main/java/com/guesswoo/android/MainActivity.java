package com.guesswoo.android;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.FragmentById;
import org.androidannotations.annotations.ViewById;

/**
 * Ecran présentant les fonctionnalités principales de l'application
 */
@EActivity(R.layout.activity_main)
public class MainActivity extends ActionBarActivity
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
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_home);
                break;
            case 2:
                mTitle = getString(R.string.title_profile_informations);
                break;
            case 3:
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
            getMenuInflater().inflate(R.menu.main, menu);
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

    /**
     * Fragment substituable : concrètement, c'est un fragment que l'on va insérer dans le layout de l'activité (ou
     * retirer) en fonction de l'élément cliqué dans le fragment de navigation
     */
    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView;
            switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
                case 1:
                    rootView = inflater.inflate(R.layout.fragment_main, container, false);
                    break;
                case 2:
                    rootView = inflater.inflate(R.layout.fragment_profile_informations, container, false);
                    break;
                case 3:
                    rootView = inflater.inflate(R.layout.fragment_profile_preferences, container, false);
                    break;
                default:
                    rootView = null;
            }

            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
