package com.example.mfekr.newswindow;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.mfekr.newswindow.Adapters.NewsRecyclerviewAdapter;
import com.example.mfekr.newswindow.Model.Article;

import java.util.List;

public class SavedActivity extends AppCompatActivity {

    List<Article> mArticles;
    NewsRecyclerviewAdapter mainArticleAdapter;
    RecyclerView mainRecycler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);

        getSupportActionBar().setTitle(R.string.saved_actionbar);

        mainRecycler = findViewById(R.id.recylcer_view);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mainRecycler.setLayoutManager(linearLayoutManager);

        setupViewModel(this);


    }


    private void setupViewModel(final Context context) {
        SavedViewModel viewModel = ViewModelProviders.of(this).get(SavedViewModel.class);
        viewModel.getMovieList().observe(this, new Observer<List<Article>>() {
            @Override
            public void onChanged(@Nullable List<Article> articleEnteries) {
                //Log.d(LOG_TAG, "Updating list of Movies from LiveData in ViewModel");
                mainArticleAdapter = new NewsRecyclerviewAdapter(context,articleEnteries,this);
                mainRecycler.setAdapter(mainArticleAdapter);
                mainArticleAdapter.setArticle(articleEnteries);
            }
        });
    }
}
