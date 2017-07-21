package com.phonesj.news.model.bean.zhihu;

import java.util.List;

/**
 * Created by Phone on 2017/7/18.
 */

public class DailyBeforeBean {

    /**
     * date : 20170630
     * stories : [{"images":["https://pic4.zhimg.com/v2-1ced0243fbe709a3bf0d103e5c85fbcf.jpg"],"type":0,"id":9501831,"ga_prefix":"063022","title":"小事 · 我要当爸爸了"},{"images":["https://pic2.zhimg.com/v2-f7c0954726d32adf94880fcd39482b49.jpg"],"type":0,"id":9501391,"ga_prefix":"063021","title":"我恨他拍片太慢，一辈子只留了那几部电影"},{"images":["https://pic2.zhimg.com/v2-c0bf8dfa2f50ff19a79398e55517ac6d.jpg"],"type":0,"id":9501313,"ga_prefix":"063020","title":"黄家驹：离开了二十四年，我们依然不愿忘记的音乐少年"},{"title":"你最爱的那几个健身动作，十有八九做错了","ga_prefix":"063019","images":["https://pic2.zhimg.com/v2-118d60c7dba52d90f2abf7967dbd2841.jpg"],"multipic":true,"type":0,"id":9492571},{"images":["https://pic4.zhimg.com/v2-f5fd7c44cdaed73edb248c4b86ad3ebb.jpg"],"type":0,"id":9500474,"ga_prefix":"063018","title":"能写出这篇朋友圈谣言的人，你能从头错到脚也是不容易"},{"images":["https://pic1.zhimg.com/v2-360df86345a712c3bfd5f2b0b862610c.jpg"],"type":0,"id":9501431,"ga_prefix":"063017","title":"想知道自己能不能发大财，先做三道单选题吧"},{"images":["https://pic4.zhimg.com/v2-bccaaf0a94f66586cc705b5e5a38ad2f.jpg"],"type":0,"id":9495949,"ga_prefix":"063016","title":"两碗炸酱面放你面前，请说出哪碗是北京、哪碗是山西的？"},{"images":["https://pic2.zhimg.com/v2-c8d1b0477b361f653d2ee38a063ace95.jpg"],"type":0,"id":9501413,"ga_prefix":"063015","title":"这三位皇帝为了安稳大搞分封制，可苦了后面的子孙"},{"images":["https://pic4.zhimg.com/v2-7c241fadab62a041dff079bf8162536f.jpg"],"type":0,"id":9499388,"ga_prefix":"063014","title":"能在中国吃到这么多好吃的蔬菜，我很庆幸"},{"images":["https://pic4.zhimg.com/v2-89fb58c325823a3e8bbacfe6b4bd8f8b.jpg"],"type":0,"id":9501053,"ga_prefix":"063013","title":"10 年前，苹果直营店外被挤得水泄不通：iPhone 上市了"},{"images":["https://pic3.zhimg.com/v2-cbc9204fe7a20db1835fc760d5deeef2.jpg"],"type":0,"id":9487987,"ga_prefix":"063012","title":"大误 · 我是神奇宝贝道馆馆主"},{"title":"高温天，需要这几道比空调还消暑的小凉菜","ga_prefix":"063011","images":["https://pic4.zhimg.com/v2-a3c8c13c912928f61bfdfc78525d196f.jpg"],"multipic":true,"type":0,"id":9495989},{"images":["https://pic3.zhimg.com/v2-4054d45ba504b144691e7ab18d9f0c92.jpg"],"type":0,"id":9500612,"ga_prefix":"063010","title":"人们期待的创新，小厂商和大企业谁更有可能实现？"},{"images":["https://pic4.zhimg.com/v2-beed10bea1a80cffe154863652f128cf.jpg"],"type":0,"id":9499760,"ga_prefix":"063009","title":"违反垄断法被欧盟罚了 27 亿美元，Google 这次冤不冤？"},{"images":["https://pic4.zhimg.com/v2-80aa074d71e88d2db4061fddc01b3b17.jpg"],"type":0,"id":9500591,"ga_prefix":"063008","title":"想看超市生意最近好不好，连接卫星查一下就知道"},{"images":["https://pic4.zhimg.com/v2-c6eb45cf53800a2957d525508550ffcf.jpg"],"type":0,"id":9499987,"ga_prefix":"063007","title":"那个你们听说过的「斯坦福监狱实验」，是我做的"},{"images":["https://pic3.zhimg.com/v2-cb17e4612ec641510d2ff9c4b8bcdf16.jpg"],"type":0,"id":9500527,"ga_prefix":"063007","title":"手一滑 30 万天价镯子被摔碎，游客到底该赔多少钱？"},{"images":["https://pic1.zhimg.com/v2-34dff1a946da45de2780c55f68204e68.jpg"],"type":0,"id":9500116,"ga_prefix":"063007","title":"你爱的网红都在发广告，没办法，原来的营销渠道不太灵了"},{"images":["https://pic2.zhimg.com/v2-2b3a82fb6b093aaf1b26af35e0e5d18d.jpg"],"type":0,"id":9499528,"ga_prefix":"063006","title":"瞎扯 · 如何正确地吐槽"}]
     */

    private String date;
    private List<StoriesBean> stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<StoriesBean> getStories() {
        return stories;
    }

    public void setStories(List<StoriesBean> stories) {
        this.stories = stories;
    }
}
