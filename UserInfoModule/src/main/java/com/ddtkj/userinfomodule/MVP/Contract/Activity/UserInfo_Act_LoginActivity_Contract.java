package com.ddtkj.userinfomodule.MVP.Contract.Activity;

import com.ddtkj.commonmodule.Base.Common_BasePresenter;
import com.ddtkj.commonmodule.Base.Common_BaseView;
import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 *  登录界面
 *
 *  @Author: 杨重诚
 *  @CreatTime: 2018/7/6  14:10
 */

public interface UserInfo_Act_LoginActivity_Contract {

    interface View extends Common_BaseView {
        /**
         * 登录的结果
         * @param isSucceed 是否登录成功
         */
        void loginResult(boolean isSucceed);
    }

    abstract class Presenter extends Common_BasePresenter<View> {
        /**
         * 获取友盟授权
         * @param platform  授权渠道
         */
        public abstract void bindUmengAuth(SHARE_MEDIA platform);
        /**
         * 三方授权后判断是已绑定过手机号
         * @param openId
         * @param openType
         */
        public abstract void canLogin(String openId, String openType);
    }
}
