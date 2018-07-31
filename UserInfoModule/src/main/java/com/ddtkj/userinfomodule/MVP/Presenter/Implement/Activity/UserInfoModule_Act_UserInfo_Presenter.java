package com.ddtkj.userinfomodule.MVP.Presenter.Implement.Activity;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.ddtkj.commonmodule.Base.Common_Application;
import com.ddtkj.commonmodule.HttpRequest.Common_HttpPath;
import com.ddtkj.commonmodule.HttpRequest.Common_HttpRequestMethod;
import com.ddtkj.commonmodule.HttpRequest.ResultListener.Common_ResultDataListener;
import com.ddtkj.commonmodule.MVP.Model.Bean.ResponseBean.Common_RequestBean;
import com.ddtkj.commonmodule.MVP.Model.Implement.Common_Base_HttpRequest_Implement;
import com.ddtkj.commonmodule.MVP.Model.Interface.Common_Base_HttpRequest_Interface;
import com.ddtkj.userinfomodule.MVP.Contract.Activity.UserInfoModule_Act_UserInfo_Contract;
import com.utlis.lib.ToastUtils;

import java.util.HashMap;
import java.util.Map;

/**
 *  用户信息
 *
 *  @Author: 杨重诚
 *  @CreatTime: 2018/7/14  10:11
 */
public class UserInfoModule_Act_UserInfo_Presenter extends UserInfoModule_Act_UserInfo_Contract.Presenter{
    Common_Base_HttpRequest_Interface mCommonBaseHttpRequestInterface;

    public UserInfoModule_Act_UserInfo_Presenter(){
        mCommonBaseHttpRequestInterface = new Common_Base_HttpRequest_Implement();//接口实现类
    }
    @Override
    public void onStart() {

    }
    /**
     * 提交
     * @param userName
     * @param userNum
     * @param password
     */
    @Override
    public  void submit(String userName,String phone, String userNum,String bankcode,String bankName,String password,String upcode) {
        if(TextUtils.isEmpty(userName)){
            ToastUtils.WarnImageToast(context,"姓名不可为空");
            return;
        }else if(TextUtils.isEmpty(phone)){
            ToastUtils.WarnImageToast(context,"手机号不可为空");
            return;
        }else if(TextUtils.isEmpty(userNum)){
            ToastUtils.WarnImageToast(context,"身份证号不可为空");
            return;
        }else if(TextUtils.isEmpty(bankcode)){
            ToastUtils.WarnImageToast(context,"银行卡号不可为空");
            return;
        }else if(TextUtils.isEmpty(bankName)){
            ToastUtils.WarnImageToast(context,"开户行不可为空");
            return;
        }else if(TextUtils.isEmpty(password)){
            ToastUtils.WarnImageToast(context,"密码不可为空");
            return;
        }

        Map<String,Object> params = new HashMap<String, Object>();
        params.put("user_id", Common_Application.getInstance().getUseInfoVo().getUserId());//当前页
        params.put("name",userName);
        params.put("mobile",phone);
        params.put("admin_id",userNum);
        params.put("dealpassword",password);
        params.put("bankcode",bankcode);
        params.put("bankname",bankName);
        if(!TextUtils.isEmpty(upcode)){
            upcode="";
        }
        params.put("upcode",upcode);
        mCommonBaseHttpRequestInterface.requestData(context, Common_HttpPath.URL_API_SETPROFILE, params, new Common_ResultDataListener() {
            @Override
            public void onResult(boolean isSucc, String msg, Common_RequestBean request_bean) {
                if(isSucc){
                    if(request_bean.getData()==null){
                        return;
                    }
                    JSONObject jsonObject = JSONObject.parseObject(request_bean.getData().toString());
                    String businessCode="";
                    if(jsonObject.containsKey("businessCode")){
                        businessCode=jsonObject.getString("businessCode");
                    }
                    if(jsonObject.containsKey("businessMsg")){
                        msg=jsonObject.getString("businessMsg");
                    }
                    if(businessCode.equals("1")){
                        mView.submitSuccess(msg);
                    }else {
                        ToastUtils.WarnImageToast(context,msg);
                    }
                }
            }
        },true, Common_HttpRequestMethod.GET);
    }
}
