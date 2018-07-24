package com.ddtkj.userinfomodule.MVP.View.Implement.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chenenyu.router.annotation.Route;
import com.customview.lib.ClearEditText;
import com.ddtkj.commonmodule.MVP.Presenter.Implement.Project.Common_ProjectUtil_Implement;
import com.ddtkj.commonmodule.MVP.Presenter.Interface.Project.Common_ProjectUtil_Interface;
import com.ddtkj.commonmodule.Public.Common_RouterUrl;
import com.ddtkj.userinfomodule.Base.UserInfo_BaseActivity;
import com.ddtkj.userinfomodule.MVP.Contract.Activity.UserInfoModule_Act_UserInfo_Contract;
import com.ddtkj.userinfomodule.MVP.Presenter.Implement.Activity.UserInfoModule_Act_UserInfo_Presenter;
import com.ddtkj.userinfomodule.R;
import com.utlis.lib.Textutils;
import com.utlis.lib.ToastUtils;

/**
 *  用户信息
 *
 *  @Author: 杨重诚
 *  @CreatTime: 2018/7/14  10:38
 */
@Route(Common_RouterUrl.USERINFO_UserInfoRouterUrl)
public class UserInfoModule_Act_UserInfo_View extends UserInfo_BaseActivity<UserInfoModule_Act_UserInfo_Contract.Presenter,
        UserInfoModule_Act_UserInfo_Presenter> implements UserInfoModule_Act_UserInfo_Contract.View  {

    ClearEditText cetName;
    ClearEditText cetNum;
    ClearEditText cetPassword;
    ClearEditText cetCard;
    ClearEditText cetBankname;
    ClearEditText cetUpcode;
    ImageView activityLogin_imgPasswordIsShowBtn;//是否显示密码
    TextView tvBtnOk;
    //是否显示密码的标识
    boolean isShowPassword = false;
    Common_ProjectUtil_Interface mCommonProjectUtilInterface;
    @Override
    public void onClick(View v) {
        super.onClick(v);
        if(v.getId()==R.id.tvBtnOk){
            mPresenter.submit(Textutils.getEditText(cetName),Textutils.getEditText(cetNum),
                    Textutils.getEditText(cetCard),Textutils.getEditText(cetBankname),
                    Textutils.getEditText(cetPassword),Textutils.getEditText(cetUpcode));
        }else if (v.getId()==R.id.activityLogin_imgPasswordIsShowBtn){
            //是否显示密码
            isShowPassword = mCommonProjectUtilInterface.isShowPassword(isShowPassword,cetPassword,activityLogin_imgPasswordIsShowBtn);
        }
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.userinfomodule_act_userinfo_layout);
    }

    @Override
    protected void initMyView() {
         cetName=findViewById(R.id.cetName);
         cetNum=findViewById(R.id.cetNum);
         cetPassword=findViewById(R.id.cetPassword);
        cetCard=findViewById(R.id.cetCard);
        cetBankname=findViewById(R.id.cetBankname);
        cetUpcode=findViewById(R.id.cetUpcode);
         tvBtnOk=findViewById(R.id.tvBtnOk);
        activityLogin_imgPasswordIsShowBtn=findViewById(R.id.activityLogin_imgPasswordIsShowBtn);
    }

    @Override
    protected void init() {
        mCommonProjectUtilInterface=new Common_ProjectUtil_Implement();
    }

    @Override
    public void getBundleValues(Bundle mBundle) {
        super.getBundleValues(mBundle);
    }

    @Override
    protected void setTitleBar() {
        //设置Actionbar
        setActionbarBar("个人信息", R.color.app_gray, R.color.white, true,false);
    }

    @Override
    protected void getData() {

    }

    @Override
    protected void setListeners() {
        tvBtnOk.setOnClickListener(this);
        activityLogin_imgPasswordIsShowBtn.setOnClickListener(this);
    }

    @Override
    public void submitSuccess(String msg) {
        ToastUtils.RightImageToast(mUserInfoApplicationInterface.getApplication(),msg);
        getIntentTool().intent_RouterTo(context,Common_RouterUrl.USERINFO_WithdrawRouterUrl);
    }
}
