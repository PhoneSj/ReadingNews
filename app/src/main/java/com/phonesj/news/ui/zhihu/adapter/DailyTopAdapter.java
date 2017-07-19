package com.phonesj.news.ui.zhihu.adapter;

import com.phonesj.news.R;
import com.phonesj.news.component.ImageLoader;
import com.phonesj.news.model.bean.zhihu.DailyBean;

import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Phone on 2017/7/18.
 */

public class DailyTopAdapter extends PagerAdapter {

    private Context context;
    private List<DailyBean.TopStoriesBean> topStoriesBeanList;

    public DailyTopAdapter(Context context, List<DailyBean.TopStoriesBean> topStoriesBeanList) {
        this.context = context;
        this.topStoriesBeanList = topStoriesBeanList;
    }

    @Override
    public int getCount() {
        return topStoriesBeanList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        View view = LayoutInflater
            .from(context)
            .inflate(R.layout.item_daily_top_pager, container, false);
        ImageView ivTopImage = (ImageView) view.findViewById(R.id.iv_top_image);
        TextView tvTopTitle = (TextView) view.findViewById(R.id.tv_top_title);
        tvTopTitle.setText(topStoriesBeanList.get(position).getTitle());
        ImageLoader.load(context, topStoriesBeanList.get(position).getImage(), ivTopImage);
        //设置点击事件
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 2017/7/18
                //                Intent intent = new Intent(context, ZhihuDetailActivity.class);
                //                //传递该news的id过去
                //                int id = topStoriesBeanList.get(position).getId();
                //                intent.putExtra(Constants.INTENT_ZHIHU_DETAIL_ID, id);
                //                intent.putExtra(Constants.INTENT_ZHIHU_DETAIL_TRANSITION, true);
                //                context.startActivity(intent);
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
