package com.phonesj.news.base.contract.wechat;

import com.phonesj.news.base.BasePresenter;
import com.phonesj.news.base.BaseView;
import com.phonesj.news.model.bean.wechat.WXItemBean;

import java.util.List;

/**
 * Created by Phone on 2017/8/1.
 */

public interface WechatNewsContract {
    interface View extends BaseView {
        void showContent(List<WXItemBean> info);

        void showMoreContent(List<WXItemBean> info);
    }

    interface Presenter extends BasePresenter<View> {
        void getWechatData(String type);

        void getMoreWechatData(String type);
    }
}
