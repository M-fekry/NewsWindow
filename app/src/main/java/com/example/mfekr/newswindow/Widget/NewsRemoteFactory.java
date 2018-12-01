package com.example.mfekr.newswindow.Widget;

import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.mfekr.newswindow.Model.Article;
import com.example.mfekr.newswindow.R;
import com.example.mfekr.newswindow.Retrofit.RequestService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsRemoteFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context mContext;
    private ArrayList<String> mNames;
    private Response<ArrayList<Article>> mResponse;
    private Bundle mBundle;


    public NewsRemoteFactory(Context context, Intent intent) {
        mNames = new ArrayList<>();
        mContext = context;

    }

    @Override
    public void onCreate() {


    }

    @Override
    public void onDataSetChanged() {

        final ArrayList<String> names = new ArrayList<>();
        final long identityToken = Binder.clearCallingIdentity();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(RequestService.mBaseUrl)
                .addConverterFactory(GsonConverterFactory.create()).build();
        RequestService requestService = retrofit.create(RequestService.class);
        requestService.method().enqueue(new Callback<ArrayList<Article>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<Article>> call, @NonNull Response<ArrayList<Article>> response) {
                for (int i = 0; i < response.body().size(); i++) {
                    if (response.body().get(i).getTitle() != null)
                        names.add(response.body().get(i).getTitle());
                }

                mNames = names;
                mResponse = response;


                Binder.restoreCallingIdentity(identityToken);


            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<Article>> call, @NonNull Throwable t) {

                Log.d("error", t.getMessage());


            }
        });

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {


        while (mNames.size() == 0) {
            try {
                Thread.sleep(3000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mNames.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {




        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.row_listitem);
        rv.setTextViewText(R.id.tv_article, mNames.get(position));


//        Intent fillInIntent = new Intent();
//        fillInIntent.putExtra("bundle_key", getData(position));
//        rv.setOnClickFillInIntent(R.id.tv_article, fillInIntent);


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

//    private Bundle getData(int position) {
//
//        ArrayList<String> ingredients = new ArrayList<>();
//        ArrayList<String> measures = new ArrayList<>();
//        float[] quantities = new float[mResponse.body().get(position).getIngredients().size()];
//        int[] stepId = new int[mResponse.body().get(position).getSteps().size()];
//        ArrayList<String> shortDescriptions = new ArrayList<>();
//        ArrayList<String> descriptions = new ArrayList<>();
//        ArrayList<String> vidUrl = new ArrayList<>();
//        ArrayList<String> thumbnailURL = new ArrayList<>();
//
//        // parse to individual collections
//        for (int i = 0; i < mResponse.body().get(position).getIngredients().size(); i++) {
//
//            ingredients.add(mResponse.body().get(position).getIngredients().get(i).getIngredient());
//            measures.add(mResponse.body().get(position).getIngredients().get(i).getMeasure());
//            quantities[i] = mResponse.body().get(position).getIngredients().get(i).getQuantity();
//
//
//        }
//
//        for (int i = 0; i < mResponse.body().get(position).getSteps().size(); i++) {
//
//            stepId[i] = mResponse.body().get(position).getSteps().get(i).getId();
//            shortDescriptions.add(mResponse.body().get(position).getSteps().get(i).getShortDescription());
//            descriptions.add(mResponse.body().get(position).getSteps().get(i).getDescription());
//            vidUrl.add(mResponse.body().get(position).getSteps().get(i).getVideoURL());
//            thumbnailURL.add(mResponse.body().get(position).getSteps().get(i).getThumbnailURL());
//
//        }
//
//
//        Bundle bundle = new Bundle();
//
//        // put ingredient data
//        bundle.putString("recipe_name_key", mResponse.body().get(position).getName());
//        bundle.putStringArrayList("ingredient_name_key", ingredients);
//        bundle.putFloatArray("quantity_key", quantities);
//        bundle.putStringArrayList("measures_key", measures);
//
//        // put step data
//        bundle.putIntArray("step_id_key", stepId);
//        bundle.putStringArrayList("step_short_description_key", shortDescriptions);
//        bundle.putStringArrayList("step_description_key", descriptions);
//        bundle.putStringArrayList("step_video_url_key", vidUrl);
//        bundle.putStringArrayList("step_thumbnail_url_key", thumbnailURL);
//
//
//
//        return bundle;
//    }
}