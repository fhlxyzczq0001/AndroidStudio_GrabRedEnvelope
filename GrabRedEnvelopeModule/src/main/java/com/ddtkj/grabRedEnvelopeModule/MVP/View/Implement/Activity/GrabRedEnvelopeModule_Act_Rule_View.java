package com.ddtkj.grabRedEnvelopeModule.MVP.View.Implement.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.chenenyu.router.annotation.Route;
import com.ddtkj.commonmodule.Public.Common_RouterUrl;
import com.ddtkj.grabRedEnvelopeModule.MVP.Model.Bean.ResponseBean.GrabRedEnvelopeModule_Bean_Rule;
import com.ddtkj.grabRedEnvelopeModule.R;
import com.ddtkj.userinfomodule.Base.UserInfo_BaseActivity;

/**
 *  规则详情
 *
 *  @Author: 杨重诚
 *  @CreatTime: 2018/2/13  17:41  
 */
@Route(Common_RouterUrl.GRAB_RED_ENVELOPE_RuleInfoRouterUrl)
public class GrabRedEnvelopeModule_Act_Rule_View extends UserInfo_BaseActivity{

    TextView tvTitle;
    TextView tvTime;
    TextView tvInfo;
    GrabRedEnvelopeModule_Bean_Rule mGrabRedEnvelopeModuleBeanAnnouncement;
    @Override
    public void onClick(View v) {
        super.onClick(v);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.grabredenvelopemodule_act_rule_info_layout);
    }

    @Override
    protected void initMyView() {
         tvTitle=findViewById(R.id.tvTitle);
         tvTime=findViewById(R.id.tvTime);
         tvInfo=findViewById(R.id.tvInfo);
    }

    @Override
    protected void init() {
        tvTitle.setText(mGrabRedEnvelopeModuleBeanAnnouncement.getTitle());
        tvTime.setText("发布时间："+mGrabRedEnvelopeModuleBeanAnnouncement.getCreatedate());
        tvInfo.setText("\t\t"+mGrabRedEnvelopeModuleBeanAnnouncement.getRules());
    }

    @Override
    public void getBundleValues(Bundle mBundle) {
        super.getBundleValues(mBundle);
        if (mBundle != null) {
            mGrabRedEnvelopeModuleBeanAnnouncement = mBundle.getParcelable("announcementBean");
        }
    }

    @Override
    protected void setTitleBar() {
        //设置Actionbar
        setActionbarBar("规则详情", R.color.app_gray, R.color.white, true,false);
    }

    @Override
    protected void getData() {

    }

    @Override
    protected void setListeners() {
    }
}
