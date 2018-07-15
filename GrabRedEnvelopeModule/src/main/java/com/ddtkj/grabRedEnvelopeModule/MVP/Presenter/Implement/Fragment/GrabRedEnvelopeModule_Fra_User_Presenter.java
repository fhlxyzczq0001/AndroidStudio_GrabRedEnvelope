package com.ddtkj.grabRedEnvelopeModule.MVP.Presenter.Implement.Fragment;


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
import com.ddtkj.grabRedEnvelopeModule.MVP.Contract.Fragment.GrabRedEnvelopeModule_Fra_User_Contract;
import com.utlis.lib.ToastUtils;

import java.util.HashMap;
import java.util.Map;

/**
 *  我的
 *
 *  @Author: 杨重诚
 *  @CreatTime: 2018/7/6  12:34  
 */

public class GrabRedEnvelopeModule_Fra_User_Presenter extends GrabRedEnvelopeModule_Fra_User_Contract.Presenter {
    Common_Base_HttpRequest_Interface common_base_httpRequest_interface;
    Common_UserAll_Presenter_Interface mCommonUserAllPresenterInterface;
    public GrabRedEnvelopeModule_Fra_User_Presenter(){
        common_base_httpRequest_interface=new Common_Base_HttpRequest_Implement();
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
                    mView.setUserInfoData(userInfoBean);
                }
            }
        }, true);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 意见反馈
     * @param contents
     */
    @Override
    public void requestUserideas(final String contents) {
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("content",contents);
        common_base_httpRequest_interface.requestData(context, Common_HttpPath.URL_API_USERS_USERIDEAS, params, new Common_ResultDataListener() {
            @Override
            public void onResult(boolean isSucc, String msg, Common_RequestBean request_bean) {
                if(isSucc){
                    if(request_bean.getData()==null){
                        return;
                    }
                    JSONObject jsonObject = JSONObject.parseObject(request_bean.getData().toString());
                    if(jsonObject.containsKey("businessMsg")){
                        msg=jsonObject.getString("businessMsg");
                    }
                    ToastUtils.RightImageToast(context,msg);
                }
            }
        },true, Common_HttpRequestMethod.GET);
    }
}
