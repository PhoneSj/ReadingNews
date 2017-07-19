package com.phonesj.news.ui.zhihu.adapter;

import com.phonesj.news.R;
import com.phonesj.news.component.ImageLoader;
import com.phonesj.news.model.bean.zhihu.HotBean;

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
 * Created by Phone on 2017/7/19.
 */

public class HotAdapter extends RecyclerView.Adapter<HotAdapter.ViewHolder> {

    private Context mContext;
    private List<HotBean.RecentBean> datas;

    public HotAdapter(Context mContext, List<HotBean.RecentBean> datas) {
        this.mContext = mContext;
        this.datas = datas;
    }

    @Override
    public HotAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
            .from(mContext)
            .inflate(R.layout.item_daily_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HotAdapter.ViewHolder holder, int position) {
        ImageLoader.load(mContext, datas.get(position).getThumbnail(), holder.ivDailyItemImage);
        holder.tvDailyItemTitle.setText(datas.get(position).getTitle());
        if (datas.get(position).getReadState()) {
            holder.tvDailyItemTitle.setTextColor(ContextCompat.getColor(mContext, R.color.news_read));
        } else {
            holder.tvDailyItemTitle.setTextColor(ContextCompat.getColor(mContext, R.color.news_unread));
        }

        // TODO: 2017/7/19 设置点击事件
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
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
}
