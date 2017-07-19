package com.phonesj.news.base.contract.zhihu;

import com.phonesj.news.base.BasePresenter;
import com.phonesj.news.base.BaseView;
import com.phonesj.news.model.bean.zhihu.SectionBean;

/**
 * Created by Phone on 2017/7/19.
 */

public interface SectionConstract {

    interface View extends BaseView {
        void showContent(SectionBean info);
    }

    interface Presenter extends BasePresenter<View> {
        void getSectionData();
    }
}
