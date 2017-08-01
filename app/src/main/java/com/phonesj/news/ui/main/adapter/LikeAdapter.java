package com.phonesj.news.ui.main.adapter;

import com.phonesj.news.R;
import com.phonesj.news.app.Constants;
import com.phonesj.news.component.ImageLoader;
import com.phonesj.news.model.bean.zhihu.LikeBean;
import com.phonesj.news.ui.gank.activity.TechDetailActivity;
import com.phonesj.news.ui.gank.activity.WelfareDetailActivity;
import com.phonesj.news.ui.zhihu.activity.ZhihuDetailActivity;

import java.util.List;

import android.content.Context;
import android.content.Intent;
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

public class LikeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<LikeBean> datas;
    private OnItemClickListener onItemClickListener;

    private static final int Item_Article = 0;
    private static final int Item_Welfare = 1;

    public LikeAdapter(Context mContext, List<LikeBean> datas) {
        this.mContext = mContext;
        this.datas = datas;
    }

    @Override
    public int getItemViewType(int position) {
        if (datas.get(position).getType() == Constants.TYPE_WELFARE) {
            return Item_Welfare;
        } else {
            return Item_Article;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == Item_Article) {
            View view = LayoutInflater
                .from(mContext)
                .inflate(R.layout.item_like_article, parent, false);
            return new ArticelViewHolder(view);
        } else {
            View view = LayoutInflater
                .from(mContext)
                .inflate(R.layout.item_like_welfare, parent, false);
            return new WelfareViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ArticelViewHolder) {
            ArticelViewHolder articelViewHolder = (ArticelViewHolder) holder;
            articelViewHolder.tvArticleTitle.setText(datas.get(position).getTitle());
            switch (datas.get(position).getType()) {
                case Constants.TYPE_ZHIHU:
                    ImageLoader.load(mContext, datas
                        .get(articelViewHolder.getAdapterPosition())
                        .getImage(), R.mipmap.ic_launcher, R.mipmap.ic_launcher, articelViewHolder.ivArticleImage);
                    articelViewHolder.tvArticleFrom.setText("知乎");
                    break;
                case Constants.TYPE_ANDROID:
                    articelViewHolder.ivArticleImage.setImageResource(R.mipmap.ic_android);
                    articelViewHolder.tvArticleFrom.setText("干货集中营");
                    break;
                case Constants.TYPE_IOS:
                    articelViewHolder.ivArticleImage.setImageResource(R.mipmap.ic_ios);
                    articelViewHolder.tvArticleFrom.setText("干货集中营");
                    break;
                case Constants.TYPE_WEB:
                    articelViewHolder.ivArticleImage.setImageResource(R.mipmap.ic_web);
                    articelViewHolder.tvArticleFrom.setText("干货集中营");
                    break;
                case Constants.TYPE_GOLD:
                    ImageLoader.load(mContext, datas
                        .get(articelViewHolder.getAdapterPosition())
                        .getImage(), articelViewHolder.ivArticleImage);
                    articelViewHolder.tvArticleTitle.setText(datas
                        .get(articelViewHolder.getAdapterPosition())
                        .getTitle());
                    articelViewHolder.tvArticleFrom.setText("稀土掘金");
                    break;
                case Constants.TYPE_WECHAT:
                    ImageLoader.load(mContext, datas
                        .get(articelViewHolder.getAdapterPosition())
                        .getImage(), articelViewHolder.ivArticleImage);
                    articelViewHolder.tvArticleTitle.setText(datas
                        .get(articelViewHolder.getAdapterPosition())
                        .getTitle());
                    articelViewHolder.tvArticleFrom.setText("微信精选");
                    break;
            }
        } else if (holder instanceof WelfareViewHolder) {
            WelfareViewHolder welfareViewHolder = (WelfareViewHolder) holder;
            switch (datas.get(position).getType()) {
                case Constants.TYPE_WELFARE:
                    ImageLoader.load(mContext, datas
                        .get(position)
                        .getImage(), welfareViewHolder.ivGirlImage);
                    welfareViewHolder.tvGirlFrom.setText("来自干货集中营");
                    break;
            }
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoDetail(datas.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public static class ArticelViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_article_image)
        ImageView ivArticleImage;
        @BindView(R.id.tv_article_title)
        TextView tvArticleTitle;
        @BindView(R.id.tv_article_from)
        TextView tvArticleFrom;

        public ArticelViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class WelfareViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_girl_image)
        ImageView ivGirlImage;
        @BindView(R.id.tv_girl_from)
        TextView tvGirlFrom;

        public WelfareViewHolder(View itemView) {
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

    private void gotoDetail(LikeBean bean) {
        switch (bean.getType()) {
            case Constants.TYPE_ZHIHU:
                gotoDailyDetail(bean);
                break;
            case Constants.TYPE_ANDROID:
            case Constants.TYPE_IOS:
            case Constants.TYPE_WEB:
                gotoTechDetail(bean);
                break;
            case Constants.TYPE_WELFARE:
                gotoWelfareDetail(bean);
                break;
        }
    }

    private void gotoDailyDetail(LikeBean bean) {
        int id = Integer.parseInt(bean.getId());
        Intent intent = new Intent(mContext, ZhihuDetailActivity.class);
        intent.putExtra(Constants.INTENT_ZHIHU_DETAIL_ID, id);
        mContext.startActivity(intent);
    }

    private void gotoTechDetail(LikeBean bean) {
        TechDetailActivity.launch(new TechDetailActivity.Builder()
            .setContext(mContext)
            .setId(bean.getId())
            .setTitle(bean.getTitle())
            .setType(bean.getType())
            .setUrl(bean.getUrl())
            .setImage(bean.getImage()));
    }

    private void gotoWelfareDetail(LikeBean bean) {
        Intent intent = new Intent(mContext, WelfareDetailActivity.class);
        intent.putExtra(Constants.INTENT_WELFARE_DETAIL_ID, bean.getId());
        intent.putExtra(Constants.INTENT_WELFARE_DETAIL_URL, bean.getImage());
        mContext.startActivity(intent);
    }
}
