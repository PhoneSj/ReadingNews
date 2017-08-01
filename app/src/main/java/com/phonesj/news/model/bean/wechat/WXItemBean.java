package com.phonesj.news.model.bean.wechat;

/**
 * Created by Phone on 2017/8/1.
 */

public class WXItemBean {

    /**
     * ctime : 2017-08-01
     * title : 1955年授衔时七位将军临时降衔内幕
     * description : 非常历史
     * picUrl : https://zxpic.gtimg.com/infonew/0/wechat_pics_-32189406.jpg/640
     * url : https://mp.weixin.qq.com/s?src=16&ver=277&timestamp=1501549229&signature=qc0mgeDkqVo*wD0ir7uU5xzToPtkQUHqCb4PsUG77NJCRL3GjWGyQhcDy1VMyxYBEKnR9AWToStisX*lA8RjnhtJPOAqE0tQiabqGp1yu6U=
     */

    private String ctime;
    private String title;
    private String description;
    private String picUrl;
    private String url;

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
