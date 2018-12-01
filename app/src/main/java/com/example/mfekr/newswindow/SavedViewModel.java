package com.example.mfekr.newswindow;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.mfekr.newswindow.Database.AppDatabase;
import com.example.mfekr.newswindow.Model.Article;

import java.util.List;

public class SavedViewModel extends AndroidViewModel {

    private LiveData<List<Article>> articleList;


    public SavedViewModel(@NonNull Application application) {
        super(application);
        AppDatabase appDatabase = AppDatabase.getInstance(this.getApplication());
        articleList = appDatabase.articleDao().loadAllArticles();
    }

    public LiveData<List<Article>> getMovieList(){
        return articleList;
    }
}

