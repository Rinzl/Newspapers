package com.thd.newspapers.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.thd.newspapers.model.News;
import com.thd.newspapers.R;
import com.thd.newspapers.utils.GlideApp;
import com.thd.newspapers.utils.SqliteDB;
import com.varunest.sparkbutton.SparkButton;
import com.varunest.sparkbutton.SparkEventListener;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;
import java.util.List;

/**
 * Created by Tran Hai Dang on 3/20/2018.
 * Email : tranhaidang2320@gmail.com
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    private List<News> newsList;
    private Context context;
    private List<News> favoriteNews;
    private SqliteDB db;
    public interface OnclickListener {
        void onClick(int position);
    }
    private OnclickListener listener;

    public NewsAdapter(List<News> newsList, Context context) {
        this.db = new SqliteDB(context);
        this.newsList = newsList;
        this.context = context;
        this.favoriteNews = db.getAllFavoriteNews();
    }

    public void setListener(OnclickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.news_item,parent,false);
        return new NewsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        News news = newsList.get(position);
        holder.tvHeadline.setText(news.getHeadline());
        holder.tvSource.setText(news.getSource());
        if (isFavorite(news)) holder.sparkButton.setChecked(true);
        GlideApp
                .with(context)
                .load(news.getImageUrl())
//                .load(R.drawable.demo)
                .transition(withCrossFade())
                .centerCrop()
                .into(holder.ivPreview);
    }

    @Override
    public int getItemCount() {
        return null != newsList ? newsList.size() : 0;
    }


    public class NewsViewHolder extends RecyclerView.ViewHolder {
        TextView tvHeadline;
        TextView tvSource;
        ImageView ivPreview;
        SparkButton sparkButton;
        public NewsViewHolder(View itemView) {
            super(itemView);
            tvHeadline = itemView.findViewById(R.id.tvHeadline);
            tvSource = itemView.findViewById(R.id.tvSource);
            ivPreview = itemView.findViewById(R.id.ivPreview);
            sparkButton = itemView.findViewById(R.id.sparkButton);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            Log.i("newsItemClick", "onClick: "+newsList.get(position));
                            listener.onClick(position);
                        }
                    }
                }
            });
            sparkButton.setEventListener(new SparkEventListener() {
                @Override
                public void onEvent(ImageView button, boolean buttonState) {
                    if (buttonState) {
                        db.insertToDB(newsList.get(getAdapterPosition()));
                    } else {

                    }
                }

                @Override
                public void onEventAnimationEnd(ImageView button, boolean buttonState) {

                }

                @Override
                public void onEventAnimationStart(ImageView button, boolean buttonState) {

                }
            });
        }
    }
    private boolean isFavorite(News n) {
        for (News fNews : favoriteNews) {
            if (n.getNewsUrl().equals(fNews.getNewsUrl())) return true;
        }
        return false;
    }
}
