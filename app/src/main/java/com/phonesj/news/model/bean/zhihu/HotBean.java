package com.phonesj.news.model.bean.zhihu;

import java.util.List;

/**
 * Created by Phone on 2017/7/19.
 */

public class HotBean {

    private List<RecentBean> recent;

    public List<RecentBean> getRecent() {
        return recent;
    }

    public void setRecent(List<RecentBean> recent) {
        this.recent = recent;
    }

    public static class RecentBean {
        /**
         * news_id : 9525918
         * url : http://news-at.zhihu.com/api/2/news/9525918
         * thumbnail : https://pic3.zhimg.com/v2-38a10318fcd4d97ed4d86d7194bb2972.jpg
         * title : 瞎扯 · 如何正确地吐槽
         */

        private int news_id;
        private String url;
        private String thumbnail;
        private String title;
        private boolean readState;

        public int getNews_id() {
            return news_id;
        }

        public void setNews_id(int news_id) {
            this.news_id = news_id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public boolean getReadState() {
            return readState;
        }

        public void setReadState(boolean readState) {
            this.readState = readState;
        }
    }
}
