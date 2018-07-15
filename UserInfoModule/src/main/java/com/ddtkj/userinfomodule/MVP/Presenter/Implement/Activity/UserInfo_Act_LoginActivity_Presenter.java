package com.ddtkj.userinfomodule.MVP.Presenter.Implement.Activity;

import com.alibaba.fastjson.JSONObject;
import com.ddtkj.commonmodule.HttpRequest.Common_HttpPath;
import com.ddtkj.commonmodule.HttpRequest.Common_HttpRequestMethod;
import com.ddtkj.commonmodule.HttpRequest.ResultListener.Common_ResultDataListener;
import com.ddtkj.commonmodule.Lintener.Common_UmengAuthListener;
import com.ddtkj.commonmodule.MVP.Model.Bean.ResponseBean.Common_RequestBean;
import com.ddtkj.commonmodule.MVP.Model.Bean.ResponseBean.Common_UserInfoBean;
import com.ddtkj.commonmodule.MVP.Model.Implement.Common_Base_HttpRequest_Implement;
import com.ddtkj.commonmodule.MVP.Model.Interface.Common_Base_HttpRequest_Interface;
import com.ddtkj.userinfomodule.Base.Application.UserInfo_Application_Interface;
import com.ddtkj.userinfomodule.Base.UserInfo_Application_Utils;
import com.ddtkj.userinfomodule.MVP.Contract.Activity.UserInfo_Act_LoginActivity_Contract;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.HashMap;
import java.util.Map;

import mvpdemo.com.unmeng_share_librarys.UmengUserInfo;
/**
 *  UserInfo_Act_LoginActivity_Presenter
 *
 *  @Author: 杨重诚
 *  @CreatTime: 2018/7/6  14:10  
 */

public class UserInfo_Act_LoginActivity_Presenter extends UserInfo_Act_LoginActivity_Contract.Presenter{
    Common_Base_HttpRequest_Interface common_base_httpRequest_interface;
    UserInfo_Application_Interface userInfoApplicationInterface;
    public UserInfo_Act_LoginActivity_Presenter() {
        common_base_httpRequest_interface = new Common_Base_HttpRequest_Implement();
        userInfoApplicationInterface = UserInfo_Application_Utils.getApplication();
    }
    @Override
    public void onStart() {
    }

    @Override
    public void bindUmengAuth(SHARE_MEDIA platform) {
        new UmengUserInfo().getPlatformInfo(context, platform, new Common_UmengAuthListener(context).umAuthListener);
    }

    @Override
    public void canLogin(String openid, String screen_name,String iconurl) {
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("openid", openid);
        params.put("screen_name", screen_name);
        params.put("iconurl", iconurl);
        common_base_httpRequest_interface.requestData(context, Common_HttpPath.URL_API_LOGING, params, new Common_ResultDataListener() {
            @Override
            public void onResult(boolean isSucc, String msg, Common_RequestBean request_bean) {
                if(isSucc){
                    if(request_bean.getData()==null){
                        return;
                    }
                    JSONObject jsonObject = JSONObject.parseObject(request_bean.getData().toString());
                    Common_UserInfoBean commonUserInfoBean=JSONObject.parseObject(jsonObject.getString("userinfo"),Common_UserInfoBean.class);
                    userInfoApplicationInterface.setUseInfoVo(commonUserInfoBean);
                    mView.loginResult(true);
                }
            }
        },true, Common_HttpRequestMethod.GET);
    }
}
