package com.example.mfekr.newswindow;

import android.content.Context;
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
import com.example.mfekr.newswindow.sync.NewsFirebaseJobService;
import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;

public class MainActivity extends AppCompatActivity {


    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;
    private TabLayout tabLayout;

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
        scheduleJob(this);
    }



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
                Intent y = new Intent(this, AboutActivity.class);
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
        adapter.addFragment(new PoliticsFragment(), getString(R.string.politics));
        adapter.addFragment(new BusinessFragment(), getString(R.string.business));
        adapter.addFragment(new SportsFragment(), getString(R.string.sports));
        adapter.addFragment(new TechnologyFragment(), getString(R.string.tech));
        viewPager.setAdapter(adapter);
    }

    public static void scheduleJob(Context context) {
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(context));
        Job job = createJob(dispatcher);
        dispatcher.mustSchedule(job);
    }

    public static Job createJob(FirebaseJobDispatcher dispatcher){

        Job job = dispatcher.newJobBuilder()
                .setLifetime(Lifetime.FOREVER)
                .setService(NewsFirebaseJobService.class)
                .setTag("news_service")
                .setReplaceCurrent(false)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(30, 60))
                .setRetryStrategy(RetryStrategy.DEFAULT_LINEAR)
                .setConstraints(Constraint.ON_ANY_NETWORK, Constraint.DEVICE_CHARGING)
                .build();
        return job;
    }

    public static Job updateJob(FirebaseJobDispatcher dispatcher) {
        Job newJob = dispatcher.newJobBuilder()
                .setReplaceCurrent(true)
                .setService(NewsFirebaseJobService.class)
                .setTag("news_service")
                .setTrigger(Trigger.executionWindow(30, 60))
                .build();
        return newJob;
    }

    public void cancelJob(Context context){

        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(context));
        dispatcher.cancelAll();
        dispatcher.cancel("news_service");

    }

}
