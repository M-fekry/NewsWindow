package com.example.mfekr.newswindow.Retrofit;

import com.example.mfekr.newswindow.Model.Article;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RequestService {
    String mBaseUrl = "https://newsapi.org/v2/";

    @GET("top-headlines?sources=nbc-news&apiKey=58875bc7a1134b109bdc60f338406b4f")
    Call<ArrayList<Article>> method();
}
