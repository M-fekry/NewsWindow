package com.example.mfekr.newswindow;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.mfekr.newswindow.Database.AppDatabase;
import com.example.mfekr.newswindow.Model.Article;

public class DetailsViewModel extends ViewModel {

    private LiveData<Article> articleLiveData;

    public DetailsViewModel(AppDatabase appDatabase, int articleId) {

        articleLiveData = appDatabase.articleDao().loadArticleById(articleId);

    }

    public LiveData<Article> getArticle() {
        return articleLiveData;
    }
}

