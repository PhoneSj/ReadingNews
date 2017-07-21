package com.phonesj.news.model.bean.zhihu;

import java.util.List;

/**
 * Created by Phone on 2017/7/21.
 */

public class StoriesBean {
    /**
     * images : ["https://pic3.zhimg.com/v2-fe176979642f78fe29f84c7b4cf5d04e.jpg"]
     * type : 0
     * id : 9529643
     * ga_prefix : 071814
     * title : 「算了，我跟你脑回路不同，讲不清楚」
     * multipic : true
     */

    private int type;
    private int id;
    private String ga_prefix;
    private String title;
    private boolean multipic;
    private List<String> images;
    private String date;
    private String display_date;
    private boolean readState;

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

    public boolean isMultipic() {
        return multipic;
    }

    public void setMultipic(boolean multipic) {
        this.multipic = multipic;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDisplay_date() {
        return display_date;
    }

    public void setDisplay_date(String display_date) {
        this.display_date = display_date;
    }

    public boolean getReadState() {
        return readState;
    }

    public void setReadState(boolean readState) {
        this.readState = readState;
    }
}
