package com.phonesj.news.base;

/**
 * Created by Phone on 2017/7/14.
 * 使用MVP结构的Activity、Fragment实现该接口，Presenter拿到该引用刷新Activity、Fragment显示的内容
 */

public interface BaseView {

    /**
     * 显示错误信息
     *
     * @param msg
     */
    void showErrorMsg(String msg);

    /**
     * 使用夜间模式主题
     * @param isUse
     */
    void useNightMode(boolean isUse);

    void stateError();

    void stateEmpty();

    void stateLoading();

    void stateMain();
}
