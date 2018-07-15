package com.ddtkj.grabRedEnvelopeModule.MVP.Model.Bean.ResponseBean;

import com.alibaba.fastjson.annotation.JSONField;

/**
 *  红包详情列表信息
 *
 *  @Author: 杨重诚
 *  @CreatTime: 2018/7/6  15:56  
 */

public class GrabRedEnvelopeModule_Bean_RedInfoListInfo {
    @JSONField(name = "nickname")
    private String nikeName;
    @JSONField(name = "hbdate")
    private String time;
    @JSONField(name = "money")
    private String redPrice;
    private String avatar;
    public String getNikeName() {
        return nikeName;
    }

    public void setNikeName(String nikeName) {
        this.nikeName = nikeName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRedPrice() {
        return redPrice;
    }

    public void setRedPrice(String redPrice) {
        this.redPrice = redPrice;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
