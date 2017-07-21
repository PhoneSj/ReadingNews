package com.phonesj.news.model;

import com.phonesj.news.model.bean.main.WelcomeBean;
import com.phonesj.news.model.bean.zhihu.CommonBean;
import com.phonesj.news.model.bean.zhihu.DailyBean;
import com.phonesj.news.model.bean.zhihu.DailyBeforeBean;
import com.phonesj.news.model.bean.zhihu.HotBean;
import com.phonesj.news.model.bean.zhihu.LikeBean;
import com.phonesj.news.model.bean.zhihu.SectionBean;
import com.phonesj.news.model.bean.zhihu.SectionSubBean;
import com.phonesj.news.model.bean.zhihu.ThemeBean;
import com.phonesj.news.model.bean.zhihu.ThemeSubBean;
import com.phonesj.news.model.bean.zhihu.ZhihuDetailBean;
import com.phonesj.news.model.bean.zhihu.ZhihuDetailExtraBean;
import com.phonesj.news.model.db.DBHelper;
import com.phonesj.news.model.http.HttpHelper;
import com.phonesj.news.model.sp.SPHelper;

import java.util.List;

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
    public Flowable<CommonBean> fetchShortCommonInfo(int id) {
        return mHttpHelper.fetchShortCommonInfo(id);
    }

    @Override
    public Flowable<CommonBean> fetchLongCommonInfo(int id) {
        return mHttpHelper.fetchLongCommonInfo(id);
    }

    @Override
    public Flowable<ThemeSubBean> fetchThemeSubInfo(int id) {
        return mHttpHelper.fetchThemeSubInfo(id);
    }

    @Override
    public Flowable<SectionSubBean> fetchSectionSubInfo(int id) {
        return mHttpHelper.fetchSectionSubInfo(id);
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

    @Override
    public void inertLikeBean(LikeBean bean) {
        mDBHelper.inertLikeBean(bean);
    }

    @Override
    public void deleteLikeBean(String id) {
        mDBHelper.deleteLikeBean(id);
    }

    @Override
    public boolean queryLikeId(String id) {
        return mDBHelper.queryLikeId(id);
    }

    @Override
    public List<LikeBean> queryLikeAll() {
        return mDBHelper.queryLikeAll();
    }
}
