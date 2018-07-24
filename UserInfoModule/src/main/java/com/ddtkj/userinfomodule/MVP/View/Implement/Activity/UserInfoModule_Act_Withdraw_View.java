package com.ddtkj.userinfomodule.MVP.View.Implement.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chenenyu.router.annotation.Route;
import com.customview.lib.ClearEditText;
import com.ddtkj.commonmodule.MVP.Model.Bean.ResponseBean.Common_UserInfoBean;
import com.ddtkj.commonmodule.Public.Common_RouterUrl;
import com.ddtkj.commonmodule.Util.Common_CustomDialogBuilder;
import com.ddtkj.userinfomodule.Base.UserInfo_BaseActivity;
import com.ddtkj.userinfomodule.MVP.Contract.Activity.UserInfoModule_Act_Withdraw_Contract;
import com.ddtkj.userinfomodule.MVP.Presenter.Implement.Activity.UserInfoModule_Act_Withdraw_Presenter;
import com.ddtkj.userinfomodule.R;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.utlis.lib.Textutils;
import com.utlis.lib.ToastUtils;
import com.utlis.lib.ViewUtils;

/**
 *  提现
 *
 *  @Author: 杨重诚
 *  @CreatTime: 2018/7/14  18:28  
 */
@Route(Common_RouterUrl.USERINFO_WithdrawRouterUrl)
public class UserInfoModule_Act_Withdraw_View extends UserInfo_BaseActivity<UserInfoModule_Act_Withdraw_Contract.Presenter,
        UserInfoModule_Act_Withdraw_Presenter> implements UserInfoModule_Act_Withdraw_Contract.View{

    ClearEditText cetMoney;//提现金额
    TextView tvBalance;//余额
    TextView tvBtnAllBalance;//全部提现
    TextView tvBtnWithdraw;//提现按钮
    TextView tvModifyPassword;//修改密码
    TextView tvForgetPassword;//忘记密码

    //弹窗对象
    Common_CustomDialogBuilder common_customDialogBuilder;
    @Override
    public void onClick(View v) {
        super.onClick(v);
        if(v.getId()==R.id.tvBtnWithdraw){
            //提现按钮
            String money=Textutils.getEditText(cetMoney);
            if(TextUtils.isEmpty(money)){
                ToastUtils.WarnImageToast(context,"请输入提现金额！");
                return;
            }else if(Double.parseDouble(money)<0.01){
                ToastUtils.WarnImageToast(context,"提现金额必须大于0.01元！");
                return;
            }else if(Double.parseDouble(money)>Double.parseDouble(Textutils.getEditText(tvBalance))){
                ToastUtils.WarnImageToast(context,"提现金额不能大于余额！");
                return;
            }
            mPresenter.submit(money);
        }else if (v.getId()==R.id.tvModifyPassword){
            //修改密码
            getIntentTool().intent_RouterTo(context,Common_RouterUrl.USERINFO_ChangePasswordRouterUrl);
        }else if (v.getId()==R.id.tvForgetPassword){
            //忘记密码
            showDialog("18534539748","18534539748");
        }else if (v.getId()==R.id.tvBtnAllBalance){
            //全部提现
            if(!TextUtils.isEmpty(Textutils.getEditText(tvBalance)))
            cetMoney.setText(Textutils.getEditText(tvBalance));
        }else if(v.getId()==R.id.tvRightTitleRight){
            getIntentTool().intent_RouterTo(context,Common_RouterUrl.USERINFO_WithdrawalsRecordRouterUrl);
        }
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.userinfomodule_act_withdrawals_layout);
    }

    @Override
    protected void initMyView() {
         cetMoney=findViewById(R.id.cetMoney);//提现金额
         tvBalance=findViewById(R.id.tvBalance);//余额
         tvBtnAllBalance=findViewById(R.id.tvBtnAllBalance);//全部提现
         tvBtnWithdraw=findViewById(R.id.tvBtnWithdraw);//提现按钮
         tvModifyPassword=findViewById(R.id.tvModifyPassword);//修改密码
         tvForgetPassword=findViewById(R.id.tvForgetPassword);//忘记密码
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
        setActionbarBar("零钱提现", R.color.app_gray, R.color.white, true,false);
        tvRightTitleRight.setVisibility(View.VISIBLE);
        tvRightTitleRight.setCompoundDrawables(ViewUtils.getDrawableSvg(context,R.drawable.drawable_svg_icon_more), null, null, null);
    }

    @Override
    protected void getData() {

    }

    @Override
    protected void setListeners() {
        tvBtnWithdraw.setOnClickListener(this);
        tvModifyPassword.setOnClickListener(this);
        tvForgetPassword.setOnClickListener(this);
        tvBtnAllBalance.setOnClickListener(this);
        tvRightTitleRight.setOnClickListener(this);
    }

    @Override
    public void withdrawSuccess(String msg) {
        ToastUtils.RightImageToast(context,msg);
        mPresenter.refreshUserInfo();
    }
    @Override
    public void setData(Common_UserInfoBean bean){
        cetMoney.setText("");
        tvBalance.setText(bean.getAccountbalance());
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.refreshUserInfo();
    }

    /**
     *
     * @param phone
     * @param weixin
     */
    public void showDialog (String phone,String weixin){
        if (common_customDialogBuilder == null){
            common_customDialogBuilder = new Common_CustomDialogBuilder(context);
        }
        final NiftyDialogBuilder dialogBuilder =  common_customDialogBuilder.showDialog(R.layout.common_dialog_custom_layout);
        //设置弹窗圆角背景
        dialogBuilder.getParentPanel().setBackgroundDrawable(ViewUtils.getGradientDrawable(context.getResources().getDimension(R.dimen.x20),1,
                context.getResources().getColor(R.color.white),context.getResources().getColor(R.color.white)));
        //标题
        TextView tvTitle=dialogBuilder.findViewById(R.id.tvTitle);
        //关闭按钮
        ImageView imgBtnClose=dialogBuilder.findViewById(R.id.imgBtnClose);
        //图标
        ImageView imgMsg=dialogBuilder.findViewById(R.id.imgMsg);
        //内容
        TextView tvMsg=dialogBuilder.findViewById(R.id.tvMsg);
        //分割线
        View viewLineBottom=dialogBuilder.findViewById(R.id.viewLineBottom);
        //确认按钮
        TextView tvBtnConfirm=dialogBuilder.findViewById(R.id.tvBtnConfirm);
        //取消按钮
        TextView tvBtnCancel=dialogBuilder.findViewById(R.id.tvBtnCancel);
        //设置标题
        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX,context.getResources().getDimension(R.dimen.x33));
        tvTitle.setTextColor(Color.parseColor("#000000"));
        tvTitle.setText("请联系客服找回密码");
        //设置隐藏关闭按钮
        imgBtnClose.setVisibility(View.VISIBLE);
        //设置微信图标
        LinearLayout.LayoutParams imgMsgLayoutParams= (LinearLayout.LayoutParams) imgMsg.getLayoutParams();
        imgMsgLayoutParams.width= (int) context.getResources().getDimension(R.dimen.x131);
        imgMsgLayoutParams.height= (int) context.getResources().getDimension(R.dimen.x131);
        imgMsgLayoutParams.setMargins(0, (int) context.getResources().getDimension(R.dimen.x50),0,0);
        imgMsg.setLayoutParams(imgMsgLayoutParams);
        imgMsg.setImageResource(R.mipmap.icon_launcher);
        //设置内容
        tvMsg.setTextSize(TypedValue.COMPLEX_UNIT_PX,context.getResources().getDimension(R.dimen.x26));
        tvMsg.setTextColor(Color.parseColor("#898989"));
        tvMsg.setText("手机号："+phone+"\n"+"微信号："+weixin);
        tvMsg.setGravity(Gravity.LEFT);
        tvMsg.setPadding((int) context.getResources().getDimension(R.dimen.x127),(int) context.getResources().getDimension(R.dimen.x50),
                (int) context.getResources().getDimension(R.dimen.x27),(int) context.getResources().getDimension(R.dimen.x50));
        //设置隐藏分割线
        viewLineBottom.setVisibility(View.VISIBLE);
        /*//设置隐藏取消按钮
        tvBtnCancel.setVisibility(View.GONE);
        //设置复制微信号按钮
        tvBtnConfirm.setTextColor(context.getResources().getColor(R.color.white));
        tvBtnConfirm.setText("点击复制微信号");
        tvBtnConfirm.setTextSize(TypedValue.COMPLEX_UNIT_PX,context.getResources().getDimension(R.dimen.x32));
        tvBtnConfirm.setBackgroundColor(context.getResources().getColor(R.color.app_color));
        LinearLayout.LayoutParams tvBtnConfirmLayoutParams= (LinearLayout.LayoutParams) tvBtnConfirm.getLayoutParams();
        tvBtnConfirmLayoutParams.weight=0;
        tvBtnConfirmLayoutParams.width=LinearLayout.LayoutParams.WRAP_CONTENT;
        tvBtnConfirm.setLayoutParams(tvBtnConfirmLayoutParams);
        tvBtnConfirm.setPadding((int)context.getResources().getDimension(R.dimen.x50),(int)context.getResources().getDimension(R.dimen.x20),
                (int)context.getResources().getDimension(R.dimen.x50),(int)context.getResources().getDimension(R.dimen.x20));
        tvBtnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                String text = context.getResources().getString(R.string.venturecapital_2_customerServiceWeiXin);
                ClipData myClip = ClipData.newPlainText(null, text);//text是内容
                cmb.setPrimaryClip(myClip);
                ToastUtils.RightImageToast(context, "复制成功");
                dialogBuilder.dismiss();
            }
        });*/
    }
}
