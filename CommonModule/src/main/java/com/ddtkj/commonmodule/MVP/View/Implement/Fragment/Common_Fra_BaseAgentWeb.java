package com.ddtkj.commonmodule.MVP.View.Implement.Fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.ddtkj.commonmodule.Base.Common_Application;
import com.ddtkj.commonmodule.Base.Common_View_BaseNoToolbarFragment;
import com.ddtkj.commonmodule.Lintener.Common_Fra_BaseAgentWebLoadFinishListener;
import com.ddtkj.commonmodule.Lintener.Common_FragmentKeyDownListener;
import com.ddtkj.commonmodule.MVP.Contract.Activity.Common_Act_WebView_Contract;
import com.ddtkj.commonmodule.MVP.Model.Bean.ResponseBean.Common_CookieMapBean;
import com.ddtkj.commonmodule.MVP.Presenter.Implement.Activity.Common_Act_WebView_Presenter;
import com.ddtkj.commonmodule.MVP.Presenter.Implement.Project.Common_ProjectUtil_Implement;
import com.ddtkj.commonmodule.MVP.Presenter.Interface.Project.Common_ProjectUtil_Interface;
import com.ddtkj.commonmodule.Public.Common_RouterUrl;
import com.ddtkj.commonmodule.Public.Common_SharedPreferences_Key;
import com.ddtkj.commonmodule.R;
import com.ddtkj.commonmodule.Util.Common_SharePer_UserInfo;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.AgentWebConfig;
import com.utlis.lib.AgentWebViewUtils;
import com.utlis.lib.L;
import com.utlis.lib.SharePre;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;


/**
 * AgentWeb浏览器Fragment
 *
 * @Author: 杨重诚
 * @CreatTime: 2018/3/30  16:34
 */
@SuppressLint("ValidFragment")
public class Common_Fra_BaseAgentWeb extends Common_View_BaseNoToolbarFragment<Common_Act_WebView_Contract.Presenter, Common_Act_WebView_Presenter> implements Common_FragmentKeyDownListener {
    private String url;//页面链接
    private String bar_name;//标题
    private String shareLink;// 分享链接
    private String shareImage;//分享图片
    private String shareTitle;// 分享标题
    private String shareContent;// 分享内容
    private String marker = "";// 用于标记一些特殊的类别，比如“赚“活动，需要做特殊处理
    public static TextView tvMainTitle=null;
    Common_ProjectUtil_Interface mProjectUtil_presenter_implement;
    protected AgentWeb mAgentWeb;
    public static Common_Fra_BaseAgentWebLoadFinishListener mWebLoadFinishListener=null;

    public static Common_Fra_BaseAgentWeb getInstance(Bundle bundle, TextView titles, Common_Fra_BaseAgentWebLoadFinishListener mWebLoadFinishListeners) {
        Common_Fra_BaseAgentWeb mAgentWebX5Fragment = new Common_Fra_BaseAgentWeb();
        if (bundle != null)
            mAgentWebX5Fragment.setArguments(bundle);
        if (titles != null) {
            tvMainTitle = titles;
        }
        if (mWebLoadFinishListeners != null)
            mWebLoadFinishListener = mWebLoadFinishListeners;
        return mAgentWebX5Fragment;
    }
    @Override
    protected void initMyView() {
    }

    @Override
    protected View setContentView() {
        return inflater.inflate(R.layout.common_fragment_base_agentweb_layout, null);
    }

    @Override
    protected void init() {
        mProjectUtil_presenter_implement = new Common_ProjectUtil_Implement();
        initWebView();
    }

    /**
     * 初始化WebView
     */
    protected void initWebView() {
        L.e("======初始化url======",url);
        //初始化AgentWebX5设置
        final AgentWeb.PreAgentWeb preAgentWeb = AgentWebViewUtils.initWebView(context, public_view, getResources().getColor(R.color.app_color), mWebViewClient, mWebChromeClient);
        //将cookie同步到WebView
        mProjectUtil_presenter_implement.syncCookie(context, url, new Common_ProjectUtil_Implement.SyncCookieListener() {
            @Override
            public void onResult(boolean isSucc) {
                if (isSucc) {
                    L.e("=====将cookie同步到WebView====", url);
                    mAgentWeb = preAgentWeb.go(url);
                }
            }
        });
        //设置 WebSettings。
        AgentWebViewUtils.setWebViewSetting(context, mAgentWeb.getWebCreator().getWebView());
        if (mWebLoadFinishListener != null)
            mWebLoadFinishListener.onLoadFinishListener(mAgentWeb);
    }

    @Override
    protected void setListeners() {
    }

    /**
     * 获取页面传值
     */
    @Override
    public void getBundleValues(Bundle mBundle) {
        super.getBundleValues(mBundle);
        bar_name = mBundle.getString("barname", "");
        url = mBundle.getString("url", "");
        shareLink = mBundle.getString("shareLink", "");
        shareTitle = mBundle.getString("shareTitle", "");
        shareContent = mBundle.getString("shareContent", "");
        marker = mBundle.getString("marker", "");
    }


    /**
     * 自定义WebViewClient
     *
     * @author Administrator
     */
    protected WebViewClient mWebViewClient = new WebViewClient() {
        // 是否在本WebView中跳转还是通过系统浏览器跳转，true为webview内跳转
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            L.e("=====shouldOverrideUrlLoading====", url);
            // TODO Auto-generated method stub
            if (url != null && !url.equals("")) {
                //判断是不是需要重定向到登陆页面
                if (url.trim().equals("ddt://ation/login")) {
                    upLoginOnAndroid();
                } else if (url.startsWith("tel:")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse(url));
                    startActivity(intent);
                    return true;
                } else {
                    //302到授权中心
                    if (url.startsWith("http://passport.ddtkj.net") || url.startsWith("http://account.dadetongkeji.net.cn")
                            || url.startsWith("http://authorize.jsd.dadetongkeji.net.cn")) {
                        try {
                            URL parsedUrl = new URL(url);
                            SharePre sharePre = new SharePre(context, Common_SharedPreferences_Key.COOKIE_CACHE, Context.MODE_PRIVATE);
                            //获取cookie缓存对象
                            Common_CookieMapBean cookieMapBean = Common_SharePer_UserInfo.sharePre_GetCookieCache();
                            if (null != cookieMapBean) {
                                HashSet<String> hosts_cookie = cookieMapBean.getCookieMap().get(parsedUrl.getHost());
                                if (null != hosts_cookie && hosts_cookie.size() > 0) {
                                    for (String cookie : hosts_cookie) {
                                        if (url.startsWith("http://passport.ddtkj.net")) {//正式服务器
                                            AgentWebConfig.syncCookie("http://passport.ddtkj.net/", cookie);
                                        } else if (url.startsWith("http://account.dadetongkeji.net.cn")) {//测试服务器
                                            AgentWebConfig.syncCookie("http://account.dadetongkeji.net.cn/", cookie);
                                        } else if (url.startsWith("http://authorize.jsd.dadetongkeji.net.cn")) {//新测试域名
                                            AgentWebConfig.syncCookie("http://authorize.jsd.dadetongkeji.net.cn", cookie);
                                        }
                                    }
                                }
                            }
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                    }
                    if (url.startsWith("http://") || url.startsWith("https://")) {
                        view.loadUrl(url);
                    }
                }
                return true;
            }
            return false;
        }

        // 页面开始加载
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        // 页面加载完成
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            L.e("=====onPageFinished==",url);
        }
        //暂时未用到，晋商银行支付在pay_library WebViewJCBAct中
     /*   @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            //super.onReceivedSslError(view, handler, error);//注意:这句一定要注释掉
            L.e("====onReceivedSslError======","====onReceivedSslError======");
            handler.proceed();//接受证书
//            try {
//                HttpsUtils.checkSSLXINLANG(handler, view.getUrl(),context.getAssets().open("jcbankPublic.cer"));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }*/
    };
    /**
     * 自定义WebChromeClient
     */
    protected WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (null == title || title.equals("")) {
                return;
            }
            if (title.length() > 10) {
                title = title.substring(0, 10) + "...";
            }
            if (tvMainTitle != null) {
                if (null != bar_name && !"".equals(bar_name)) {
                    settvTitleStr(tvMainTitle, bar_name, R.color.account_text_gray);
                    L.e("bar_name:", bar_name);
                } else {
                    settvTitleStr(tvMainTitle, title, R.color.account_text_gray);
                    L.e("t:", title);
                }
            }
        }
    };

    public void settvTitleStr(TextView tv, String str, int color) {
        tv.setText(str);
        tv.setTextColor(getResources().getColor(color));
        tv.setVisibility(View.VISIBLE);
    }

    /**
     * 清除 WebView 缓存
     */
    private void toCleanWebCache() {
        if (this.mAgentWeb != null) {
            //清理所有跟WebView相关的缓存 ，数据库， 历史记录 等。
            this.mAgentWeb.clearWebCache();
            //清空所有 AgentWeb 硬盘缓存，包括 WebView 的缓存 , AgentWeb 下载的图片 ，视频 ，apk 等文件。
            //            AgentWebConfig.clearDiskCache(this.getContext());
        }
    }

    /**
     * H5界面请求用户登录
     */
    public void upLoginOnAndroid() {
        //判断用户登录状态 ，如果没有登录，则跳转至登录页面
        if (Common_Application.getInstance().getUseInfoVo() == null) {
            getIntentTool().intent_RouterTo(context, Common_RouterUrl.USERINFO_LogingRouterUrl);
            return;
        }
        reload();//刷新webview
    }

    /**
     * webview重新加载
     */
    public void reload() {
        mProjectUtil_presenter_implement.syncCookie(context, url, new Common_ProjectUtil_Implement.SyncCookieListener() {
            @Override
            public void onResult(boolean isSucc) {
                if (isSucc) {
                    //将cookie同步到WebView
                    mAgentWeb.getUrlLoader().reload(); // 刷新
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onResume() {
        isShowSystemBarTintManager=false;
        mAgentWeb.getWebLifeCycle().onResume();//恢复
        super.onResume();
    }

    @Override
    public void onPause() {
        mAgentWeb.getWebLifeCycle().onPause(); //暂停应用内所有WebView ， 调用mWebView.resumeTimers();/mAgentWeb.getWebLifeCycle().onResume(); 恢复。
        super.onPause();
    }

    @Override
    public boolean onFragmentKeyDown(int keyCode, KeyEvent event) {
        return mAgentWeb.handleKeyEvent(keyCode, event);
    }

    @Override
    public void onDestroyView() {
        mAgentWeb.getWebLifeCycle().onDestroy();
        tvMainTitle=null;
        mWebLoadFinishListener=null;
        super.onDestroyView();
    }
}
