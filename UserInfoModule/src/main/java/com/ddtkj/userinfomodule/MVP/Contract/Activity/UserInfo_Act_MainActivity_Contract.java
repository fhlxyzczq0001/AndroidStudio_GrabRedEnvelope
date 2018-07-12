package com.ddtkj.userinfomodule.MVP.Contract.Activity;


import com.ddtkj.commonmodule.Base.Common_BasePresenter;
import com.ddtkj.commonmodule.Base.Common_BaseView;


/**
 * 主Activity接口契约类
 * Created by ${杨重诚} on 2017/6/2.
 */

public interface UserInfo_Act_MainActivity_Contract {
    interface View extends Common_BaseView {
    }

    abstract class Presenter extends Common_BasePresenter<View> {
        @Override
        public void onStart() {

        }
    }
}
