package com.ddtkj.userinfomodule.MVP.Model.Bean.ResponseBean;

/**
 *  提现记录
 *
 *  @Author: 杨重诚
 *  @CreatTime: 2018/7/8  16:16
 */

public class UserInfoModule_Bean_WithdrawalsRecord {
    private String title;
    private String bringuptime;
    private String bringupamount;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBringuptime() {
        return bringuptime;
    }

    public void setBringuptime(String bringuptime) {
        this.bringuptime = bringuptime;
    }

    public String getBringupamount() {
        return bringupamount;
    }

    public void setBringupamount(String bringupamount) {
        this.bringupamount = bringupamount;
    }
}
