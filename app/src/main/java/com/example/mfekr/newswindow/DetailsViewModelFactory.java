package com.example.mfekr.newswindow;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.mfekr.newswindow.Database.AppDatabase;

public class DetailsViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private AppDatabase mAppDatabase;
    private int articleID;

    public DetailsViewModelFactory(AppDatabase appDatabase, int articleID) {
        this.mAppDatabase = appDatabase;
        this.articleID = articleID;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new DetailsViewModel(mAppDatabase, articleID);
    }
}
