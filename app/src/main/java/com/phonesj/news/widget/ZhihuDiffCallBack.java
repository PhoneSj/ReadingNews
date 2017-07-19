package com.phonesj.news.widget;

import com.phonesj.news.model.bean.zhihu.DailyBean;

import java.util.List;

import android.support.v7.util.DiffUtil;

/**
 * Created by Phone on 2017/7/18.
 */

public class ZhihuDiffCallBack extends DiffUtil.Callback {

    private List<DailyBean.StoriesBean> mOldDatas;
    private List<DailyBean.StoriesBean> mNewDatas;

    public ZhihuDiffCallBack(List<DailyBean.StoriesBean> mOldDatas, List<DailyBean.StoriesBean> mNewDatas) {
        this.mOldDatas = mOldDatas;
        this.mNewDatas = mNewDatas;
    }

    @Override
    public int getOldListSize() {
        return mOldDatas != null ? mOldDatas.size() : 0;
    }

    @Override
    public int getNewListSize() {
        return mNewDatas != null ? mNewDatas.size() : 0;
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldDatas.get(oldItemPosition).getId() == mNewDatas.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        DailyBean.StoriesBean oldBean = mOldDatas.get(oldItemPosition);
        DailyBean.StoriesBean newBean = mNewDatas.get(newItemPosition);
        if (!oldBean.getTitle().equals(newBean.getTitle())) {
            return false;
        }
        if (oldBean.getReadState() != newBean.getReadState()) {
            return false;
        }
        if (oldBean.getImages().size() != newBean.getImages().size()) {
            return false;
        } else {
            for (int i = 0; i < oldBean.getImages().size(); i++) {
                if (!oldBean.getImages().get(i).equals(newBean.getImages().get(i))) {
                    return false;
                }
            }
        }
        return true;
    }
}
