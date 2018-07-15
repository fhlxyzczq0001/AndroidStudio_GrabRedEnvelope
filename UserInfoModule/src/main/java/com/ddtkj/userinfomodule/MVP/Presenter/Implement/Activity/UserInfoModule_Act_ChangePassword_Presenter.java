package com.ddtkj.userinfomodule.MVP.Presenter.Implement.Activity;

import com.alibaba.fastjson.JSONObject;
import com.ddtkj.commonmodule.HttpRequest.Common_HttpPath;
import com.ddtkj.commonmodule.HttpRequest.Common_HttpRequestMethod;
import com.ddtkj.commonmodule.HttpRequest.ResultListener.Common_ResultDataListener;
import com.ddtkj.commonmodule.MVP.Model.Bean.ResponseBean.Common_RequestBean;
import com.ddtkj.commonmodule.MVP.Model.Implement.Common_Base_HttpRequest_Implement;
import com.ddtkj.commonmodule.MVP.Model.Interface.Common_Base_HttpRequest_Interface;
import com.ddtkj.commonmodule.MVP.Presenter.Interface.Project.Common_UserAll_Presenter_Interface;
import com.ddtkj.userinfomodule.MVP.Contract.Activity.UserInfoModule_Act_ChangePassword_Contract;
import com.utlis.lib.ToastUtils;

import java.util.HashMap;
import java.util.Map;

/**
 *  修改密码
 *
 *  @Author: 杨重诚
 *  @CreatTime: 2018/7/14  18:42  
 */
public class UserInfoModule_Act_ChangePassword_Presenter extends UserInfoModule_Act_ChangePassword_Contract.Presenter{
    Common_Base_HttpRequest_Interface mCommonBaseHttpRequestInterface;
    Common_UserAll_Presenter_Interface mCommonUserAllPresenterInterface;
    public UserInfoModule_Act_ChangePassword_Presenter(){
        mCommonBaseHttpRequestInterface = new Common_Base_HttpRequest_Implement();//接口实现类
    }
    @Override
    public void onStart() {

    }

    /**
     *修改密码
     */
    @Override
    public  void submit(String password1,String password2) {
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("olddealpassword", password1);
        params.put("newdealpassword", password2);
        mCommonBaseHttpRequestInterface.requestData(context, Common_HttpPath.URL_API_CHANGE_PASSWORD, params, new Common_ResultDataListener() {
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
                        mView.changePasswordSuccess(msg);
                    }else {
                        ToastUtils.WarnImageToast(context,msg);
                    }
                }
            }
        },true, Common_HttpRequestMethod.GET);
    }
}
