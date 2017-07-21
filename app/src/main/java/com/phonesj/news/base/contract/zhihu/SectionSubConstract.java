package com.phonesj.news.base.contract.zhihu;

import com.phonesj.news.base.BasePresenter;
import com.phonesj.news.base.BaseView;
import com.phonesj.news.model.bean.zhihu.SectionSubBean;

/**
 * Created by Phone on 2017/7/21.
 */

public interface SectionSubConstract {

    interface View extends BaseView {
        void showContent(SectionSubBean info);
    }

    interface Presenter extends BasePresenter<View> {

        void getSectionSubData(int id);

        void insertReadStateToDB(int id);
    }
}
