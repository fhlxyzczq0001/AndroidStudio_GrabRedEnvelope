package com.ddtkj.userinfomodule.MVP.Contract.Activity;

import com.ddtkj.commonmodule.Base.Common_BasePresenter;
import com.ddtkj.commonmodule.Base.Common_BaseView;
import com.ddtkj.commonmodule.MVP.Model.Bean.ResponseBean.Common_UserInfoBean;

/**
 *  提现
 *
 *  @Author: 杨重诚
 *  @CreatTime: 2018/7/14  18:41  
 */
public interface UserInfoModule_Act_Withdraw_Contract {

    interface View extends Common_BaseView {
        /**
         * 提现成功
         * @param msg
         */
        void withdrawSuccess(String msg);

        public void setData(Common_UserInfoBean bean);
    }

    abstract class Presenter extends Common_BasePresenter<View> {
        /**
         * 提现
         */
        public abstract void submit(String money,String password);
        public abstract void refreshUserInfo();
    }
}
