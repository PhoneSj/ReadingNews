package com.phonesj.news.model.http;

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
import com.phonesj.news.model.http.response.GankHttpResponse;
import com.phonesj.news.model.http.response.GoldHttpResponse;
import com.phonesj.news.model.http.response.WXHttpResponse;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by Phone on 2017/7/14.
 */

public interface HttpHelper {

    /**
     * 获取欢迎界面的数据
     *
     * @param res
     * @return
     */
    Flowable<WelcomeBean> fetchWelcomeInfo(String res);

    /**
     * 获取“日报”页面的最新数据
     *
     * @return
     */
    Flowable<DailyBean> fetchDailyInfo();

    /**
     * 获取“日报”页面的往日数据
     *
     * @param date
     * @return
     */
    Flowable<DailyBeforeBean> fetchDailyBeforeInfo(String date);

    /**
     * 获取“主题”页面的数据
     *
     * @return
     */
    Flowable<ThemeBean> fetchThemeInfo();

    /**
     * 获取“专栏”页面的数据
     *
     * @return
     */
    Flowable<SectionBean> fetchSectionInfo();

    /**
     * 获取“热点”页面的数据
     *
     * @return
     */
    Flowable<HotBean> fetchHotInfo();

    /**
     * 获取“详情”页面的数据
     *
     * @param id
     * @return
     */
    Flowable<ZhihuDetailBean> fetchZhihuDetailInfo(int id);

    /**
     * 获取“详情”页面的额外信息
     *
     * @param id
     * @return
     */
    Flowable<ZhihuDetailExtraBean> fetchZhihuDetailExtraInfo(int id);

    /**
     * 获取知乎的短评论
     *
     * @param id
     * @return
     */
    Flowable<CommonBean> fetchShortCommonInfo(int id);

    /**
     * 获取知乎的长评论
     *
     * @param id
     * @return
     */
    Flowable<CommonBean> fetchLongCommonInfo(int id);

    /**
     * 主体日报
     *
     * @param id 主体日报id
     * @return
     */
    Flowable<ThemeSubBean> fetchThemeSubInfo(int id);

    /**
     * 专栏
     *
     * @param id 专栏id
     * @return
     */
    Flowable<SectionSubBean> fetchSectionSubInfo(int id);

    /**
     * 获取该应用的最新版本
     *
     * @return
     */
    Flowable<VersionBean> fetchVersionInfo();

    Flowable<GankHttpResponse<List<GankItemBean>>> fetchTechList(String tech, int num, int page);

    Flowable<GankHttpResponse<List<GankItemBean>>> fetchGrilList(int num, int page);

    Flowable<GankHttpResponse<List<GankItemBean>>> fetchRandomGril(int num);

    Flowable<GankHttpResponse<List<GankSearchBean>>> fetchGankSearchList(String query, String type, int count, int page);

    Flowable<GoldHttpResponse<List<GoldListBean>>> fetchGoldList(String type, int num, int page);

    Flowable<GoldHttpResponse<List<GoldListBean>>> fetchGoldHotList(String type, String dataTime, int limit);

    Flowable<WXHttpResponse<List<WXItemBean>>> fetchWXInfo(int num, int page);

    Flowable<WXHttpResponse<List<WXItemBean>>> fetchWXSearchInfo(int num, int page, String word);
}
