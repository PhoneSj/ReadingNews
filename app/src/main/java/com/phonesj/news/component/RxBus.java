package com.phonesj.news.component;

import com.phonesj.news.util.RxUtil;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;

/**
 * Created by Phone on 2017/7/17.
 */

public class RxBus {

    private final FlowableProcessor<Object> bus;

    private RxBus() {
        bus = PublishProcessor.create().toSerialized();
    }

    public static RxBus getDefault() {
        return RxBusHolder.instance;
    }

    private static class RxBusHolder {
        private static final RxBus instance = new RxBus();
    }

    public void post(Object o) {
        bus.onNext(o);
    }

    /**
     * 该RxBus事件使用当前线程执行
     *
     * @param eventType
     * @param <T>
     * @return
     */
    public <T> Flowable<T> toFlowable(Class<T> eventType) {
        return bus.ofType(eventType);
    }

    /**
     * 该RxBus使用默认线程执行
     *
     * @param eventType
     * @param act
     * @param <T>
     * @return
     */
    public <T> Disposable toDefaultFlowable(Class<T> eventType, Consumer<T> act) {
        return bus.ofType(eventType).compose(RxUtil.<T>rxSchedulerHelper()).subscribe(act);
    }
}
