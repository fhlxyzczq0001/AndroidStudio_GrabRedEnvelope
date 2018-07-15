package com.ddtkj.userinfomodule.MVP.View.Implement.Activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chenenyu.router.annotation.Route;
import com.customview.lib.ClearEditText;
import com.ddtkj.commonmodule.Public.Common_RouterUrl;
import com.ddtkj.userinfomodule.Base.UserInfo_BaseActivity;
import com.ddtkj.userinfomodule.MVP.Contract.Activity.UserInfoModule_Act_Recharge_Contract;
import com.ddtkj.userinfomodule.MVP.Presenter.Implement.Activity.UserInfoModule_Act_Recharge_Presenter;
import com.ddtkj.userinfomodule.R;
import com.utlis.lib.Textutils;
import com.utlis.lib.ToastUtils;
import com.utlis.lib.ViewUtils;

/**
 *  充值
 *
 *  @Author: 杨重诚
 *  @CreatTime: 2018/7/14  18:28  
 */
@Route(Common_RouterUrl.USERINFO_RechargeRouterUrl)
public class UserInfoModule_Act_Recharge_View extends UserInfo_BaseActivity<UserInfoModule_Act_Recharge_Contract.Presenter,
        UserInfoModule_Act_Recharge_Presenter> implements UserInfoModule_Act_Recharge_Contract.View{

    ClearEditText cetMoney;//金额
    TextView tvBtnOk;//提现按钮
    @Override
    public void onClick(View v) {
        super.onClick(v);
        if(v.getId()==R.id.tvBtnOk){
            //提现按钮
            String money=Textutils.getEditText(cetMoney);
            if(TextUtils.isEmpty(money)){
                ToastUtils.WarnImageToast(context,"请输入充值金额！");
                return;
            }else if(Double.parseDouble(money)<0.01){
                ToastUtils.WarnImageToast(context,"充值金额必须大于0.01元！");
                return;
            }
            mPresenter.recharge(money);
        }else if(v.getId()==R.id.tvRightTitleRight){
            getIntentTool().intent_RouterTo(context,Common_RouterUrl.USERINFO_RechargeRecordRouterUrl);
        }
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.userinfomodule_act_recharge_layout);
    }

    @Override
    protected void initMyView() {
         cetMoney=findViewById(R.id.cetMoney);//金额
        tvBtnOk=findViewById(R.id.tvBtnOk);//提现按钮
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
        setActionbarBar("充值", R.color.app_gray, R.color.white, true,false);
        tvRightTitleRight.setVisibility(View.VISIBLE);
        tvRightTitleRight.setCompoundDrawables(ViewUtils.getDrawableSvg(context,R.drawable.drawable_svg_icon_more), null, null, null);
    }

    @Override
    protected void getData() {

    }

    @Override
    protected void setListeners() {
        tvBtnOk.setOnClickListener(this);
        tvRightTitleRight.setOnClickListener(this);
    }

    @Override
    public void rechargeSuccess(String msg) {
        ToastUtils.RightImageToast(context,msg);
    }
}
