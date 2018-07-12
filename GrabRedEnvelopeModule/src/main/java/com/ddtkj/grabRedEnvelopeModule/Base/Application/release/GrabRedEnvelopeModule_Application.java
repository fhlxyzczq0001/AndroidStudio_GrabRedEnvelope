package com.ddtkj.grabRedEnvelopeModule.Base.Application.release;

import android.app.Application;
import android.content.Context;

import com.alibaba.sdk.android.httpdns.HttpDnsService;
import com.ddtkj.commonmodule.Base.Common_Application;
import com.ddtkj.commonmodule.MVP.Model.Bean.ResponseBean.Common_UserInfoBean;
import com.ddtkj.grabRedEnvelopeModule.Base.Application.GrabRedEnvelopeModule_Application_Interface;
import com.utlis.lib.SharePre;

/**
 * @ClassName: Directly_Application
 * @author: Administrator杨重诚
 * @date: 2016/6/6 15:43
 */
//public class Directly_Application extends LitePalApplication {
public class GrabRedEnvelopeModule_Application implements GrabRedEnvelopeModule_Application_Interface {
    public static Application mApplication;//上下文
    static Common_Application mCommonApplication;//公共lib的Application
    static GrabRedEnvelopeModule_Application ventureCapital2Application;//自己的Application

    /**
     * 联调打包要在主项目app初始化这个方法
     * @param application
     * @param mCommonApplication
     * @return
     */
    public void initVentureCapital_2_Application(Application application, Common_Application mCommonApplication){
        mApplication=application;
        GrabRedEnvelopeModule_Application.mCommonApplication=mCommonApplication;
        ventureCapital2Application=getInstance();
    }

    /**
     * 初始化配置文件
     * @param mContext
     */
    @Override
    public void requestProfile(Context mContext){
    }

    /**
     * 获取单例对象
     * @return
     */
    public static GrabRedEnvelopeModule_Application getInstance(){
        if(ventureCapital2Application==null){
            //第一次check，避免不必要的同步
            synchronized (GrabRedEnvelopeModule_Application.class){//同步
                if(ventureCapital2Application==null){
                    //第二次check，保证线程安全
                    ventureCapital2Application=new GrabRedEnvelopeModule_Application();
                }
            }
        }
        return ventureCapital2Application;
    }

    /**
     * 获取上下文
     * @return
     */
    public Application getApplication(){
        return mApplication;
    }

    /**
     * 获取用户SharePre
     *
     * @return
     */
    @Override
    public SharePre getUserSharedPreferences() {
        return mCommonApplication.getUserSharedPreferences();
    }

    @Override
    public SharePre getGlobalSharedPreferences() {
        return mCommonApplication.getGlobalSharedPreferences();
    }

    // 取得用户信息
    @Override
    public Common_UserInfoBean getUseInfoVo() {
        if (mCommonApplication.getUseInfoVo() != null) {
            return mCommonApplication.getUseInfoVo();
        }
        return null;
    }
    // 设置用户信息
    @Override
    public void setUseInfoVo(Common_UserInfoBean useInfoVo) {
        mCommonApplication.setUserInfoBean(useInfoVo);
    }

    @Override
    public HttpDnsService getHttpDnsService() {
        return Common_Application.httpdns;
    }
}
