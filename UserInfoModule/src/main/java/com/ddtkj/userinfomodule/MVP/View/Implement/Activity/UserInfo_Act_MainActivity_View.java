package com.ddtkj.userinfomodule.MVP.View.Implement.Activity;

import android.view.View;

import com.chenenyu.router.annotation.Route;
import com.ddtkj.commonmodule.Public.Common_RouterUrl;
import com.ddtkj.userinfomodule.Base.UserInfo_BaseActivity;
import com.ddtkj.userinfomodule.MVP.Contract.Activity.UserInfo_Act_MainActivity_Contract;
import com.ddtkj.userinfomodule.MVP.Presenter.Implement.Activity.UserInfo_Act_MainActivity_Presenter;
import com.ddtkj.userinfomodule.R;

/**
 * 用户主Activity
 */
@Route(Common_RouterUrl.USERINFO_MainActivityRouterUrl)
public class UserInfo_Act_MainActivity_View extends UserInfo_BaseActivity<UserInfo_Act_MainActivity_Contract.Presenter,UserInfo_Act_MainActivity_Presenter> implements UserInfo_Act_MainActivity_Contract.View {
    @Override
    public void onClick(View v) {
        super.onClick(v);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.userinfo_act_main_layout);
    }

    @Override
    protected void init() {
    }

    @Override
    protected void initMyView() {
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
}
