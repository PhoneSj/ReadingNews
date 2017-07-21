package com.phonesj.news.model.bean.zhihu;

import java.util.List;

/**
 * Created by Phone on 2017/7/18.
 */

public class DailyBean {

    /**
     * date : 20170718
     * stories : [{"images":["https://pic3.zhimg.com/v2-fe176979642f78fe29f84c7b4cf5d04e.jpg"],"type":0,"id":9529643,"ga_prefix":"071814","title":"「算了，我跟你脑回路不同，讲不清楚」"},{"images":["https://pic1.zhimg.com/v2-5e55fc4b0a263240e807379567f0d6e0.jpg"],"type":0,"id":9529553,"ga_prefix":"071813","title":"天气这么热，你领防暑降温费了吗？"},{"images":["https://pic1.zhimg.com/v2-42db85f025de3b17f4c5655a3329efb4.jpg"],"type":0,"id":9529567,"ga_prefix":"071812","title":"大误 · 我们穷人都不吃食堂荷包蛋的"},{"images":["https://pic3.zhimg.com/v2-ce100f31802147acb717fc72aa11cd2e.jpg"],"type":0,"id":9529490,"ga_prefix":"071811","title":"电视剧里光鲜亮丽的咨询行业，现实中什么样？"},{"title":"冰箱里藏一罐青酱，早餐或夜宵都有了着落","ga_prefix":"071810","images":["https://pic4.zhimg.com/v2-41dc6e07c86ec265d90dfaa84dba8167.jpg"],"multipic":true,"type":0,"id":9528956},{"images":["https://pic3.zhimg.com/v2-18d9b24abb99bd517605462618da3012.jpg"],"type":0,"id":9528347,"ga_prefix":"071809","title":"有人在网上泄露了你的隐私，别怕，手把手教你把他揪出来"},{"images":["https://pic3.zhimg.com/v2-653b8cdd0870ee152cd816598de6d752.jpg"],"type":0,"id":9528894,"ga_prefix":"071808","title":"每个月总有那么一天，金融从业者都在担惊受怕睡不着"},{"images":["https://pic4.zhimg.com/v2-c0e34f40f7fb8f53d202726839b6d06b.jpg"],"type":0,"id":9528047,"ga_prefix":"071807","title":"如果章莹颖案嫌疑人被判无罪，会有什么结果？"},{"images":["https://pic1.zhimg.com/v2-19989e6ffb519ee206fd25d9445bfae0.jpg"],"type":0,"id":9527885,"ga_prefix":"071807","title":"不吹不黑，我们来客观地讲讲《大护法》是好是坏"},{"title":"由篮球引起的海峡两岸说唱大战，比《中国有嘻哈》还精彩","ga_prefix":"071807","images":["https://pic4.zhimg.com/v2-159c761d789b48a2dab22792acf8aec3.jpg"],"multipic":true,"type":0,"id":9528961},{"images":["https://pic2.zhimg.com/v2-9c1ac9c7aec4caf796d03fb0f9f51cb5.jpg"],"type":0,"id":9528004,"ga_prefix":"071806","title":"瞎扯 · 如何正确地吐槽"}]
     * top_stories : [{"image":"https://pic4.zhimg.com/v2-98da281e1920039a645a32a6d668905b.jpg","type":0,"id":9529553,"ga_prefix":"071813","title":"天气这么热，你领防暑降温费了吗？"},{"image":"https://pic3.zhimg.com/v2-49b405d6105451f94d754d563503ccc6.jpg","type":0,"id":9529490,"ga_prefix":"071811","title":"电视剧里光鲜亮丽的咨询行业，现实中什么样？"},{"image":"https://pic2.zhimg.com/v2-99781a78239543a7e44db38b7a022ac5.jpg","type":0,"id":9528047,"ga_prefix":"071807","title":"如果章莹颖案嫌疑人被判无罪，会有什么结果？"},{"image":"https://pic3.zhimg.com/v2-5f3030dcf1fb3fe2b618a00409ba594a.jpg","type":0,"id":9528961,"ga_prefix":"071807","title":"由篮球引起的海峡两岸说唱大战，比《中国有嘻哈》还精彩"},{"image":"https://pic4.zhimg.com/v2-989d3c913bf166613c8521dffee2cb63.jpg","type":0,"id":9527885,"ga_prefix":"071807","title":"不吹不黑，我们来客观地讲讲《大护法》是好是坏"}]
     */

    private String date;
    private List<StoriesBean> stories;
    private List<TopStoriesBean> top_stories;

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

    public List<TopStoriesBean> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(List<TopStoriesBean> top_stories) {
        this.top_stories = top_stories;
    }


    public static class TopStoriesBean {
        /**
         * image : https://pic4.zhimg.com/v2-98da281e1920039a645a32a6d668905b.jpg
         * type : 0
         * id : 9529553
         * ga_prefix : 071813
         * title : 天气这么热，你领防暑降温费了吗？
         */

        private String image;
        private int type;
        private int id;
        private String ga_prefix;
        private String title;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGa_prefix() {
            return ga_prefix;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
