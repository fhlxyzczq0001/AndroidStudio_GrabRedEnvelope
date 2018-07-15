package com.ddtkj.grabRedEnvelopeModule.MVP.View.Implement.Activity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;

import com.chenenyu.router.annotation.Route;
import com.ddtkj.commonmodule.Base.Common_Application;
import com.ddtkj.commonmodule.CustomView.Common_BottomNavigationViewEx;
import com.ddtkj.commonmodule.MVP.Model.Bean.EventBusBean.Common_LoginSuccess_EventBus;
import com.ddtkj.commonmodule.MVP.Presenter.Implement.Project.Common_ProjectUtil_Implement;
import com.ddtkj.commonmodule.MVP.Presenter.Interface.Project.Common_ProjectUtil_Interface;
import com.ddtkj.commonmodule.Public.Common_Main_PublicCode;
import com.ddtkj.commonmodule.Public.Common_RouterUrl;
import com.ddtkj.commonmodule.Public.Common_SD_FilePath;
import com.ddtkj.commonmodule.Receiver.Common_JPushReceiver;
import com.ddtkj.commonmodule.Util.Common_SharePer_GlobalInfo;
import com.ddtkj.grabRedEnvelopeModule.Base.GrabRedEnvelopeModule_BaseActivity;
import com.ddtkj.grabRedEnvelopeModule.MVP.Contract.Activity.GrabRedEnvelopeModule_Act_MainActivity_Contract;
import com.ddtkj.grabRedEnvelopeModule.MVP.Presenter.Implement.Activity.GrabRedEnvelopeModule_Act_MainActivity_Presenter;
import com.ddtkj.grabRedEnvelopeModule.MVP.View.Implement.Fragment.GrabRedEnvelopeModule_Fra_Home_View;
import com.ddtkj.grabRedEnvelopeModule.MVP.View.Implement.Fragment.GrabRedEnvelopeModule_Fra_User_View;
import com.ddtkj.grabRedEnvelopeModule.R;
import com.utlis.lib.FileUtils;
import com.utlis.lib.WindowUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 借款主Activity
 */
@Route(Common_RouterUrl.GRAB_RED_ENVELOPE_MainActivityRouterUrl)
public class GrabRedEnvelopeModule_Act_MainActivity_View extends GrabRedEnvelopeModule_BaseActivity<GrabRedEnvelopeModule_Act_MainActivity_Contract.Presenter,GrabRedEnvelopeModule_Act_MainActivity_Presenter> implements GrabRedEnvelopeModule_Act_MainActivity_Contract.View {
    //底部导航
    Common_BottomNavigationViewEx navigation;

    //FragmentManager管理器
    FragmentManager mFragmentManager = null;
    //FragmentTransaction事务
    FragmentTransaction mTransaction = null;
    //当前展示的fragment
    Fragment currentFragment = null;
    //所有的fragment集合
    List<Fragment> mFragmentList;
    int menuIndex = -1;

    //存储底部tab对应的id
   int[] tabIds = {R.id.jsdloanmodule_2_TitleHome,R.id.jsdloanmodule_2_TitleUser};
    //存储底部tab对应的name
    String[] tabNames = {Common_Main_PublicCode.GRAB_RED_ENVELOPE_TAB_HOME.toString(),Common_Main_PublicCode.GRAB_RED_ENVELOPE_TAB_USER.toString()};
    //存储name和id的对应关系
    Map<String, Integer> tabMaps;

    Common_ProjectUtil_Interface mCommonProjectUtilInterface;
    @Override
    public void onClick(View v) {
        super.onClick(v);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.grabredenvelopemodule_act_main_layout);
    }

    @Override
    protected void init() {
        mFragmentManager = getSupportFragmentManager();
        initTabMaps();
        initFragment();
        menuIndex = 0;
        setNavigationItemClick(menuIndex);
        //记录主app已经启动
        Common_Application.getInstance().setMainAppIsStart();
        //初始化底部导航
        initNavigation();
        mCommonProjectUtilInterface = new Common_ProjectUtil_Implement();
        mPresenter.initP();
        //检测更新
        //mCommonProjectUtilInterface.checkAppVersion(context, "main", null);
    }

    /**
     * 初始化底部tab的map存储对应关系name——id
     */
    private void initTabMaps() {
        tabMaps = new HashMap<>();
        for (int i = 0; i < tabNames.length; i++) {
            tabMaps.put(tabNames[i], tabIds[i]);
        }
    }

    @Override
    protected void initMyView() {
        navigation =  findViewById(R.id.cusNavigation);
    }
    @Override
    protected void setListeners() {

    }

    @Override
    protected void setTitleBar() {
        setActionbarGone();
    }

    @Override
    protected void getData() {
    }

    /**
     * 初始化底部导航
     */
    private void initNavigation(){
        navigation.enableAnimation(false);
        navigation.enableShiftingMode(false);
        navigation.enableItemShiftingMode(false);
        navigation.setTextSize(10);
        //        Centure_BottomNavigationViewHelper.disableShiftMode(navigation);
        int[][] states = new int[][]{
                new int[]{-android.R.attr.state_checked},
                new int[]{android.R.attr.state_checked}
        };

        int[] colors = new int[]{getResources().getColor(R.color.white),
                getResources().getColor(R.color.app_color)
        };
        //动态更换首页底部tab导航背景颜色
        if (FileUtils.isFileExist(Common_SD_FilePath.homeNavigationBjPath)) {
            Bitmap bitmap = BitmapFactory.decodeFile(Common_SD_FilePath.homeNavigationBjPath);// 读取本地图片
            BitmapDrawable drawableDefault = new BitmapDrawable(bitmap);
            navigation.setBackgroundDrawable(drawableDefault);
        }
        navigation.setBackgroundColor(getResources().getColor(R.color.app_gray));
        ColorStateList csl = new ColorStateList(states, colors);
        navigation.setItemTextColor(csl);
        navigation.setItemIconTintList(csl);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
    /**
     * 初始化导航数据
     */
    public void initFragment() {
        mFragmentList = new ArrayList<>();
        mFragmentList.add(GrabRedEnvelopeModule_Fra_Home_View.newInstance(new Bundle()));
        mFragmentList.add(GrabRedEnvelopeModule_Fra_User_View.newInstance(new Bundle()));
    }

    /**
     * 需加载的底部导航位置
     *
     * @param menuIndex
     */
    public void loadFragmentIndex(int menuIndex) {
        if (isShowSystemBarTintManager) {
            if(menuIndex == 4){
                systemBarTintManagerColor = R.color.app_color;
            }else{
                systemBarTintManagerColor = R.color.act_systemBarColor_whiteToolBar;
            }
            WindowUtils.setSystemBarTintManager(context, getResources().getColor(systemBarTintManagerColor));
        }
        //初始化FragmentTransaction
        mTransaction = mFragmentManager.beginTransaction();
        Fragment fragment = mFragmentManager.findFragmentByTag("" + menuIndex);

        if (currentFragment != null) {
            mTransaction.hide(currentFragment);
        }
        if (fragment != null) {
            mTransaction.attach(fragment);
            mTransaction.show(fragment);
            fragment.onResume();
        } else {
            fragment = mFragmentList.get(menuIndex);
            mTransaction.add(R.id.framContent, fragment, "" + menuIndex);
        }
        currentFragment = fragment;
        mTransaction.commitAllowingStateLoss();
    }

    /**
     * 底部按钮的点击处理事件
     */
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            if (item.getItemId() == tabIds[0]) {
                menuIndex = 0;
                setNavigationItemClick(menuIndex);
                return true;
            } else if (item.getItemId() == tabIds[1]) {
                menuIndex = 1;
                if(mCommonProjectUtilInterface.logingStatus()){//用户是否已登录
                    setNavigationItemClick(menuIndex);
                    return true;
                }else{
                    getIntentTool().intent_RouterTo(context, Common_RouterUrl.USERINFO_LogingRouterUrl);
                }
            }
            return false;
        }
    };

    /**
     * 设置navigage点击事件
     *
     * @param menuIndex 当前点击的索引
     */
    public void setNavigationItemClick(int menuIndex) {
        loadFragmentIndex(menuIndex);
    }

    @Override
    public void getBundleValues(Bundle mBundle) {
        super.getBundleValues(mBundle);
        setNavigationTabIndex(mBundle);
    }

    /**
     * 接收再次进入主activity的传参
     *
     * @param intent
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        this.setIntent(intent);
        if (intent.getExtras() != null) {
            setNavigationTabIndex(intent.getExtras());
        }
    }

    /**
     * 设置tab被动切换位置
     *
     * @param mBundle
     */
    private void setNavigationTabIndex(Bundle mBundle) {
        if (mBundle != null) {
            if (mBundle.getString("tab") != null) {
                String tabName = mBundle.getString("tab");
                if (tabMaps == null) {
                    for (int i = 0; i < tabNames.length; i++) {
                        if (tabName.contains(tabNames[i])) {
                            menuIndex = i;
                        }
                    }
                } else {
                    //设置跳转置顶fragment
                    navigation.setSelectedItemId(tabMaps.get(tabName));
                }
            }
        }
    }

    //按返回键退出的监听
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            mPresenter.exit();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // 注册EventBus
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        //判断用户状态和切换tab界面
         if(menuIndex==1&&!mCommonProjectUtilInterface.logingStatus()){
             navigation.setSelectedItemId(R.id.jsdloanmodule_2_TitleHome);
        }else if(menuIndex==1&&mCommonProjectUtilInterface.logingStatus()){
             navigation.setSelectedItemId(R.id.jsdloanmodule_2_TitleUser);
         }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 注销EventBus
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    /**
     * 登录成功执行的操作
     * @param eventBus
     */
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void logSuccess(Common_LoginSuccess_EventBus eventBus) {
        if(eventBus.isLoginSuccess()){
            //如果在未启动应用的情况下接收到推送，并点击进入，在需要登录的情况下
            if(null!= Common_SharePer_GlobalInfo.sharePre_GetJpushBundle()&&!Common_SharePer_GlobalInfo.sharePre_GetJpushBundle().isEmpty()){
                Bundle bundle=Common_SharePer_GlobalInfo.sharePre_GetJpushBundle();//获取jpush本地存放数据
                if(bundle!=null){
                    boolean isLoging=bundle.getBoolean("isLoging",false);//标识jpush是否是需要登录的页面
                    if(isLoging){
                        Common_JPushReceiver.sendActivity(context,bundle);
                    }
                }
            }
        }
    }
}
