package com.phonesj.news.model.sp;

/**
 * Created by Phone on 2017/7/14.
 */

public interface SPHelper {

    /**
     * 当前是否启用了夜间模式
     *
     * @return
     */
    boolean getNightModeState();

    /**
     * 启用/关闭夜间模式
     *
     * @param state
     */
    void setNightModeState(boolean state);

    /**
     * 当前是否启用了无图模式
     *
     * @return
     */
    boolean getNoImageState();

    /**
     * 启用/关闭无图模式
     *
     * @param state
     */
    void setNoImageState(boolean state);

    /**
     * 当前是否启用了自动缓存模式
     *
     * @return
     */
    boolean getAutoCacheState();

    /**
     * 启用/关闭缓存模式
     *
     * @param state
     */
    void setAutoCacheState(boolean state);

    /**
     * 在MainActivity销毁前，选中的Fragment的key
     *
     * @return
     */
    int getCurrentItem();

    /**
     * 将当前选中的Fragment的key保存到ap中
     *
     * @param item
     */
    void setCurrentItem(int item);

}
