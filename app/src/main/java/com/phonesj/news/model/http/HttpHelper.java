package com.phonesj.news.model.http;

import com.phonesj.news.model.bean.WelcomeBean;
import com.phonesj.news.model.bean.zhihu.CommonBean;
import com.phonesj.news.model.bean.zhihu.DailyBean;
import com.phonesj.news.model.bean.zhihu.DailyBeforeBean;
import com.phonesj.news.model.bean.zhihu.HotBean;
import com.phonesj.news.model.bean.zhihu.SectionBean;
import com.phonesj.news.model.bean.zhihu.ThemeBean;
import com.phonesj.news.model.bean.zhihu.ZhihuDetailBean;
import com.phonesj.news.model.bean.zhihu.ZhihuDetailExtraBean;

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
}
