package com.ddt.redEnvelope.Base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.alibaba.sdk.android.httpdns.HttpDns;
import com.alibaba.sdk.android.httpdns.HttpDnsService;
import com.ddt.redEnvelope.BuildConfig;
import com.ddt.redEnvelope.R;
import com.ddtkj.commonmodule.Base.Common_Application;
import com.ddtkj.commonmodule.HttpRequest.Common_HostPath;
import com.ddtkj.commonmodule.MVP.Model.Bean.ResponseBean.Common_UserInfoBean;
import com.ddtkj.commonmodule.Public.Common_PublicMsg;
import com.meituan.android.walle.WalleChannelReader;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.utlis.lib.L;
import com.utlis.lib.SharePre;
import com.utlis.lib.ToolsUtils;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.RxRetrofitApp;

import java.util.ArrayList;
import java.util.Arrays;

import cat.ereza.customactivityoncrash.CustomActivityOnCrash;
import cn.jpush.android.api.JPushInterface;

/**
 * @ClassName: My_Application
 * @author: Administrator杨重诚
 * @date: 2016/6/6 15:43
 */
public class My_Application extends Application {
    public static My_Application mApp;
    Common_Application mCommonApplication;
    public static My_Application getInstance() {
        return mApp;
    }
    //阿里HttpDns
    public static HttpDnsService httpdns;
    //渠道名
    String channel;
    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        //初始化Module模块
        initModule();
        //初始化数据库
        //LitePal.initialize(this);
        //初始化系统异常显示的activity(第二个参数是异常图片，-1是默认图片，否则传R.mipmap.icon)
        CustomActivityOnCrash.install(this, R.drawable.icon_fail);
        //获取渠道名
        channel = WalleChannelReader.getChannel(this.getApplicationContext());
        //TODO 打包时需把app项目下的common_build.gradle中的LOG_DEBUG定义为false(非调试模式)
        //设置渠道
        Bugly.setAppChannel(this,channel);
        // 调试时，将第三个参数改为true
        Bugly.init(this, Common_PublicMsg.TENCENT_BUGLY_APPKEY, BuildConfig.LOG_DEBUG);
        //bugly声明为开发设备，用于开发者调试使用
        Bugly.setIsDevelopmentDevice(getApplicationContext(), BuildConfig.LOG_DEBUG);
        //初始化友盟分享
        UMShareAPI.get(this);
        //初始化HttpDns
        initHttpDns();
        //同步HttpDns对象
        mCommonApplication.setHttpDnsService(httpdns);
        //初始化Talkingdata
        initTalkingData();
        // 初始化 JPush
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        //RXJAVA+OKHTTP+RETROFIT+...的初始化
        RxRetrofitApp.init(this);
        //动态判断是debug还是release
        if(BuildConfig.LOG_DEBUG){
            L.isDebug = true;//设置Log打印是否显示
            Config.DEBUG = true;//打印友盟分享Log调试打印
            // Talkingdata
            //TCAgent.LOG_ON = true;
        }else{
            L.isDebug = false;//设置Log打印是否显示
            Config.DEBUG = false;//打印友盟分享Log调试打印
            // Talkingdata
            //TCAgent.LOG_ON = false;
        }
    }
    /**
     * 初始化HttpDns
     */
    private void initHttpDns(){
        // 设置APP Context和Account ID，并初始化HTTPDNS
        httpdns = HttpDns.getService(getApplicationContext(), Common_PublicMsg.HTTPDNS_APPKEY,Common_PublicMsg.HTTPDNS_SECRETKEY);
        // 设置预解析域名列表，真正使用时，建议您将预解析操作放在APP启动函数中执行。预解析操作为异步行为，不会阻塞您的启动流程
        httpdns.setPreResolveHosts(new ArrayList<>(Arrays.asList(Common_HostPath.HOSTS)));
        // 允许返回过期的IP，通过设置允许返回过期的IP，配合异步查询接口，我们可以实现DNS懒更新策略
        httpdns.setExpiredIPEnabled(true);
    }

    //各个平台的配置，建议放在全局Application或者程序入口
    {
        //微信
        PlatformConfig.setWeixin(Common_PublicMsg.WX_APP_ID, Common_PublicMsg.WX_SECRET);
        //新浪微博
        PlatformConfig.setSinaWeibo(Common_PublicMsg.WB_APPID, Common_PublicMsg.WB_APPKEY,"http://sns.whalecloud.com/sina2/callback");//分享回调地址
        //qq
        PlatformConfig.setQQZone(Common_PublicMsg.QQ_APPID, Common_PublicMsg.QQ_APPKEY);

    }
    /**
     * 初始化Module模块
     */
    private void initModule(){
        //初始化公共Module
        mCommonApplication=Common_Application.initCommonApplication(mApp);
        if(!com.ddtkj.commonmodule.BuildConfig.IsBuildMudle){
            //初始化用户中心模块
            ToolsUtils.initReflectionModule("com.ddtkj.userinfomodule.Base.Application.release.UserInfo_Application","initUserInfo_Application",
                    new Class[]{Application.class,Common_Application.class},new Object[]{mApp, mCommonApplication});
            //初始化众包模块
            ToolsUtils.initReflectionModule("com.ddtkj.crowdsourcing.employersModule.Base.Application.release.Employers_Application","initEmployers_Application",
                    new Class[]{Application.class,Common_Application.class},new Object[]{mApp, mCommonApplication});
        }
    }
    /**
     * 获取全局SharePre
     *
     * @return
     */
    public SharePre getGlobalSharedPreferences() {
        return mCommonApplication.getGlobalSharedPreferences();
    }

    /**
     * 获取用户SharePre
     *
     * @return
     */
    public SharePre getUserSharedPreferences() {
        return mCommonApplication.getUserSharedPreferences();
    }

    /**
     * 获取sdcard的SharePre
     *
     * @return
     */
    public SharePre getSdCardSharedPreferences() {
        return mCommonApplication.getSdCardSharedPreferences();
    }

    /**
     * 初始化Talkingdata
     */
    private void initTalkingData() {
        // App ID: 在TalkingData创建应用后，进入数据报表页中，在“系统设置”-“编辑应用”页面里查看App ID。
        // 渠道 ID: 是渠道标识符，可通过不同渠道单独追踪数据。
        // TCAgent.init(this, "36584E61F35FBB6FB89ECCD0FF79C8CD",
       /* TCAgent.init(this, Common_PublicMsg.TCAgent_APPKEY, channel);
        TCAgent.setReportUncaughtExceptions(true);
        // talkingdata广告统计
        TalkingDataAppCpa
                .init(this, Common_PublicMsg.TalkingDataAppCpa_APPKEY, channel);*/
    }


    // 取得用户信息
    public Common_UserInfoBean getUseInfoVo() {
        if (mCommonApplication.getUseInfoVo() != null) {
            return mCommonApplication.getUseInfoVo();
        }
        return null;
    }

    // 设置用户信息
    public void setUseInfoVo(Common_UserInfoBean useInfoVo) {
        mCommonApplication.setUserInfoBean(useInfoVo);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
        // 安装tinker
        Beta.installTinker();
    }
}
