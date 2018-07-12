package com.ddt.redEnvelope.MVP.Model.Bean.RequestBean;

import android.os.Parcel;
import android.os.Parcelable;

import com.alibaba.fastjson.annotation.JSONField;

/**启动页bean
 * @ClassName: com.ygworld.MVP.Model.Bean
 * @author: Administrator 杨重诚
 * @date: 2016/10/14:15:35
 */

public class Main_WelcomePageBean implements Parcelable {
    @JSONField(name="common_image")
    private String url;//android启动图
    @JSONField(name = "linkUrl")
    private String link;//跳转链接
    private int version = 1;
    private String imgServicePath;//图片路径域名

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public void setImgServicePath(String imgServicePath) {
        this.imgServicePath = imgServicePath;
    }

    public String getLink() {
        return link;
    }

    public int getVersion() {
        return version;
    }

    public String getImgServicePath() {
        return imgServicePath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeString(this.link);
        dest.writeInt(this.version);
        dest.writeString(this.imgServicePath);
    }

    public Main_WelcomePageBean() {
    }

    protected Main_WelcomePageBean(Parcel in) {
        this.url = in.readString();
        this.link = in.readString();
        this.version = in.readInt();
        this.imgServicePath = in.readString();
    }

    public static final Creator<Main_WelcomePageBean> CREATOR = new Creator<Main_WelcomePageBean>() {
        @Override
        public Main_WelcomePageBean createFromParcel(Parcel source) {
            return new Main_WelcomePageBean(source);
        }

        @Override
        public Main_WelcomePageBean[] newArray(int size) {
            return new Main_WelcomePageBean[size];
        }
    };
}
