package com.ddt.redEnvelope.MVP.View.Implement.Activity;

import android.view.View;
import android.widget.LinearLayout;

import com.chenenyu.router.annotation.Route;
import com.ddt.redEnvelope.Base.Main_BaseActivity;
import com.ddt.redEnvelope.MVP.Contract.Activity.Main_Act_Guide_Contract;
import com.ddt.redEnvelope.MVP.Presenter.Implement.Activity.Main_Act_Guide_Presenter;
import com.ddt.redEnvelope.R;
import com.ddtkj.commonmodule.MVP.Model.Bean.EventBusBean.Common_LoginSuccess_EventBus;
import com.ddtkj.commonmodule.Public.Common_PublicMsg;
import com.ddtkj.commonmodule.Public.Common_RouterUrl;
import com.jazzy.viewpager.GuideViewPagers;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


/**引导页
 * @author: Administrator 杨重诚
 * @date: 2016/10/21:11:26
 */
@Route(Common_RouterUrl.MAIN_GuideRouterUrl)
public class Main_Act_Guide_View extends Main_BaseActivity<Main_Act_Guide_Contract.Presenter,Main_Act_Guide_Presenter> implements Main_Act_Guide_Contract.View {
    LinearLayout lyParent;
    //引导页
    GuideViewPagers cusGuideViewPagers;

    @Override
    protected void initMyView() {
        lyParent=findViewById(R.id.lyParent);
        cusGuideViewPagers=findViewById(R.id.cusGuideViewPagers);
    }
    @Override
    protected void setContentView() {
        setContentView(R.layout.main_act_guide_layout);
    }

    @Override
    protected void init() {
        //初始化引导页
        cusGuideViewPagers.setGuideData(mPresenter.getDefaultGuidPage(),new int[]{R.drawable.drawable_shape_oval_gray_6,R.drawable.drawable_shape_oval_appcolor_6},R.drawable.bg_guide1);
        //设置按钮背景
        cusGuideViewPagers.setLogingBtSty(R.drawable.drawable_shap_powder_bg_radius20);
        cusGuideViewPagers.setTiYangBtSty(R.drawable.drawable_shap_powder_bg_radius20);
    }

    @Override
    protected void setListeners() {
        cusGuideViewPagers.getLogingBt().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 进入登录
                getIntentTool().intent_RouterTo(context, Common_RouterUrl.USERINFO_LogingRouterUrl);
            }
        });
        cusGuideViewPagers.getTiyanBt().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 进入首页
                getIntentTool().intent_RouterTo(context,  Common_PublicMsg.ROUTER_MAINACTIVITY);
                finish();
            }
        });
    }

    @Override
    protected void setTitleBar() {
        setActionbarGone();//隐藏actionbar
    }
    @Override
    protected void getData() {

    }
    @Override
    public void onResume() {
        systemBarTintManagerColor=R.color.transparent;
        super.onResume();
        // 注册EventBus
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
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
    @Subscribe(threadMode = ThreadMode.BACKGROUND,sticky = true)
    public void logSuccess(Common_LoginSuccess_EventBus eventBus) {
        if(eventBus.isLoginSuccess()){
            finish();
        }
    }
}
