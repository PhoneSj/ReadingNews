package com.phonesj.news.model.bean.zhihu;

import java.util.List;

/**
 * Created by Phone on 2017/7/20.
 * <p>短评论、长评论都是使用这个bean，只不过"短评论"没有ReplyToBean属性</p>
 */

public class CommonBean {

    private List<CommentsBean> comments;

    public List<CommentsBean> getComments() {
        return comments;
    }

    public void setComments(List<CommentsBean> comments) {
        this.comments = comments;
    }

    public static class CommentsBean {
        /**
         * author : 张厘米
         * content : 大学放暑假时候去过工厂干活，一个月，他们的生活就是上班然后下班去食堂排队打饭吃饭，晚上去逛超市或者宿舍睡觉。为了钱，甚至盼着加班，每一小时工资是几块钱，好像没超过五块，我当时就很震惊。毕业后，我都不敢再去工厂这类地方应聘，觉得是个可怜的地方，心生怜悯可我也无能为力，他们真的不是可恨，仅仅是可怜而已！
         * avatar : http://pic3.zhimg.com/ab27bea9c2f6de8112ce5553cb9a63ee_im.jpg
         * time : 1433605034
         * id : 1154249
         * likes : 0
         * reply_to : {"content":"贫穷怎么就不可怕了？如果你觉得开心最重要那你要在贫穷中开心呢还是富足中开心？物质上的贫穷极大可能也会带来心理身理上的贫穷，所以贫穷绝对不是好事。摆脱贫穷，是每个人都应该努力的方向，当然这个社会始终有底层和贫穷的人存在。我无法想象未来的样子，社会科技高度发达的样子，也许那时候没有底层、没有贫穷了吧。","status":0,"id":1136736,"author":"尽可能自我"}
         */

        private String author;
        private String content;
        private String avatar;
        private int time;
        private int id;
        private int likes;
        private ReplyToBean reply_to;

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getLikes() {
            return likes;
        }

        public void setLikes(int likes) {
            this.likes = likes;
        }

        public ReplyToBean getReply_to() {
            return reply_to;
        }

        public void setReply_to(ReplyToBean reply_to) {
            this.reply_to = reply_to;
        }

        public static class ReplyToBean {
            /**
             * content : 贫穷怎么就不可怕了？如果你觉得开心最重要那你要在贫穷中开心呢还是富足中开心？物质上的贫穷极大可能也会带来心理身理上的贫穷，所以贫穷绝对不是好事。摆脱贫穷，是每个人都应该努力的方向，当然这个社会始终有底层和贫穷的人存在。我无法想象未来的样子，社会科技高度发达的样子，也许那时候没有底层、没有贫穷了吧。
             * status : 0
             * id : 1136736
             * author : 尽可能自我
             */

            private String content;
            private int status;
            private int id;
            private String author;
            private int expandState;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getAuthor() {
                return author;
            }

            public void setAuthor(String author) {
                this.author = author;
            }

            public int getExpandState() {
                return expandState;
            }

            public void setExpandState(int expandState) {
                this.expandState = expandState;
            }
        }
    }
}
