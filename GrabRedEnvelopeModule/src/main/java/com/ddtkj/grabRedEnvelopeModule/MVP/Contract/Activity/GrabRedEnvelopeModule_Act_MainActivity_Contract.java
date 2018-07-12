package com.ddtkj.grabRedEnvelopeModule.MVP.Contract.Activity;


import com.ddtkj.commonmodule.Base.Common_BasePresenter;
import com.ddtkj.commonmodule.Base.Common_BaseView;
import com.ddtkj.commonmodule.MVP.Model.Bean.ResponseBean.Common_UserInfoBean;


/**
 * 主Activity接口契约类
 * Created by ${杨重诚} on 2017/6/2.
 */

public interface GrabRedEnvelopeModule_Act_MainActivity_Contract {
    interface View extends Common_BaseView {
        /**
         * 刷新用户信息成功
         */
        public void refreshUserInfoSuccess(Common_UserInfoBean userInfoBean);
    }

    abstract class Presenter extends Common_BasePresenter<View> {
        /**
         * 初始化P层
         */
        public abstract void initP();
        /**
         * 用户信息接口是否请求成功
         * @return
         */
        public abstract boolean isRequestUserState();

        /**
         * 退出操作
         */
        public abstract void exit();
        @Override
        public void onStart() {

        }
    }
}
