package com.phonesj.news.model.http;

import com.phonesj.news.model.bean.WelcomeBean;
import com.phonesj.news.model.bean.zhihu.DailyBean;
import com.phonesj.news.model.bean.zhihu.DailyBeforeBean;
import com.phonesj.news.model.bean.zhihu.HotBean;
import com.phonesj.news.model.bean.zhihu.SectionBean;
import com.phonesj.news.model.bean.zhihu.ThemeBean;
import com.phonesj.news.model.bean.zhihu.ZhihuDetailBean;
import com.phonesj.news.model.bean.zhihu.ZhihuDetailExtraBean;
import com.phonesj.news.model.http.api.ZhihuApis;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by Phone on 2017/7/14.
 */

public class RetrofitHelper implements HttpHelper {

    private ZhihuApis zhihuApis;

    @Inject
    public RetrofitHelper(ZhihuApis zhihuApis) {
        this.zhihuApis = zhihuApis;
    }

    @Override
    public Flowable<WelcomeBean> fetchWelcomeInfo(String res) {
        return zhihuApis.getWelcomeInfo(res);
    }

    @Override
    public Flowable<DailyBean> fetchDailyInfo() {
        return zhihuApis.getDailyInfo();
    }

    @Override
    public Flowable<DailyBeforeBean> fetchDailyBeforeInfo(String date) {
        return zhihuApis.getDailyBeforeInfo(date);
    }

    @Override
    public Flowable<ThemeBean> fetchThemeInfo() {
        return zhihuApis.getThemeInfo();
    }

    @Override
    public Flowable<SectionBean> fetchSectionInfo() {
        return zhihuApis.getSectionInfo();
    }

    @Override
    public Flowable<HotBean> fetchHotInfo() {
        return zhihuApis.getHotInfo();
    }

    @Override
    public Flowable<ZhihuDetailBean> fetchZhihuDetailInfo(int id) {
        return zhihuApis.getZhihuDetailInfo(id);
    }

    @Override
    public Flowable<ZhihuDetailExtraBean> fetchZhihuDetailExtraInfo(int id) {
        return zhihuApis.getZhihuDetailExtraInfo(id);
    }
}
