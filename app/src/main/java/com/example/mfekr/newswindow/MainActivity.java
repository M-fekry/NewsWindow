package com.example.mfekr.newswindow;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.mfekr.newswindow.Adapters.SectionsPageAdapter;
import com.example.mfekr.newswindow.Fragments.BusinessFragment;
import com.example.mfekr.newswindow.Fragments.PoliticsFragment;
import com.example.mfekr.newswindow.Fragments.SportsFragment;
import com.example.mfekr.newswindow.Fragments.TechnologyFragment;

public class MainActivity extends AppCompatActivity {


    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private static final String API_KEY ="58875bc7a1134b109bdc60f338406b4f";
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        mViewPager = findViewById(R.id.container);
        setupViewPager(mViewPager);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        setupTabIcons();

    }

//    public void fetchDataForWidget(){
//        RetrofitInterface service = RestClient.getApiClient().create(RetrofitInterface.class);
//        Call<ResponseModel> call = service.getListNews("abc-news",API_KEY);
//        call.enqueue(new Callback<ResponseModel>() {
//            @Override
//            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
//
//                if(!response.isSuccessful()) {
//                    Toast.makeText(MainActivity.this, "cod: "+response.code(), Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                List<Article> articleList = response.body().getArticles();
//                sharedPref = MainActivity.this.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);
//                sharedPref.edit()
//                        .put("WIDGET_TITLE", articleList)
//                        .apply();
//
//                ComponentName provider = new ComponentName(MainActivity.this, NewsAppWidgetProvider.class);
//                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(MainActivity.this);
//                int[] ids = appWidgetManager.getAppWidgetIds(provider);
//                NewsAppWidgetProvider bakingWidgetProvider = new NewsAppWidgetProvider();
//                bakingWidgetProvider.onUpdate(MainActivity.this, appWidgetManager, ids);
//
//            }
//
//
//            @Override
//            public void onFailure(Call<ResponseModel> call, Throwable t) {
//                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
//            }
//        });
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.save:
                Intent i = new Intent(this, SavedActivity.class);
                startActivity(i);
                return true;
            case R.id.about:
                Intent y = new Intent(this, SavedActivity.class);
                startActivity(y);
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(R.drawable.politics);
        tabLayout.getTabAt(1).setIcon(R.drawable.business);
        tabLayout.getTabAt(2).setIcon(R.drawable.sports);
        tabLayout.getTabAt(3).setIcon(R.drawable.technology);
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new PoliticsFragment(), "Politics");
        adapter.addFragment(new BusinessFragment(), "Business");
        adapter.addFragment(new SportsFragment(), "Sports");
        adapter.addFragment(new TechnologyFragment(), "Tech");
        viewPager.setAdapter(adapter);
    }



}
