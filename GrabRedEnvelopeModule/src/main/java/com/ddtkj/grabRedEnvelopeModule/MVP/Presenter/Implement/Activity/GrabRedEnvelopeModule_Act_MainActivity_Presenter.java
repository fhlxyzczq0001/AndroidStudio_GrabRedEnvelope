package com.ddtkj.grabRedEnvelopeModule.MVP.Presenter.Implement.Activity;


import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.ddtkj.commonmodule.Base.Common_Application;
import com.ddtkj.commonmodule.MVP.Model.Bean.ResponseBean.Common_CookieMapBean;
import com.ddtkj.commonmodule.MVP.Model.Bean.ResponseBean.Common_UserInfoBean;
import com.ddtkj.commonmodule.MVP.Model.Implement.Common_Base_HttpRequest_Implement;
import com.ddtkj.commonmodule.MVP.Model.Interface.Common_Base_HttpRequest_Interface;
import com.ddtkj.commonmodule.MVP.Presenter.Implement.Project.Common_UserAll_Presenter_Implement;
import com.ddtkj.commonmodule.MVP.Presenter.Interface.Project.Common_UserAll_Presenter_Interface;
import com.ddtkj.commonmodule.Util.Common_SharePer_UserInfo;
import com.ddtkj.grabRedEnvelopeModule.Base.Application.GrabRedEnvelopeModule_Application_Interface;
import com.ddtkj.grabRedEnvelopeModule.Base.GrabRedEnvelopeModule_Application_Utils;
import com.ddtkj.grabRedEnvelopeModule.MVP.Contract.Activity.GrabRedEnvelopeModule_Act_MainActivity_Contract;
import com.utlis.lib.ToastUtils;

/**
 * Created by ${杨重诚} on 2017/6/2.
 */

public class GrabRedEnvelopeModule_Act_MainActivity_Presenter extends GrabRedEnvelopeModule_Act_MainActivity_Contract.Presenter {
    Common_Base_HttpRequest_Interface common_base_httpRequest_interface;
    Common_UserAll_Presenter_Interface mCommonUserAllPresenterInterface;
    GrabRedEnvelopeModule_Application_Interface mJsdLoanModule2ApplicationInterface;

    boolean isExit;//返回按钮退出的监听标识

    public GrabRedEnvelopeModule_Act_MainActivity_Presenter() {
        common_base_httpRequest_interface = new Common_Base_HttpRequest_Implement();
        mJsdLoanModule2ApplicationInterface = GrabRedEnvelopeModule_Application_Utils.getApplication();
    }

    /**
     * 初始化P层
     */
    @Override
    public void initP() {
        Common_CookieMapBean setCookie_MapBean = Common_SharePer_UserInfo.sharePre_GetCookieCache();
        if (setCookie_MapBean != null) {

            if (mCommonUserAllPresenterInterface == null)
                mCommonUserAllPresenterInterface = new Common_UserAll_Presenter_Implement();
            //刷新用户信息
            mCommonUserAllPresenterInterface.refreshUserInfo(context, new Common_UserAll_Presenter_Implement.RefreshUserInfoListener() {
                @Override
                public void requestListener(boolean isSucc, Common_UserInfoBean userInfoBean) {
                    if (isSucc) {
                        Common_Application.getInstance().setUserInfoBean(userInfoBean);
                    }
                }
            }, true);
        }
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            isExit = false;
        }
    };

    @Override
    public void exit() {
        if (!isExit) {
            isExit = true;
            ToastUtils.RightImageToast(context, "再次返回退出程序", "show");
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            final Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    context.startActivity(intent);
                    System.exit(0);
                }
            }, 300);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
