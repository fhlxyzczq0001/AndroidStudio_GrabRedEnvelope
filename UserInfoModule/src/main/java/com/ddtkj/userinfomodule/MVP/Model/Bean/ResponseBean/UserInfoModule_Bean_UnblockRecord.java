package com.ddtkj.userinfomodule.MVP.Model.Bean.ResponseBean;

/**
 *  解封账号
 *
 *  @Author: 杨重诚
 *  @CreatTime: 2018/7/8  16:16
 */

public class UserInfoModule_Bean_UnblockRecord {
    private String id;
    private String username;
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
