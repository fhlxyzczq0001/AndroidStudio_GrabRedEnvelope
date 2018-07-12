package com.ddt.redEnvelope.MVP.Presenter.Interface.Project;

import android.content.Context;

import com.ddtkj.commonmodule.MVP.Presenter.Implement.Project.Common_ProjectUtil_Implement;

/**项目相关的工具类接口
 * @author: Administrator 杨重诚
 * @date: 2016/11/2:14:09
 */

public interface Main_ProjectUtil_Interface {
    /**
     * 根据URL跳转不同页面
     * @param context
     * @param menuUrl
     * @param title
     */
    public void urlIntentJudge(Context context, String menuUrl, String title);
    /**
     * 将cookie同步到WebView
     * @return true 同步cookie成功，false同步cookie失败
     * @Author JPH
     */
    public void syncCookie(Context mContext, String url, Common_ProjectUtil_Implement.SyncCookieListener syncCookieListener);
}
