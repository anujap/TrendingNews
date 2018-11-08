package com.example.anuja.trendingnews.app.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.anuja.trendingnews.R;
import com.example.anuja.trendingnews.app.activities.CategoryListActivity;
import com.example.anuja.trendingnews.app.adapters.CategoriesAdapter;

import java.util.ArrayList;
import java.util.List;

public class CategoriesFragment extends Fragment implements CategoriesAdapter.CategoryItemClickListener {

    public static final String KEY_CATAEGORY_NAME = "category";

    private RecyclerView mRecyclerView;
    private CategoriesAdapter mCategoriesAdapter;

    private List<Integer> mCategoriesImgList = new ArrayList<>();
    private List<String> mCategoriesList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        createCategoryList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_categories, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = view.findViewById(R.id.rv_categories);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), calculateNoOfColumns()));
        mCategoriesAdapter = new CategoriesAdapter(mCategoriesImgList, mCategoriesList, this);
        mRecyclerView.setAdapter(mCategoriesAdapter);
    }

    /**
     * function to create category list
     */
    private void createCategoryList() {

        mCategoriesImgList.add(R.drawable.ic_business_category);
        mCategoriesImgList.add(R.drawable.ic_entertainment_category);
        mCategoriesImgList.add(R.drawable.ic_general_category);
        mCategoriesImgList.add(R.drawable.ic_health_category);
        mCategoriesImgList.add(R.drawable.ic_science_category);
        mCategoriesImgList.add(R.drawable.ic_sports_category);
        mCategoriesImgList.add(R.drawable.ic_tech_category);

        mCategoriesList.add(getActivity().getResources().getString(R.string.str_business));
        mCategoriesList.add(getActivity().getResources().getString(R.string.str_entertainment));
        mCategoriesList.add(getActivity().getResources().getString(R.string.str_general));
        mCategoriesList.add(getActivity().getResources().getString(R.string.str_health));
        mCategoriesList.add(getActivity().getResources().getString(R.string.str_science));
        mCategoriesList.add(getActivity().getResources().getString(R.string.str_sports));
        mCategoriesList.add(getActivity().getResources().getString(R.string.str_technology));
    }

    @Override
    public void onCategoryItemClicked(String category) {
        /*
        NewsFragment newsFragment= new NewsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_CATAEGORY_NAME, category);
        newsFragment.setArguments(bundle);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, newsFragment,"CategoryFragment")
                .addToBackStack(null)
                .commit();
                */
        Intent intent = new Intent(getActivity(), CategoryListActivity.class);
        intent.putExtra(KEY_CATAEGORY_NAME, category);
        startActivity(intent);
    }

    /**
     * function called to auto fit the number of columns in a grid
     */
    private int calculateNoOfColumns() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (dpWidth / 180);
        return noOfColumns;
    }
}
