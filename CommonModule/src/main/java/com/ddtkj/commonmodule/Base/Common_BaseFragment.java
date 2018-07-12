package com.ddtkj.commonmodule.Base;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ddtkj.commonmodule.MVP.View.Implement.Activity.Common_Act_NetworkError_Implement;
import com.ddtkj.commonmodule.Public.Common_RouterUrl;
import com.ddtkj.commonmodule.R;
import com.ddtkj.commonmodule.Util.IntentUtil;
import com.utlis.lib.NetworkUtils;
import com.utlis.lib.ToastUtils;
import com.utlis.lib.ViewUtils;
import com.utlis.lib.WindowUtils;

public abstract class Common_BaseFragment extends Fragment implements
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

    protected View public_view;
    protected IntentUtil intentTool;
    protected Context context;
    protected int contentView = -555;
    protected boolean isShowSystemBarTintManager = true;//是否显示沉浸式状态栏
    protected int systemBarTintManagerColor = R.color.act_systemBarColor_whiteToolBar;//沉浸式状态栏背景色
    protected LayoutInflater inflater;
    protected Bundle mBundle;

    @CallSuper
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (v.getId() == R.id.tvLeftTitle) {
            FinishA();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (public_view == null) {
            this.inflater = inflater;
            public_view = setContentView();
            initActionbarView();
            initMyView();
            context = public_view.getContext();
            initApplication();
            intentTool = new IntentUtil();
            mBundle = getArguments();
            getBundleValues(mBundle);//获取页面传值
            setTitleBar();
            init();
            setListeners();
        }

        // 缓存的rootView需要判断是否已经被加过parent，
        // 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) public_view.getParent();
        if (parent != null) {
            parent.removeView(public_view);
        }
        if (contentView != R.layout.common_act_network_error_layout) {
            checkNetwork();//检查网络是否正常
        }
        return public_view;
    }

    public void initActionbarView() {
        //标题栏父布局
        lyToolbarParent = (LinearLayout) public_view.findViewById(R.id.lyToolbarParent);
        //Toolbar布局
        simpleToolbar = (Toolbar) public_view.findViewById(R.id.simpleToolbar);
        //标题栏左边返回控件
        tvLeftTitle = (TextView) public_view.findViewById(R.id.tvLeftTitle);
        //标题栏居中控件
        tvMainTitle = (TextView) public_view.findViewById(R.id.tvMainTitle);
        //标题栏右边图标文字空间
        tvRightTitleRight = (TextView) public_view.findViewById(R.id.tvRightTitleRight);
        tvRightTitleMiddle = (TextView) public_view.findViewById(R.id.tvRightTitleMiddle);
        tvRightTitleLeft = (TextView) public_view.findViewById(R.id.tvRightTitleLeft);
        //分割线
        viewLine = (View) public_view.findViewById(R.id.viewLine);
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
    protected abstract View setContentView();

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
        tvLeftTitle.setCompoundDrawables(ViewUtils.getDrawableSvg(context,R.drawable.drawable_svg_icon_arror_black_left), null, null, null);
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
    public void onResume() {
        super.onResume();
        if(context == null){
            context = getContext();
        }
        // --------------------------设置沉浸式状态栏----------------------------------
        if (isShowSystemBarTintManager) {
            WindowUtils.setSystemBarTintManager(context, getResources().getColor(systemBarTintManagerColor));
        }
        //设置状态栏的字体颜色
//        StatusBarUtil.StatusBarLightMode((Activity) context, StatusBarUtil.StatusBarLightMode((Activity) context));
    }

    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    public IntentUtil getIntentTool() {
        return intentTool;
    }

    /**
     * 设置隐藏Actionbar
     */
    public void setActionbarGone() {
        //设置Actionbar的高度
        lyToolbarParent.setVisibility(View.GONE);
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

    /**
     * finish当前Activity
     */
    public void FinishA() {
        getActivity().finish();
        getActivity().overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_right);
    }
}
