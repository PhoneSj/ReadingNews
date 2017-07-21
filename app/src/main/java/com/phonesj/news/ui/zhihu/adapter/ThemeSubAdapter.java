package com.phonesj.news.ui.zhihu.adapter;

import com.phonesj.news.R;
import com.phonesj.news.component.ImageLoader;
import com.phonesj.news.model.bean.zhihu.StoriesBean;

import java.util.List;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Phone on 2017/7/21.
 */

public class ThemeSubAdapter extends RecyclerView.Adapter<ThemeSubAdapter.ViewHolder> {

    private Context mContext;
    private List<StoriesBean> datas;

    private OnItemClickListener onItemClickListener;

    public ThemeSubAdapter(Context mContext, List<StoriesBean> datas) {
        this.mContext = mContext;
        this.datas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
            .from(mContext)
            .inflate(R.layout.item_daily_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        List<String> imgs = datas.get(position).getImages();
        if (imgs != null && imgs.size() > 0) {
            ImageLoader.load(mContext, imgs.get(0), holder.ivDailyItemImage);
        }
        holder.tvDailyItemTitle.setText(datas.get(position).getTitle());
        if (datas.get(position).getReadState()) {
            holder.tvDailyItemTitle.setTextColor(ContextCompat.getColor(mContext, R.color.news_read));
        } else {
            holder.tvDailyItemTitle.setTextColor(ContextCompat.getColor(mContext, R.color.news_unread));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(holder.itemView, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public void setReadState(int positon, boolean state) {
        datas.get(positon).setReadState(state);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_daily_item_image)
        ImageView ivDailyItemImage;
        @BindView(R.id.tv_daily_item_title)
        TextView tvDailyItemTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int positon);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
