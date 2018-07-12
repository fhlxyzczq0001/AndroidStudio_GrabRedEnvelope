package com.ddtkj.commonmodule.MVP.Presenter.Interface.Project;

import android.content.Context;

import com.ddtkj.commonmodule.HttpRequest.ResultListener.Common_ResultDataListener;
import com.ddtkj.commonmodule.MVP.Presenter.Implement.Project.Common_UserAll_Presenter_Implement;


/**用户所有信息通用获取接口
 * @author: Administrator 杨重诚
 * @date: 2016/10/12:17:07
 */

public interface Common_UserAll_Presenter_Interface {
    /**
     * 刷新用户信息
     */
    public void refreshUserInfo(Context context, Common_UserAll_Presenter_Implement.RefreshUserInfoListener refreshUserInfoListener, boolean isLoadingDialog);
    /**
     * 获取第三方登录用户信息
     */
    public void refreshOtherLogin(Context context, String content, String loginType, String openId, Common_UserAll_Presenter_Implement.RefreshUserInfoListener refreshUserInfoListener, boolean isLoadingDialog);
    /**
     *  基本校验-是否开通第三方账户与交易密码
     */
    public void requestThirdAndTrade(Context context,Common_ResultDataListener mResultListener);
}
