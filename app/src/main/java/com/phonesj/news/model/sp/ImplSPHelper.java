package com.phonesj.news.model.sp;

import com.phonesj.news.app.App;
import com.phonesj.news.app.Constants;

import javax.inject.Inject;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Phone on 2017/7/14.
 */

public class ImplSPHelper implements SPHelper {

    private static final String SP_NAME = "main";

    //主体默认不使用夜间模式
    private static final boolean DEFAULT_NIGHT_MODE = false;
    //默认有图模式
    private static final boolean DEFAULT_NO_IMAGE = false;
    //默认自动保存模式
    private static final boolean DEFAULT_AUTO_SAVE = true;

    private static final boolean DEFAULT_LIKE_POINT = false;
    private static final boolean DEFAULT_VERSION_POINT = false;
    private static final boolean DEFAULT_MANAGER_POINT = false;

    //MainActivity默认选中的是知乎页面
    private static final int DEFAULT_CURRENT_ITEM = Constants.TYPE_ZHIHU;

    private final SharedPreferences mSP;

    @Inject
    public ImplSPHelper() {
        mSP = App.getInstance().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public boolean getNightModeState() {
        return mSP.getBoolean(Constants.SP_NIGHT_MODE, DEFAULT_NIGHT_MODE);
    }

    @Override
    public void setNightModeState(boolean state) {
        mSP.edit().putBoolean(Constants.SP_NIGHT_MODE, state).apply();
    }

    @Override
    public boolean getNoImageState() {
        return mSP.getBoolean(Constants.SP_NO_IMAGE, DEFAULT_NO_IMAGE);
    }

    @Override
    public void setNoImageState(boolean state) {
        mSP.edit().putBoolean(Constants.SP_NO_IMAGE, state).apply();
    }

    @Override
    public boolean getAutoCacheState() {
        return mSP.getBoolean(Constants.SP_AUTO_CACHE, DEFAULT_AUTO_SAVE);
    }

    @Override
    public void setAutoCacheState(boolean state) {
        mSP.edit().putBoolean(Constants.SP_AUTO_CACHE, state).apply();
    }

    @Override
    public int getCurrentItem() {
        return mSP.getInt(Constants.SP_CURRENT_ITEM, DEFAULT_CURRENT_ITEM);
    }

    @Override
    public void setCurrentItem(int key) {
        mSP.edit().putInt(Constants.SP_CURRENT_ITEM, key).apply();
    }

    @Override
    public boolean getLikePoint() {
        return mSP.getBoolean(Constants.SP_LIKE_POINT, DEFAULT_LIKE_POINT);
    }

    @Override
    public void setLikePoint(boolean isFirst) {
        mSP.edit().putBoolean(Constants.SP_LIKE_POINT, isFirst).apply();
    }

    @Override
    public boolean getVersionPoint() {
        return mSP.getBoolean(Constants.SP_VERSION_POINT, DEFAULT_VERSION_POINT);
    }

    @Override
    public void setVersionPoint(boolean isFirst) {
        mSP.edit().putBoolean(Constants.SP_VERSION_POINT, isFirst).apply();
    }

    @Override
    public boolean getManagerPoint() {
        return mSP.getBoolean(Constants.SP_MANAGER_POINT, DEFAULT_MANAGER_POINT);
    }

    @Override
    public void setManagerPoint(boolean isFirst) {
        mSP.edit().putBoolean(Constants.SP_MANAGER_POINT, isFirst).apply();
    }
}
