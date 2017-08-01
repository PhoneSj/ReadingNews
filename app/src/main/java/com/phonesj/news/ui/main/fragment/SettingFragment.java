package com.phonesj.news.ui.main.fragment;

import com.phonesj.news.R;
import com.phonesj.news.app.Constants;
import com.phonesj.news.base.BaseFragment;
import com.phonesj.news.base.contract.main.SettingContract;
import com.phonesj.news.component.ACache;
import com.phonesj.news.component.RxBus;
import com.phonesj.news.model.bean.main.VersionBean;
import com.phonesj.news.model.event.NightModeEvent;
import com.phonesj.news.presenter.main.SettingPresenter;
import com.phonesj.news.ui.main.activity.MainActivity;
import com.phonesj.news.util.LogUtil;
import com.phonesj.news.util.ShareUtil;

import java.io.File;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;

import static com.phonesj.news.R.id.tv_setting_clear;

/**
 * Created by Phone on 2017/7/19.
 */

public class SettingFragment extends BaseFragment<SettingPresenter> implements SettingContract.View, CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.cb_setting_cache)
    AppCompatCheckBox cbSettingCache;
    @BindView(R.id.cb_setting_image)
    AppCompatCheckBox cbSettingImage;
    @BindView(R.id.cb_setting_night)
    AppCompatCheckBox cbSettingNight;
    @BindView(R.id.ll_setting_feedback)
    LinearLayout llSettingFeedback;
    @BindView(tv_setting_clear)
    TextView tvSettingClear;
    @BindView(R.id.ll_setting_clear)
    LinearLayout llSettingClear;
    @BindView(R.id.tv_setting_update)
    TextView tvSettingUpdate;
    @BindView(R.id.ll_setting_update)
    LinearLayout llSettingUpdate;

    private File cacheFile;
    private String versionName;
    private boolean isNull = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        isNull = savedInstanceState == null;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_setting;
    }

    @Override
    protected void initEventAndData() {
        cbSettingCache.setChecked(mPresenter.getAutoCacheState());
        cbSettingImage.setChecked(mPresenter.getNoImageState());
        cbSettingNight.setChecked(mPresenter.getNightModeState());
        cbSettingCache.setOnCheckedChangeListener(this);
        cbSettingImage.setOnCheckedChangeListener(this);
        cbSettingNight.setOnCheckedChangeListener(this);

        cacheFile = new File(Constants.PATH_CACHE);//缓存路径
        tvSettingClear.setText(ACache.getCacheSize(cacheFile));//缓存大小

        try {
            PackageManager pm = mContext.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(getActivity().getPackageName(), PackageManager.GET_ACTIVITIES);
            versionName = pi.versionName;
            tvSettingUpdate.setText(String.format("当前版本 v%s", versionName));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.cb_setting_cache:
                mPresenter.setAutoCacheState(b);
                break;
            case R.id.cb_setting_image:
                mPresenter.setNoImageState(b);
                break;
            case R.id.cb_setting_night:
                if (isNull) {   //防止夜间模式MainActivity执行reCreate后重复调用
                    mPresenter.setNightModeState(b);
                    NightModeEvent event = new NightModeEvent();
                    event.setNightMode(b);
                    RxBus.getDefault().post(event);//发送事件到RxBus中，在MainPresenter中注册了该事件
                }
                break;
        }
    }


    @OnClick({R.id.ll_setting_feedback, R.id.ll_setting_clear, R.id.ll_setting_update})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_setting_feedback:
                ShareUtil.sendEmail(mContext, "选择邮件客户端");
                break;
            case R.id.ll_setting_clear:
                ACache.deleteDir(cacheFile);//清空缓存
                tvSettingClear.setText(ACache.getCacheSize(cacheFile));
                break;
            case R.id.ll_setting_update:
                mPresenter.checkVersion(versionName);
                break;
        }
    }

    @Override
    public void showUpdateDialog(VersionBean bean) {
        StringBuilder content = new StringBuilder("版本号: v");
        content.append(bean.getVersionName());
        content.append("\r\n");
        content.append("版本大小: ");
        content.append(bean.getSize());
        content.append("\r\n");
        content.append("更新内容:\r\n");
        content.append(bean.getDescription().replace("\\r\\n", "\r\n"));
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(mContext);
        builder.setTitle("检测到新版本!");
        builder.setMessage(content);
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("马上更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Activity mActivity = getActivity();
                if (mActivity instanceof MainActivity) {
                    ((MainActivity) mActivity).checkPermissions();
                }
            }
        });
        builder.show();
    }
}
