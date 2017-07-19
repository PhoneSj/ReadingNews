package com.phonesj.news.di.module;

import com.phonesj.news.di.qualifier.ZhihuUrl;
import com.phonesj.news.model.http.api.ZhihuApis;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
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
    Retrofit.Builder getRetrofitBuilder() {
        return new Retrofit.Builder();
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

    @Provides
    @Singleton
    @ZhihuUrl
    Retrofit getZhihuRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, ZhihuApis.HOST);
    }

    @Provides
    @Singleton
    ZhihuApis getZhihuApis(@ZhihuUrl Retrofit retrofit) {
        return retrofit.create(ZhihuApis.class);
    }

    @Singleton
    @Provides
    OkHttpClient provideClient(OkHttpClient.Builder builder) {
        //        if (BuildConfig.DEBUG) {
        //            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        //            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        //            builder.addInterceptor(loggingInterceptor);
        //        }
        //        File cacheFile = new File(Constants.PATH_CACHE);
        //        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
        //        Interceptor cacheInterceptor = new Interceptor() {
        //            @Override
        //            public Response intercept(Chain chain) throws IOException {
        //                Request request = chain.request();
        //                if (!SystemUtil.isNetworkConnected()) {
        //                    request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
        //                }
        //                Response response = chain.proceed(request);
        //                if (SystemUtil.isNetworkConnected()) {
        //                    int maxAge = 0;
        //                    // 有网络时, 不缓存, 最大保存时长为0
        //                    response
        //                        .newBuilder()
        //                        .header("Cache-Control", "public, max-age=" + maxAge)
        //                        .removeHeader("Pragma")
        //                        .build();
        //                } else {
        //                    // 无网络时，设置超时为4周
        //                    int maxStale = 60 * 60 * 24 * 28;
        //                    response
        //                        .newBuilder()
        //                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
        //                        .removeHeader("Pragma")
        //                        .build();
        //                }
        //                return response;
        //            }
        //        };
        //        //        Interceptor apikey = new Interceptor() {
        //        //            @Override
        //        //            public Response intercept(Chain chain) throws IOException {
        //        //                Request request = chain.request();
        //        //                request = request.newBuilder()
        //        //                        .addHeader("apikey",Constants.KEY_API)
        //        //                        .build();
        //        //                return chain.proceed(request);
        //        //            }
        //        //        }
        //        //        设置统一的请求头部参数
        //        //        builder.addInterceptor(apikey);
        //        //设置缓存
        //        builder.addNetworkInterceptor(cacheInterceptor);
        //        builder.addInterceptor(cacheInterceptor);
        //        builder.cache(cache);
        //设置超时
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
        return builder.build();
    }
}
