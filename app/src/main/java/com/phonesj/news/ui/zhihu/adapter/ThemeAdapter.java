package com.phonesj.news.ui.zhihu.adapter;

import com.phonesj.news.R;
import com.phonesj.news.component.ImageLoader;
import com.phonesj.news.model.bean.zhihu.ThemeBean;

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
 * Created by Phone on 2017/7/19.
 */

public class ThemeAdapter extends RecyclerView.Adapter<ThemeAdapter.ViewHolder> {

    private Context mContext;
    private List<ThemeBean.OthersBean> datas;
    private OnItemClickListener mOnItemClickListener;

    public ThemeAdapter(Context mContext, List<ThemeBean.OthersBean> othersBeanList) {
        this.mContext = mContext;
        this.datas = othersBeanList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_theme, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // TODO: 2017/7/19
        //        ViewGroup.LayoutParams lp=holder.themeBg.getLayoutParams();
        //        lp.width = (App.SCREEN_WIDTH - SystemUtil.dp2px(mContext,12)) / 2;
        //        lp.height = SystemUtil.dp2px(mContext,120);

        ImageLoader.load(mContext, datas.get(position).getThumbnail(), holder.themeBg);
        holder.themeKind.setText(datas.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.theme_bg)
        ImageView themeBg;
        @BindView(R.id.theme_kind)
        TextView themeKind;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
}
