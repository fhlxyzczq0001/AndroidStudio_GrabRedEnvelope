package com.ddtkj.userinfomodule.MVP.Model.Bean.ResponseBean;

import com.alibaba.fastjson.annotation.JSONField;

/**
 *  红包记录
 *
 *  @Author: 杨重诚
 *  @CreatTime: 2018/7/8  16:16
 */

public class UserInfoModule_Bean_RedEnvelopeRecord {
    @JSONField(name = "title")
    private String typeName;
    @JSONField(name = "getdate")
    private String time;
    @JSONField(name = "moneyaccount")
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
