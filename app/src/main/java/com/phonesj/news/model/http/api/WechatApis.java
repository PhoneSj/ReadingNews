package com.phonesj.news.model.http.api;

import com.phonesj.news.model.bean.wechat.WXItemBean;
import com.phonesj.news.model.http.response.WXHttpResponse;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Phone on 2017/8/1.
 */

public interface WechatApis {

    String HOST = "http://api.tianapi.com/";

    @GET("{type}")
    Flowable<WXHttpResponse<List<WXItemBean>>> getWechatHot(@Path("type") String type, @Query("key") String key, @Query("num") int num, @Query("page") int page);

    @GET("{type}")
    Flowable<WXHttpResponse<List<WXItemBean>>> getWechatHotSearch(@Path("type") String type, @Query("key") String key, @Query("num") int num, @Query("page") int page, @Query("word") String word);
}
