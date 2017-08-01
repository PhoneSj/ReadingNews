package com.phonesj.news.ui.gold.adapter;

import com.phonesj.news.R;
import com.phonesj.news.app.Constants;
import com.phonesj.news.component.ImageLoader;
import com.phonesj.news.model.bean.gold.GoldListBean;
import com.phonesj.news.ui.gank.activity.TechDetailActivity;
import com.phonesj.news.util.DateUtil;

import java.util.List;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Phone on 2017/7/29.
 */

public class GoldListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<GoldListBean> datas;
    private String typeStr;

    private boolean mHotFlag = true;

    private OnHotCloseListener onHotCloseListener;

    public enum ITEM_TYPE {
        ITEM_TITLE,     //标题
        ITEM_HOT,       //热门
        ITEM_CONTENT    //内容
    }

    public GoldListAdapter(Context mContext, List<GoldListBean> datas, String typeStr) {
        this.mContext = mContext;
        this.datas = datas;
        this.typeStr = typeStr;
    }

    @Override
    public int getItemViewType(int position) {
        if (!mHotFlag) {
            return ITEM_TYPE.ITEM_CONTENT.ordinal();
        } else {
            if (position == 0) {
                return ITEM_TYPE.ITEM_TITLE.ordinal();
            } else if (position > 0 && position <= 3) {
                return ITEM_TYPE.ITEM_HOT.ordinal();
            } else {
                return ITEM_TYPE.ITEM_CONTENT.ordinal();
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE.ITEM_TITLE.ordinal()) {
            View view = LayoutInflater
                .from(mContext)
                .inflate(R.layout.item_gold_title, parent, false);
            return new TitleViewHolder(view);
        } else if (viewType == ITEM_TYPE.ITEM_HOT.ordinal()) {
            View view = LayoutInflater
                .from(mContext)
                .inflate(R.layout.item_gold_hot, parent, false);
            return new HotViewHolder(view);
        } else {
            View view = LayoutInflater
                .from(mContext)
                .inflate(R.layout.item_gold_content, parent, false);
            return new ContentViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        GoldListBean bean = datas.get(holder.getAdapterPosition());
        //第一个Viewholder时title类型，不需要数据源的数据
        if (position > 0) {
            bean = datas.get(position - 1);
        }

        if (holder instanceof TitleViewHolder) {
            TitleViewHolder titleViewHolder = (TitleViewHolder) holder;
            titleViewHolder.tvGoldHotTitle.setText(String.format("%s 热门", typeStr));
            titleViewHolder.btnGoldHotClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 移除热点
                    mHotFlag = false;
                    for (int i = 0; i < 4; i++) {
                        datas.remove(0);
                    }
                    notifyItemRangeRemoved(0, 4);
                    if (onHotCloseListener != null) {
                        onHotCloseListener.onClose();
                    }
                }
            });
        } else if (holder instanceof HotViewHolder) {
            HotViewHolder hotViewHolder = (HotViewHolder) holder;
            hotViewHolder.tvGoldItemTitle.setText(bean.getTitle());
            hotViewHolder.tvGoldItemLike.setText(String.valueOf(bean.getCollectionCount()));
            hotViewHolder.tvGoldItemAuthor.setText(bean.getUser().getUsername());
            hotViewHolder.tvGoldItemTime.setText(DateUtil.formatDate2String(DateUtil.subStandardTime(bean
                .getCreatedAt())));
            if (bean.getScreenshot() != null && bean.getScreenshot().getUrl() != null) {
                ImageLoader.load(mContext, bean
                    .getScreenshot()
                    .getUrl(), hotViewHolder.ivGoldItemImg);
            }
            hotViewHolder.itemView.setOnClickListener(new MyOnClickListener(--position));
        } else {
            ContentViewHolder contentViewHolder = (ContentViewHolder) holder;
            if (bean.getScreenshot() != null && bean.getScreenshot().getUrl() != null) {
                ImageLoader.load(mContext, bean
                    .getScreenshot()
                    .getUrl(), contentViewHolder.ivGoldItemImg);
            }
            contentViewHolder.tvGoldItemTitle.setText(bean.getTitle());
            contentViewHolder.tvGoldItemInfo.setText(buildItemInfoStr(bean.getCollectionCount(), bean
                .getCommentsCount(), bean
                .getUser()
                .getUsername(), DateUtil.formatDate2String(DateUtil.subStandardTime(bean.getCreatedAt()))));
            contentViewHolder.itemView.setOnClickListener(new MyOnClickListener(--position));
        }
    }


    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    private class MyOnClickListener implements View.OnClickListener {

        private int position;

        public MyOnClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View view) {
            GoldListBean bean = datas.get(position);
            if (bean != null) {
                String imgUrl = null;
                if (bean.getScreenshot() != null) {
                    imgUrl = bean.getScreenshot().getUrl();
                }
                TechDetailActivity.launch(new TechDetailActivity.Builder()
                    .setContext(mContext)
                    .setId(bean.getObjectId())
                    .setUrl(bean.getUrl())
                    .setTitle(bean.getTitle())
                    .setType(Constants.TYPE_GOLD)
                    .setImage(imgUrl));
            }
        }
    }

    private String buildItemInfoStr(int collectionCount, int commentsCount, String username, String time) {
        StringBuilder sb = new StringBuilder();
        sb.append(collectionCount + "人收藏·");
        sb.append(commentsCount + "条评论·");
        sb.append(username + "·");
        sb.append(time);
        return sb.toString();
    }

    public boolean isHotFlag() {
        return mHotFlag;
    }

    public void setHotFlag(boolean mHotFlag) {
        this.mHotFlag = mHotFlag;
    }

    public void updateData(List<GoldListBean> datas) {
        this.datas = datas;
    }

    static class TitleViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_gold_hot_title)
        TextView tvGoldHotTitle;
        @BindView(R.id.btn_gold_hot_close)
        AppCompatButton btnGoldHotClose;

        public TitleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class HotViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_gold_item_title)
        TextView tvGoldItemTitle;
        @BindView(R.id.tv_gold_item_like)
        TextView tvGoldItemLike;
        @BindView(R.id.tv_gold_item_author)
        TextView tvGoldItemAuthor;
        @BindView(R.id.tv_gold_item_time)
        TextView tvGoldItemTime;
        @BindView(R.id.iv_gold_item_img)
        ImageView ivGoldItemImg;

        public HotViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class ContentViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_gold_item_img)
        ImageView ivGoldItemImg;
        @BindView(R.id.tv_gold_item_title)
        TextView tvGoldItemTitle;
        @BindView(R.id.tv_gold_item_info)
        TextView tvGoldItemInfo;

        public ContentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnHotCloseListener {
        void onClose();
    }

    public void setOnHotCloseListener(OnHotCloseListener onHotCloseListener) {
        this.onHotCloseListener = onHotCloseListener;
    }
}
