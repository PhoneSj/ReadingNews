package com.phonesj.news.ui.gank.adapter;

import com.phonesj.news.R;
import com.phonesj.news.model.bean.gank.GankItemBean;
import com.phonesj.news.ui.gank.fragment.GankMainFragment;
import com.phonesj.news.util.DateUtil;

import java.util.List;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Phone on 2017/7/26.
 */

public class TechAdapter extends RecyclerView.Adapter<TechAdapter.ViewHolder> {

    private Context mContext;
    private List<GankItemBean> datas;
    private String tech;
    private OnItemClickListener onItemClickListener;

    public TechAdapter(Context mContext, List<GankItemBean> datas, String tech) {
        this.mContext = mContext;
        this.datas = datas;
        this.tech = tech;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_tech, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (tech.equals(GankMainFragment.titles[0])) {
            holder.ivTechIcon.setImageResource(R.mipmap.ic_android);
        } else if (tech.equals(GankMainFragment.titles[1])) {
            holder.ivTechIcon.setImageResource(R.mipmap.ic_ios);
        } else if (tech.equals(GankMainFragment.titles[2])) {
            holder.ivTechIcon.setImageResource(R.mipmap.ic_web);
        }
        holder.tvTechTitle.setText(datas.get(position).getDesc());
        holder.tvTechAuthor.setText(datas.get(position).getWho());
        holder.tvTechTime.setText(DateUtil.formatDateTime(DateUtil.subStandardTime(datas
            .get(position)
            .getPublishedAt()), true));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(position, holder.itemView);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_tech_icon)
        ImageView ivTechIcon;
        @BindView(R.id.tv_tech_title)
        TextView tvTechTitle;
        @BindView(R.id.tv_tech_author)
        TextView tvTechAuthor;
        @BindView(R.id.tv_tech_time)
        TextView tvTechTime;
        @BindView(R.id.cv_tech_content)
        CardView cvTechContent;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position, View view);
    }
}
