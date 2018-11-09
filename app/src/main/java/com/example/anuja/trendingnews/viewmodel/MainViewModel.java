package com.example.anuja.trendingnews.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.anuja.trendingnews.app.activities.NewsDetailsActivity;
import com.example.anuja.trendingnews.model.Articles;
import com.example.anuja.trendingnews.model.NewsModel;
import com.example.anuja.trendingnews.webservice.NewsRetrofitClient;
import com.example.anuja.trendingnews.webservice.NewsUtils;
import com.example.anuja.trendingnews.webservice.NewsWebserviceInterface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * The Main View Model class
 */
public class MainViewModel extends ViewModel {

    private static final String TAG = "MainViewModel";
    private static final String DB_REFERENCE_CHILD_NAME = "TrendingNews";

    private NewsWebserviceInterface mWebserviceInterface = NewsRetrofitClient.getInstance().getNewsWebservice();

    private MutableLiveData<List<Articles>> allNewsList;
    private MutableLiveData<List<Articles>> newsByCategoryList;

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(DB_REFERENCE_CHILD_NAME);

    private ArrayList<Articles> storedNewsArticlesList = new ArrayList<>();;

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
                        ArrayList<Articles> newsArticlesList = response.body().getArticlesArrayList();
                        if(newsArticlesList != null && newsArticlesList.size() > 0)
                            getAllNewsList().postValue(insertAndRetrieveFromDatabase(newsArticlesList));
                    }
                }

                @Override
                public void onFailure(Call<NewsModel> call, Throwable t) {
                }
            });
        }
    }

    /**
     * function called to insert the data into the database
     */
    private ArrayList<Articles> insertAndRetrieveFromDatabase(List<Articles> newsArticlesList) {
        for (Articles article : newsArticlesList) {
            String id = databaseReference.push().getKey();
            article.setArticleId(id);
            article.setIsFav("false");
            if(!storedNewsArticlesList.contains(article)){
                storedNewsArticlesList.add(article);
                databaseReference.child(id).setValue(article);
            }
        }
        return storedNewsArticlesList;
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
