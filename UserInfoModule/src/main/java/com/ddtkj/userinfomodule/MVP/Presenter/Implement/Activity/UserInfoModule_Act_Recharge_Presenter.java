package com.ddtkj.userinfomodule.MVP.Presenter.Implement.Activity;

import com.ddtkj.commonmodule.HttpRequest.Common_HttpPath;
import com.ddtkj.commonmodule.HttpRequest.Common_HttpRequestMethod;
import com.ddtkj.commonmodule.HttpRequest.ResultListener.Common_ResultDataListener;
import com.ddtkj.commonmodule.MVP.Model.Bean.ResponseBean.Common_RequestBean;
import com.ddtkj.commonmodule.MVP.Model.Implement.Common_Base_HttpRequest_Implement;
import com.ddtkj.commonmodule.MVP.Model.Interface.Common_Base_HttpRequest_Interface;
import com.ddtkj.userinfomodule.MVP.Contract.Activity.UserInfoModule_Act_Recharge_Contract;

import java.util.HashMap;
import java.util.Map;

/**
 *  充值
 *
 *  @Author: 杨重诚
 *  @CreatTime: 2018/7/14  18:42  
 */
public class UserInfoModule_Act_Recharge_Presenter extends UserInfoModule_Act_Recharge_Contract.Presenter{
    Common_Base_HttpRequest_Interface mCommonBaseHttpRequestInterface;
    public UserInfoModule_Act_Recharge_Presenter(){
        mCommonBaseHttpRequestInterface = new Common_Base_HttpRequest_Implement();//接口实现类
    }
    @Override
    public void onStart() {

    }

    /**
     * 充值
     */
    @Override
    public  void recharge(String money) {
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("money", money);
        mCommonBaseHttpRequestInterface.requestData(context, Common_HttpPath.URL_API_RECHARGE, params, new Common_ResultDataListener() {
            @Override
            public void onResult(boolean isSucc, String msg, Common_RequestBean request_bean) {
                if(isSucc){
                    if(request_bean.getData()==null){
                        return;
                    }
                    mView.rechargeSuccess(request_bean.getData().toString());
                }
            }
        },true, Common_HttpRequestMethod.GET);
    }
}
