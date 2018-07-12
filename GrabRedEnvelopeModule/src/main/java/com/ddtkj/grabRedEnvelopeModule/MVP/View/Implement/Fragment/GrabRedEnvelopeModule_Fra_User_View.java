package com.ddtkj.grabRedEnvelopeModule.MVP.View.Implement.Fragment;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import com.ddtkj.commonmodule.Public.Common_Main_PublicCode;
import com.ddtkj.commonmodule.Public.Common_RouterUrl;
import com.ddtkj.commonmodule.Util.Common_CustomDialogBuilder;
import com.ddtkj.grabRedEnvelopeModule.Base.GrabRedEnvelopeModule_BaseFragment;
import com.ddtkj.grabRedEnvelopeModule.MVP.Contract.Fragment.GrabRedEnvelopeModule_Fra_User_Contract;
import com.ddtkj.grabRedEnvelopeModule.MVP.Presenter.Implement.Fragment.GrabRedEnvelopeModule_Fra_User_Presenter;
import com.ddtkj.grabRedEnvelopeModule.R;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.pop.spinner.PopCommon;
import com.pop.spinner.PopModel;
import com.utlis.lib.L;
import com.utlis.lib.ViewUtils;
import com.utlis.lib.WindowUtils;

import java.util.ArrayList;
import java.util.List;

/**
 *  我的——View
 *
 *  @Author: 杨重诚
 *  @CreatTime: 2018/7/4  14:06  
 */
public class GrabRedEnvelopeModule_Fra_User_View extends GrabRedEnvelopeModule_BaseFragment<GrabRedEnvelopeModule_Fra_User_Contract.Presenter, GrabRedEnvelopeModule_Fra_User_Presenter> implements GrabRedEnvelopeModule_Fra_User_Contract.View {

    //领取红包
    TextView tvBtnRedPackage;
    TextView tvBtnJieFeng;
    List<PopModel> mPopModels=new ArrayList<>();
    Integer []icons={R.drawable.icon_tixian,R.drawable.icon_chongzhi,R.drawable.icon_hongbao,R.drawable.icon_xiaofei,R.drawable.icon_yijian};
    String [] iconTitles={"提现记录","充值记录","领取红包记录","消费记录","意见反馈"};
    int location[] = new int[2];
    //弹窗对象
    Common_CustomDialogBuilder common_customDialogBuilder;
    @Override
    public void onClick(View v) {
        super.onClick(v);
        if(v.getId()==R.id.tvBtnRedPackage){
            //领取红包
            getIntentTool().intent_destruction_other_activity_RouterTo(context, Common_RouterUrl.GRAB_RED_ENVELOPE_MainActivityRouterUrl + "?tab=" + Common_Main_PublicCode.GRAB_RED_ENVELOPE_TAB_HOME.toString());
        }else if(v.getId()==R.id.tvRightTitleRight){
            //更多
            tvRightTitleRight.getLocationOnScreen(location);
            int x = 0;
            int y = location[1] + tvRightTitleRight.getHeight();
            //下拉菜单
            PopCommon mPopCommon = new PopCommon((Activity) context, mPopModels, new PopCommon.OnPopCommonListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    L.e("======position====",iconTitles[position]);
                    switch (iconTitles[position]){
                        case "提现记录":
                            getIntentTool().intent_RouterTo(context,Common_RouterUrl.USERINFO_WithdrawalsRecordRouterUrl);
                            break;
                        case "充值记录":
                            getIntentTool().intent_RouterTo(context,Common_RouterUrl.USERINFO_RechargeRecordRouterUrl);
                            break;
                        case "领取红包记录":
                            getIntentTool().intent_RouterTo(context,Common_RouterUrl.USERINFO_RedEnvelopeRecordRouterUrl);
                            break;
                        case "消费记录":
                            getIntentTool().intent_RouterTo(context,Common_RouterUrl.USERINFO_ConsumptionRecordRouterUrl);
                            break;
                        case "意见反馈":

                            if (common_customDialogBuilder == null){
                                common_customDialogBuilder = new Common_CustomDialogBuilder(context);
                            }
                            final NiftyDialogBuilder dialogBuilder =  common_customDialogBuilder.showDialog(R.layout.userinfomodule_dialog_feedback_layout);
                            //设置弹窗圆角背景
                            dialogBuilder.getParentPanel().setBackgroundDrawable(ViewUtils.getGradientDrawable(context.getResources().getDimension(R.dimen.x20),1,
                                    Color.parseColor("#D2D2D2"),Color.parseColor("#D2D2D2")));

                            EditText editText=dialogBuilder.findViewById(R.id.edit);
                            TextView tvBtnConfirm=dialogBuilder.findViewById(R.id.tvBtnConfirm);
                            TextView tvBtnCancel=dialogBuilder.findViewById(R.id.tvBtnCancel);
                            //确定
                            tvBtnConfirm.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            });
                            //取消
                            tvBtnCancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialogBuilder.dismiss();
                                }
                            });
                            dialogBuilder.show();
                            break;
                    }
                }

                @Override
                public void onDismiss() {

                }
            });
            mPopCommon.showPop(tvRightTitleRight, x, y);
        }else if(v.getId()==R.id.tvBtnJieFeng){
            getIntentTool().intent_RouterTo(context,Common_RouterUrl.USERINFO_UnblockRecordRouterUrl);
        }
    }
    public static Fragment newInstance(Bundle bundle) {
        GrabRedEnvelopeModule_Fra_User_View fragment = new GrabRedEnvelopeModule_Fra_User_View();
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    protected void initMyView() {
        tvBtnRedPackage=public_view.findViewById(R.id.tvBtnRedPackage);
        tvBtnJieFeng=public_view.findViewById(R.id.tvBtnJieFeng);
    }

    @Override
    protected View setContentView() {
        return inflater.inflate(R.layout.grabredenvelopemodule_fra_user_layout, null);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
    @Override
    protected void init() {
        for(int i=0;i<icons.length;i++){
            PopModel popModel=new PopModel();
            popModel.setDrawableId(icons[i]);
            popModel.setItemDesc(iconTitles[i]);
            popModel.setSelect(false);
            mPopModels.add(popModel);
        }
    }

    @Override
    protected void setListeners() {
        tvBtnRedPackage.setOnClickListener(this);
        tvRightTitleRight.setOnClickListener(this);
        tvBtnJieFeng.setOnClickListener(this);
    }

    @Override
    protected void setTitleBar() {
        //设置Actionbar
        int StatusHeight = WindowUtils.getStatusHeight((Activity) context);// 获取状态栏高度
        lyToolbarParent.setPadding(0, StatusHeight, 0, 0);
        setActionbarBar("我的", R.color.app_gray, R.color.white, false,false);
        tvRightTitleRight.setVisibility(View.VISIBLE);
        tvRightTitleRight.setCompoundDrawables(ViewUtils.getDrawableSvg(context,R.drawable.drawable_svg_icon_more), null, null, null);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
