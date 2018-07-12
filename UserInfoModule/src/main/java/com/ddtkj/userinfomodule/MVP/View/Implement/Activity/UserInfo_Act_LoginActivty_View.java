package com.ddtkj.userinfomodule.MVP.View.Implement.Activity;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chenenyu.router.annotation.Route;
import com.ddtkj.commonmodule.MVP.Model.Bean.EventBusBean.Common_LoginSuccess_EventBus;
import com.ddtkj.commonmodule.MVP.Model.Bean.EventBusBean.Common_UmengAuth_EventBus;
import com.ddtkj.commonmodule.Public.Common_RouterUrl;
import com.ddtkj.userinfomodule.Base.UserInfo_BaseActivity;
import com.ddtkj.userinfomodule.MVP.Contract.Activity.UserInfo_Act_LoginActivity_Contract;
import com.ddtkj.userinfomodule.MVP.Presenter.Implement.Activity.UserInfo_Act_LoginActivity_Presenter;
import com.ddtkj.userinfomodule.R;
import com.utlis.lib.ViewUtils;
import com.utlis.lib.WindowUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static com.umeng.socialize.bean.SHARE_MEDIA.WEIXIN;

/**
 * @author lizhipei
 * 登录界面
 * Created by Administrator on 2018/2/12.
 */
@Route(Common_RouterUrl.USERINFO_LogingRouterUrl)
public class UserInfo_Act_LoginActivty_View extends UserInfo_BaseActivity<UserInfo_Act_LoginActivity_Contract.Presenter, UserInfo_Act_LoginActivity_Presenter> implements UserInfo_Act_LoginActivity_Contract.View{
    Button activityLogin_loginBtn;//登录按钮
    ImageView imgBtnClose;//关闭按钮
    LinearLayout lyParent;

    @Override
    protected void initMyView() {
        activityLogin_loginBtn= findViewById(R.id.activityLogin_loginBtn);
        imgBtnClose= findViewById(R.id.imgBtnClose);
        lyParent= findViewById(R.id.lyParent);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.userinfo_act_login_layout);
    }


    @Override
    protected void init() {
        //设置登录按钮样式
        setLoginBtn();
    }
    @Override
    protected void setListeners() {
        activityLogin_loginBtn.setOnClickListener(this);
        imgBtnClose.setOnClickListener(this);
    }

    @Override
    protected void setTitleBar() {
       setActionbarGone();
        int StatusHeight = WindowUtils.getStatusHeight((Activity) context);// 获取状态栏高度
        lyParent.setPadding(0, StatusHeight, 0, 0);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.activityLogin_loginBtn) {// 登录
            mPresenter.bindUmengAuth(WEIXIN);
        }else if(v.getId() == R.id.imgBtnClose){
            //关闭按钮
            FinishA();
        }
    }
    /**
     * 获取页面传值
     */
    @Override
    public void getBundleValues(Bundle mBundle) {
        super.getBundleValues(mBundle);
    }

    @Override
    protected void getData() {

    }

    @Override
    public void loginResult(boolean isSucceed) {
        if(isSucceed){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //这里发送粘性事件
                    EventBus.getDefault().postSticky(new Common_LoginSuccess_EventBus(true));
                }
            }, 300);
            finish();
        }
    }
    /**
     * 设置登录按钮
     */
    public void setLoginBtn(){
            Drawable idNormal =ViewUtils.getGradientDrawable((int)context.getResources().getDimension(R.dimen.x10),(int)context.getResources().getDimension(R.dimen.x1),
                    Color.parseColor("#E3351F"),Color.parseColor("#E3351F"));
            Drawable idPressed =ViewUtils.getGradientDrawable((int)context.getResources().getDimension(R.dimen.x10),(int)context.getResources().getDimension(R.dimen.x1),
                    context.getResources().getColor(R.color.app_color),context.getResources().getColor(R.color.app_color));
            activityLogin_loginBtn.setBackgroundDrawable(ViewUtils.newSelector(idNormal,idPressed));
    }
    @Override
    public void onResume() {
        // TODO Auto-generated method stub
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
    //-----------------------------------Eventbus------------------------------------------
    /**
     * 友盟第三方授权成功
     *
     * @param eventBus
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void umengAuthSuccess(Common_UmengAuth_EventBus eventBus) {
        if (!eventBus.isReceive()) {
            eventBus.setReceive(true);
        } else {
            return;
        }
        switch (eventBus.getShareCode()) {
            case SHARE_SUCCESS:
                //授权成功
                switch (eventBus.getPlatform()) {
                    case WEIXIN:
                        //微信渠道
                        String wxOpenId = eventBus.getData().get("openid");
                        mPresenter.canLogin("wx", wxOpenId);
                        break;
                    case QQ:
                        //QQ渠道
                        String qqOpenId = eventBus.getData().get("openid");
                        mPresenter.canLogin("qq", qqOpenId);
                        break;
                    case SINA:
                        //微博渠道
                        String uid = eventBus.getData().get("uid");
                        mPresenter.canLogin("wb", uid);
                        break;
                }
                break;
            case SHARE_FAIL:
                //授权错误
                break;
            case SHARE_CANCLE:
                //授权取消
                break;
        }
    }
}
