package com.ddtkj.commonmodule.Base;

import android.app.Application;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatDelegate;

import com.alibaba.sdk.android.httpdns.HttpDnsService;
import com.chenenyu.router.Configuration;
import com.chenenyu.router.Router;
import com.ddtkj.commonmodule.BuildConfig;
import com.ddtkj.commonmodule.CustomView.Common_SmartRefreshFoot;
import com.ddtkj.commonmodule.CustomView.Common_SmartRefreshHeader;
import com.ddtkj.commonmodule.HttpRequest.Common_HostPath;
import com.ddtkj.commonmodule.HttpRequest.Interceptor.Common_LoggerInterceptor;
import com.ddtkj.commonmodule.HttpRequest.Interceptor.Common_RequestEncapsulationInterceptor;
import com.ddtkj.commonmodule.MVP.Model.Bean.ResponseBean.Common_UserInfoBean;
import com.ddtkj.commonmodule.Public.Common_SharedPreferences_Key;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.utlis.lib.FileUtils;
import com.utlis.lib.L;
import com.utlis.lib.SharePre;
import com.utlis.lib.ToolsUtils;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.Api.BaseApi;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.RxRetrofitApp;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.cookie.CacheInterceptor;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.https.HttpsUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


/**
 * 公告module的Application
 * Created by ${杨重诚} on 2017/5/31.
 */

public class Common_Application {
    public static Application mApplication;
    public SharePre userSharePre;//用户信息SharePre
    public SharePre userOldSharePre;//用户信息SharePre
    private SharePre globalSharePre;//全局SharePre
    private SharePre sdCardSharePre;//本地存储信息SharePre
    // 用户信息
    private Common_UserInfoBean useInfoVo = null;
    /*单利对象*/
    private volatile static Retrofit myRetrofit;
    //阿里HttpDns
    public static HttpDnsService httpdns;
    //存放dns和域名对应关系的map
    //public static Map<String, String> httpdnsHosMap;//key:ip+项目名，截取前4个/，value 域名
    //缓存域名对应Dns解析IP
    public static Map<String, HashMap<String,Long>> httpDnsTimeMap;//String：域名 HashMap String：Dns解析IP Long：时间戳

    private boolean mainAppIsStart = false;//主App是否启动

    private MediaPlayer mediaPlayer;
    String mediaPlayerUrl="http://cuiniu.ycnxsm.com/caihong.mp3";

    public static Common_Application initCommonApplication(Application application) {
        mApplication = application;
        mCommonApplication = getInstance();
        mCommonApplication.getUserSharedPreferences();
        //httpdnsHosMap = new HashMap<>();
        httpDnsTimeMap=new HashMap<>();
        initRouter();
        return mCommonApplication;
    }
    //全部初始化SmartRefresh刷新动画static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                return new Common_SmartRefreshHeader(context);
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new Common_SmartRefreshFoot(context);
            }
        });
        //SVG的向下兼容配置
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    static Common_Application mCommonApplication;

    public static Common_Application getInstance() {
        if (mCommonApplication == null) {
            //第一次check，避免不必要的同步
            synchronized (Common_Application.class) {//同步
                if (mCommonApplication == null) {
                    //第二次check，保证线程安全
                    mCommonApplication = new Common_Application();
                }
            }
        }
        return mCommonApplication;
    }

    public static Application getApplication() {
        return mApplication;
    }

    /**
     * 获取用户SharePre
     *
     * @return
     */
    public SharePre getUserSharedPreferences() {
        if (userSharePre == null) {
            userSharePre = new SharePre(mApplication,
                    Common_SharedPreferences_Key.APP_User_SharedPreferences, mApplication.MODE_APPEND);
        }
        return userSharePre;
    }

    /**
     * 获取全局SharePre
     *
     * @return
     */
    public SharePre getGlobalSharedPreferences() {
        if (globalSharePre == null) {
            globalSharePre = new SharePre(mApplication,
                    Common_SharedPreferences_Key.APP_Global_SharedPreferences, mApplication.MODE_APPEND);
        }
        return globalSharePre;
    }

    /**
     * 获取sdcard的SharePre
     *
     * @return
     */
    public SharePre getSdCardSharedPreferences() {
        if (sdCardSharePre == null) {
            sdCardSharePre = new SharePre(mApplication,
                    Common_SharedPreferences_Key.APP_SdCard_SharedPreferences, mApplication.MODE_APPEND);
        }
        return sdCardSharePre;
    }

    // 取得用户信息
    public Common_UserInfoBean getUseInfoVo() {
        if (useInfoVo != null) {
            return useInfoVo;
        }
        return null;
    }

    // 设置用户信息
    public void setUserInfoBean(Common_UserInfoBean useInfoVo) {
        this.useInfoVo = useInfoVo;
        // 调用JPush API设置Alias
        if (useInfoVo != null && useInfoVo.getUserId() != null && !useInfoVo.getUserId().equals("")) {
            mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, useInfoVo.getUserId()));
        } else {
            mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, ""));
        }
    }

    /**
     * 设置主App是否启动
     */
    public void setMainAppIsStart() {
        boolean mainAppIsStart = getMainAppIsStart();
        if (!mainAppIsStart) {
            this.mainAppIsStart = true;
        }
    }

    /**
     * 获取主App是否启动
     *
     * @return
     */
    public boolean getMainAppIsStart() {
        return mainAppIsStart;
    }

    /**
     * 设置HttpDnsService
     *
     * @param httpdns
     */
    public HttpDnsService setHttpDnsService(HttpDnsService httpdns) {
        this.httpdns = httpdns;
        return this.httpdns;
    }

    /**
     * 设置HttpDnsService
     */
    public HttpDnsService getHttpDnsService() {
        return httpdns;
    }

    /**
     * 获取单例Retrofit
     *
     * @return
     */
    public static Retrofit getRetrofit(BaseApi basePar) {
        if (myRetrofit == null) {
            synchronized (mCommonApplication.buildRetrofit(basePar)) {
                if (myRetrofit == null) {
                    mCommonApplication.buildRetrofit(basePar);
                }
            }
        }
        return myRetrofit;
    }

    /**
     * 初始化Retrofit
     *
     * @param basePar
     * @return
     */
    private Retrofit buildRetrofit(BaseApi basePar) {
        RxRetrofitApp.init(mApplication);
        // 缓存 http://www.jianshu.com/p/93153b34310e
        File cacheFile = new File(FileUtils.getAppCacheDir(mApplication), "/NetCache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
        //==============cookie缓存配置================================
        //ClearableCookieJar cookieJar1 = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(Second_Application.getInstance().getApplicationContext()));
        //CookieJarImpl cookieJar = new CookieJarImpl(new PersistentCookieStore(getApplicationContext()));

        //===================https 设置可访问所有的https网站=============================
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
        //手动创建一个OkHttpClient并设置超时时间缓存等设置
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(basePar.getConnectionTime(), TimeUnit.SECONDS)//请求超时时间
                .readTimeout(basePar.getReadTimeout(), TimeUnit.SECONDS)//读取超时时间
                .writeTimeout(basePar.getWriteTimeout(), TimeUnit.SECONDS)//写入超时时间
                .retryOnConnectionFailure(true)//超时错误重连
                .cache(cache)//缓存设置
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                /*.cookieJar(new CookiesManager(mApp,Common_HttpPath.HOSTS))*///cookie自动管理
                .addInterceptor(new Common_RequestEncapsulationInterceptor())//请求封装等过滤器
                .addInterceptor(new Common_LoggerInterceptor("TAG", true))//log过滤器
                .addInterceptor(new CacheInterceptor())//get请求缓存拦截器
                .followRedirects(false)  //禁制OkHttp的重定向操作，我们自己处理重定向
                .followSslRedirects(false);

        /*创建retrofit对象*/
        myRetrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(Common_HostPath.HTTP_HOST_PROJECT)
                .build();
        return myRetrofit;
    }

    /**
     * 初始化路由框架
     */
    private static void initRouter() {
        // 初始化——（注：as3.0初始化方式）
        Router.initialize(new Configuration.Builder()
               // 调试模式，开启后会打印log
               .setDebuggable(BuildConfig.DEBUG)
                // 模块名，每个使用Router的module都要在这里注册
              .registerModules("app", "GrabRedEnvelopeModule", "CommonModule", "UserInfoModule")
               .build());

        //=============（注：as2.3初始化方式）==================
       /* Router.initialize(mApplication);
        // 开启log
        if (BuildConfig.DEBUG) {
            // 开启路由框架log
            Router.setDebuggable(true);
        }*/
    }
    //--------------------------------------极光推送-----------------------------------------------------
    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    L.i("极光推送", logs);
                    break;

                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    L.i("极光推送", logs);
                    if (ToolsUtils.isConnected(mApplication)) {
                        mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    }
                    break;

                default:
                    logs = "Failed with errorCode = " + code;
                    L.i("极光推送", logs);
            }

        }

    };

    private static final int MSG_SET_ALIAS = 1001;
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    L.e("极光推送", "Set alias in handler.");
                    JPushInterface.setAliasAndTags(mApplication, (String) msg.obj, null, mAliasCallback);
                    break;
                default:
                    L.e("极光推送", "Unhandled msg - " + msg.what);
            }
        }
    };
}
