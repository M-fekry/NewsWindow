package com.example.mfekr.newswindow.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mfekr.newswindow.Adapters.NewsRecyclerviewAdapter;
import com.example.mfekr.newswindow.MainActivity;
import com.example.mfekr.newswindow.Model.Article;
import com.example.mfekr.newswindow.Model.ResponseModel;
import com.example.mfekr.newswindow.R;
import com.example.mfekr.newswindow.Retrofit.RestClient;
import com.example.mfekr.newswindow.Retrofit.RestInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PoliticsFragment extends Fragment {

    private static final String API_KEY ="58875bc7a1134b109bdc60f338406b4f";

//    private RecyclerView mRecyclerView;
//    private NewsRecyclerviewAdapter mAdapter;
//    private List<Article> mArticleList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.politics_fragment, container,false);


        final RecyclerView mainRecycler = view.findViewById(R.id.recylcer_view);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mainRecycler.setLayoutManager(linearLayoutManager);




        RestInterface service = RestClient.getApiClient().create(RestInterface.class);
        Call<ResponseModel> call = service.getListNews("abc-news",API_KEY);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {

                if(!response.isSuccessful()){
                    Toast.makeText(getContext(), "cod: "+response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                List<Article> articleList = response.body().getArticles();
                if(articleList.size()>0) {
                    final NewsRecyclerviewAdapter mainArticleAdapter = new NewsRecyclerviewAdapter(getActivity(),articleList);
                    mainRecycler.setAdapter(mainArticleAdapter);
                }
            }


            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }
}
