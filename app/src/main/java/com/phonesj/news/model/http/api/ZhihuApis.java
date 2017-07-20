package com.phonesj.news.model.http.api;

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
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Phone on 2017/7/17.
 */

public interface ZhihuApis {

    String HOST = "http://news-at.zhihu.com/api/4/";

    @GET("start-image/{res}")
    Flowable<WelcomeBean> getWelcomeInfo(@Path("res") String res);

    @GET("news/latest")
    Flowable<DailyBean> getDailyInfo();

    @GET("news/before/{date}")
    Flowable<DailyBeforeBean> getDailyBeforeInfo(@Path("date") String date);

    @GET("themes")
    Flowable<ThemeBean> getThemeInfo();

    @GET("sections")
    Flowable<SectionBean> getSectionInfo();

    @GET("news/hot")
    Flowable<HotBean> getHotInfo();

    @GET("news/{id}")
    Flowable<ZhihuDetailBean> getZhihuDetailInfo(@Path("id") int id);

    @GET("story-extra/{id}")
    Flowable<ZhihuDetailExtraBean> getZhihuDetailExtraInfo(@Path("id") int id);

    @GET("story/{id}/short-comments")
    Flowable<CommonBean> getShortCommonInfo(@Path("id") int id);

    @GET("story/{id}/long-comments")
    Flowable<CommonBean> getLongCommonInfo(@Path("id") int id);
}
