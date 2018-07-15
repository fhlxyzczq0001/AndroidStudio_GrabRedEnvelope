package com.ddtkj.commonmodule.MVP.Presenter.Implement.Project;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;

import com.alibaba.fastjson.JSONObject;
import com.ddtkj.commonmodule.Base.Common_Application;
import com.ddtkj.commonmodule.HttpRequest.Common_HttpPath;
import com.ddtkj.commonmodule.HttpRequest.Common_HttpRequestMethod;
import com.ddtkj.commonmodule.HttpRequest.ResultListener.Common_ResultDataListener;
import com.ddtkj.commonmodule.MVP.Model.Bean.EventBusBean.Common_OtherLoginSuccess_Eventbus;
import com.ddtkj.commonmodule.MVP.Model.Bean.ResponseBean.Common_RequestBean;
import com.ddtkj.commonmodule.MVP.Model.Bean.ResponseBean.Common_UserInfoBean;
import com.ddtkj.commonmodule.MVP.Model.Implement.Common_Base_HttpRequest_Implement;
import com.ddtkj.commonmodule.MVP.Model.Interface.Common_Base_HttpRequest_Interface;
import com.ddtkj.commonmodule.MVP.Presenter.Interface.Project.Common_UserAll_Presenter_Interface;
import com.ddtkj.commonmodule.Public.Common_PublicMsg;
import com.ddtkj.commonmodule.Public.Common_RouterUrl;
import com.ddtkj.commonmodule.Util.Common_CustomDialogBuilder;
import com.ddtkj.commonmodule.Util.IntentUtil;
import com.utlis.lib.AES;
import com.utlis.lib.L;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

/**用户所有信息通用获取接口
 * @author: Administrator 杨重诚
 * @date: 2016/10/12:17:07
 */

public class Common_UserAll_Presenter_Implement implements Common_UserAll_Presenter_Interface {
    Common_Application mCommonApplication;
    Common_Base_HttpRequest_Interface mCommonBaseHttpRequestInterface;//请求网络数据的model实现类
    String TAG="Common_UserAll_Presenter_Implement";
    Common_CustomDialogBuilder mCommonCustomDialogBuilder;
    public Common_UserAll_Presenter_Implement(){
        this.mCommonBaseHttpRequestInterface=new Common_Base_HttpRequest_Implement();
        mCommonApplication=Common_Application.getInstance();
    }

    public interface RefreshUserInfoListener {
        /**
         * 刷新用户信息Listener
         * @param userInfoBean
         */
        public void requestListener(boolean isSucc, Common_UserInfoBean userInfoBean);
    }
    /**
     * 刷新用户信息
     */
    @Override
    public void refreshUserInfo(Context context,final RefreshUserInfoListener refreshUserInfoListener, boolean isLoadingDialog){
        refreshUserInfo(context,getRefreshUserInfo_Params(),refreshUserInfoListener,isLoadingDialog);
    }
    /**
     * 获取刷新用户信息的Params
     * @return
     */
    public Map<String, Object> getRefreshUserInfo_Params() {
        Map<String, Object> params = new HashMap<String, Object>();
        return params;
    }

    /**
     * 刷新用户信息
     * @param params
     */
    public void refreshUserInfo(Context context,Map<String, Object> params,final RefreshUserInfoListener refreshUserInfoListener, boolean isLoadingDialog) {
        mCommonBaseHttpRequestInterface.requestData(context, Common_HttpPath.URL_API_TREFRESH_USER_INFO, new HashMap<String, Object>(), new Common_ResultDataListener() {
            @Override
            public void onResult(boolean isSucc, String msg, Common_RequestBean request_bean) {
                Common_UserInfoBean infoBean=null;
                if (isSucc) {
                    if(request_bean.getData()!=null){
                        JSONObject jsonObject = JSONObject.parseObject(request_bean.getData().toString());
                        infoBean=JSONObject.parseObject(jsonObject.getString("userinfo"),Common_UserInfoBean.class);
                    }
                    //更新用户信息
                    mCommonApplication.setUserInfoBean(infoBean);
                }
                if(refreshUserInfoListener!=null)
                    refreshUserInfoListener.requestListener(isSucc,infoBean);

            }
        },isLoadingDialog, Common_HttpRequestMethod.POST);
    }
    /**
     * 获取第三方登录用户信息
     */
    @Override
    public void refreshOtherLogin(Context context,String content, String loginType, String openId,final RefreshUserInfoListener refreshUserInfoListener,boolean isLoadingDialog){
        refreshOtherLogin(context,getOtherLogin_Params(content,openId, loginType),loginType,openId,refreshUserInfoListener,isLoadingDialog);
    }
    /**
     * 获取第三方登录用户信息的Params
     * @return
     */
    public Map<String, Object> getOtherLogin_Params(String content, String openId, String openType) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("openInfo", content);
        params.put("openId", openId);
        params.put("openType", openType);
        return params;
    }

    /**
     * 获取第三方登录用户信息
     * @param params
     */
    public void refreshOtherLogin(final Context context, Map<String, Object> params, final String loginType, final String openId, final RefreshUserInfoListener refreshUserInfoListener, boolean isLoadingDialog) {
        mCommonBaseHttpRequestInterface.requestData(context, Common_HttpPath.URL_API_TREFRESH_OTHER_LOGING_INFO, params, new Common_ResultDataListener() {
            @Override
            public void onResult(boolean isSucc, String msg, Common_RequestBean request_bean) {
                try{
                    if(request_bean.getCode()==null){
                        return;
                    }
                    if (request_bean.getCode().equals("1")) {
                        if (request_bean.getData() == null) {
                            return;
                        }
                        JSONObject jsonObject=JSONObject.parseObject(request_bean.getData().toString());
                        if (jsonObject != null ){
                            Common_UserInfoBean infoBean = null;
                            infoBean = JSONObject.parseObject(request_bean.getData().toString(),
                                    Common_UserInfoBean.class);
                            //更新用户信息
                            mCommonApplication.setUserInfoBean(infoBean);
                            //发送其它方式登录成功的通知
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    //这里发送粘性事件
                                    EventBus.getDefault().post(new Common_OtherLoginSuccess_Eventbus(true));
                                }
                            }, 300);

                            refreshUserInfoListener.requestListener(isSucc,infoBean);
                        }
                    } else {
                        //进入绑定手机界面
                        L.e(TAG,"三方登录授权通过，未绑定手机号");
                        JSONObject jsonObj = new JSONObject();
                        jsonObj.put("openType", loginType);
                        jsonObj.put("openId", openId);
                        //加密信息处理
                        String openInfo = AES.encryptToBase64(jsonObj.toString(),
                                Common_PublicMsg.AES_ENCRYPT_KEY);
                        Bundle bundle=new Bundle();
                        bundle.putString("openInfo",openInfo);
                        bundle.putString("openType", loginType);
                        bundle.putString("openId", openId);
                        //进入三方账号绑定页面
                        new IntentUtil().intent_RouterTo(context, Common_RouterUrl.USERINFO_ThiryUserLogingRouterUrl,bundle);
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        },isLoadingDialog,Common_HttpRequestMethod.POST);
    }
}
