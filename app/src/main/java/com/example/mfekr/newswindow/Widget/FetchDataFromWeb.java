package com.example.mfekr.newswindow.Widget;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.mfekr.newswindow.Adapters.NewsRecyclerviewAdapter;
import com.example.mfekr.newswindow.BuildConfig;
import com.example.mfekr.newswindow.Model.Article;
import com.example.mfekr.newswindow.Model.ResponseModel;
import com.example.mfekr.newswindow.Retrofit.RestClient;
import com.example.mfekr.newswindow.Retrofit.RestInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FetchDataFromWeb {
    SharedPreferences sharedPref;
    private static final String API_KEY ="58875bc7a1134b109bdc60f338406b4f";

    public void fetchData(final Context context){
        RestInterface service = RestClient.getApiClient().create(RestInterface.class);
        Call<ResponseModel> call = service.getListNews("business-insider",API_KEY);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {

                if(!response.isSuccessful()){
                    Toast.makeText(context, "cod: "+response.code(), Toast.LENGTH_SHORT).show();

                    return;
                }
                List<Article> articleList = response.body().getArticles();
                for (int i = 0; i < articleList.size(); i++) {
                    String title = articleList.get(i).getTitle();
                    sharedPref = context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);

                    sharedPref.edit()
                            .putString("WIDGET_TITLE", title)
                            .apply();

                    ComponentName provider = new ComponentName(context, NewsWindowWidgetProvider.class);
                    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                    int[] ids = appWidgetManager.getAppWidgetIds(provider);
                    NewsWindowWidgetProvider widgetProvider = new NewsWindowWidgetProvider();
                    widgetProvider.onUpdate(context, appWidgetManager, ids);
                }
            }


            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
