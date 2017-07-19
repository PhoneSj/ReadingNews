package com.phonesj.news.widget;

import com.phonesj.news.base.BaseView;

import android.text.TextUtils;
import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by Phone on 2017/7/18.
 */

public abstract class CommonSubscriber<T> extends ResourceSubscriber<T> {

    private BaseView mView;
    private String mErrorMsg;
    private boolean isShowErrorState = true;

    public CommonSubscriber(BaseView mView) {
        this.mView = mView;
    }

    public CommonSubscriber(BaseView mView, String mErrorMsg) {
        this.mView = mView;
        this.mErrorMsg = mErrorMsg;
    }

    public CommonSubscriber(BaseView mView, String mErrorMsg, boolean isShowErrorState) {
        this.mView = mView;
        this.mErrorMsg = mErrorMsg;
        this.isShowErrorState = isShowErrorState;
    }

    @Override
    public void onError(Throwable t) {
        if (mView == null) {
            return;
        }
        if (!TextUtils.isEmpty(mErrorMsg)) {
            mView.showErrorMsg(mErrorMsg);
        }
        if (isShowErrorState) {
            mView.stateError();
        }
    }

    @Override
    public void onComplete() {

    }
}
