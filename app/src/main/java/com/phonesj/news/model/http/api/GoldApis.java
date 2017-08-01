package com.phonesj.news.model.http.api;

import com.phonesj.news.model.bean.gold.GoldListBean;
import com.phonesj.news.model.http.response.GoldHttpResponse;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * Created by Phone on 2017/7/29.
 */

public interface GoldApis {
    String HOST = "https://api.leancloud.cn/";

    /**
     * 文章列表
     */
    @GET("1.1/classes/Entry")
    Flowable<GoldHttpResponse<List<GoldListBean>>> getGoldList(@Header("X-LC-Id") String id, @Header("X-LC-Sign") String sign, @Query("where") String where, @Query("order") String order, @Query("include") String include, @Query("limit") int limit, @Query("skip") int skip);

    /**
     * 热门推荐
     */
    @GET("1.1/classes/Entry")
    Flowable<GoldHttpResponse<List<GoldListBean>>> getGoldHot(@Header("X-LC-Id") String id, @Header("X-LC-Sign") String sign, @Query("where") String where, @Query("order") String order, @Query("include") String include, @Query("limit") int limit);
}
