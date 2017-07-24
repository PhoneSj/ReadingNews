package com.phonesj.news.model.db;

import com.phonesj.news.model.bean.zhihu.LikeBean;

import java.util.List;

/**
 * Created by Phone on 2017/7/14.
 */

public interface DBHelper {

    /**
     * 已阅读的新闻id
     *
     * @param id
     */
    void insertNewsId(int id);

    /**
     * 查看指定id的新闻是由阅读过
     *
     * @param id
     * @return
     */
    boolean queryNewsId(int id);

    /**
     * 存入一个收藏记录
     *
     * @param bean
     */
    void inertLikeBean(LikeBean bean);

    /**
     * 删除一个收藏记录
     *
     * @param id
     */
    void deleteLikeBean(String id);

    /**
     * 查询指定id的收藏记录
     *
     * @param id
     * @return
     */
    boolean queryLikeId(String id);

    /**
     * 查询所有收藏记录
     *
     * @return
     */
    List<LikeBean> queryLikeAll();

    /**
     * 修改收藏页面中 指定记录的时间戳
     *
     * @param id
     * @param time
     * @param isPlus
     */
    void changeLikeTime(String id, long time, boolean isPlus);
}
