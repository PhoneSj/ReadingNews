package com.phonesj.news.model.http.api;

import com.phonesj.news.model.bean.gank.GankItemBean;
import com.phonesj.news.model.bean.gank.GankSearchBean;
import com.phonesj.news.model.http.response.GankHttpResponse;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Phone on 2017/7/26.
 */

public interface GankApis {

    String HOST = "http://gank.io/api/";

    @GET("data/{tech}/{num}/{page}")
    Flowable<GankHttpResponse<List<GankItemBean>>> getTechList(@Path("tech") String tech, @Path("num") int num, @Path("page") int page);

    @GET("data/福利/{num}/{page}")
    Flowable<GankHttpResponse<List<GankItemBean>>> getGrilList(@Path("num") int num, @Path("page") int page);

    @GET("random/data/福利/{num}")
    Flowable<GankHttpResponse<List<GankItemBean>>> getRandomGril(@Path("num") int num);

    @GET("search/query/{query}/category/{type}/count/{count}/page/{page} ")
    Flowable<GankHttpResponse<List<GankSearchBean>>> getSearchList(@Path("query") String query, @Path("type") String type, @Path("count") int count, @Path("page") int page);

}


