package com.phonesj.news.model.http;

import com.phonesj.news.app.Constants;
import com.phonesj.news.model.bean.gank.GankItemBean;
import com.phonesj.news.model.bean.gank.GankSearchBean;
import com.phonesj.news.model.bean.gold.GoldListBean;
import com.phonesj.news.model.bean.main.VersionBean;
import com.phonesj.news.model.bean.main.WelcomeBean;
import com.phonesj.news.model.bean.wechat.WXItemBean;
import com.phonesj.news.model.bean.zhihu.CommonBean;
import com.phonesj.news.model.bean.zhihu.DailyBean;
import com.phonesj.news.model.bean.zhihu.DailyBeforeBean;
import com.phonesj.news.model.bean.zhihu.HotBean;
import com.phonesj.news.model.bean.zhihu.SectionBean;
import com.phonesj.news.model.bean.zhihu.SectionSubBean;
import com.phonesj.news.model.bean.zhihu.ThemeBean;
import com.phonesj.news.model.bean.zhihu.ThemeSubBean;
import com.phonesj.news.model.bean.zhihu.ZhihuDetailBean;
import com.phonesj.news.model.bean.zhihu.ZhihuDetailExtraBean;
import com.phonesj.news.model.http.api.GankApis;
import com.phonesj.news.model.http.api.GoldApis;
import com.phonesj.news.model.http.api.MyApis;
import com.phonesj.news.model.http.api.WechatApis;
import com.phonesj.news.model.http.api.ZhihuApis;
import com.phonesj.news.model.http.response.GankHttpResponse;
import com.phonesj.news.model.http.response.GoldHttpResponse;
import com.phonesj.news.model.http.response.WXHttpResponse;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by Phone on 2017/7/14.
 */

public class RetrofitHelper implements HttpHelper {

    private MyApis myApis;
    private ZhihuApis zhihuApis;
    private GankApis gankApis;
    private GoldApis goldApis;
    private WechatApis wechatApis;

    @Inject
    public RetrofitHelper(MyApis myApis, ZhihuApis zhihuApis, GankApis gankApis, GoldApis goldApis, WechatApis wechatApis) {
        this.myApis = myApis;
        this.zhihuApis = zhihuApis;
        this.gankApis = gankApis;
        this.goldApis = goldApis;
        this.wechatApis = wechatApis;
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

    @Override
    public Flowable<CommonBean> fetchShortCommonInfo(int id) {
        return zhihuApis.getShortCommonInfo(id);
    }

    @Override
    public Flowable<CommonBean> fetchLongCommonInfo(int id) {
        return zhihuApis.getLongCommonInfo(id);
    }

    @Override
    public Flowable<ThemeSubBean> fetchThemeSubInfo(int id) {
        return zhihuApis.getThemeSubInfo(id);
    }

    @Override
    public Flowable<SectionSubBean> fetchSectionSubInfo(int id) {
        return zhihuApis.getSectionSubInfo(id);
    }

    @Override
    public Flowable<VersionBean> fetchVersionInfo() {
        return myApis.getVersionInfo();
    }

    @Override
    public Flowable<GankHttpResponse<List<GankItemBean>>> fetchTechList(String tech, int num, int page) {
        return gankApis.getTechList(tech, num, page);
    }

    @Override
    public Flowable<GankHttpResponse<List<GankItemBean>>> fetchGrilList(int num, int page) {
        return gankApis.getGrilList(num, page);
    }

    @Override
    public Flowable<GankHttpResponse<List<GankItemBean>>> fetchRandomGril(int num) {
        return gankApis.getRandomGril(num);
    }

    @Override
    public Flowable<GankHttpResponse<List<GankSearchBean>>> fetchGankSearchList(String query, String type, int count, int page) {
        return gankApis.getSearchList(query, type, count, page);
    }

    @Override
    public Flowable<GoldHttpResponse<List<GoldListBean>>> fetchGoldList(String type, int num, int page) {
        return goldApis.getGoldList(Constants.LEANCLOUD_ID, Constants.LEANCLOUD_SIGN, "{\"category\":\"" + type + "\"}", "-createdAt", "user,user.installation", num, page * num);
    }

    @Override
    public Flowable<GoldHttpResponse<List<GoldListBean>>> fetchGoldHotList(String type, String dataTime, int limit) {
        return goldApis.getGoldHot(Constants.LEANCLOUD_ID, Constants.LEANCLOUD_SIGN, "{\"category\":\"" + type + "\",\"createdAt\":{\"$gt\":{\"__type\":\"Date\",\"iso\":\"" + dataTime + "T00:00:00.000Z\"}},\"objectId\":{\"$nin\":[\"58362f160ce463005890753e\",\"583659fcc59e0d005775cc8c\",\"5836b7358ac2470065d3df62\"]}}", "-hotIndex", "user,user.installation", limit);
    }

    @Override
    public Flowable<WXHttpResponse<List<WXItemBean>>> fetchWXInfo(String type, int num, int page) {
        return wechatApis.getWechatHot(type, Constants.KEY_API_WECHAT, num, page);
    }

    @Override
    public Flowable<WXHttpResponse<List<WXItemBean>>> fetchWXSearchInfo(String type, int num, int page, String word) {
        return wechatApis.getWechatHotSearch(type, Constants.KEY_API_WECHAT, num, page, word);
    }

}
