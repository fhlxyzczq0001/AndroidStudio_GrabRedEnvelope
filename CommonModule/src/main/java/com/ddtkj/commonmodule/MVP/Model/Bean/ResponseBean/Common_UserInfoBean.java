package com.ddtkj.commonmodule.MVP.Model.Bean.ResponseBean;

import com.alibaba.fastjson.annotation.JSONField;

/**
 *  用户信息
 *  @Author: 杨重诚
 *  @CreatTime: 2018/6/5  18:27  
 */

public class Common_UserInfoBean {
    private String access_token;
    private long access_token_expire_timestamp = -1;
    private long access_token_generate_timestamp = -1;
    private String refresh_token;
    @JSONField(name = "uid")
    private String userId;
    private String mobile = "";
    private int thirdPartyIsOpen = -1;//实名认证状态 0未认证  1已认证
    private int payPwd = -1;
    private String realName;
    private String thirdPartyToOpen;
    private String idCard;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public long getAccess_token_expire_timestamp() {
        return access_token_expire_timestamp;
    }

    public void setAccess_token_expire_timestamp(long access_token_expire_timestamp) {
        this.access_token_expire_timestamp = access_token_expire_timestamp;
    }

    public long getAccess_token_generate_timestamp() {
        return access_token_generate_timestamp;
    }

    public void setAccess_token_generate_timestamp(long access_token_generate_timestamp) {
        this.access_token_generate_timestamp = access_token_generate_timestamp;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getThirdPartyIsOpen() {
        return thirdPartyIsOpen;
    }

    public void setThirdPartyIsOpen(int thirdPartyIsOpen) {
        this.thirdPartyIsOpen = thirdPartyIsOpen;
    }

    public int getPayPwd() {
        return payPwd;
    }

    public void setPayPwd(int payPwd) {
        this.payPwd = payPwd;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getThirdPartyToOpen() {
        return thirdPartyToOpen;
    }

    public void setThirdPartyToOpen(String thirdPartyToOpen) {
        this.thirdPartyToOpen = thirdPartyToOpen;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }
}
