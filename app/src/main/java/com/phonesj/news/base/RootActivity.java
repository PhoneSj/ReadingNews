package com.phonesj.news.base;

import com.phonesj.news.R;
import com.phonesj.news.widget.ProgressImageView;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Phone on 2017/7/18.
 */

public abstract class RootActivity<T extends BasePresenter> extends BaseActivity<T> {

    private static final int STATE_MAIN = 0x00;
    private static final int STATE_LOADING = 0x01;
    private static final int STATE_ERROR = 0x02;

    private int currentState = STATE_MAIN;
    private boolean isErrorViewAdded = false;//错误提示界面是否已经添加到该页面了
    private boolean isLoadingViewAdded = false;//加载提示界面是否已经添加到该页面了
    private int mLoadingResource = R.layout.view_progress;
    private int mErrorResource = R.layout.view_error;

    private ViewGroup viewMain;
    private ViewGroup viewParent;

    private View viewError;
    private View viewLoading;

    private ProgressImageView piv_progress;

    @Override
    protected void initEventAndData() {
        viewMain = (ViewGroup) findViewById(R.id.view_main);
        if (viewMain == null) {
            throw new IllegalStateException("The subclasss of RootFragment must contain a View named 'view_main'.");
        }
        viewParent = (ViewGroup) viewMain.getParent();
        viewMain.setVisibility(View.VISIBLE);
    }

    @Override
    public void stateError() {
        super.stateError();
        if (currentState == STATE_MAIN) {
            return;
        }
        if (!isErrorViewAdded) {
            isErrorViewAdded = true;
            View.inflate(mContext, mErrorResource, viewParent);
            viewError = viewParent.findViewById(R.id.view_error);
            if (viewError == null) {
                throw new IllegalStateException("A View should be named 'view_error' in ErrorLayoutResource.");
            }
        }
        hideCurrentView();
        currentState = STATE_ERROR;
        viewError.setVisibility(View.VISIBLE);
    }

    @Override
    public void stateLoading() {
        super.stateLoading();
        if (currentState == STATE_LOADING) {
            return;
        }
        if (!isLoadingViewAdded) {
            isLoadingViewAdded = true;
            View.inflate(mContext, mLoadingResource, viewParent);
            viewLoading = viewParent.findViewById(R.id.view_loading);
            if (viewLoading == null) {
                throw new IllegalStateException("A view should be named 'view_loading' in " + "LoadingLayout");
            }
            piv_progress = (ProgressImageView) viewLoading.findViewById(R.id.piv_progress);
        }
        hideCurrentView();
        currentState = STATE_LOADING;
        viewLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void stateMain() {
        super.stateMain();
        if (currentState == STATE_MAIN) {
            return;
        }
        hideCurrentView();
        currentState = STATE_MAIN;
        piv_progress.start();
        viewMain.setVisibility(View.VISIBLE);
    }

    private void hideCurrentView() {
        switch (currentState) {
            case STATE_MAIN:
                viewMain.setVisibility(View.GONE);
                break;
            case STATE_LOADING:
                if (viewLoading != null) {
                    piv_progress.stop();
                    viewLoading.setVisibility(View.GONE);
                }
                break;
            case STATE_ERROR:
                if (viewError != null) {
                    viewError.setVisibility(View.GONE);
                }
                break;
        }
    }
}
