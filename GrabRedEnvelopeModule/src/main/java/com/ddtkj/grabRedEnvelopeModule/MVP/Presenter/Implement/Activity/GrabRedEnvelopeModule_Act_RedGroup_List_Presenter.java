package com.ddtkj.grabRedEnvelopeModule.MVP.Presenter.Implement.Activity;

import android.os.Handler;

import com.alibaba.fastjson.JSONObject;
import com.ddtkj.commonmodule.HttpRequest.Common_HttpPath;
import com.ddtkj.commonmodule.HttpRequest.Common_HttpRequestMethod;
import com.ddtkj.commonmodule.HttpRequest.ResultListener.Common_ResultDataListener;
import com.ddtkj.commonmodule.MVP.Model.Bean.ResponseBean.Common_RequestBean;
import com.ddtkj.commonmodule.MVP.Model.Implement.Common_Base_HttpRequest_Implement;
import com.ddtkj.commonmodule.MVP.Model.Interface.Common_Base_HttpRequest_Interface;
import com.ddtkj.grabRedEnvelopeModule.MVP.Contract.Activity.GrabRedEnvelopeModule_Act_RedGroup_List_Contract;
import com.ddtkj.grabRedEnvelopeModule.MVP.Model.Bean.ResponseBean.GrabRedEnvelopeModule_Bean_RedGroupListInfo;

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
     * @param type 类型
     */
    @Override
    public void requestInvestmentProductData(String type) {
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("page",page);//当前页
        params.put("size",pageSize);//每页显示条数
        params.put("category",type);//空间类型，1. 酒店 2、公寓 3.民宿，4.更多
        mCommonBaseHttpRequestInterface.requestData(context, Common_HttpPath.URL_INVEST_LIST, params, new Common_ResultDataListener() {
            @Override
            public void onResult(boolean isSucc, String msg, Common_RequestBean request_bean) {
                if(isSucc){
                    if(request_bean.getData()==null){
                        return;
                    }
                    JSONObject jsonObject = JSONObject.parseObject("{\n" +
                            "\t\"hb\": [{\n" +
                            "\t\t\"id\": 27,\n" +
                            "\t\t\"house_id\": 1,\n" +
                            "\t\t\"hbcount\": 10,\n" +
                            "\t\t\"endtime\": \"2018-07-01 16:51:30\",\n" +
                            "\t\t\"hbstatus\": \"18070116462\"\n" +
                            "\t}, {\n" +
                            "\t\t\"id\": 29,\n" +
                            "\t\t\"house_id\": 1,\n" +
                            "\t\t\"hbcount\": 10,\n" +
                            "\t\t\"endtime\": \"2018-07-01 16:52:00\",\n" +
                            "\t\t\"hbstatus\": \"18070116471\"\n" +
                            "\t}, {\n" +
                            "\t\t\"id\": 31,\n" +
                            "\t\t\"house_id\": 1,\n" +
                            "\t\t\"hbcount\": 10,\n" +
                            "\t\t\"endtime\": \"2018-07-01 16:52:30\",\n" +
                            "\t\t\"hbstatus\": \"18070116472\"\n" +
                            "\t}, {\n" +
                            "\t\t\"id\": 33,\n" +
                            "\t\t\"house_id\": 1,\n" +
                            "\t\t\"hbcount\": 10,\n" +
                            "\t\t\"endtime\": \"2018-07-01 16:53:00\",\n" +
                            "\t\t\"hbstatus\": \"18070116481\"\n" +
                            "\t}, {\n" +
                            "\t\t\"id\": 35,\n" +
                            "\t\t\"house_id\": 1,\n" +
                            "\t\t\"hbcount\": 10,\n" +
                            "\t\t\"endtime\": \"2018-07-01 16:53:30\",\n" +
                            "\t\t\"hbstatus\": \"18070116482\"\n" +
                            "\t}, {\n" +
                            "\t\t\"id\": 37,\n" +
                            "\t\t\"house_id\": 1,\n" +
                            "\t\t\"hbcount\": 10,\n" +
                            "\t\t\"endtime\": \"2018-07-01 16:54:00\",\n" +
                            "\t\t\"hbstatus\": \"18070116491\"\n" +
                            "\t}, {\n" +
                            "\t\t\"id\": 39,\n" +
                            "\t\t\"house_id\": 1,\n" +
                            "\t\t\"hbcount\": 10,\n" +
                            "\t\t\"endtime\": \"2018-07-01 16:54:30\",\n" +
                            "\t\t\"hbstatus\": \"18070116492\"\n" +
                            "\t}, {\n" +
                            "\t\t\"id\": 41,\n" +
                            "\t\t\"house_id\": 1,\n" +
                            "\t\t\"hbcount\": 10,\n" +
                            "\t\t\"endtime\": \"2018-07-01 16:55:00\",\n" +
                            "\t\t\"hbstatus\": \"18070116501\"\n" +
                            "\t}, {\n" +
                            "\t\t\"id\": 43,\n" +
                            "\t\t\"house_id\": 1,\n" +
                            "\t\t\"hbcount\": 10,\n" +
                            "\t\t\"endtime\": \"2018-07-01 16:55:30\",\n" +
                            "\t\t\"hbstatus\": \"18070116502\"\n" +
                            "\t}, {\n" +
                            "\t\t\"id\": 45,\n" +
                            "\t\t\"house_id\": 1,\n" +
                            "\t\t\"hbcount\": 10,\n" +
                            "\t\t\"endtime\": \"2018-07-01 16:56:00\",\n" +
                            "\t\t\"hbstatus\": \"18070116511\"\n" +
                            "\t}]\n" +
                            "}");
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


}
