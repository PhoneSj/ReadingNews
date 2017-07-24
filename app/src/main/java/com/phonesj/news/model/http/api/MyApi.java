package com.phonesj.news.model.http.api;

import com.phonesj.news.model.bean.main.VersionBean;

import io.reactivex.Flowable;
import retrofit2.http.GET;

/**
 * Created by Phone on 2017/7/24.
 */

public interface MyApi {

    String HOST = "http://news-at.zhihu.com/api/4/";

    @GET("version")
    Flowable<VersionBean> getVersionInfo();
}
