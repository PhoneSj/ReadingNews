package com.phonesj.news.ui.wechat.adapter;

import com.phonesj.news.R;
import com.phonesj.news.app.Constants;
import com.phonesj.news.component.ImageLoader;
import com.phonesj.news.model.bean.wechat.WXItemBean;
import com.phonesj.news.ui.gank.activity.TechDetailActivity;

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
 * Created by Phone on 2017/8/1.
 */

public class WechatMainAdapter extends RecyclerView.Adapter<WechatMainAdapter.ViewHolder> {

    private Context mContext;
    private List<WXItemBean> datas;

    public WechatMainAdapter(Context mContext, List<WXItemBean> datas) {
        this.mContext = mContext;
        this.datas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_wechat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        ImageLoader.load(mContext, datas.get(position).getPicUrl(), holder.ivWechatItemImage);
        holder.tvWechatItemTitle.setText(datas.get(position).getTitle());
        holder.tvWechatItemFrom.setText(datas.get(position).getDescription());
        holder.tvWechatItemTime.setText(datas.get(position).getCtime());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TechDetailActivity.launch(new TechDetailActivity.Builder()
                    .setContext(mContext)
                    .setId(datas.get(holder.getAdapterPosition()).getPicUrl())//这里url作为数据库标示的id
                    .setImage(datas.get(holder.getAdapterPosition()).getPicUrl())
                    .setTitle(datas.get(holder.getAdapterPosition()).getTitle())
                    .setUrl(datas.get(holder.getAdapterPosition()).getUrl())
                    .setType(Constants.TYPE_WECHAT));//使用图片作为数据库的位移标示

            }
        });
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_wechat_item_image)
        ImageView ivWechatItemImage;
        @BindView(R.id.tv_wechat_item_title)
        TextView tvWechatItemTitle;
        @BindView(R.id.tv_wechat_item_from)
        TextView tvWechatItemFrom;
        @BindView(R.id.tv_wechat_item_time)
        TextView tvWechatItemTime;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
