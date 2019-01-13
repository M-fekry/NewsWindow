package com.example.mfekr.newswindow.Widget;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.mfekr.newswindow.Model.Article;
import com.example.mfekr.newswindow.Model.ResponseModel;
import com.example.mfekr.newswindow.R;
import com.example.mfekr.newswindow.Retrofit.RestClient;
import com.example.mfekr.newswindow.Retrofit.RetrofitInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsRemoteFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context mContext;
    private ArrayList<String> mNames;
    private ArrayList<String>  mTitles;

    //Add your API Key here.
    private static final String API_KEY ="";





    public NewsRemoteFactory(Context context, Intent intent) {
        mNames = new ArrayList<>();
        mContext = context;

    }

    @Override
    public void onCreate() {


    }

    @Override
    public void onDataSetChanged() {



        RetrofitInterface service = RestClient.getApiClient().create(RetrofitInterface.class);
        Call<ResponseModel> call = service.getListNews("abc-news",API_KEY);
        mTitles = new ArrayList<>();
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {

                for (int i = 0 ; i < response.body().getArticles().size(); i++) {

                    mTitles.add(response.body().getArticles().get(i).getTitle());

                }

            }


            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {

            }
        });




    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {


        while (mTitles.size() == 0) {
            try {
                Thread.sleep(3000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mTitles.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {




        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.row_listitem);
        rv.setTextViewText(R.id.tv_article, mTitles.get(position));


        Intent fillInIntent = new Intent();
        fillInIntent.putExtra("bundle_key", mTitles.get(position));
        rv.setOnClickFillInIntent(R.id.tv_article, fillInIntent);


        return rv;

    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}