package com.ddtkj.commonmodule.MVP.Presenter.Implement.Project;


import android.content.Context;
import android.webkit.JavascriptInterface;

import com.ddtkj.commonmodule.Base.Common_Application;
import com.ddtkj.commonmodule.MVP.Contract.Activity.Common_Act_WebView_Contract;
import com.ddtkj.commonmodule.MVP.Model.Implement.Common_Base_HttpRequest_Implement;
import com.ddtkj.commonmodule.MVP.Model.Interface.Common_Base_HttpRequest_Interface;
import com.ddtkj.commonmodule.MVP.Presenter.Interface.Project.Common_ProjectUtil_Interface;
import com.ddtkj.commonmodule.MVP.Presenter.Interface.Project.Common_UserAll_Presenter_Interface;
import com.ddtkj.commonmodule.MVP.Presenter.Interface.Project.Common_WebView_Presenter_Javascript_Interface;
import com.ddtkj.commonmodule.Public.Common_RouterUrl;
import com.ddtkj.commonmodule.Util.Common_CustomDialogBuilder;
import com.utlis.lib.AppUtils;


/**
 * webview与服务器js交互的本地方法
 *
 * @ClassName: com.ygworld.MVP.Presenter.Implement
 * @author: Administrator 杨重诚
 * @date: 2016/11/2:17:34
 */

public class Common_WebView_Presenter_Javascript_Implement implements Common_WebView_Presenter_Javascript_Interface {
    Context mContext;
    Common_ProjectUtil_Interface mCommonProjectUtilInterface;
    Common_Application mApplication;
    Common_Act_WebView_Contract.View mCommonActWebViewViewInterface;
    Common_Base_HttpRequest_Interface mCommonBaseHttpRequestInterface;
    Common_CustomDialogBuilder common_customDialogBuilder;
    Common_UserAll_Presenter_Interface mCommonUserAllPresenterInterface;

    public Common_WebView_Presenter_Javascript_Implement(Context context, Common_Act_WebView_Contract.View view) {
        this.mContext = context;
        mCommonProjectUtilInterface = new Common_ProjectUtil_Implement();
        mApplication = Common_Application.getInstance();
        this.mCommonActWebViewViewInterface = view;
        mCommonBaseHttpRequestInterface = new Common_Base_HttpRequest_Implement();
        mCommonUserAllPresenterInterface = new Common_UserAll_Presenter_Implement();
    }

    /**
     * 获取用户id
     */
    @JavascriptInterface
    @Override
    public String getUserID() {
        if (!mCommonProjectUtilInterface.logingStatus()) {
            mCommonProjectUtilInterface.urlIntentJudge(mContext, Common_RouterUrl.USERINFO_LogingRouterUrl, "");
        } else {
            return mApplication.getUseInfoVo().getUserId();
        }
        return null;
    }

    /**
     * 获取安卓应用版本号
     */
    @JavascriptInterface
    @Override
    public int getVersion() {
        return AppUtils.getAppVersionCode(mContext);
    }

    /**
     * 打开登录界面
     */
    @JavascriptInterface
    @Override
    public void toLogin() {
        mCommonProjectUtilInterface.urlIntentJudge(mContext, Common_RouterUrl.USERINFO_LogingRouterUrl, "");
    }
}
