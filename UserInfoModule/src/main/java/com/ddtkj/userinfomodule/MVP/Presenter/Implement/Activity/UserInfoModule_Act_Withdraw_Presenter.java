package com.ddtkj.userinfomodule.MVP.Presenter.Implement.Activity;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.ddtkj.commonmodule.HttpRequest.Common_HttpPath;
import com.ddtkj.commonmodule.HttpRequest.Common_HttpRequestMethod;
import com.ddtkj.commonmodule.HttpRequest.ResultListener.Common_ResultDataListener;
import com.ddtkj.commonmodule.MVP.Model.Bean.ResponseBean.Common_RequestBean;
import com.ddtkj.commonmodule.MVP.Model.Bean.ResponseBean.Common_UserInfoBean;
import com.ddtkj.commonmodule.MVP.Model.Implement.Common_Base_HttpRequest_Implement;
import com.ddtkj.commonmodule.MVP.Model.Interface.Common_Base_HttpRequest_Interface;
import com.ddtkj.commonmodule.MVP.Presenter.Implement.Project.Common_UserAll_Presenter_Implement;
import com.ddtkj.commonmodule.MVP.Presenter.Interface.Project.Common_UserAll_Presenter_Interface;
import com.ddtkj.userinfomodule.MVP.Contract.Activity.UserInfoModule_Act_Withdraw_Contract;

import java.util.HashMap;
import java.util.Map;

/**
 *  提现
 *
 *  @Author: 杨重诚
 *  @CreatTime: 2018/7/14  18:42  
 */
public class UserInfoModule_Act_Withdraw_Presenter extends UserInfoModule_Act_Withdraw_Contract.Presenter{
    Common_Base_HttpRequest_Interface mCommonBaseHttpRequestInterface;
    Common_UserAll_Presenter_Interface mCommonUserAllPresenterInterface;
    public UserInfoModule_Act_Withdraw_Presenter(){
        mCommonBaseHttpRequestInterface = new Common_Base_HttpRequest_Implement();//接口实现类
    }
    @Override
    public void onStart() {

    }

    @Override
    public void refreshUserInfo() {
        if(mCommonUserAllPresenterInterface==null)
            mCommonUserAllPresenterInterface=new Common_UserAll_Presenter_Implement();
        //刷新用户信息
        mCommonUserAllPresenterInterface.refreshUserInfo(context, new Common_UserAll_Presenter_Implement.RefreshUserInfoListener() {
            @Override
            public void requestListener(boolean isSucc, Common_UserInfoBean userInfoBean) {
                if(isSucc){
                   mView.setData(userInfoBean);
                }
            }
        }, true);
    }
    /**
     * 提交
     */
    @Override
    public  void submit(String money,String password) {
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("money", money);
        params.put("dealpassword", password);
        mCommonBaseHttpRequestInterface.requestData(context, Common_HttpPath.URL_API_WITHDRAW, params, new Common_ResultDataListener() {
            @Override
            public void onResult(boolean isSucc, String msg, Common_RequestBean request_bean) {
                if(isSucc){
                    if(request_bean.getData()==null){
                        return;
                    }
                    String businessCode="";
                    String businessMsg="";
                    JSONObject jsonObject = JSONObject.parseObject(request_bean.getData().toString());
                    if(jsonObject.containsKey("businessCode"))
                        businessCode=jsonObject.getString("businessCode");
                    if(jsonObject.containsKey("businessMsg"))
                        businessMsg=jsonObject.getString("businessMsg");
                    if(businessCode.equals("1")&& !TextUtils.isEmpty(businessMsg))
                        msg=businessMsg;
                    mView.withdrawSuccess(msg);
                }
            }
        },true, Common_HttpRequestMethod.GET);
    }
}
