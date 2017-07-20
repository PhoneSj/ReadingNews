package com.phonesj.news.model.db;

import com.phonesj.news.model.bean.zhihu.LikeBean;

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

    void inertLikeBean(LikeBean bean);

    void deleteLikeBean(String id);

    boolean queryLikeId(String id);
}
