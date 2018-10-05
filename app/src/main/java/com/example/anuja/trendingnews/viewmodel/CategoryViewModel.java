package com.example.anuja.trendingnews.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.anuja.trendingnews.model.Articles;
import com.example.anuja.trendingnews.model.NewsModel;
import com.example.anuja.trendingnews.webservice.NewsRetrofitClient;
import com.example.anuja.trendingnews.webservice.NewsUtils;
import com.example.anuja.trendingnews.webservice.NewsWebserviceInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * The Category View Model class
 */
public class CategoryViewModel extends ViewModel {

    private static final String TAG = "CategoryViewModel";
    private NewsWebserviceInterface mWebserviceInterface = NewsRetrofitClient.getInstance().getNewsWebservice();

    private MutableLiveData<List<Articles>> newsByCategoryList;

    public MutableLiveData<List<Articles>> getNewsByCategoryList() {
        if(newsByCategoryList == null)
            newsByCategoryList = new MutableLiveData<>();
        return newsByCategoryList;
    }

    /**
     * function used to download the news based on category
     */
    public void displayNewsByCategory(String categoryName) {
        if(newsByCategoryList == null) {
            mWebserviceInterface.getNewsByCategory(NewsUtils.ENDPOINT_NEWS_TOP_HEADLINES, NewsUtils.COUNTRY_PARAM_VALUE
                                                    , categoryName, NewsUtils.API_KEY).enqueue(new Callback<NewsModel>() {
                @Override
                public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {
                    if(response.isSuccessful()) {
                        List<Articles> newsArticlesList = response.body().getArticlesArrayList();
                        if(newsArticlesList != null && newsArticlesList.size() > 0)
                            getNewsByCategoryList().postValue(newsArticlesList);
                    }
                }

                @Override
                public void onFailure(Call<NewsModel> call, Throwable t) {
                }
            });
        }
    }
}
