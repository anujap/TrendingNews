package com.example.anuja.trendingnews.app.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.anuja.trendingnews.R;
import com.example.anuja.trendingnews.databinding.ItemCategoryBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * This is an adapter class to display categories
 */
public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesHolder>{

    private List<Integer> mCategoriesImgList;
    private List<String> mCategoryList;
    private CategoryItemClickListener mCategoryItemClickListener;
    private Context context;

    public interface CategoryItemClickListener {
        void onCategoryItemClicked(String category);
    }

    public CategoriesAdapter(List<Integer> mCategoriesImgList, List<String> mCategoryList, CategoryItemClickListener mCategoryItemClickListener) {
        this.mCategoriesImgList = mCategoriesImgList;
        this.mCategoryList = mCategoryList;
        this.mCategoryItemClickListener = mCategoryItemClickListener;
    }

    @NonNull
    @Override
    public CategoriesHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        LayoutInflater mInflater = LayoutInflater.from(context);
        return new CategoriesHolder(ItemCategoryBinding.inflate(mInflater, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesHolder categoriesHolder, int i) {
        Integer mCategoryImage = mCategoriesImgList.get(i);
        String mCategory = mCategoryList.get(i);
        Picasso.with(context)
                .load(mCategoryImage)
                .fit().centerCrop()
                .placeholder(R.drawable.ic_news_thumbnail_placeholder)
                .into(categoriesHolder.mBinding.ivMovieImages);

        categoriesHolder.mBinding.tvCategoryName.setText(mCategory);
    }

    @Override
    public int getItemCount() {
        if(mCategoryList == null)
            return 0;
        return mCategoryList.size();
    }

    public class CategoriesHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ItemCategoryBinding mBinding;

        public CategoriesHolder(ItemCategoryBinding mBinding) {
            super(mBinding.getRoot());

            this.mBinding = mBinding;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            String mCategory = mCategoryList.get(position);
            mCategoryItemClickListener.onCategoryItemClicked(mCategory);
        }
    }
}
