package com.ddtkj.commonmodule.MVP.View.Implement.Activity;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.chenenyu.router.annotation.Route;
import com.customview.lib.DragImageView;
import com.ddtkj.commonmodule.Base.Common_View_BaseActivity;
import com.ddtkj.commonmodule.Lintener.Common_Fra_BaseAgentWebLoadFinishListener;
import com.ddtkj.commonmodule.Lintener.Common_FragmentKeyDownListener;
import com.ddtkj.commonmodule.Lintener.Common_WXShareLintener;
import com.ddtkj.commonmodule.MVP.Contract.Activity.Common_Act_WebView_Contract;
import com.ddtkj.commonmodule.MVP.Model.Bean.EventBusBean.Common_LoginSuccess_EventBus;
import com.ddtkj.commonmodule.MVP.Model.Bean.EventBusBean.Common_SyncCookie_EventBus;
import com.ddtkj.commonmodule.MVP.Presenter.Implement.Activity.Common_Act_WebView_Presenter;
import com.ddtkj.commonmodule.MVP.Presenter.Implement.Project.Common_ProjectUtil_Implement;
import com.ddtkj.commonmodule.MVP.Presenter.Implement.Project.Common_WebView_Presenter_Javascript_Implement;
import com.ddtkj.commonmodule.MVP.Presenter.Interface.Project.Common_ProjectUtil_Interface;
import com.ddtkj.commonmodule.MVP.Presenter.Interface.Project.Common_WebView_Presenter_Javascript_Interface;
import com.ddtkj.commonmodule.MVP.View.Implement.Fragment.Common_Fra_BaseAgentWeb;
import com.ddtkj.commonmodule.Public.Common_RouterUrl;
import com.ddtkj.commonmodule.R;
import com.just.agentweb.AgentWeb;
import com.umeng.socialize.media.UMImage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import mvpdemo.com.unmeng_share_librarys.UmengShare;
import mvpdemo.com.unmeng_share_librarys.UmengShareBean;


/**
 * Created by cenxiaozhong on 2017/5/23.
 */
@Route(Common_RouterUrl.MAIN_WebViewRouterUrl)
public class Common_Act_BaseAgentWebX5_View extends Common_View_BaseActivity<Common_Act_WebView_Contract.Presenter, Common_Act_WebView_Presenter> implements Common_Act_WebView_Contract.View {
    // 可缩放的图片
    private DragImageView cusDragImageView;
    //可缩放的图片父布局
    private RelativeLayout reyDragImageViewParent;
    //可缩放的图片关闭按钮
    private ImageView imgDragImageClose;

    Message mMessage;
    Handler webViewHandle;
    private String url;//页面链接
    private String bar_name;//标题
    private String shareLink;// 分享链接
    private String shareImage;//分享图片
    private String shareTitle;// 分享标题
    private String shareContent;// 分享内容
    private String marker = "";// 用于标记一些特殊的类别，比如“赚“活动，需要做特殊处理

    Common_ProjectUtil_Interface mProjectUtil_presenter_implement;
    Common_WebView_Presenter_Javascript_Interface mMainWebView_presenter_javascript_interface;

    private FrameLayout mFrameLayout;
    private FragmentManager mFragmentManager;
    Common_Fra_BaseAgentWeb mCommonBaseAgentWebX5Fragment;
    private AgentWeb mAgentWeb;

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.tvRightTitleRight) {
            //调起分享页面
            UMImage image;
            if (shareImage == null || shareImage.isEmpty()) {
                image = new UMImage(context, R.mipmap.icon_launcher);
            } else {
                image = new UMImage(context, shareImage);
            }
            new UmengShare().openShare(context, shareTitle, shareContent, shareLink, image, new Common_WXShareLintener());
        } else if (v.getId() == R.id.imgDragImageClose) {
            //手势缩放图片关闭监听
            reyDragImageViewParent.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initMyView() {
        mFrameLayout = findViewById(R.id.framWeb);
        // 可缩放的图片
        cusDragImageView = findViewById(R.id.cusDragImageView);
        //可缩放的图片父布局
        reyDragImageViewParent = findViewById(R.id.reyDragImageViewParent);
        //可缩放的图片关闭按钮
        imgDragImageClose = findViewById(R.id.imgDragImageClose);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.common_act_base_agentweb);
    }

    @Override
    protected void init() {
        //网页中的视频，上屏幕的时候，可能出现闪烁的情况，需要如下设置：Activity在onCreate时需要设置:
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        //设置软键盘弹起方式
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getWindow().setBackgroundDrawableResource(R.color.white);//设置窗体背景色

        mProjectUtil_presenter_implement = new Common_ProjectUtil_Implement();
        mMainWebView_presenter_javascript_interface = new Common_WebView_Presenter_Javascript_Implement(context, this);
        initHandler();//初始化Handler

        mFragmentManager = this.getSupportFragmentManager();
        openFragment(new Common_Fra_BaseAgentWebLoadFinishListener() {
            @Override
            public void onLoadFinishListener(AgentWeb mAgentWebs) {
                mAgentWeb = mAgentWebs;
                //设置js调用本地方法
                if (mAgentWeb != null) {
                    mAgentWeb.getJsInterfaceHolder().addJavaObject("android", mMainWebView_presenter_javascript_interface);
                }
            }
        });
    }

    @Override
    protected void setListeners() {
        //分享按钮的监听
        tvRightTitleRight.setOnClickListener(this);
        //手势缩放图片监听
        cusDragImageView.setOnTouchUpListener(new DragImageView.TouchUpListener() {
            @Override
            public void doAfterTouchUp() {
                // TODO Auto-generated method stub
                reyDragImageViewParent.invalidate();
            }
        });
        //手势缩放图片关闭监听
        imgDragImageClose.setOnClickListener(this);
        //返回按钮监听
        tvLeftTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* if (!mAgentWebX5.back())*/
                FinishA();
            }
        });
    }

    @Override
    protected void setTitleBar() {
        //设置Actionbar
        setActionbarBar("", R.color.white, R.color.account_text_gray, true, true);
        settvTitleStr(tvLeftTitle, "返回", R.color.account_text_gray);
        //设置分享按钮
        VectorDrawableCompat vectorDrawableCompat = VectorDrawableCompat.create(getResources(), R.drawable.drawable_svg_icon_share, context.getTheme());
        vectorDrawableCompat.setBounds(0, 0, vectorDrawableCompat.getMinimumWidth(), vectorDrawableCompat.getMinimumHeight());
        tvRightTitleRight.setCompoundDrawables(null, null, vectorDrawableCompat, null);
    }

    @Override
    protected void getData() {

    }

    private void openFragment(Common_Fra_BaseAgentWebLoadFinishListener mWebLoadFinishListeners) {
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.add(R.id.framWeb, mCommonBaseAgentWebX5Fragment = Common_Fra_BaseAgentWeb.getInstance(mBundle, tvMainTitle, mWebLoadFinishListeners), Common_Fra_BaseAgentWeb.class.getName());
        ft.commit();
    }

    /**
     * 初始化Handler
     */
    private void initHandler() {
        webViewHandle = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                this.obtainMessage();
                switch (msg.what) {
                    case 0x1001:
                        tvRightTitleRight.setVisibility(View.VISIBLE);
                        break;
                    case 0x1002:
                        tvRightTitleRight.setVisibility(View.GONE);
                        break;
                    case 0x1003:
                        //根据不同渠道调起分享面板，只有图片
                        UmengShareBean openShareImage = (UmengShareBean) msg.obj;
                        new UmengShare().openShare(context, openShareImage.getShareTitle(), openShareImage.getShareContent(), openShareImage.getShareLink(), openShareImage.getShareImg(), new Common_WXShareLintener());
                        break;
                }
            }
        };
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
     * webview重新加载
     */
    @Override
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

    /**
     * webview加载
     *
     * @return
     */
    @Override
    public void webViewLoadUrl(final String url) {
        if (url == null || url.isEmpty())
            return;
        reload();
    }

    /**
     * 友盟分享调用接口
     *
     * @param shareParameterBean
     */
    @Override
    public void openShare(UmengShareBean shareParameterBean) {
        mMessage = new Message();
        mMessage.what = 0x1003;
        mMessage.obj = shareParameterBean;
        webViewHandle.sendMessage(mMessage);
    }

    /**
     * 分享
     *
     * @param shareJsonStr
     */
    @Override
    public void setShareData(String shareJsonStr) {
        JSONObject json;
        try {
            json = JSONObject.parseObject(shareJsonStr);
            shareLink = json.getString("link");
            shareTitle = json.getString("title");
            shareContent = json.getString("content");
            shareImage = json.getString("iconUrl");
            if ((null == shareLink || shareLink.equals("")) && (null == shareTitle || shareTitle.equals(""))
                    && (null == shareContent || shareContent.equals(""))) {
                hideAcbarBtnShare();//隐藏右侧按钮
            } else {
                showAcbarBtnShare();//显示分享按钮
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
        }
    }

    /**
     * 显示分享按钮
     */
    @Override
    public void showAcbarBtnShare() {
        mMessage = new Message();
        mMessage.what = 0x1001;
        webViewHandle.sendMessage(mMessage);
    }

    /**
     * 隐藏分享按钮
     */
    @Override
    public void hideAcbarBtnShare() {
        mMessage = new Message();
        mMessage.what = 0x1002;
        webViewHandle.sendMessage(mMessage);
    }

    /**
     * 获取JS接口对象
     *
     * @return
     */
    @Override
    public Common_WebView_Presenter_Javascript_Interface getJavascriptInterface() {
        return mMainWebView_presenter_javascript_interface;
    }

    /**
     * 获取Handler
     *
     * @return
     */
    @Override
    public Handler getHandler() {
        return webViewHandle;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCommonBaseAgentWebX5Fragment.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Common_Fra_BaseAgentWeb mAgentWebX5Fragment = this.mCommonBaseAgentWebX5Fragment;
        if (mAgentWebX5Fragment != null) {
            Common_FragmentKeyDownListener mFragmentKeyDown = (Common_FragmentKeyDownListener) mAgentWebX5Fragment;
            if (mFragmentKeyDown.onFragmentKeyDown(keyCode, event))
                return true;
            else
                return super.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        mAgentWeb.getWebLifeCycle().onDestroy();
        webViewHandle.removeCallbacksAndMessages(null);
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void onResume() {
        isShowSystemBarTintManager = true;//不显示沉浸式状态栏
        mAgentWeb.getWebLifeCycle().onResume();//恢复
        super.onResume();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onPause() {
        mAgentWeb.getWebLifeCycle().onPause(); //暂停应用内所有WebView ， 调用mWebView.resumeTimers();/mAgentWeb.getWebLifeCycle().onResume(); 恢复。
        super.onPause();
    }
    //----------------------------------------------EventBus接收消息处理-----------------------------------------------------------

    /**
     * Cookie成功执行的操作
     *
     * @param eventBus
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void syncCookieSuccess(Common_SyncCookie_EventBus eventBus) {
        if (eventBus.isSyncCookie()) {
            //reload();
        }
    }

    /**
     * 登录成功执行的操作
     *
     * @param eventBus
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void longinSuccess(Common_LoginSuccess_EventBus eventBus) {
        if (eventBus.isLoginSuccess() && !eventBus.isReceive())
            reload();//刷新webview
    }
}
