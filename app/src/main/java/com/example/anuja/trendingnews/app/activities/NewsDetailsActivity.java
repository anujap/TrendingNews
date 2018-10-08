package com.example.anuja.trendingnews.app.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.anuja.trendingnews.BR;
import com.example.anuja.trendingnews.R;
import com.example.anuja.trendingnews.app.fragments.NewsFragment;
import com.example.anuja.trendingnews.databinding.ActivityNewsDetailsBinding;
import com.example.anuja.trendingnews.model.Articles;

/**
 * Reference: - https://www.learn2crack.com/2015/10/android-floating-action-button-animations.html
 */
public class NewsDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityNewsDetailsBinding mBinding;
    private Articles mArticle;

    private Boolean isFabOpen = false;
    private Animation fab_open,fab_close,rotate_forward,rotate_backward;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_news_details);
        retrieveIntent();

        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_backward);

        mBinding.fab.setOnClickListener(this);
    }

    /**
     * Function called to get the intent - this intent
     * has the Article that was been clicked
     */
    private void retrieveIntent() {
        Intent intent = getIntent();
        if(intent.hasExtra(NewsFragment.KEY_ARTICLE)) {
            mArticle = intent.getParcelableExtra(NewsFragment.KEY_ARTICLE);
            mBinding.setVariable(BR.news, mArticle);
            mBinding.executePendingBindings();
        }
    }

    public void animateFAB(){

        if(isFabOpen){
            mBinding.fab.startAnimation(rotate_backward);

            mBinding.fabLaunch.startAnimation(fab_close);
            mBinding.fabFavorite.startAnimation(fab_close);
            mBinding.fabComment.startAnimation(fab_close);
            mBinding.fabShare.startAnimation(fab_close);

            mBinding.fabLaunch.setClickable(false);
            mBinding.fabFavorite.setClickable(false);
            mBinding.fabComment.setClickable(false);
            mBinding.fabShare.setClickable(false);

            isFabOpen = false;

        } else {
            mBinding.fab.startAnimation(rotate_forward);

            mBinding.fabLaunch.startAnimation(fab_open);
            mBinding.fabFavorite.startAnimation(fab_open);
            mBinding.fabComment.startAnimation(fab_open);
            mBinding.fabShare.startAnimation(fab_open);

            mBinding.fabLaunch.setClickable(true);
            mBinding.fabFavorite.setClickable(true);
            mBinding.fabComment.setClickable(true);
            mBinding.fabShare.setClickable(true);

            isFabOpen = true;
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.fab:
                animateFAB();
                break;
            case R.id.fab_comment:
                break;
            case R.id.fab_share:
                break;
            case R.id.fab_launch:
                break;
            case R.id.fab_favorite:
                break;
        }
    }
}
