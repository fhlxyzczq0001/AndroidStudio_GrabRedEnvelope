package com.ddtkj.commonmodule.MVP.Model.Bean.ResponseBean;

/**
 * Created by Administrator on 2016/11/8.
 */

public class Common_VersionBean {
    /*"vCode": "0：暂无更新；1:普通更新；2：强制更新",
            "vContent": "更新的内容",
            "vApkUrl": "apk的下载地址，无需更新时可以不返回"*/
    //@JSONField(name = "content")
    private String refreshContent;
    //@JSONField(name = "url")
    private String refreshUrl;
    //@JSONField(name = "code")
    private String refreshCode;
    private String versionsForce;
    private String versionsNow;//最新版本
    public String getRefreshContent() {
        return refreshContent;
    }

    public void setRefreshContent(String refreshContent) {
        this.refreshContent = refreshContent;
    }

    public String getRefreshUrl() {
        return refreshUrl;
    }

    public void setRefreshUrl(String refreshUrl) {
        this.refreshUrl = refreshUrl;
    }

    public String getRefreshCode() {
        return refreshCode;
    }

    public void setRefreshCode(String refreshCode) {
        this.refreshCode = refreshCode;
    }

    public String getVersionsForce() {
        return versionsForce;
    }

    public void setVersionsForce(String versionsForce) {
        this.versionsForce = versionsForce;
    }

    public String getVersionsNow() {
        return versionsNow;
    }

    public void setVersionsNow(String versionsNow) {
        this.versionsNow = versionsNow;
    }
}
