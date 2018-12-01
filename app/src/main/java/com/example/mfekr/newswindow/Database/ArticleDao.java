package com.example.mfekr.newswindow.Database;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.mfekr.newswindow.Model.Article;

import java.util.List;

@Dao
public interface ArticleDao {


    @Query("SELECT * FROM favoritesArticles")
    LiveData<List<Article>> loadAllArticles();

    @Query("SELECT * FROM favoritesArticles")
    List<Article> getArticls();

    @Insert
    void insertArticle(Article articleEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateArticle(Article movieEntry);

    @Delete
    void deleteArticle(Article movieEntry);

    @Query("SELECT * FROM favoritesArticles WHERE article_id = :id")
    LiveData<Article> loadArticleById(int id);
}
