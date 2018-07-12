package com.ddt.redEnvelope.MVP.Presenter.Implement.Project;

import android.content.Context;

import com.ddt.redEnvelope.MVP.Presenter.Interface.Project.Main_ProjectUtil_Interface;
import com.ddtkj.commonmodule.MVP.Presenter.Implement.Project.Common_ProjectUtil_Implement;
import com.ddtkj.commonmodule.MVP.Presenter.Interface.Project.Common_ProjectUtil_Interface;

/**
 * 应用需求工具类实现
 *
 * @author: Administrator 杨重诚
 * @date: 2016/11/2:14:10
 */

public class Main_ProjectUtil_Implement implements Main_ProjectUtil_Interface {
    Common_ProjectUtil_Interface mCommon_projectUtil_interface;
    public Main_ProjectUtil_Implement() {
        mCommon_projectUtil_interface=new Common_ProjectUtil_Implement();
    }

    /**
     * 根据URL跳转不同页面
     *
     * @param context
     * @param menuUrl
     * @param title
     */
    @Override
    public void urlIntentJudge(Context context, String menuUrl, String title) {
        mCommon_projectUtil_interface.urlIntentJudge(context,menuUrl,title);
    }
    /**
     * 将cookie同步到WebView
     * @param mContext
     * @param url
     * @param syncCookieListener
     */
    @Override
    public void syncCookie(final Context mContext, final String url, final  Common_ProjectUtil_Implement.SyncCookieListener syncCookieListener) {
        mCommon_projectUtil_interface.syncCookie(mContext, url, syncCookieListener);
    }
}
