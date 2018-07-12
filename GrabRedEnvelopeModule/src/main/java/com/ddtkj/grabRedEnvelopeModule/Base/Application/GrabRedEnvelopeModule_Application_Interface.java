package com.ddtkj.grabRedEnvelopeModule.Base.Application;

import android.app.Application;
import android.content.Context;

import com.alibaba.sdk.android.httpdns.HttpDnsService;
import com.ddtkj.commonmodule.MVP.Model.Bean.ResponseBean.Common_UserInfoBean;
import com.utlis.lib.SharePre;

/**
 * Created by Administrator on 2017/7/7.
 */

public interface GrabRedEnvelopeModule_Application_Interface {

    /**
     * 获取application对象
     * @return
     */
    Application getApplication();

    /**
     * 获取用户sharePre对象
     * @return
     */
    SharePre getUserSharedPreferences();

    /**
     * 获取全局sharePre对象
     * @return
     */
    SharePre getGlobalSharedPreferences();

    /**
     * 获取用户信息
     * @return
     */
    Common_UserInfoBean getUseInfoVo();

    /**
     * 设置用户信息
     * @param useInfoVo
     */
    void setUseInfoVo(Common_UserInfoBean useInfoVo);

    /**
     * 加载module的配置信息
     */
    void requestProfile(Context mContext);
    /**
     * 设置HttpDnsService
     */
    public HttpDnsService getHttpDnsService();
}
