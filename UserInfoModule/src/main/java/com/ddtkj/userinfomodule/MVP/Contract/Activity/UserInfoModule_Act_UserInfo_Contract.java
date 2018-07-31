package com.ddtkj.userinfomodule.MVP.Contract.Activity;

import com.ddtkj.commonmodule.Base.Common_BasePresenter;
import com.ddtkj.commonmodule.Base.Common_BaseView;

/**
 *  个人信息
 *
 *  @Author: 杨重诚
 *  @CreatTime: 2018/7/6  16:24  
 */
public interface UserInfoModule_Act_UserInfo_Contract {

    interface View extends Common_BaseView {
        /**
         * 提交成功
         * @param msg
         */
        void submitSuccess(String msg);
    }

    abstract class Presenter extends Common_BasePresenter<View> {
        /**
         * 提交
         * @param userName
         * @param userNum
         * @param password
         */
        public abstract void submit(String userName,String phone, String userNum,String bankcode,String bankName,String password,String upcode);

    }
}
