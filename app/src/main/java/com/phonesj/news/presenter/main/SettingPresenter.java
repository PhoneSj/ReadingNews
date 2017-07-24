package com.phonesj.news.presenter.main;

import com.phonesj.news.base.RxPresenter;
import com.phonesj.news.base.contract.main.SettingContract;
import com.phonesj.news.model.DataManager;
import com.phonesj.news.model.bean.main.VersionBean;
import com.phonesj.news.util.RxUtil;
import com.phonesj.news.widget.CommonSubscriber;

import javax.inject.Inject;

/**
 * Created by Phone on 2017/7/24.
 */

public class SettingPresenter extends RxPresenter<SettingContract.View> implements SettingContract.Presenter {

    private DataManager mDataManager;

    @Inject
    public SettingPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    @Override
    public boolean getAutoCacheState() {
        return mDataManager.getAutoCacheState();
    }

    @Override
    public void setAutoCacheState(boolean state) {
        mDataManager.setAutoCacheState(state);
    }

    @Override
    public boolean getNoImageState() {
        return mDataManager.getNoImageState();
    }

    @Override
    public void setNoImageState(boolean state) {
        mDataManager.setNoImageState(state);
    }

    @Override
    public boolean getNightModeState() {
        return mDataManager.getNightModeState();
    }

    @Override
    public void setNightModeState(boolean state) {
        mDataManager.setNightModeState(state);
    }

    @Override
    public void checkVersion(final String versionName) {
        addSubscribe(mDataManager
            .fetchVersionInfo()
            .compose(RxUtil.<VersionBean>rxSchedulerHelper())
            .subscribeWith(new CommonSubscriber<VersionBean>(mView) {
                @Override
                public void onNext(VersionBean versionBean) {
                    if (Integer.valueOf(versionName.replace(".", "")) < Integer.valueOf(versionBean
                        .getVersionName()
                        .replace(".", ""))) {
                        mView.showUpdateDialog(versionBean);
                    }
                }

                @Override
                public void onError(Throwable t) {
                    super.onError(t);
                    mView.showErrorMsg("版本信息获取失败");
                }
            }));
    }
}
