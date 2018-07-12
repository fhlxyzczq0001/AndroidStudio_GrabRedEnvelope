package com.ddtkj.userinfomodule.MVP.Model.Bean.ResponseBean;

/**
 *  红包记录
 *
 *  @Author: 杨重诚
 *  @CreatTime: 2018/7/8  16:16
 */

public class UserInfoModule_Bean_RedEnvelopeRecord {
    private String typeName;
    private String time;
    private String money;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
