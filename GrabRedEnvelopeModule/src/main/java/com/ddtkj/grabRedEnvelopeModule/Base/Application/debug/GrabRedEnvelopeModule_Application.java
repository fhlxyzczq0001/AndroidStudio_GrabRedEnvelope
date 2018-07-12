package com.ddtkj.grabRedEnvelopeModule.Base.Application.debug;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.alibaba.sdk.android.httpdns.HttpDnsService;
import com.ddtkj.commonmodule.Base.Common_Application;
import com.ddtkj.commonmodule.MVP.Model.Bean.ResponseBean.Common_UserInfoBean;
import com.ddtkj.grabRedEnvelopeModule.Base.Application.GrabRedEnvelopeModule_Application_Interface;
import com.ddtkj.userinfomodule.Base.Application.release.UserInfo_Application;
import com.utlis.lib.SharePre;

/**
 * Created by ${杨重诚} on 2017/7/11.
 */

public class GrabRedEnvelopeModule_Application extends Application implements GrabRedEnvelopeModule_Application_Interface {
    public static Application mApplication;//上下文
    static Common_Application mCommonApplication;//公共lib的Application
    static GrabRedEnvelopeModule_Application ventureCapital2Application;//自己的Application
    /**
     * 单元测试会调用onCreate方法，所以要初始化一些东西
     */
    @Override
    public void onCreate() {
        super.onCreate();
        ventureCapital2Application=this;
        mApplication=ventureCapital2Application;
        mCommonApplication=Common_Application.initCommonApplication(ventureCapital2Application);
        //初始化用户信息模块
        new UserInfo_Application().initUserInfo_Application(mApplication,mCommonApplication);
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
    @Override
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

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
