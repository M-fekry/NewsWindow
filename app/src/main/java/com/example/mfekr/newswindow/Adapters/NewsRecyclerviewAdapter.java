package com.example.mfekr.newswindow.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mfekr.newswindow.DetailsActivity;
import com.example.mfekr.newswindow.Model.Article;
import com.example.mfekr.newswindow.R;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Observer;

public class NewsRecyclerviewAdapter extends RecyclerView.Adapter<NewsRecyclerviewAdapter.NewsViewHolder> {

//    public static final String TAG = "RecyclerViewAdapter";
//    public static final String LOG_TAG = RecyclerViewAdapter.class.getSimpleName();

    Context mContext;
    List<Article> mArticles;
    android.arch.lifecycle.Observer<List<Article>> mListObserver;

    public NewsRecyclerviewAdapter(Context context, List<Article> articles, android.arch.lifecycle.Observer<List<Article>> mObserver) {
        mContext = context;
        mArticles = articles;
        mListObserver = mObserver;

    }


    public NewsRecyclerviewAdapter(Context context, List<Article> articles) {
        mContext = context;
        mArticles = articles;
    }


    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.news_list_item, parent, false);
        NewsViewHolder mviewViewHolder = new NewsViewHolder(view);
        return mviewViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, final int position) {
        final Article article = mArticles.get(position);
        String poster_Path = article.getUrlToImage();
        if (poster_Path != null) {
            Picasso.get().load(Uri.parse(article.getUrlToImage())).into(holder.thumbnail);
        }

        holder.title.setText(article.getTitle());
        holder.description.setText(article.getDescription());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putParcelable("article", article);
                Intent i = new Intent(mContext, DetailsActivity.class);
                i.putExtras(bundle);
                mContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (!isNetworkAvailable()) {
            Toast.makeText(mContext, R.string.network_not_available, Toast.LENGTH_SHORT).show();
        }
        return mArticles.size();

    }

    public void setArticle(List<Article> Article) {
        this.mArticles = Article;
        notifyDataSetChanged();
    }


    public class NewsViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView description;
        ImageView thumbnail;
        View mView;

        public NewsViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            title = itemView.findViewById(R.id.tv_news_title);
            thumbnail = itemView.findViewById(R.id.iv_news_image);
            description = itemView.findViewById(R.id.tv_news_description);

        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager cm =
                (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;

    }
}