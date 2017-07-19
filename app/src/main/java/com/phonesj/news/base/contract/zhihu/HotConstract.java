package com.phonesj.news.base.contract.zhihu;

import com.phonesj.news.base.BasePresenter;
import com.phonesj.news.base.BaseView;
import com.phonesj.news.model.bean.zhihu.HotBean;

/**
 * Created by Phone on 2017/7/19.
 */

public interface HotConstract {

    interface View extends BaseView {
        void showContent(HotBean info);
    }

    interface Presenter extends BasePresenter<View> {
        void getHotData();
    }
}
