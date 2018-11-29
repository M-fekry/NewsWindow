package com.example.mfekr.newswindow;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mfekr.newswindow.Adapters.SectionsPageAdapter;
import com.example.mfekr.newswindow.Fragments.BusinessFragment;
import com.example.mfekr.newswindow.Fragments.PoliticsFragment;
import com.example.mfekr.newswindow.Fragments.SportsFragment;
import com.example.mfekr.newswindow.Fragments.TechnologyFragment;

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
