package com.phonesj.news.ui.zhihu.adapter;

import com.phonesj.news.R;
import com.phonesj.news.component.ImageLoader;
import com.phonesj.news.model.bean.zhihu.DailyBean;
import com.phonesj.news.model.bean.zhihu.DailyBeforeBean;
import com.phonesj.news.widget.ZhihuDiffCallBack;

import java.util.List;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Phone on 2017/7/18.
 */

public class DailyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    /*
    * 三种item的布局文件id
     */
    private final int topItemLayoutId = R.layout.item_daily_top;
    private final int dateItemLayoutId = R.layout.item_daily_date;
    private final int contentItemLayoutId = R.layout.item_daily_content;

    private Context context;
    private List<DailyBean.StoriesBean> storiesBeanList;
    private List<DailyBean.TopStoriesBean> topStoriesBeanList;

    private ViewPager topViewPager;
    private DailyTopAdapter topAdapter;

    private boolean isBeforeData = false;//不是当前的数据则true
    private String currentTitle = "今日热闻";

    private OnItemClickListener onItemClickListener;

    public enum ITEM_TYPE {
        ITEM_TOP,       //滚动栏
        ITEM_DATE,      //日期
        ITEM_CONTENT    //内容
    }

    public DailyAdapter(Context context, List<DailyBean.StoriesBean> storiesBeanList) {
        this.context = context;
        this.storiesBeanList = storiesBeanList;
    }

    @Override
    public int getItemViewType(int position) {
        if (isBeforeData) {
            if (position == 0) {
                return ITEM_TYPE.ITEM_DATE.ordinal();
            } else {
                return ITEM_TYPE.ITEM_CONTENT.ordinal();
            }
        } else {
            if (position == 0) {
                return ITEM_TYPE.ITEM_TOP.ordinal();
            } else if (position == 1) {
                return ITEM_TYPE.ITEM_DATE.ordinal();
            } else {
                return ITEM_TYPE.ITEM_CONTENT.ordinal();
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE.ITEM_TOP.ordinal()) {
            // TODO: 2017/7/18
            topAdapter = new DailyTopAdapter(context, topStoriesBeanList);
            View view = LayoutInflater.from(context).inflate(topItemLayoutId, parent, false);
            return new TopViewHolder(view);
        } else if (viewType == ITEM_TYPE.ITEM_DATE.ordinal()) {
            View view = LayoutInflater.from(context).inflate(dateItemLayoutId, parent, false);
            return new DateViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(contentItemLayoutId, parent, false);
            return new ContentViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TopViewHolder) {
            ((TopViewHolder) holder).vpTop.setAdapter(topAdapter);
            topViewPager = ((TopViewHolder) holder).vpTop;
        } else if (holder instanceof DateViewHolder) {
            ((DateViewHolder) holder).tvDailyDate.setText(currentTitle);
        } else {
            final int currentPosition;
            if (isBeforeData) {
                currentPosition = position - 1;
            } else {
                currentPosition = position - 2;
            }
            ((ContentViewHolder) holder).tvDailyItemTitle.setText(storiesBeanList
                .get(currentPosition)
                .getTitle());
            //已读/未读使用不同的文字颜色
            if (storiesBeanList.get(currentPosition).getReadState()) {
                ((ContentViewHolder) holder).tvDailyItemTitle.setTextColor(ContextCompat.getColor(context, R.color.news_read));
            } else {
                ((ContentViewHolder) holder).tvDailyItemTitle.setTextColor(ContextCompat.getColor(context, R.color.news_unread));
            }
            //加载图片
            ImageLoader.load(context, storiesBeanList
                .get(currentPosition)
                .getImages()
                .get(0), ((ContentViewHolder) holder).ivDailyItemImage);
            //设置item监听器
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(holder.itemView, currentPosition);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return storiesBeanList.size();
    }


    public static class TopViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.vp_top)
        ViewPager vpTop;
        @BindView(R.id.ll_point_container)
        LinearLayout llPointContainer;

        public TopViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class DateViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_daily_date)
        TextView tvDailyDate;

        public DateViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class ContentViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_daily_item_image)
        ImageView ivDailyItemImage;
        @BindView(R.id.tv_daily_item_title)
        TextView tvDailyItemTitle;

        public ContentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public boolean isBeforeData() {
        return isBeforeData;
    }

    public void setReadState(int position, boolean state) {
        storiesBeanList.get(position).setReadState(state);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void addDailyDate(DailyBean info) {
        currentTitle = "今日热闻";
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new ZhihuDiffCallBack(storiesBeanList, info
            .getStories()), true);
        storiesBeanList = info.getStories();
        topStoriesBeanList = info.getTop_stories();
        isBeforeData = false;
        diffResult.dispatchUpdatesTo(this);//通知数据源发生改变，刷新ui
    }

    public void addDailyBeforeDate(DailyBeforeBean info) {
        currentTitle = info.getDate();
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new ZhihuDiffCallBack(storiesBeanList, info
            .getStories()), true);
        storiesBeanList = info.getStories();
        isBeforeData = true;
        diffResult.dispatchUpdatesTo(this);//通知数据源发生改变，刷新ui
    }

    public void changeTopPage(int current) {
        if (!isBeforeData && topViewPager != null) {
            topViewPager.setCurrentItem(current);
        }
    }
}
