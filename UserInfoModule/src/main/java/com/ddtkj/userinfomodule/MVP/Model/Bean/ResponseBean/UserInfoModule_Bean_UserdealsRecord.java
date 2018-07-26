package com.ddtkj.userinfomodule.MVP.Model.Bean.ResponseBean;

/**
 *  本人流水记录
 *
 *  @Author: 杨重诚
 *  @CreatTime: 2018/7/8  16:16
 */

public class UserInfoModule_Bean_UserdealsRecord {
    private String level;
    private String getmoney;
    private String consumemoney;
    private String totalmoney;
    private String date;

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getGetmoney() {
        return getmoney;
    }

    public void setGetmoney(String getmoney) {
        this.getmoney = getmoney;
    }

    public String getConsumemoney() {
        return consumemoney;
    }

    public void setConsumemoney(String consumemoney) {
        this.consumemoney = consumemoney;
    }

    public String getTotalmoney() {
        return totalmoney;
    }

    public void setTotalmoney(String totalmoney) {
        this.totalmoney = totalmoney;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
