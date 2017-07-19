package com.phonesj.news.base;

/**
 * Created by Phone on 2017/7/14.
 */

public interface BasePresenter<T extends BaseView> {

    /**
     * 将MVP中的V的引用递给Presenter
     *
     * @param view
     */
    void attachView(T view);

    /**
     * 将MVP中Presenter持有的V的引用置空
     */
    void detachView();
}
