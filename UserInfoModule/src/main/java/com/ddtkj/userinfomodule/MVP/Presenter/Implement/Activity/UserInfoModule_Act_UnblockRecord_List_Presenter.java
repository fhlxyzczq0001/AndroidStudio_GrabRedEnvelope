package com.ddtkj.userinfomodule.MVP.Presenter.Implement.Activity;

import android.os.Handler;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.ddtkj.commonmodule.HttpRequest.Common_HttpPath;
import com.ddtkj.commonmodule.HttpRequest.Common_HttpRequestMethod;
import com.ddtkj.commonmodule.HttpRequest.ResultListener.Common_ResultDataListener;
import com.ddtkj.commonmodule.MVP.Model.Bean.ResponseBean.Common_RequestBean;
import com.ddtkj.commonmodule.MVP.Model.Implement.Common_Base_HttpRequest_Implement;
import com.ddtkj.commonmodule.MVP.Model.Interface.Common_Base_HttpRequest_Interface;
import com.ddtkj.userinfomodule.MVP.Contract.Activity.UserInfoModule_Act_UnblockRecord_List_Contract;
import com.ddtkj.userinfomodule.MVP.Model.Bean.ResponseBean.UserInfoModule_Bean_UnblockRecord;
import com.utlis.lib.ToastUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 解封账号记录列表
 *
 * @Author: 杨重诚
 * @CreatTime: 2018/2/13  14:13
 */
public class UserInfoModule_Act_UnblockRecord_List_Presenter extends UserInfoModule_Act_UnblockRecord_List_Contract.Presenter {
    Common_Base_HttpRequest_Interface mCommonBaseHttpRequestInterface;
    private int countHttpMethod = 0;//总共有多少个请求网络数据的方法
    private int indexHttpMethod = 0;//记录当前执行到第几个请求方法
    private int page = 1;//请求的page
    private int pageSize = 10;//请求的page

    public UserInfoModule_Act_UnblockRecord_List_Presenter() {
        mCommonBaseHttpRequestInterface = new Common_Base_HttpRequest_Implement();//接口实现类
    }

    /**
     * 初始化参数
     */
    @Override
    public void initData(int countHttpMethod) {
        this.countHttpMethod = countHttpMethod;
        indexHttpMethod = 0;
    }

    /**
     * 关闭刷新控件
     */
    public void closeRefresh() {
        indexHttpMethod++;
        if (indexHttpMethod >= countHttpMethod) {
            indexHttpMethod = 0;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mView.closeRefresh();
                }
            }, 300);
        }
    }

    @Override
    public void onStart() {

    }

    /**
     * 获取投资列表数据
     *
     * @param type 类型
     */
    @Override
    public void requestInvestmentProductData(String type) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("page", page);//当前页
        params.put("size", pageSize);//每页显示条数
        mCommonBaseHttpRequestInterface.requestData(context, Common_HttpPath.URL_API_USERLIST, params, new Common_ResultDataListener() {
            @Override
            public void onResult(boolean isSucc, String msg, Common_RequestBean request_bean) {
                if (isSucc) {
                    if (request_bean.getData() == null) {
                        return;
                    }
                    JSONObject jsonObject = JSONObject.parseObject(request_bean.getData().toString());
                    List<UserInfoModule_Bean_UnblockRecord> invoiceBeanList = JSONObject.parseArray(jsonObject.getString("userlist"), UserInfoModule_Bean_UnblockRecord.class);
                    setInvestmentProductListData(invoiceBeanList);
                }
                closeRefresh();
            }
        }, true, Common_HttpRequestMethod.GET);
    }

    /**
     * 用户搜索
     * @param user_id
     */
    @Override
    public void requestSearchuser(String user_id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("user_id", user_id);
        mCommonBaseHttpRequestInterface.requestData(context, Common_HttpPath.URL_API_SEARCHUSER, params, new Common_ResultDataListener() {
            @Override
            public void onResult(boolean isSucc, String msg, Common_RequestBean request_bean) {
                if (isSucc) {
                    if (request_bean.getData() == null) {
                        return;
                    }
                    JSONObject jsonObject = JSONObject.parseObject(request_bean.getData().toString());
                    List<UserInfoModule_Bean_UnblockRecord> invoiceBeanList = JSONObject.parseArray(jsonObject.getString("userinfo"), UserInfoModule_Bean_UnblockRecord.class);
                    setInvestmentProductListData(invoiceBeanList);
                }
                closeRefresh();
            }
        }, true, Common_HttpRequestMethod.GET);
    }
    /**
     * 设置投资列表数据
     */
    public void setInvestmentProductListData(List<UserInfoModule_Bean_UnblockRecord> beans) {
        if (beans != null) {
            //调用View的接口，设置投资列表接口数据
            mView.setInvestmentProductData(beans);
        }
    }

    /**
     * 设置请求page
     */
    @Override
    public void setPageNum(int page) {
        this.page = page;
    }

    @Override
    public int getPageNum() {
        return page;
    }

    /**
     * 账户锁定
     *
     * @param user_id
     * @param commonResultDataListener
     */
    @Override
    public void requestLockuser(String user_id, final Common_ResultDataListener commonResultDataListener) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("user_id", user_id);
        mCommonBaseHttpRequestInterface.requestData(context, Common_HttpPath.URL_API_LOCKUSER, params, new Common_ResultDataListener() {
            @Override
            public void onResult(boolean isSucc, String msg, Common_RequestBean request_bean) {
                if (isSucc) {
                    if (request_bean.getData() == null) {
                        return;
                    }
                    String businessCode = "";
                    String businessMsg = "";
                    JSONObject jsonObject = JSONObject.parseObject(request_bean.getData().toString());
                    if (jsonObject.containsKey("businessCode"))
                        businessCode = jsonObject.getString("businessCode");
                    if (jsonObject.containsKey("businessMsg"))
                        businessMsg = jsonObject.getString("businessMsg");
                    if(!TextUtils.isEmpty(businessMsg))
                        msg=businessMsg;
                    if (businessCode.equals("1")){
                        commonResultDataListener.onResult(true, msg, null);
                    }else {
                        ToastUtils.WarnImageToast(context,msg);
                    }

                }
            }
        }, true, Common_HttpRequestMethod.GET);
    }

    /**
     * 账户解锁
     *
     * @param user_id
     * @param commonResultDataListener
     */
    @Override
    public void requestUnLockuser(String user_id, final Common_ResultDataListener commonResultDataListener) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("user_id", user_id);
        mCommonBaseHttpRequestInterface.requestData(context, Common_HttpPath.URL_API_UNLOCKUSER, params, new Common_ResultDataListener() {
            @Override
            public void onResult(boolean isSucc, String msg, Common_RequestBean request_bean) {
                if (isSucc) {
                    if (request_bean.getData() == null) {
                        return;
                    }
                    String businessCode = "";
                    String businessMsg = "";
                    JSONObject jsonObject = JSONObject.parseObject(request_bean.getData().toString());
                    if (jsonObject.containsKey("businessCode"))
                        businessCode = jsonObject.getString("businessCode");
                    if (jsonObject.containsKey("businessMsg"))
                        businessMsg = jsonObject.getString("businessMsg");
                    if(!TextUtils.isEmpty(businessMsg))
                        msg=businessMsg;
                    if (businessCode.equals("1")){
                        commonResultDataListener.onResult(true, msg, null);
                    }else {
                        ToastUtils.WarnImageToast(context,msg);
                    }
                }
            }
        }, true, Common_HttpRequestMethod.GET);
    }
}
