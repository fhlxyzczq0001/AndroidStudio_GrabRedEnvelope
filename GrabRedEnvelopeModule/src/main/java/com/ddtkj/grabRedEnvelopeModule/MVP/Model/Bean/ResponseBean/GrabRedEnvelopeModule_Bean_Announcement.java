package com.ddtkj.grabRedEnvelopeModule.MVP.Model.Bean.ResponseBean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *  公告信息
 *
 *  @Author: 杨重诚
 *  @CreatTime: 2018/7/6  15:56  
 */

public class GrabRedEnvelopeModule_Bean_Announcement implements Parcelable {
    private String id;
    private String title;
    private String time;
    private String info;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.time);
        dest.writeString(this.info);
    }

    public GrabRedEnvelopeModule_Bean_Announcement() {
    }

    protected GrabRedEnvelopeModule_Bean_Announcement(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.time = in.readString();
        this.info = in.readString();
    }

    public static final Parcelable.Creator<GrabRedEnvelopeModule_Bean_Announcement> CREATOR = new Parcelable.Creator<GrabRedEnvelopeModule_Bean_Announcement>() {
        @Override
        public GrabRedEnvelopeModule_Bean_Announcement createFromParcel(Parcel source) {
            return new GrabRedEnvelopeModule_Bean_Announcement(source);
        }

        @Override
        public GrabRedEnvelopeModule_Bean_Announcement[] newArray(int size) {
            return new GrabRedEnvelopeModule_Bean_Announcement[size];
        }
    };
}

