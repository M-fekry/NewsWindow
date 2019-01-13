package com.example.mfekr.newswindow;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Movie;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mfekr.newswindow.Database.AppDatabase;
import com.example.mfekr.newswindow.Model.Article;
import com.example.mfekr.newswindow.utils.AppExecutor;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.squareup.picasso.Picasso;

import es.dmoral.toasty.Toasty;

public class DetailsActivity extends AppCompatActivity {

    private ImageView mThumbnail;
    private ImageView mBookmark;
    private TextView mTitle;
    private TextView mAuthor;
    private TextView mDate;
    private TextView mContent;
    private Button mReadButton;
    private Intent intent;

    private AppDatabase mAppDatabase;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        MobileAds.initialize(this, getString(R.string.ad_id));
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mThumbnail = findViewById(R.id.iv_image_news);
        mTitle = findViewById(R.id.tv_title_news);
        mAuthor = findViewById(R.id.tv_author_name);
        mDate = findViewById(R.id.tv_date);
        mContent = findViewById(R.id.tv_content);
        mReadButton = findViewById(R.id.btn_read);
        mBookmark = findViewById(R.id.save);

        Bundle bundle = getIntent().getExtras();
        final Article article = bundle.getParcelable("article");

        String poster_Path = article.getUrlToImage();
        if(poster_Path!= null) {
            Picasso.get().load(Uri.parse(article.getUrlToImage())).into(mThumbnail);
        }
        mTitle.setText(article.getTitle());
        mAuthor.setText(article.getAuthor());
        mDate.setText(article.getPublishedAt());
        mContent.setText(article.getContent());

        mReadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = article.getUrl();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        mAppDatabase = AppDatabase.getInstance(this.getApplicationContext());
        int movieId = article.getId();
        DetailsViewModelFactory factory = new DetailsViewModelFactory(mAppDatabase, movieId);
        DetailsViewModel viewModel = ViewModelProviders.of(this, factory).get(DetailsViewModel.class);

        viewModel.getArticle().observe(this, new Observer<Article>(){

            @Override
            public void onChanged(@Nullable Article article1) {
                if (article1 == null) {
                    mBookmark.setImageResource(R.drawable.bookmark);
                    mBookmark.setTag(R.drawable.bookmark);
                } else {
                    mBookmark.setImageResource(R.drawable.marked);
                    mBookmark.setTag(R.drawable.marked);
                }
            }
        });
        mBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mBookmark.getTag().equals(R.drawable.bookmark)){
                    saveArticle();
                    //Toast.makeText(DetailsActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                    Toasty.normal(DetailsActivity.this, getString(R.string.saved)).show();
                }else  if (mBookmark.getTag().equals(R.drawable.marked)){
                    removeArticle();
                    //Toast.makeText(DetailsActivity.this, "removed", Toast.LENGTH_SHORT).show();
                    Toasty.normal(DetailsActivity.this, getString(R.string.removed)).show();
                }

            }
        });



    }


    private void saveArticle(){
        Bundle bundle = getIntent().getExtras();
        final Article article = bundle.getParcelable("article");
        AppExecutor.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mAppDatabase.articleDao().insertArticle(article);

            }

        });
    }

    private void removeArticle(){
        Bundle bundle = getIntent().getExtras();
        final Article article = bundle.getParcelable("article");
        AppExecutor.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mAppDatabase.articleDao().deleteArticle(article);


            }
        });
    }


}
