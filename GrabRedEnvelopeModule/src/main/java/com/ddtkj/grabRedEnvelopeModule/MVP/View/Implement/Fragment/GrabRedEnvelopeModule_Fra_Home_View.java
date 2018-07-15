package com.ddtkj.grabRedEnvelopeModule.MVP.View.Implement.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;

import com.ddtkj.commonmodule.MVP.Presenter.Implement.Project.Common_ProjectUtil_Implement;
import com.ddtkj.commonmodule.MVP.Presenter.Interface.Project.Common_ProjectUtil_Interface;
import com.ddtkj.commonmodule.Public.Common_RouterUrl;
import com.ddtkj.grabRedEnvelopeModule.Base.GrabRedEnvelopeModule_BaseFragment;
import com.ddtkj.grabRedEnvelopeModule.MVP.Contract.Fragment.GrabRedEnvelopeModule_Fra_Home_Contract;
import com.ddtkj.grabRedEnvelopeModule.MVP.Presenter.Implement.Fragment.GrabRedEnvelopeModule_Fra_Home_Presenter;
import com.ddtkj.grabRedEnvelopeModule.R;
import com.utlis.lib.ViewUtils;
import com.utlis.lib.WindowUtils;

/**
 *  首页——View
 *
 *  @Author: 杨重诚
 *  @CreatTime: 2018/7/4  14:06  
 */
public class GrabRedEnvelopeModule_Fra_Home_View extends GrabRedEnvelopeModule_BaseFragment<GrabRedEnvelopeModule_Fra_Home_Contract.Presenter, GrabRedEnvelopeModule_Fra_Home_Presenter> implements GrabRedEnvelopeModule_Fra_Home_Contract.View {
    //红包
   ImageView imgBtnRedPrice3;
   ImageView imgBtnRedPrice7;
   ImageView imgBtnRedPrice10;
   ImageView imgBtnRedPrice20;
    Common_ProjectUtil_Interface mProjectUtilInterface;
    @Override
    public void onClick(View v) {
        super.onClick(v);
        if(v.getId()==R.id.imgBtnRedPrice3){
            redImageOnclick("3");
        }else if(v.getId()==R.id.imgBtnRedPrice7){
            redImageOnclick("7");
        }else if(v.getId()==R.id.imgBtnRedPrice10){
            redImageOnclick("10");
        }else if(v.getId()==R.id.imgBtnRedPrice20){
            redImageOnclick("20");
        }else if(v.getId()==R.id.tvRightTitleRight){
            //公告
            getIntentTool().intent_RouterTo(context,Common_RouterUrl.GRAB_RED_ENVELOPE_AnnouncementListRouterUrl);
        }
    }
    public static Fragment newInstance(Bundle bundle) {
        GrabRedEnvelopeModule_Fra_Home_View fragment = new GrabRedEnvelopeModule_Fra_Home_View();
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    protected void initMyView() {
         imgBtnRedPrice3=public_view.findViewById(R.id.imgBtnRedPrice3);
         imgBtnRedPrice7=public_view.findViewById(R.id.imgBtnRedPrice7);
         imgBtnRedPrice10=public_view.findViewById(R.id.imgBtnRedPrice10);
         imgBtnRedPrice20=public_view.findViewById(R.id.imgBtnRedPrice20);
    }

    @Override
    protected View setContentView() {
        return inflater.inflate(R.layout.grabredenvelopemodule_fra_home_layout, null);
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
        mProjectUtilInterface=new Common_ProjectUtil_Implement();
    }

    @Override
    protected void setListeners() {
        imgBtnRedPrice3.setOnClickListener(this);
        imgBtnRedPrice7.setOnClickListener(this);
        imgBtnRedPrice10.setOnClickListener(this);
        imgBtnRedPrice20.setOnClickListener(this);
        tvRightTitleRight.setOnClickListener(this);
    }

    @Override
    protected void setTitleBar() {
        //设置Actionbar
        int StatusHeight = WindowUtils.getStatusHeight((Activity) context);// 获取状态栏高度
        lyToolbarParent.setPadding(0, StatusHeight, 0, 0);
        setActionbarBar("红包", R.color.app_gray, R.color.white, false,false);
        settvTitleStr(tvRightTitleRight,"公告",R.color.white);
        tvRightTitleRight.setCompoundDrawables(null, null, ViewUtils.getDrawableSvg(context, R.drawable.drawable_svg_icon_right_jiantou_white,(int)context.getResources().getDimension(R.dimen.x15),(int)context.getResources().getDimension(R.dimen.x15)), null);
    }

    /**
     * 红包点击事件
     * @param redPrice
     */
    private void redImageOnclick(String redPrice){
        if(!mProjectUtilInterface.logingStatus()){
            getIntentTool().intent_RouterTo(context, Common_RouterUrl.USERINFO_LogingRouterUrl);
            return;
        }
        Bundle bundle=new Bundle();
        bundle.putString("category",redPrice);
        getIntentTool().intent_RouterTo(context,Common_RouterUrl.GRAB_RED_ENVELOPE_RedRoomListRouterUrl,bundle);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
