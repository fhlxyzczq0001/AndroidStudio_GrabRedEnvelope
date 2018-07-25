package com.ddtkj.grabRedEnvelopeModule.MVP.Model.Bean.ResponseBean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *  规则
 *
 *  @Author: 杨重诚
 *  @CreatTime: 2018/7/6  15:56  
 */

public class GrabRedEnvelopeModule_Bean_Rule implements Parcelable {
    private String id;
    private String user_id;
    private String rules;
    private String createdate;
    private String title;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.user_id);
        dest.writeString(this.rules);
        dest.writeString(this.createdate);
        dest.writeString(this.title);
    }

    public GrabRedEnvelopeModule_Bean_Rule() {
    }

    protected GrabRedEnvelopeModule_Bean_Rule(Parcel in) {
        this.id = in.readString();
        this.user_id = in.readString();
        this.rules = in.readString();
        this.createdate = in.readString();
        this.title = in.readString();
    }

    public static final Parcelable.Creator<GrabRedEnvelopeModule_Bean_Rule> CREATOR = new Parcelable.Creator<GrabRedEnvelopeModule_Bean_Rule>() {
        @Override
        public GrabRedEnvelopeModule_Bean_Rule createFromParcel(Parcel source) {
            return new GrabRedEnvelopeModule_Bean_Rule(source);
        }

        @Override
        public GrabRedEnvelopeModule_Bean_Rule[] newArray(int size) {
            return new GrabRedEnvelopeModule_Bean_Rule[size];
        }
    };
}

