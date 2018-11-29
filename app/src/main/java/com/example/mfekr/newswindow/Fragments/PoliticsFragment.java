package com.example.mfekr.newswindow.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
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

    private RecyclerView mRecyclerView;
    private NewsRecyclerviewAdapter mAdapter;
    private List<Article> mArticleList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.politics_fragment, container,false);


        mRecyclerView =   view.findViewById(R.id.recylcer_view);
        mArticleList = new ArrayList<>();
        mAdapter = new NewsRecyclerviewAdapter(getActivity(), mArticleList);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1,1,false);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setAdapter(mAdapter);


        RestInterface service = RestClient.getApiClient().create(RestInterface.class);
        Call<ResponseModel> call = service.getListNews("abc-news",API_KEY);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {

                if(!response.isSuccessful()){
                    Toast.makeText(getContext(), "cod: "+response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                List<Article> articles = response.body().getArticles();
                if (articles == null){
                    Toast.makeText(getContext(), "Something went wrong, please check your internet connection and try again! ", Toast.LENGTH_SHORT).show();
                }else {
                    mAdapter.setArticle(articles);
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
