package com.example.mfekr.newswindow.Retrofit;

import com.example.mfekr.newswindow.Model.Article;
import com.example.mfekr.newswindow.Model.ResponseModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestInterface  {

    @GET("everything")
    Call<ResponseModel> getListNews(@Query("sources") String source, @Query("apiKey") String apiKey);
}
