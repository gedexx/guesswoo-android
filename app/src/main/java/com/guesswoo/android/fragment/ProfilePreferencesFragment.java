package com.guesswoo.android.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

import com.guesswoo.android.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

@EFragment(R.layout.fragment_profile_preferences)
public class ProfilePreferencesFragment extends Fragment {

    /**
     * Instance du fragment, pour gérer les changements d'orientation de l'activity (permet, à la recréation de
     * l'activity qui expose le fragment, de récupérer toutes les valeurs avant basculement de l'affichage)
     *
     * @return
     */
    public static ProfilePreferencesFragment newInstance() {
        ProfilePreferencesFragment fragment = new ProfilePreferencesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ProfilePreferencesFragment() {
    }

    @AfterViews
    protected void init() {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
