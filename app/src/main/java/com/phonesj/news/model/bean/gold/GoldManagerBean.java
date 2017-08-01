package com.phonesj.news.model.bean.gold;

import android.os.Parcel;
import android.os.Parcelable;
import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Phone on 2017/7/29.
 */

public class GoldManagerBean extends RealmObject implements Parcelable {

    private RealmList<GoldManagerItemBean> managerList;

    public GoldManagerBean() {
    }

    public GoldManagerBean(RealmList<GoldManagerItemBean> managerList) {
        this.managerList = managerList;
    }

    public RealmList<GoldManagerItemBean> getManagerList() {
        return managerList;
    }

    public void setManagerList(RealmList<GoldManagerItemBean> managerList) {
        this.managerList = managerList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeList(managerList);
    }

    protected GoldManagerBean(Parcel in) {
        this.managerList = new RealmList<>();
        in.readList(this.managerList, GoldManagerItemBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<GoldManagerBean> CREATOR = new Parcelable.Creator<GoldManagerBean>() {
        @Override
        public GoldManagerBean createFromParcel(Parcel source) {
            return new GoldManagerBean(source);
        }

        @Override
        public GoldManagerBean[] newArray(int size) {
            return new GoldManagerBean[size];
        }
    };
}
