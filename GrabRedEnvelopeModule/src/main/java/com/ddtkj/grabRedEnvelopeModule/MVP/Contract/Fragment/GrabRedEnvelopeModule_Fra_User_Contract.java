package com.ddtkj.grabRedEnvelopeModule.MVP.Contract.Fragment;

import com.ddtkj.commonmodule.Base.Common_BasePresenter;
import com.ddtkj.commonmodule.Base.Common_BaseView;

/**
 *  我的Fragment契约类
 *
 *  @Author: 杨重诚
 *  @CreatTime: 2018/7/4  14:02  
 */

public interface GrabRedEnvelopeModule_Fra_User_Contract {
    interface View extends Common_BaseView {
    }

    abstract class Presenter extends Common_BasePresenter<View> {
    }
}
