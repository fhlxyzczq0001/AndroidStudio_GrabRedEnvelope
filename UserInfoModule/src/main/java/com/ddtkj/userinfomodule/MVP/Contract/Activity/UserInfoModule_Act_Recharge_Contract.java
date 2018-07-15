package com.ddtkj.userinfomodule.MVP.Contract.Activity;

import com.ddtkj.commonmodule.Base.Common_BasePresenter;
import com.ddtkj.commonmodule.Base.Common_BaseView;

/**
 *  充值
 *
 *  @Author: 杨重诚
 *  @CreatTime: 2018/7/14  18:41  
 */
public interface UserInfoModule_Act_Recharge_Contract {

    interface View extends Common_BaseView {
        /**
         * 充值成功
         * @param msg
         */
        void rechargeSuccess(String msg);

    }

    abstract class Presenter extends Common_BasePresenter<View> {
        /**
         * 充值
         */
        public abstract void recharge(String money);
    }
}
