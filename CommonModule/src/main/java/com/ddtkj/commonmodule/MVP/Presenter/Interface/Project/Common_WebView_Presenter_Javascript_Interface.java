package com.ddtkj.commonmodule.MVP.Presenter.Interface.Project;

import android.webkit.JavascriptInterface;

/**webview与服务器js交互的本地方法
 * @ClassName: com.ygworld.MVP.Presenter.Interface
 * @author: Administrator 杨重诚
 * @date: 2016/11/2:17:33
 */

public interface Common_WebView_Presenter_Javascript_Interface {
    /**
     * 获取用户id
     */
    @JavascriptInterface
    public String getUserID();
    /**
     * 获取安卓应用版本号
     */
    @JavascriptInterface
    public int getVersion();
    /**
     * 打开登录界面
     */
    @JavascriptInterface
    public void toLogin();
}
