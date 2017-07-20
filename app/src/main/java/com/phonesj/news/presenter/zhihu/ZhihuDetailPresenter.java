package com.phonesj.news.presenter.zhihu;

import com.phonesj.news.app.Constants;
import com.phonesj.news.base.RxPresenter;
import com.phonesj.news.base.contract.zhihu.ZhihuDetailContract;
import com.phonesj.news.model.DataManager;
import com.phonesj.news.model.bean.zhihu.LikeBean;
import com.phonesj.news.model.bean.zhihu.ZhihuDetailBean;
import com.phonesj.news.model.bean.zhihu.ZhihuDetailExtraBean;
import com.phonesj.news.util.RxUtil;
import com.phonesj.news.widget.CommonSubscriber;

import javax.inject.Inject;

/**
 * Created by Phone on 2017/7/19.
 */

public class ZhihuDetailPresenter extends RxPresenter<ZhihuDetailContract.View> implements ZhihuDetailContract.Presenter {

    private DataManager mDataManager;
    private ZhihuDetailBean zhihuDetailBean;

    @Inject
    public ZhihuDetailPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    @Override
    public void getDetailData(int id) {
        addSubscribe(mDataManager
            .fetchZhihuDetailInfo(id)
            .compose(RxUtil.<ZhihuDetailBean>rxSchedulerHelper())
            .subscribeWith(new CommonSubscriber<ZhihuDetailBean>(mView) {
                @Override
                public void onNext(ZhihuDetailBean zhihuDetailBean) {
                    ZhihuDetailPresenter.this.zhihuDetailBean = zhihuDetailBean;
                    mView.showContent(zhihuDetailBean);
                }
            }));
    }

    @Override
    public void getDetailExtraData(int id) {
        addSubscribe(mDataManager
            .fetchZhihuDetailExtraInfo(id)
            .compose(RxUtil.<ZhihuDetailExtraBean>rxSchedulerHelper())
            .subscribeWith(new CommonSubscriber<ZhihuDetailExtraBean>(mView) {
                @Override
                public void onNext(ZhihuDetailExtraBean zhihuDetailExtraBean) {
                    mView.showExtraInfo(zhihuDetailExtraBean);
                }
            }));
    }

    @Override
    public void inertLikeData() {
        if (zhihuDetailBean != null) {
            LikeBean bean = new LikeBean();
            bean.setId(String.valueOf(zhihuDetailBean.getId()));
            bean.setImage(String.valueOf(zhihuDetailBean.getImage()));
            bean.setTitle(zhihuDetailBean.getTitle());
            bean.setType(Constants.TYPE_ZHIHU);
            bean.setTime(System.currentTimeMillis());
            mDataManager.inertLikeBean(bean);
        } else {
            mView.showErrorMsg("操作失败");
        }
    }

    @Override
    public void deleteLikeData() {
        if (zhihuDetailBean != null) {
            mDataManager.deleteLikeBean(String.valueOf(zhihuDetailBean.getId()));
        } else {
            mView.showErrorMsg("操作失败");
        }
    }

    @Override
    public void queryLikeData(int id) {
        mView.setLikeButtonState(mDataManager.queryLikeId(String.valueOf(id)));
    }

    @Override
    public boolean getNoImageState() {
        return mDataManager.getNoImageState();
    }

    @Override
    public boolean getAutoCacheState() {
        return mDataManager.getAutoCacheState();
    }
}
