package com.phonesj.news.model;

import com.phonesj.news.model.bean.WelcomeBean;
import com.phonesj.news.model.bean.zhihu.DailyBean;
import com.phonesj.news.model.bean.zhihu.DailyBeforeBean;
import com.phonesj.news.model.bean.zhihu.HotBean;
import com.phonesj.news.model.bean.zhihu.SectionBean;
import com.phonesj.news.model.bean.zhihu.ThemeBean;
import com.phonesj.news.model.bean.zhihu.ZhihuDetailBean;
import com.phonesj.news.model.bean.zhihu.ZhihuDetailExtraBean;
import com.phonesj.news.model.db.DBHelper;
import com.phonesj.news.model.http.HttpHelper;
import com.phonesj.news.model.sp.SPHelper;

import io.reactivex.Flowable;

/**
 * Created by Phone on 2017/7/14.
 */

public class DataManager implements HttpHelper, DBHelper, SPHelper {

    private HttpHelper mHttpHelper;
    private DBHelper mDBHelper;
    private SPHelper mSPHelper;

    public DataManager(HttpHelper mHttpHelper, DBHelper mDBHelper, SPHelper mSPHelper) {
        this.mHttpHelper = mHttpHelper;
        this.mDBHelper = mDBHelper;
        this.mSPHelper = mSPHelper;
    }

    @Override
    public Flowable<WelcomeBean> fetchWelcomeInfo(String res) {
        return mHttpHelper.fetchWelcomeInfo(res);
    }

    @Override
    public Flowable<DailyBean> fetchDailyInfo() {
        return mHttpHelper.fetchDailyInfo();
    }

    @Override
    public Flowable<DailyBeforeBean> fetchDailyBeforeInfo(String date) {
        return mHttpHelper.fetchDailyBeforeInfo(date);
    }

    @Override
    public Flowable<ThemeBean> fetchThemeInfo() {
        return mHttpHelper.fetchThemeInfo();
    }

    @Override
    public Flowable<SectionBean> fetchSectionInfo() {
        return mHttpHelper.fetchSectionInfo();
    }

    @Override
    public Flowable<HotBean> fetchHotInfo() {
        return mHttpHelper.fetchHotInfo();
    }

    @Override
    public Flowable<ZhihuDetailBean> fetchZhihuDetailInfo(int id) {
        return mHttpHelper.fetchZhihuDetailInfo(id);
    }

    @Override
    public Flowable<ZhihuDetailExtraBean> fetchZhihuDetailExtraInfo(int id) {
        return mHttpHelper.fetchZhihuDetailExtraInfo(id);
    }

    @Override
    public boolean getNightModeState() {
        return mSPHelper.getNightModeState();
    }

    @Override
    public void setNightModeState(boolean state) {
        mSPHelper.setNightModeState(state);
    }

    @Override
    public boolean getNoImageState() {
        return mSPHelper.getNoImageState();
    }

    @Override
    public void setNoImageState(boolean state) {
        mSPHelper.setNoImageState(state);
    }

    @Override
    public boolean getAutoCacheState() {
        return mSPHelper.getAutoCacheState();
    }

    @Override
    public void setAutoCacheState(boolean state) {
        mSPHelper.setAutoCacheState(state);
    }

    @Override
    public int getCurrentItem() {
        return mSPHelper.getCurrentItem();
    }

    @Override
    public void setCurrentItem(int item) {
        mSPHelper.getCurrentItem();
    }

    @Override
    public void insertNewsId(int id) {
        mDBHelper.insertNewsId(id);
    }

    @Override
    public boolean queryNewsId(int id) {
        return mDBHelper.queryNewsId(id);
    }
}
