package com.ddtkj.userinfomodule.MVP.View.Implement.Activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chenenyu.router.annotation.Route;
import com.customview.lib.ClearEditText;
import com.ddtkj.commonmodule.Public.Common_RouterUrl;
import com.ddtkj.userinfomodule.Base.UserInfo_BaseActivity;
import com.ddtkj.userinfomodule.MVP.Contract.Activity.UserInfoModule_Act_ChangePassword_Contract;
import com.ddtkj.userinfomodule.MVP.Presenter.Implement.Activity.UserInfoModule_Act_ChangePassword_Presenter;
import com.ddtkj.userinfomodule.R;
import com.utlis.lib.Textutils;
import com.utlis.lib.ToastUtils;

/**
 *  修改密码
 *
 *  @Author: 杨重诚
 *  @CreatTime: 2018/7/14  18:28  
 */
@Route(Common_RouterUrl.USERINFO_ChangePasswordRouterUrl)
public class UserInfoModule_Act_ChangePassword_View extends UserInfo_BaseActivity<UserInfoModule_Act_ChangePassword_Contract.Presenter,
        UserInfoModule_Act_ChangePassword_Presenter> implements UserInfoModule_Act_ChangePassword_Contract.View{

    ClearEditText cetOldPassword;
    ClearEditText cetPassword1;
    ClearEditText cetPassword2;
    TextView tvBtnOk;//确定
    @Override
    public void onClick(View v) {
        super.onClick(v);
        if(v.getId()==R.id.tvBtnOk){
            String oldPwd=Textutils.getEditText(cetOldPassword);
            String pwd1=Textutils.getEditText(cetPassword1);
            String pwd2=Textutils.getEditText(cetPassword2);
            if(TextUtils.isEmpty(oldPwd)){
                ToastUtils.WarnImageToast(context,"旧密码不能为空！");
                return;
            }else if(TextUtils.isEmpty(pwd1)){
                ToastUtils.WarnImageToast(context,"新密码不能为空！");
                return;
            }else if(TextUtils.isEmpty(pwd2)){
                ToastUtils.WarnImageToast(context,"确认密码不能为空！");
                return;
            }else if(!pwd1.equals(pwd2)){
                ToastUtils.WarnImageToast(context,"新密码和确认密码不一样！");
                return;
            }
            mPresenter.submit(oldPwd,pwd2);
        }else if(v.getId()==R.id.tvRightTitleRight){
            getIntentTool().intent_RouterTo(context,Common_RouterUrl.USERINFO_RechargeRecordRouterUrl);
        }
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.userinfomodule_act_change_password_layout);
    }

    @Override
    protected void initMyView() {
        cetOldPassword=findViewById(R.id.cetOldPassword);
        cetPassword1=findViewById(R.id.cetPassword1);
        cetPassword2=findViewById(R.id.cetPassword2);
        tvBtnOk=findViewById(R.id.tvBtnOk);
    }

    @Override
    protected void init() {

    }

    @Override
    public void getBundleValues(Bundle mBundle) {
        super.getBundleValues(mBundle);
    }

    @Override
    protected void setTitleBar() {
        //设置Actionbar
        setActionbarBar("密码修改", R.color.app_gray, R.color.white, true,false);
    }

    @Override
    protected void getData() {

    }

    @Override
    protected void setListeners() {
        tvBtnOk.setOnClickListener(this);
    }

    @Override
    public void changePasswordSuccess(String msg) {
        ToastUtils.RightImageToast(context,msg);
        FinishA();
    }
}
