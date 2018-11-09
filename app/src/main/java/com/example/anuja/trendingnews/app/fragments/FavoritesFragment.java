package com.example.anuja.trendingnews.app.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;

import com.example.anuja.trendingnews.app.adapters.NewsAdapter;
import com.example.anuja.trendingnews.model.Articles;
import com.example.anuja.trendingnews.viewmodel.MainViewModel;

import java.util.ArrayList;

public class FavoritesFragment extends Fragment {

    private MainViewModel mainViewModel;
    private RecyclerView mRecyclerView;
    private NewsAdapter mNewsAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // get the viewmodel
        mainViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);

        retrieveFavoriteNews(); // should be called in the onConnected method
    }

    /**
     * function called to retrieve news that are marked as favorite by the user
     */
    private void retrieveFavoriteNews() {
        mainViewModel.retrieveFavNews();
    }
}
