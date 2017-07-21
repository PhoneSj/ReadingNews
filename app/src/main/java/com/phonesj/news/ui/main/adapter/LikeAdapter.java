package com.phonesj.news.ui.main.adapter;

import com.phonesj.news.R;
import com.phonesj.news.app.Constants;
import com.phonesj.news.component.ImageLoader;
import com.phonesj.news.model.bean.zhihu.LikeBean;

import java.util.List;

import android.content.Context;
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

public class LikeAdapter extends RecyclerView.Adapter<LikeAdapter.ViewHolder> {

    private Context mContext;
    private List<LikeBean> datas;
    private OnItemClickListener onItemClickListener;

    public LikeAdapter(Context mContext, List<LikeBean> datas) {
        this.mContext = mContext;
        this.datas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
            .from(mContext)
            .inflate(R.layout.item_like_article, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvArticleTitle.setText(datas.get(position).getTitle());
        switch (datas.get(position).getType()) {
            case Constants.TYPE_ZHIHU:
                ImageLoader.load(mContext, datas
                    .get(position)
                    .getImage(), R.mipmap.ic_launcher, R.mipmap.ic_launcher, holder.ivArticleImage);
                holder.tvArticleFrom.setText("来自知乎");
                break;
        }
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_article_image)
        ImageView ivArticleImage;
        @BindView(R.id.tv_article_title)
        TextView tvArticleTitle;
        @BindView(R.id.tv_article_from)
        TextView tvArticleFrom;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
