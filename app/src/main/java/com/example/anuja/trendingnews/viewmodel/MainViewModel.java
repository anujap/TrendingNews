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
 * The Main View Model class
 */
public class MainViewModel extends ViewModel {

    private static final String TAG = "MainViewModel";
    private NewsWebserviceInterface mWebserviceInterface = NewsRetrofitClient.getInstance().getNewsWebservice();

    private MutableLiveData<List<Articles>> allNewsList;
    private MutableLiveData<List<Articles>> newsByCategoryList;

    public MutableLiveData<List<Articles>> getAllNewsList() {
        if(allNewsList == null)
            allNewsList = new MutableLiveData<>();
        return allNewsList;
    }

    public MutableLiveData<List<Articles>> getNewsByCategoryList() {
        if(newsByCategoryList == null)
            newsByCategoryList = new MutableLiveData<>();
        return newsByCategoryList;
    }

    /**
     * function used to download all the news (with country = us)
     */
    public void displayAllNews() {
        if(allNewsList == null) {
            mWebserviceInterface.getNews(NewsUtils.ENDPOINT_NEWS_TOP_HEADLINES, NewsUtils.COUNTRY_PARAM_VALUE, NewsUtils.API_KEY).enqueue(new Callback<NewsModel>() {
                @Override
                public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {
                    if(response.isSuccessful()) {
                        List<Articles> newsArticlesList = response.body().getArticlesArrayList();
                        if(newsArticlesList != null && newsArticlesList.size() > 0)
                            getAllNewsList().postValue(newsArticlesList);
                    }
                }

                @Override
                public void onFailure(Call<NewsModel> call, Throwable t) {
                }
            });
        }
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
