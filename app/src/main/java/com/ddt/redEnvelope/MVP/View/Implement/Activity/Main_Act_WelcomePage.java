package com.ddt.redEnvelope.MVP.View.Implement.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chenenyu.router.annotation.Route;
import com.customview.lib.CircleTextProgressbar;
import com.ddt.redEnvelope.Base.Main_BaseActivity;
import com.ddt.redEnvelope.MVP.Contract.Activity.Main_Act_WelcomePage_Contract;
import com.ddt.redEnvelope.MVP.Model.Bean.RequestBean.Main_WelcomePageBean;
import com.ddt.redEnvelope.MVP.Presenter.Implement.Activity.Main_Act_WelcomePage_Presenter;
import com.ddt.redEnvelope.MVP.Presenter.Implement.Project.Main_ProjectUtil_Implement;
import com.ddt.redEnvelope.MVP.Presenter.Interface.Project.Main_ProjectUtil_Interface;
import com.ddt.redEnvelope.R;
import com.ddt.redEnvelope.Service.Main_WelcomePageService;
import com.ddt.redEnvelope.Util.Main_SharePer_SdCard_Info;
import com.ddtkj.commonmodule.Public.Common_PublicMsg;
import com.ddtkj.commonmodule.Public.Common_RouterUrl;
import com.ddtkj.commonmodule.Public.Common_SD_FilePath;
import com.utlis.lib.FileUtils;
import com.utlis.lib.L;
import com.utlis.lib.WindowUtils;

import java.io.File;
import java.util.List;


/**
 * 启动页
 * @author: Administrator 杨重诚
 * @date: 2016/10/14:16:23
 */
@Route(Common_RouterUrl.MAIN_WelcomePageRouterUrl)
public class Main_Act_WelcomePage extends Main_BaseActivity<Main_Act_WelcomePage_Contract.Presenter,Main_Act_WelcomePage_Presenter> implements Main_Act_WelcomePage_Contract.View {
    LinearLayout lyCircleTextProgressbar;
    //显示图片
    ImageView imgWelcome;
    //跳过进度条
    CircleTextProgressbar cusCircleTextProgressbar;

    Bitmap pageBitmap;// 启动页图片
    Main_ProjectUtil_Interface mMainProjectUtil_presenter_interface;
    boolean isOnStop=false;//标识是否执行了OnStop方法
    //---------------------------倒计时--------------------------------
    private int allTime=5*1000;//总时间

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if(v.getId()== R.id.cusCircleTextProgressbar){
            //跳过按钮点击事件
            toNextActivity();
        }else if(v.getId()==R.id.imgWelcome){
            //图片点击事件
            Main_WelcomePageBean welcomePageBean= Main_SharePer_SdCard_Info.sharePre_GetWelcomePageBean();
            if(welcomePageBean!=null&&!welcomePageBean.getLink().isEmpty()){
                mMainProjectUtil_presenter_interface.urlIntentJudge(context,welcomePageBean.getLink(),"");
                cusCircleTextProgressbar.stop();
                isOnStop=true;
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTheme(R.style.AppDefaultStyle);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.main_act_welcome_page_layout);
    }
   /* @Override
    public void onResume() {
        systemBarTintManagerColor=R.color.transparent;
        super.onResume();
        if(isOnStop){
            toNextActivity();
        }
    }*/


    @Override
    protected void init() { // 进入首页
        if((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0){
            finish();
            return;
        }
        //初始化presenter
        mMainProjectUtil_presenter_interface =new Main_ProjectUtil_Implement();
        //设置启动页
        setWelcomePageData();
        //请求是否下载启动页
        mPresenter.requestStartPageUpdate();
        //5秒后跳转
//        startCountDownTimer();
    }

    @Override
    protected void initMyView() {
         lyCircleTextProgressbar=findViewById(R.id.lyCircleTextProgressbar);
        //显示图片
         imgWelcome=findViewById(R.id.imgWelcome);
        //跳过进度条
         cusCircleTextProgressbar=findViewById(R.id.cusCircleTextProgressbar);
    }

    @Override
    protected void setListeners() {
        imgWelcome.setOnClickListener(this);
        //跳过进度条
        cusCircleTextProgressbar.setOnClickListener(this);
    }

    @Override
    protected void setTitleBar() {
        setActionbarGone();//隐藏actionbar
        int StatusHeight = WindowUtils.getStatusHeight((Activity) context);// 获取状态栏高度
        lyCircleTextProgressbar.setPadding(0, StatusHeight, 10, 0);
    }

    @Override
    protected void getData() {

    }

    @Override
    public void getBundleValues(Bundle mBundle) {
        super.getBundleValues(mBundle);
        getDataFromBrowser();
    }

    /**
     * 启动启动页service后台下载
     * @param pageBean
     */
    @Override
    public void startWelcomePageService(Main_WelcomePageBean pageBean) {
        // 启动service后台下载
        Intent intent = new Intent(context,
                Main_WelcomePageService.class);
        intent.putExtra("WelcomePageBean",pageBean);
        context.startService(intent);
    }

    /**
     * 设置启动页
     * @Title: setWelcomePageData
     * @Description: TODO
     * @return: void
     */
    private void setWelcomePageData() {
        List<String> filePaths = FileUtils.getFileName(new File(
                Common_SD_FilePath.welcomePagePath).listFiles());
        if (null != filePaths && filePaths.size() > 0) {
            // 随机抽取一张启动页显示
            int index = (int) (Math.random() * filePaths.size());
            pageBitmap = BitmapFactory.decodeFile(filePaths.get(index));// 读取本地图片
            imgWelcome.setImageBitmap(pageBitmap);
            //设置动画
            Animation animationTop = AnimationUtils.loadAnimation(this,
                    R.anim.anim_tutorail_scalate_top);//渐变放大效果
            imgWelcome.startAnimation(animationTop);
        }else{
            imgWelcome.setImageResource(R.drawable.bg_welcome_default);
        }
    }
    @Override
    /**
     * 待跳转的下个界面
     */
    public void toNextActivity(){
        cusCircleTextProgressbar.stop();//停止倒计时
       /* if (Main_SharePer_GlobalInfo.sharePre_GetFirstInstall()) {
            Main_SharePer_GlobalInfo.sharePre_PutFirstInstall(false);//设置不是第一次进入
            // 进入引导页
            getIntentTool().intent_RouterTo(context, Common_RouterUrl.MAIN_GuideRouterUrl);
            finish();
        } else {
            // 进入首页
            getIntentTool().intent_RouterTo(context, Common_PublicMsg.ROUTER_MAINACTIVITY);
            finish();
        }*/
        // 进入首页
        getIntentTool().intent_RouterTo(context, Common_PublicMsg.ROUTER_MAINACTIVITY);
        finish();
    }
    /**
     * 启动倒计时
     */
    @Override
    public void startCountDownTimer() {
        // 和系统普通进度条一样，0-100。
        cusCircleTextProgressbar.setProgressType(CircleTextProgressbar.ProgressType.COUNT);
        // 改变外部边框颜色。
        cusCircleTextProgressbar.setOutLineColor(Color.parseColor("#20dbdbdb"));
        // 设置倒计时时间毫秒
        cusCircleTextProgressbar.setTimeMillis(allTime);
        // 改变圆心颜色。
        cusCircleTextProgressbar.setInCircleColor(Color.parseColor("#20000000"));
        //设置进度条颜色
        cusCircleTextProgressbar.setProgressColor(context.getResources().getColor(R.color.app_color));
        //设置进度条边宽
        cusCircleTextProgressbar.setProgressLineWidth(1);
        //设进度监听
        cusCircleTextProgressbar.setCountdownProgressListener(1, new CircleTextProgressbar.OnCountdownProgressListener() {
            @Override
            public void onProgress(int what, int progress) {
                if(progress==100){
                    //倒计时结束
                    toNextActivity();
                }
            }
        });
        //启动倒计时
        cusCircleTextProgressbar.reStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        cusCircleTextProgressbar.stop();
        isOnStop=true;
    }


    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        if (pageBitmap != null) {
            pageBitmap.recycle();
        }
        cusCircleTextProgressbar.stop();//停止倒计时
    }
    String scheme;
    String host;
    List<String> params;
    /**
     * 从deep link中获取数据
     */
    private void getDataFromBrowser() {
        try {
            Uri data = getIntent().getData();
            scheme = data.getScheme(); // "will"
            host = data.getHost(); // "share"
            params = data.getPathSegments();
            String testId = params.get(0); // "uuid"
            L.e("====Deep Linking技术=====","Scheme: " + scheme + "\n" + "host: " + host + "\n" + "params: " + testId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
