package com.rujirakongsomran.rssreader.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rujirakongsomran.rssreader.Interface.ItemClickListener;
import com.rujirakongsomran.rssreader.Model.RSSObject;
import com.rujirakongsomran.rssreader.R;

class FeedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    public TextView tvTitle, tvPubData, tvContent;
    public ItemClickListener itemClickListener;

    public FeedViewHolder(@NonNull View itemView) {
        super(itemView);
        tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
        tvPubData = (TextView) itemView.findViewById(R.id.tvPubData);
        tvContent = (TextView) itemView.findViewById(R.id.tvContent);

        // Set Event
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(), false);
    }

    @Override
    public boolean onLongClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(), true);
        return true;
    }
}

public class FeedAdapter extends RecyclerView.Adapter<FeedViewHolder> {

    private RSSObject rssObject;
    private Context mContext;
    private LayoutInflater inflater;

    public FeedAdapter(RSSObject rssObject, Context mContext) {
        this.rssObject = rssObject;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.row, parent, false);
        return new FeedViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedViewHolder holder, int position) {
        holder.tvTitle.setText(rssObject.getItems().get(position).getTitle());
        holder.tvPubData.setText(rssObject.getItems().get(position).getPubDate());
        holder.tvContent.setText(rssObject.getItems().get(position).getContent());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if (!isLongClick) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(rssObject.getItems().get(position).getLink()));
                    view.getContext().startActivity(browserIntent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return rssObject.items.size();
    }
}
