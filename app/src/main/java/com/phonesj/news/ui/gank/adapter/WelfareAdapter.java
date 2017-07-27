package com.phonesj.news.ui.gank.adapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.phonesj.news.R;
import com.phonesj.news.app.App;
import com.phonesj.news.model.bean.gank.GankItemBean;
import com.phonesj.news.ui.gank.fragment.WelfareFragment;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Phone on 2017/7/26.
 */

public class WelfareAdapter extends RecyclerView.Adapter<WelfareAdapter.ViewHolder> {

    private Context mContext;
    private List<GankItemBean> datas;
    private OnItemClickListener onItemClickListener;

    public WelfareAdapter(Context mContext, List<GankItemBean> datas) {
        this.mContext = mContext;
        this.datas = datas;
    }

    @Override
    public int getItemViewType(int position) {
        return Math.round((float) App.SCREEN_WIDTH / (float) datas.get(position).getHeight() * 10f);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_welfare, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        //该item不是首次加载，即ImgeView高度已经确定
        if (datas.get(holder.getAdapterPosition()).getHeight() > 0) {
            ViewGroup.LayoutParams lp = holder.ivGirl.getLayoutParams();
            lp.height = datas.get(holder.getAdapterPosition()).getHeight();
        }

        Glide
            .with(mContext)
            .load(datas.get(position).getUrl())
            .asBitmap()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(new SimpleTarget<Bitmap>(App.SCREEN_WIDTH / 2, App.SCREEN_HEIGHT / 2) {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    if (holder.getAdapterPosition() != RecyclerView.NO_POSITION) {
                        if (datas.get(holder.getAdapterPosition()).getHeight() <= 0) {
                            //该item首次加载，即高度还没有确定
                            int width = resource.getWidth();
                            int height = resource.getHeight();
                            int realHeight = App.SCREEN_WIDTH / WelfareFragment.SPAN_COUNT * height / width;

                            ViewGroup.LayoutParams lp = holder.ivGirl.getLayoutParams();
                            lp.height = realHeight;
                        }
                        holder.ivGirl.setImageBitmap(resource);
                    }
                }
            });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClickListener(position, holder.itemView);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_girl)
        ImageView ivGirl;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClickListener(int position, View view);
    }
}
