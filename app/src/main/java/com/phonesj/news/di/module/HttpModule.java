package com.phonesj.news.di.module;

import com.phonesj.news.BuildConfig;
import com.phonesj.news.app.Constants;
import com.phonesj.news.di.qualifier.GankUrl;
import com.phonesj.news.di.qualifier.GoldUrl;
import com.phonesj.news.di.qualifier.MyUrl;
import com.phonesj.news.di.qualifier.WechatUrl;
import com.phonesj.news.di.qualifier.ZhihuUrl;
import com.phonesj.news.model.http.api.GankApis;
import com.phonesj.news.model.http.api.GoldApis;
import com.phonesj.news.model.http.api.MyApis;
import com.phonesj.news.model.http.api.WechatApis;
import com.phonesj.news.model.http.api.ZhihuApis;
import com.phonesj.news.util.SystemUtil;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Phone on 2017/7/14.
 */

@Module
public class HttpModule {


    @Provides
    @Singleton
    ZhihuApis getZhihuApis(@ZhihuUrl Retrofit retrofit) {
        return retrofit.create(ZhihuApis.class);
    }

    @Provides
    @Singleton
    MyApis getMyApis(@MyUrl Retrofit retrofit) {
        return retrofit.create(MyApis.class);
    }

    @Provides
    @Singleton
    GankApis getGankApis(@GankUrl Retrofit retrofit) {
        return retrofit.create(GankApis.class);
    }

    @Provides
    @Singleton
    GoldApis getGoldApis(@GoldUrl Retrofit retrofit) {
        return retrofit.create(GoldApis.class);
    }

    @Provides
    @Singleton
    WechatApis getWechatApis(@WechatUrl Retrofit retrofit) {
        return retrofit.create(WechatApis.class);
    }

    @Provides
    @Singleton
    @ZhihuUrl
    Retrofit getZhihuRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, ZhihuApis.HOST);
    }

    @Provides
    @Singleton
    @MyUrl
    Retrofit getMyRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, MyApis.HOST);
    }

    @Provides
    @Singleton
    @GankUrl
    Retrofit getGankRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, GankApis.HOST);
    }

    @Provides
    @Singleton
    @GoldUrl
    Retrofit getGoldRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, GoldApis.HOST);
    }

    @Provides
    @Singleton
    @WechatUrl
    Retrofit getWechatRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, WechatApis.HOST);
    }

    @Provides
    @Singleton
    Retrofit.Builder getRetrofitBuilder() {
        return new Retrofit.Builder();
    }

    @Singleton
    @Provides
    OkHttpClient provideClient(OkHttpClient.Builder builder) {
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
            builder.addInterceptor(loggingInterceptor);
        }
        File cacheFile = new File(Constants.PATH_CACHE);
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);//缓存大小50MB
        //自定义拦截器
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!SystemUtil.isNetworkConnected()) {
                    request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
                }
                Response response = chain.proceed(request);
                if (SystemUtil.isNetworkConnected()) {
                    int maxAge = 0;
                    // 有网络时, 不缓存, 最大保存时长为0
                    response
                        .newBuilder()
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .removeHeader("Pragma")
                        .build();
                } else {
                    // 无网络时，设置超时为4周
                    int maxStale = 60 * 60 * 24 * 28;
                    response
                        .newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .removeHeader("Pragma")
                        .build();
                }
                return response;
            }
        };
        //        Interceptor apikey = new Interceptor() {
        //            @Override
        //            public Response intercept(Chain chain) throws IOException {
        //                Request request = chain.request();
        //                request = request.newBuilder()
        //                        .addHeader("apikey",Constants.KEY_API)
        //                        .build();
        //                return chain.proceed(request);
        //            }
        //        }
        //        设置统一的请求头部参数
        //        builder.addInterceptor(apikey);
        //设置缓存
        builder.addNetworkInterceptor(cacheInterceptor);
        builder.addInterceptor(cacheInterceptor);
        builder.cache(cache);
        //设置超时
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
        return builder.build();
    }

    @Provides
    @Singleton
    OkHttpClient.Builder getOkHttpClientBuilder() {
        return new OkHttpClient.Builder();
    }

    private Retrofit createRetrofit(Retrofit.Builder builder, OkHttpClient client, String url) {
        return builder
            .baseUrl(url)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    }


}
