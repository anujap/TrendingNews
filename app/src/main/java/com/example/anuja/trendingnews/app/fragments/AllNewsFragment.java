package com.example.anuja.trendingnews.app.fragments;

import android.app.LauncherActivity;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.anuja.trendingnews.R;
import com.example.anuja.trendingnews.app.adapters.NewsAdapter;
import com.example.anuja.trendingnews.model.Articles;
import com.example.anuja.trendingnews.viewmodel.MainViewModel;

public class AllNewsFragment extends Fragment implements NewsAdapter.ListItemClickListener {

    private MainViewModel mainViewModel;
    private RecyclerView mRecyclerView;
    private NewsAdapter mNewsAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // get the viewmodel
        mainViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        retrieveAllNews(); // should be called in the onConnected method
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_all_news);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mNewsAdapter = new NewsAdapter(null, this);
        mRecyclerView.setAdapter(mNewsAdapter);
    }

    /**
     * function called to get all the news
     */
    private void retrieveAllNews() {
        mainViewModel.displayAllNews();
        mainViewModel.getAllNewsList().observe(this, allNews -> {
            Log.i("Test", "allNews is: " + allNews);
            mNewsAdapter.swapLists(allNews);
        });
    }

    @Override
    public void onListItemClicked(Articles article) {

    }
}