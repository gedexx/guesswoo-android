package com.gw.gw;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.gw.gw.dummy.DummyContent;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ViewById;

/**
 * Fragment représentant une liste de notifications
 */
@EFragment(R.layout.fragment_notificationdrawer)
public class NotificationDrawerFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private OnFragmentInteractionListener mListener;

    /**
     * La liste d'éléments du fragment
     */
    @ViewById(android.R.id.list)
    protected AbsListView mListView;

    @ViewById(R.id.swipe_container)
    protected SwipeRefreshLayout swipeRefreshLayout;

    /**
     * Instance du fragment, pour gérer les changements d'orientation de l'activity (permet, à la recréation de
     * l'activity qui expose le fragment, de récupérer toutes les valeurs avant basculement de l'affichage)
     *
     * @return
     */
    public static NotificationDrawerFragment newInstance() {
        NotificationDrawerFragment fragment = new NotificationDrawerFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public NotificationDrawerFragment() {
    }

    /**
     * Permet d'initialiser les composants du fragment
     */
    @AfterViews
    protected void init() {

        ListAdapter mAdapter = new ArrayAdapter<DummyContent.DummyItem>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, DummyContent.ITEMS);

        // Set the adapter
        mListView.setAdapter(mAdapter);

        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * Evènement déclenché lors d'un clic sur un item de la liste des éléments
     *
     * @param position
     */
    @ItemClick(android.R.id.list)
    public void onItemClick(int position) {
        if (null != mListener) {
            // Notifie l'activité qui expose le fragment avec l'item sélectionné dans la liste des éléments
            mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
        }
    }

    /**
     * Affiche un message si la liste d'éléments est vide
     *
     * @param emptyText Texte à afficher si la liste d'éléments est vide
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    /**
     * Permet de gérer l'évènement de rafraîchissement du SwipeRefreshLayout (malheureusement, l'event n'est pas
     * encore géré dans AndroidAnnotations)
     */
    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 5000);
    }

    /**
     * Permet de remonter l'évènement (de clic sur un élément, en l'occurence) à l'activity qui expose ce fragment
     * (permet ainsi de communiquer en plusieurs fragments liés à la même activité
     */
    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(String id);
    }

}
