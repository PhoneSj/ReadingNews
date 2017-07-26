package com.phonesj.news.util;

import com.phonesj.news.model.http.execption.ApiException;
import com.phonesj.news.model.http.response.GankHttpResponse;

import org.reactivestreams.Publisher;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Phone on 2017/7/17.
 */

public class RxUtil {

    /**
     * 简化线程：上游工作在io线程，下游工作在主线程
     *
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<T, T> rxSchedulerHelper() {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> upstream) {
                return upstream
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


    /**
     * 统一处理Gank网络请求的返回结果
     *
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<GankHttpResponse<T>, T> handleResult() {
        return new FlowableTransformer<GankHttpResponse<T>, T>() {
            @Override
            public Publisher<T> apply(Flowable<GankHttpResponse<T>> upstream) {
                return upstream.flatMap(new Function<GankHttpResponse<T>, Publisher<T>>() {
                    @Override
                    public Publisher<T> apply(@NonNull GankHttpResponse<T> tGankHttpResponse) throws Exception {
                        if (!tGankHttpResponse.isError()) {
                            return createData(tGankHttpResponse.getResults());
                        } else {
                            return Flowable.error(new ApiException("服务器返回错误"));
                        }
                    }
                });
            }
        };
    }

    /**
     * 构建Flowable
     *
     * @param results
     * @param <T>
     * @return
     */
    private static <T> Publisher<T> createData(final T results) {
        return Flowable.create(new FlowableOnSubscribe<T>() {
            @Override
            public void subscribe(FlowableEmitter<T> e) throws Exception {
                e.onNext(results);
                e.onComplete();
            }
        }, BackpressureStrategy.BUFFER);
    }
}
