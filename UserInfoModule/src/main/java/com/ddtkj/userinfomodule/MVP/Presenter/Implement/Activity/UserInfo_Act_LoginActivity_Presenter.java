package com.ddtkj.userinfomodule.MVP.Presenter.Implement.Activity;

import com.alibaba.fastjson.JSONObject;
import com.ddtkj.commonmodule.Lintener.Common_UmengAuthListener;
import com.ddtkj.commonmodule.MVP.Model.Bean.ResponseBean.Common_UserInfoBean;
import com.ddtkj.commonmodule.MVP.Model.Implement.Common_Base_HttpRequest_Implement;
import com.ddtkj.commonmodule.MVP.Model.Interface.Common_Base_HttpRequest_Interface;
import com.ddtkj.commonmodule.MVP.Presenter.Implement.Project.Common_UserAll_Presenter_Implement;
import com.ddtkj.commonmodule.MVP.Presenter.Interface.Project.Common_ProjectUtil_Interface;
import com.ddtkj.commonmodule.MVP.Presenter.Interface.Project.Common_UserAll_Presenter_Interface;
import com.ddtkj.commonmodule.Public.Common_PublicMsg;
import com.ddtkj.userinfomodule.Base.Application.UserInfo_Application_Interface;
import com.ddtkj.userinfomodule.Base.UserInfo_Application_Utils;
import com.ddtkj.userinfomodule.MVP.Contract.Activity.UserInfo_Act_LoginActivity_Contract;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.utlis.lib.AES;
import com.utlis.lib.L;
import com.utlis.lib.ToastUtils;

import java.util.ArrayList;
import java.util.List;

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
    Common_UserAll_Presenter_Interface mCommon_UserAll_Presenter_Interface;
    List<String> usernameListCache = new ArrayList<>();//缓存登录成功的用户名
    Common_ProjectUtil_Interface commonProjectUtilInterface;
    public UserInfo_Act_LoginActivity_Presenter() {
        common_base_httpRequest_interface = new Common_Base_HttpRequest_Implement();
        userInfoApplicationInterface = UserInfo_Application_Utils.getApplication();
        mCommon_UserAll_Presenter_Interface = new Common_UserAll_Presenter_Implement();
    }
    @Override
    public void onStart() {
    }

    @Override
    public void bindUmengAuth(SHARE_MEDIA platform) {
        new UmengUserInfo().getPlatformInfo(context, platform, new Common_UmengAuthListener(context).umAuthListener);
    }

    @Override
    public void canLogin(String openId, String openType) {
        String openInfo = "";
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("openType", openType);
        jsonObj.put("openId", openId);
        try {
            openInfo = AES.encryptToBase64(jsonObj.toString(),
                    Common_PublicMsg.AES_ENCRYPT_KEY);
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.WarnImageToast(context, "三方授权密文生成失败");
            return;
        }
        L.e("openType:" + openType + ",openId:" + openId + ",openInfo:" + openInfo);
        mCommon_UserAll_Presenter_Interface.refreshOtherLogin(context, openInfo, openType, openId, new Common_UserAll_Presenter_Implement.RefreshUserInfoListener() {

            @Override
            public void requestListener(boolean isSucc, Common_UserInfoBean userInfoBean) {
                if (isSucc) {
                    userInfoApplicationInterface.setUseInfoVo(userInfoBean);
                    mView.loginResult(isSucc);
                }
            }
        }, true);
    }
}
