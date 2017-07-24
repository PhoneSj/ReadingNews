package com.phonesj.news.base.contract.main;

import com.phonesj.news.base.BasePresenter;
import com.phonesj.news.base.BaseView;
import com.phonesj.news.model.bean.main.VersionBean;

/**
 * Created by Phone on 2017/7/24.
 */

public interface SettingContract {
    interface View extends BaseView {
        void showUpdateDialog(VersionBean bean);

    }

    interface Presenter extends BasePresenter<View> {
        boolean getAutoCacheState();

        void setAutoCacheState(boolean state);

        boolean getNoImageState();

        void setNoImageState(boolean state);

        boolean getNightModeState();

        void setNightModeState(boolean state);

        void checkVersion(String versionName);
    }
}
