package com.ddtkj.commonmodule.MVP.Model.Bean.ResponseBean;

import com.alibaba.fastjson.annotation.JSONField;

/**
 *  用户信息
 *  @Author: 杨重诚
 *  @CreatTime: 2018/6/5  18:27  
 */

public class Common_UserInfoBean {
    @JSONField(name = "id")
    private String userId= "";
    private String username= "";
    @JSONField(name = "nickname")
    private String nikeName = "";
    private String avatar = "";
    private String isFull;//信息是否完整 1完整 0 不完整
    private String group_id ;//1管理员  0非管理员;
    private String weixinName= "";
    private String accountbalance= "";

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNikeName() {
        return nikeName;
    }

    public void setNikeName(String nikeName) {
        this.nikeName = nikeName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getIsFull() {
        return isFull;
    }

    public void setIsFull(String isFull) {
        this.isFull = isFull;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getWeixinName() {
        return weixinName;
    }

    public void setWeixinName(String weixinName) {
        this.weixinName = weixinName;
    }

    public String getAccountbalance() {
        return accountbalance;
    }

    public void setAccountbalance(String accountbalance) {
        this.accountbalance = accountbalance;
    }
}
