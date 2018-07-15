package com.ddtkj.grabRedEnvelopeModule.MVP.Contract.Fragment;

import com.ddtkj.commonmodule.Base.Common_BasePresenter;
import com.ddtkj.commonmodule.Base.Common_BaseView;
import com.ddtkj.commonmodule.MVP.Model.Bean.ResponseBean.Common_UserInfoBean;

/**
 *  我的Fragment契约类
 *
 *  @Author: 杨重诚
 *  @CreatTime: 2018/7/4  14:02  
 */

public interface GrabRedEnvelopeModule_Fra_User_Contract {
    interface View extends Common_BaseView {
        public void setUserInfoData(Common_UserInfoBean userInfoData);
    }

    abstract class Presenter extends Common_BasePresenter<View> {
        public abstract void refreshUserInfo();
        /**
         * 意见反馈
         * @param content
         */
        public abstract void requestUserideas(String content);
    }
}
