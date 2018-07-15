package com.ddtkj.userinfomodule.MVP.Contract.Activity;

import com.ddtkj.commonmodule.Base.Common_BasePresenter;
import com.ddtkj.commonmodule.Base.Common_BaseView;

/**
 *  修改密码
 *
 *  @Author: 杨重诚
 *  @CreatTime: 2018/7/14  18:41  
 */
public interface UserInfoModule_Act_ChangePassword_Contract {

    interface View extends Common_BaseView {
        /**
         * 修改成功
         * @param msg
         */
        void changePasswordSuccess(String msg);
    }

    abstract class Presenter extends Common_BasePresenter<View> {
        /**
         * 提现
         */
        public abstract void submit(String password1,String password2);
    }
}
