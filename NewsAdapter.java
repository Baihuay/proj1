package com.example.proj4;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private List<NewsItem> newsItemList;

    public NewsAdapter(List<NewsItem> newsItemList) {
        this.newsItemList = newsItemList;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView coverView;
        TextView titleView;
        TextView contentView;
        TextView dateView;
        TextView likeView;
        TextView readView;

        ViewHolder(View itemView) {
            super(itemView);

            coverView = itemView.findViewById(R.id.coverImageView);
            titleView = itemView.findViewById(R.id.titleTextView);
            contentView = itemView.findViewById(R.id.contentTextView);
            dateView = itemView.findViewById(R.id.date_textview);
            likeView = itemView.findViewById(R.id.like_textview);
            readView = itemView.findViewById(R.id.read_textview);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NewsItem newsItem = newsItemList.get(position);
        holder.titleView.setText(newsItem.getTitle());
        holder.contentView.setText(newsItem.getContent());
        holder.dateView.setText(newsItem.getPublishDate());
        holder.likeView.setText(newsItem.getLikeNum());
        holder.readView.setText(newsItem.getReadNum());

        Glide.with(holder.itemView)
                .load("http://124.93.196.45:10001" + newsItem.getCover())
                .into(holder.coverView);
    }

    @Override
    public int getItemCount() {
        return newsItemList.size();
    }
}
