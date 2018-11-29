package com.example.mfekr.newswindow.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mfekr.newswindow.Model.Article;
import com.example.mfekr.newswindow.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsRecyclerviewAdapter extends RecyclerView.Adapter<NewsRecyclerviewAdapter.ViewHolder> {

//    public static final String TAG = "RecyclerViewAdapter";
//    public static final String LOG_TAG = RecyclerViewAdapter.class.getSimpleName();

    Context mContext;
    List<Article> mArticles;

    public NewsRecyclerviewAdapter(Context context, List<Article> articles) {
        mContext = context;
        mArticles = articles;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.news_list_item, parent, false);
        ViewHolder mviewViewHolder = new ViewHolder(view);
        return mviewViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        //final Article article = mArticles.get(position);
        String poster_Path = mArticles.get(position).getUrlToImage();
        Picasso.get().load(Uri.parse(poster_Path)).into(holder.thumbnail);
        holder.title.setText(mArticles.get(position).getTitle());
        holder.description.setText(mArticles.get(position).getDescription());

//        holder.mView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Movie movie = mMovies.get(position);
//                Intent i = new Intent(mContext,DetailsActivity.class);
//                i.putExtra("movie",movie);
//                mContext.startActivity(i);
//            }
//        });
    }

    @Override
    public int getItemCount() {
//        if (!isNetworkAvailable()) {
//            Toast.makeText(mContext, "Network not available!", Toast.LENGTH_SHORT).show();
//        }
        return mArticles.size();

    }

    public void setArticle(List<Article> Article) {
        this.mArticles = Article;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView thumbnail;
        public TextView title;
        public TextView description;
        public View mView;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mContext = itemView.getContext();
            thumbnail = itemView.findViewById(R.id.iv_news_image);
            title = itemView.findViewById(R.id.tv_news_title);
            title = itemView.findViewById(R.id.tv_news_description);
        }

    }
}