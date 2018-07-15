package com.ddtkj.grabRedEnvelopeModule.MVP.Presenter.Implement.Activity;

import android.os.Handler;

import com.alibaba.fastjson.JSONObject;
import com.ddtkj.commonmodule.Base.Common_Application;
import com.ddtkj.commonmodule.HttpRequest.Common_HttpPath;
import com.ddtkj.commonmodule.HttpRequest.Common_HttpRequestMethod;
import com.ddtkj.commonmodule.HttpRequest.ResultListener.Common_ResultDataListener;
import com.ddtkj.commonmodule.MVP.Model.Bean.ResponseBean.Common_RequestBean;
import com.ddtkj.commonmodule.MVP.Model.Implement.Common_Base_HttpRequest_Implement;
import com.ddtkj.commonmodule.MVP.Model.Interface.Common_Base_HttpRequest_Interface;
import com.ddtkj.grabRedEnvelopeModule.MVP.Contract.Activity.GrabRedEnvelopeModule_Act_RedGroup_List_Contract;
import com.ddtkj.grabRedEnvelopeModule.MVP.Model.Bean.ResponseBean.GrabRedEnvelopeModule_Bean_RedGroupListInfo;
import com.utlis.lib.ToastUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  投资列表
 *
 *  @Author: 杨重诚
 *  @CreatTime: 2018/2/13  14:13  
 */
public class GrabRedEnvelopeModule_Act_RedGroup_List_Presenter extends GrabRedEnvelopeModule_Act_RedGroup_List_Contract.Presenter{
    Common_Base_HttpRequest_Interface mCommonBaseHttpRequestInterface;
    private int countHttpMethod = 0;//总共有多少个请求网络数据的方法
    private int indexHttpMethod = 0;//记录当前执行到第几个请求方法
    private int page = 1;//请求的page
    private int pageSize = 10;//请求的page

    public GrabRedEnvelopeModule_Act_RedGroup_List_Presenter(){
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
            },300);
        }
    }
    @Override
    public void onStart() {

    }
    /**
     * 获取投资列表数据
     * @param house_id 类型
     */
    @Override
    public void requestInvestmentProductData(String house_id) {
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("page",page);//当前页
        params.put("size",pageSize);//每页显示条数
        params.put("house_id",house_id);
        mCommonBaseHttpRequestInterface.requestData(context, Common_HttpPath.URL_API_REDPACKET_PACKETINFONEW, params, new Common_ResultDataListener() {
            @Override
            public void onResult(boolean isSucc, String msg, Common_RequestBean request_bean) {
                if(isSucc){
                    if(request_bean.getData()==null){
                        return;
                    }
                    JSONObject jsonObject = JSONObject.parseObject(request_bean.getData().toString());
                    List<GrabRedEnvelopeModule_Bean_RedGroupListInfo> invoiceBeanList = JSONObject.parseArray(jsonObject.getString("hb"),GrabRedEnvelopeModule_Bean_RedGroupListInfo.class);
                    setInvestmentProductListData(invoiceBeanList);
                }
                closeRefresh();
            }
        },true, Common_HttpRequestMethod.GET);
    }

    /**
     * 设置投资列表数据
     */
    public  void setInvestmentProductListData(List<GrabRedEnvelopeModule_Bean_RedGroupListInfo> beans){
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
     * 退出红包房间
     */
    @Override
    public void requestRedpacketHouseOut(String house_id) {
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("house_id",house_id);
        params.put("user_id", Common_Application.getInstance().getUseInfoVo().getUserId());
        mCommonBaseHttpRequestInterface.requestData(context, Common_HttpPath.URL_API_REDPACKET_HOUSEOUT, params, new Common_ResultDataListener() {
            @Override
            public void onResult(boolean isSucc, String msg, Common_RequestBean request_bean) {
                if(isSucc){
                    if(request_bean.getData()==null){
                        return;
                    }
                    mView.outHomeSuccess();
                }
            }
        },true, Common_HttpRequestMethod.GET);
    }

    /**
     * 是否可以抢红包
     * @param hb_id
     * @param commonResultDataListener
     */
    @Override
    public void requestRedpacketIsShowhb(String hb_id, final Common_ResultDataListener commonResultDataListener) {
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("hb_id",hb_id);
        params.put("user_id", Common_Application.getInstance().getUseInfoVo().getUserId());
        mCommonBaseHttpRequestInterface.requestData(context, Common_HttpPath.URL_API_REDPACKET_ISSHOWHB, params, new Common_ResultDataListener() {
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
                    switch (businessCode){
                        case "1":
                            commonResultDataListener.onResult(true,businessMsg,null);
                            break;
                            default:
                                ToastUtils.WarnImageToast(context,businessMsg);

                                break;
                    }
                }
            }
        },true, Common_HttpRequestMethod.GET);
    }

    /**
     * 抢红包
     * @param hb_id
     * @param commonResultDataListener
     */
    @Override
    public void requestRedpacketPacketinfodetail(String hb_id, final Common_ResultDataListener commonResultDataListener) {
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("hb_id",hb_id);
        params.put("user_id", Common_Application.getInstance().getUseInfoVo().getUserId());
        mCommonBaseHttpRequestInterface.requestData(context, Common_HttpPath.URL_API_REDPACKET_PACKETINFODETAIA, params, new Common_ResultDataListener() {
            @Override
            public void onResult(boolean isSucc, String msg, Common_RequestBean request_bean) {
                if(isSucc){
                    if(request_bean.getData()==null){
                        return;
                    }
                    commonResultDataListener.onResult(true,msg,null);
                }
            }
        },true, Common_HttpRequestMethod.GET);
    }
}
