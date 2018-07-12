package com.ddtkj.commonmodule.Base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ddtkj.commonmodule.HttpRequest.Common_CustomRequestResponseBeanDataManager;
import com.ddtkj.commonmodule.MVP.Presenter.Implement.Project.Common_ProjectUtil_Implement;
import com.ddtkj.commonmodule.MVP.View.Implement.Activity.Common_Act_NetworkError_Implement;
import com.ddtkj.commonmodule.Public.Common_RouterUrl;
import com.ddtkj.commonmodule.R;
import com.ddtkj.commonmodule.Util.IntentUtil;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.umeng.socialize.UMShareAPI;
import com.utlis.lib.NetworkUtils;
import com.utlis.lib.ToastUtils;
import com.utlis.lib.WindowUtils;

public abstract class Common_BaseActivity extends RxAppCompatActivity implements
        OnClickListener {
    //标题栏父布局
    public LinearLayout lyToolbarParent;
    //Toolbar布局
    public Toolbar simpleToolbar;
    //标题栏左边返回控件
    public TextView tvLeftTitle;
    //标题栏居中控件
    public TextView tvMainTitle;
    //标题栏右边图标文字空间
    public TextView tvRightTitleRight;
    public TextView tvRightTitleMiddle;
    public TextView tvRightTitleLeft;
    //分割线
    public View viewLine;

    protected Bundle mBundle;
    protected IntentUtil intentTool;
    protected Context context;
    protected int contentView = -555;
    protected boolean isShowSystemBarTintManager = true;//是否显示沉浸式状态栏
    protected int systemBarTintManagerColor = R.color.act_systemBarColor_whiteToolBar;//沉浸式状态栏背景色

    @CallSuper
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (v.getId() == R.id.tvLeftTitle) {
            FinishA();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initApplication();
        context = this;//获取上下文
        intentTool = new IntentUtil();//获取intentTool
        mBundle = getIntent().getExtras();
        getBundleValues(mBundle);//获取页面传值
        setContentView();//设置布局
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//强制竖屏
        initActionbarView();
        initMyView();
        setTitleBar();//设置title
        init();//初始化数据
        setListeners();//设置监听事件
        getData();
        if (contentView != R.layout.common_act_network_error_layout) {
            checkNetwork();//检查网络是否正常
        }
    }

    public void initActionbarView() {
        //标题栏父布局
        lyToolbarParent = (LinearLayout) findViewById(R.id.lyToolbarParent);
        //Toolbar布局
        simpleToolbar = (Toolbar) findViewById(R.id.simpleToolbar);
        //标题栏左边返回控件
        tvLeftTitle = (TextView) findViewById(R.id.tvLeftTitle);
        //标题栏居中控件
        tvMainTitle = (TextView) findViewById(R.id.tvMainTitle);
        //标题栏右边图标文字空间
        tvRightTitleRight = (TextView) findViewById(R.id.tvRightTitleRight);
        tvRightTitleMiddle = (TextView) findViewById(R.id.tvRightTitleMiddle);
        tvRightTitleLeft = (TextView) findViewById(R.id.tvRightTitleLeft);
        //分割线
        viewLine = (View) findViewById(R.id.viewLine);
    }

    /**
     * 初始化组件
     */
    protected abstract void initMyView();

    /**
     * 初始化Application
     */
    protected abstract void initApplication();

    /**
     * 初始化布局
     */
    protected abstract void setContentView();

    /**
     * @category 初始化网络组件和数据组件以及LogUtil所需的clazz参数，设置布局
     */
    protected abstract void init();

    /**
     * @category 设置监听
     */
    protected abstract void setListeners();

    /**
     * 获取页面传值
     *
     * @param mBundle
     */
    public void getBundleValues(Bundle mBundle) {
    }

    ;

    /**
     * 设置标题栏
     *
     * @category 设置标题栏
     */
    protected abstract void setTitleBar();

    /**
     * 设置标题栏样式
     *
     * @param title        文字
     * @param bg           背景
     * @param titleColor   字体颜色
     * @param showReturn   是否显示返回按钮
     * @param showLineView 是否显示分割线
     */
    public void setActionbarBar(String title, int bg, int titleColor, boolean showReturn, boolean showLineView) {
        // 设置Toolbar背景
        simpleToolbar.setBackgroundResource(bg);
        //设置返回按钮
        VectorDrawableCompat vectorDrawableCompat = VectorDrawableCompat.create(context.getResources(), R.drawable.drawable_svg_icon_arror_black_left, context.getTheme());
        vectorDrawableCompat.setBounds(0, 0, vectorDrawableCompat.getMinimumWidth(), vectorDrawableCompat.getMinimumHeight());
        vectorDrawableCompat.setTint(context.getResources().getColor(R.color.white));
        tvLeftTitle.setCompoundDrawables(vectorDrawableCompat, null, null, null);
        //设置标题
        tvMainTitle.setText(title);
        tvMainTitle.setTextColor(context.getResources().getColor(titleColor));
        //显示返回按钮
        if (showReturn)
            tvLeftTitle.setVisibility(View.VISIBLE);
        tvLeftTitle.setOnClickListener(this);
        //显示分割线
        if (showLineView)
            viewLine.setVisibility(View.VISIBLE);
    }

    //设置title左边图标
    public void settvLeftTitleDrawable(int res) {
        Drawable dwRight = ContextCompat.getDrawable(context, res);
        dwRight.setBounds(0, 0, dwRight.getMinimumWidth(), dwRight.getMinimumHeight());
        tvLeftTitle.setCompoundDrawables(null, null, dwRight, null);
    }

    /**
     * 设置title右边图标
     *
     * @param tv
     * @param res
     */
    public void settvTitleDrawable(TextView tv, int res) {
        Drawable dwRight = ContextCompat.getDrawable(context, res);
        dwRight.setBounds(0, 0, dwRight.getMinimumWidth(), dwRight.getMinimumHeight());
        tv.setCompoundDrawables(null, null, dwRight, null);
        tv.setVisibility(View.VISIBLE);
    }

    /**
     * 设置title右边文字
     *
     * @param tv
     * @param str
     * @param color
     */
    public void settvTitleStr(TextView tv, String str, int color) {
        tv.setText(str);
        tv.setTextColor(getResources().getColor(color));
        tv.setVisibility(View.VISIBLE);
    }

    /**
     * 设置底部导航栏颜色
     *
     * @param res
     */
    public void setNavigationBarColor(int res) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(res));
        }
    }

    /**
     * @category 获取网络数据
     */
    protected abstract void getData();

    public void onResume() {
        super.onResume();
        //如果内存被GC的情况下，通过deeplink重新唤起应用
        if(Common_Application.getApplication()==null||Common_Application.getInstance()==null||context==null){
            Uri uri=Uri.parse("redenvelope://com.ddt.redenvelope");
            Intent intent=new Intent(Intent.ACTION_VIEW,uri);
            startActivity(intent);
            return;
        }
        // --------------------------设置沉浸式状态栏----------------------------------
        if (isShowSystemBarTintManager) {
            WindowUtils.setSystemBarTintManager(context, getResources().getColor(systemBarTintManagerColor));
        }
        //设置状态栏的字体颜色
//        StatusBarUtil.StatusBarLightMode(this, StatusBarUtil.StatusBarLightMode(this));
    }

    protected abstract void onMyPause();

    protected void onPause() {
        super.onPause();
        onMyPause();
    }

    public IntentUtil getIntentTool() {
        return intentTool;
    }

    /**
     * 返回键的监听
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            // 退出
            finish();
            overridePendingTransition(R.anim.anim_slide_in_left,
                    R.anim.anim_slide_out_right);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 拦截500ms内重复点击问题
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (isFastDoubleClick()) {
                return true;
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    long lastClickTime = System.currentTimeMillis();

    public boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (timeD >= 0 && timeD <= 500) {
            return true;
        } else {
            lastClickTime = time;
            return false;
        }
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        //UMShareAPI.get(this).release();//在使用分享或者授权的Activity中，重写onDestory()方法：内存泄漏
        if (null != Common_CustomRequestResponseBeanDataManager.commonCustomRequestResponseBeanDataManager)
            Common_CustomRequestResponseBeanDataManager.commonCustomRequestResponseBeanDataManager.onDestroy();
        if (null != Common_ProjectUtil_Implement.commonProjectUtilInterface)
            Common_ProjectUtil_Implement.commonProjectUtilInterface.onDestroy();
    }

    /**
     * finish当前Activity
     */
    public void FinishA() {
        finish();
        overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_right);
    }

    /**
     * 设置隐藏Actionbar
     */
    public void setActionbarGone() {
        //设置Actionbar的高度
        lyToolbarParent.setVisibility(View.GONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /** attention to this below ,must add this**/
        try {
            UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 检查网络是否正常
     */
    private void checkNetwork() {
        NetworkUtils.NetworkStatus(context, new NetworkUtils.NetworkListener() {
            @Override
            public void onResult(boolean Status, String msg, NetworkUtils.NoNetworkType noNetworkType) {
                if (!Status) {
                    ToastUtils.ErrorImageToast(context, msg);
                    //跳转到网络异常页面
                    Bundle bundle = new Bundle();
                    if (noNetworkType.equals(NetworkUtils.NoNetworkType.NO_NETWORK)) {
                        //无网络
                        bundle.putInt("errorType", Common_Act_NetworkError_Implement.NO_NETWORK);
                    } else if (noNetworkType.equals(NetworkUtils.NoNetworkType.NO_PING)) {
                        //不能访问外网
                        bundle.putInt("errorType", Common_Act_NetworkError_Implement.NO_PING);
                    }
                    getIntentTool().intent_no_animation_RouterTo(context, Common_RouterUrl.MAIN_NetworkErrorRouterUrl, bundle);
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        try {
            UMShareAPI.get(this).onSaveInstanceState(outState);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
