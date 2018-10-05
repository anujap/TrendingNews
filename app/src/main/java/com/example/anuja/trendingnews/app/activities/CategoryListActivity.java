package com.example.anuja.trendingnews.app.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.anuja.trendingnews.R;
import com.example.anuja.trendingnews.app.adapters.NewsAdapter;
import com.example.anuja.trendingnews.app.fragments.CategoriesFragment;
import com.example.anuja.trendingnews.model.Articles;
import com.example.anuja.trendingnews.viewmodel.CategoryViewModel;
import com.example.anuja.trendingnews.viewmodel.MainViewModel;

public class CategoryListActivity extends AppCompatActivity implements NewsAdapter.ListItemClickListener {

    private CategoryViewModel categoryViewModel;
    private RecyclerView mRecyclerView;
    private NewsAdapter mNewsAdapter;
    private String mCategoryName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);

        // get the viewmodel
        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);

        setUpActionBar();
        retrieveIntent();
        setUpRecyclerView();

        retrieveNewsByCategory(); // should be called in the onConnected method
    }

    /**
     * Function called to handle the action bar
     */
    private void setUpActionBar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_cat);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = this.getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * Function called to get the intent - this intent
     * has the CATAEGORY_NAME that was clicked
     * in the previous fragment
     */
    private void retrieveIntent() {
        Intent intent = getIntent();
        if(intent.hasExtra(CategoriesFragment.KEY_CATAEGORY_NAME)) {
            mCategoryName = intent.getStringExtra(CategoriesFragment.KEY_CATAEGORY_NAME);
        }
    }

    /**
     * Function called to setup recycler view and its adapter
     */
    private void setUpRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_cat_news);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mNewsAdapter = new NewsAdapter(null, this);
        mRecyclerView.setAdapter(mNewsAdapter);
    }

    @Override
    public void onListItemClicked(Articles article) {

    }

    private void retrieveNewsByCategory() {
        categoryViewModel.displayNewsByCategory(mCategoryName);
        categoryViewModel.getNewsByCategoryList().observe(this, newsByCategory -> {
            mNewsAdapter.swapLists(newsByCategory);
        });
    }
}
