package com.ddtkj.grabRedEnvelopeModule.MVP.Model.Bean.ResponseBean;

/**
 *  分享
 *
 *  @Author: 杨重诚
 *  @CreatTime: 2018/7/6  15:56  
 */

public class GrabRedEnvelopeModule_Bean_Share {
    private String id="";
    private String titile="";
    private String comment="";
    private String linkurl="";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitile() {
        return titile;
    }

    public void setTitile(String titile) {
        this.titile = titile;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getLinkurl() {
        return linkurl;
    }

    public void setLinkurl(String linkurl) {
        this.linkurl = linkurl;
    }
}
