package com.ddtkj.commonmodule.HttpRequest;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.ddtkj.commonmodule.Base.Common_Application;
import com.ddtkj.commonmodule.HttpRequest.HttpManager.Common_HttpRequestManager;
import com.ddtkj.commonmodule.HttpRequest.RequestBody.Common_RequestBodyApi;
import com.ddtkj.commonmodule.HttpRequest.ResultListener.Common_ResultDataListener;
import com.ddtkj.commonmodule.MVP.Model.Bean.ResponseBean.Common_RequestBean;
import com.ddtkj.commonmodule.MVP.Presenter.Implement.Project.Common_ProjectUtil_Implement;
import com.ddtkj.commonmodule.MVP.Presenter.Interface.Project.Common_ProjectUtil_Interface;
import com.ddtkj.commonmodule.Public.Common_PublicMsg;
import com.ddtkj.commonmodule.Public.Common_RouterUrl;
import com.ddtkj.commonmodule.R;
import com.ddtkj.commonmodule.Util.IntentUtil;
import com.ddtkj.commonmodule.Util.LoadingDialog;
import com.ddtkj.commonmodule.Util.MD5;
import com.utlis.lib.AppUtils;
import com.utlis.lib.CheckUtils;
import com.utlis.lib.L;
import com.utlis.lib.ToastUtils;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.exception.ApiException;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.exception.CodeException;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener.HttpOnNextListener;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import rx.Subscription;

/**
 * Rxjava+okhttp请求回调类
 * Created by Administrator on 2017/2/24.
 */

public class Common_CustomRequestResponseBeanDataManager {
    private Common_Application mCommonApplication;
    Common_ProjectUtil_Interface mCommon_projectUtil_interface;
    Dialog dialog;
    Subscription subscription=null;
    public Common_CustomRequestResponseBeanDataManager() {
        mCommonApplication = Common_Application.getInstance();
        if (mCommon_projectUtil_interface == null) {
            mCommon_projectUtil_interface =  Common_ProjectUtil_Implement.getInstance();
        }
    }

    public static Common_CustomRequestResponseBeanDataManager commonCustomRequestResponseBeanDataManager;

    public static Common_CustomRequestResponseBeanDataManager getInstance() {
        if (commonCustomRequestResponseBeanDataManager == null) {
            //第一次check，避免不必要的同步
            synchronized (Common_CustomRequestResponseBeanDataManager.class) {//同步
                if (commonCustomRequestResponseBeanDataManager == null) {
                    //第二次check，保证线程安全
                    commonCustomRequestResponseBeanDataManager = new Common_CustomRequestResponseBeanDataManager();
                }
            }
        }
        return commonCustomRequestResponseBeanDataManager;
    }

    /**
     * pos请求
     *
     * @param context
     * @param url
     * @param mParams
     * @param resultListener
     * @param isLoadingDialog
     */
    public Subscription  requestPost(final Context context, final String url, Map<String, Object> mParams, final Common_ResultDataListener resultListener, final boolean isLoadingDialog) {
        subscription=null;
        mParams.put("ts", String.valueOf(System.currentTimeMillis()));
        mParams.put("appkey", Common_PublicMsg.Post_APPKEY_ANDROID);
        mParams.put("appversion", AppUtils.getAppVersionName(context));
        mParams.put("client", "5");//android：5，ios：4
        mParams.put("appType", "2");//理财：1，借款：2
        mParams.put("sign", paramsSort(mParams));

        Common_HttpRequestManager httpRequestManager = new Common_HttpRequestManager(new HttpOnNextListener() {
            @Override
            public void onNext(String resulte, String mothead) {
                JSONObject jsonObject = JSONObject.parseObject(resulte);
                int resCode = jsonObject.getIntValue("code");
                String res_Msg = jsonObject.getString("msg");
                boolean isSucc = false;
                switch (resCode) {
                    case 1:
                        //请求成功
                        isSucc = true;
                        break;
                    case -1:
                        //登录超时,需要重新登陆
                        //清理当前存放的用户信息
                        mCommon_projectUtil_interface.logOut();
                        ToastUtils.WarnImageToast(context,res_Msg);
                        new IntentUtil().intent_RouterTo(context, Common_RouterUrl.USERINFO_LogingRouterUrl);
                        break;
                    case 10013:
                        if (!"WelcomePage_View_Implement".equals(context.getClass().getSimpleName())) {
                            String updateMessage = jsonObject.getString("message");
                            String updateUrl = jsonObject.getString("url");
                            mCommon_projectUtil_interface.showAppUpdateDialog_Forced(context, updateMessage, updateUrl);
                        } else {
                            //版本不符，需强制更新
                            ToastUtils.WarnImageToast(context, res_Msg);
                        }
                        break;
                    case 10014:
                        //服务器连接失败，请求维护页面
                        if (mCommon_projectUtil_interface == null) {
                            mCommon_projectUtil_interface = new Common_ProjectUtil_Implement();
                        }
                        mCommon_projectUtil_interface.setServerState(context, res_Msg);
                        ToastUtils.WarnImageToast(context,res_Msg);
                        break;
                    case 10015:
                        //普通更新
                        if (!"WelcomePage_View_Implement".equals(context.getClass().getSimpleName())) {
                            String updateMessage = jsonObject.getString("message");
                            String updateUrl = jsonObject.getString("url");
                            mCommon_projectUtil_interface.showAppUpdateDialog_Common(context, updateMessage, updateUrl);
                        } else {
                            //版本不符，需强制更新
                            ToastUtils.WarnImageToast(context, res_Msg);
                        }
                        break;
                    case 0:
                        //服务器返回失败
                        ToastUtils.WarnImageToast(context, res_Msg);
                        break;
                    case 10011:
                        //登录超时,需要重新登陆
                        ToastUtils.WarnImageToast(context, res_Msg);
                        //清理当前存放的用户信息
                        mCommon_projectUtil_interface.logOut();
                        break;
                    default:
                        // 操作失败 或 错误码未知
                        ToastUtils.WarnImageToast(context, res_Msg);
                        break;
                }
                Common_RequestBean request_Bean = JSONObject.parseObject(resulte, Common_RequestBean.class);
                if(request_Bean.getData()==null)
                    request_Bean.setData("");
                resultListener.onResult(isSucc, request_Bean.getMsg(), request_Bean);
                dialogDismiss(dialog);
            }

            @Override
            public void onError(ApiException e) {
                e.printStackTrace();
                L.e("请求失败", "" + e.hashCode());
                String errorStr = "未知异常，请联系客服";
                 if(e.getCode() == CodeException.HTTP_ERROR || e.getCode() == CodeException.UNKOWNHOST_ERROR){//http错误时，请求查看是否维护路径
                     errorStr  = "服务器异常-"+e.getDisplayMessage();
                     //请求维护页面
                     if (mCommon_projectUtil_interface == null) {
                         mCommon_projectUtil_interface = new Common_ProjectUtil_Implement();
                     }
                     mCommon_projectUtil_interface.setServerState(context, "");
                 }else if(e.getCode() == CodeException.NETWORD_ERROR){
                     errorStr  = context.getResources().getString(R.string.ERROR_MESSAGE);
                 }else if(e.getCode() == CodeException.JSON_ERROR){
                     errorStr  = "数据解析异常，请联系客服";
                 }
                resultListener.onResult(false, errorStr, null);
                 ToastUtils.ErrorImageToast(context, errorStr);
                dialogDismiss(dialog);
            }
        }, context);
        //====================显示进度加载=============================
        try {
            dialog = (Dialog) LoadingDialog.initProgressDialog(context).getDialog();
            if (isLoadingDialog) {
                dialog.show();
            }
        } catch (Exception e) {

        }

        Common_RequestBodyApi requestBodyApi = new Common_RequestBodyApi(url, "POST");
        requestBodyApi.setParams(mParams);
        requestBodyApi.setShowProgress(false);
        requestBodyApi.setDialog(dialog);
        subscription=httpRequestManager.doHttpDeal(requestBodyApi);
        return subscription;
    }

    /**
     * get请求
     *
     * @param context
     * @param url
     * @param resultListener
     * @param isLoadingDialog
     */
    public void requestGet(final Context context, String url, Map<String, Object> mParams, final Common_ResultDataListener resultListener, final boolean isLoadingDialog) {
        L.e("get请求："+url);
        Common_HttpRequestManager httpRequestManager = new Common_HttpRequestManager(new HttpOnNextListener() {
            @Override
            public void onNext(String resulte, String mothead) {
                Common_RequestBean request_Bean = JSONObject.parseObject(resulte, Common_RequestBean.class);
                resultListener.onResult(true, request_Bean.getMsg(), request_Bean);
                dialogDismiss(dialog);
            }

            @Override
            public void onError(ApiException e) {
                dialogDismiss(dialog);
            }
        }, context);
        //====================显示进度加载=============================
        try {
            dialog = (Dialog) LoadingDialog.initProgressDialog(context).getDialog();
            if (isLoadingDialog) {
                dialog.show();
            }
        } catch (Exception e) {

        }

        Common_RequestBodyApi requestBodyApi = new Common_RequestBodyApi(url, "GET");
        requestBodyApi.setParams(mParams);
        requestBodyApi.setShowProgress(false);
        requestBodyApi.setDialog(LoadingDialog.initProgressDialog(context).getDialog());
        httpRequestManager.doHttpDeal(requestBodyApi);
    }

    private void dialogDismiss(Dialog dialog) {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    public void onDestroy() {
       /* mCommonApplication = null;
        mCommon_projectUtil_interface = null;
        if (dialog != null) {
            dialog.dismiss();
        }
        dialog = null;
        commonCustomRequestResponseBeanDataManager = null;*/
    }

    //------------------------------------写死账号测试------------------------------------
    /**
     * 参数排序
     * @param mParams
     * @return
     */
    public String paramsSort(Map<String, Object> mParams){
        List<Map<String, Object>> packageParams = new LinkedList<Map<String, Object>>();
        Set<Map.Entry<String, Object>> paramsLinked = mParams.entrySet();
        for (Map.Entry<String, Object> entry : paramsLinked){
            if(CheckUtils.checkStringNoNull(entry.getValue().toString())){
                Map<String, Object> map = new HashMap<>();
                map.put(entry.getKey(),entry.getValue());
                packageParams.add(map);
            }
        }
//        Collections.sort(packageParams);
        return genPackageSign(packageParams);
    }
    /**
     生成签名
     */
    private String genPackageSign(List<Map<String, Object>> params) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < params.size(); i++) {
            Map<String, Object> stringObjectMap = params.get(i);
            for (Map.Entry<String, Object> entry : stringObjectMap.entrySet()){
                if(CheckUtils.checkStringNoNull(entry.getValue().toString())){
                    sb.append(entry.getKey());
                    sb.append('=');
                    sb.append(entry.getValue());
                    sb.append('&');
                }
            }

        }
        sb.append("key=");
        sb.append(Common_PublicMsg.Post_APPKEY_ANDROID);
        String packageSign = MD5.getMessageDigest(sb.toString().getBytes()).toLowerCase();
        Log.e("orion",sb.toString());
        Log.e("orion",packageSign);
        return packageSign;
    }
}