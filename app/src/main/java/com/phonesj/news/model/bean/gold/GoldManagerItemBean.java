package com.phonesj.news.model.bean.gold;

import android.os.Parcel;
import android.os.Parcelable;
import io.realm.RealmObject;

/**
 * Created by Phone on 2017/7/29.
 */

public class GoldManagerItemBean extends RealmObject implements Parcelable {

    private int index;
    private boolean isSelected;

    public GoldManagerItemBean() {
    }

    public GoldManagerItemBean(int index, boolean isSelected) {
        this.index = index;
        this.isSelected = isSelected;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(index);
        parcel.writeByte(isSelected ? (byte) 1 : (byte) 0);
    }

    protected GoldManagerItemBean(Parcel in) {
        this.index = in.readInt();
        this.isSelected = in.readByte() != 0;
    }

    public static final Parcelable.Creator<GoldManagerItemBean> CREATOR= new Creator<GoldManagerItemBean>() {
        @Override
        public GoldManagerItemBean createFromParcel(Parcel parcel) {
            return new GoldManagerItemBean(parcel);
        }

        @Override
        public GoldManagerItemBean[] newArray(int size) {
            return new GoldManagerItemBean[size];
        }
    };
}
