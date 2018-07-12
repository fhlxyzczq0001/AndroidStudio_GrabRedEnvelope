package com.ddt.redEnvelope.MVP.Contract.Activity;

import com.ddtkj.commonmodule.Base.Common_BasePresenter;
import com.ddtkj.commonmodule.Base.Common_BaseView;
import com.ddt.redEnvelope.MVP.Model.Bean.RequestBean.Main_WelcomePageBean;

/**启动页接口契约类
 * Created by ${杨重诚} on 2017/6/2.
 */

public interface Main_Act_WelcomePage_Contract {
    interface View extends Common_BaseView {

        /**
         * 启动倒计时
         */
        void startCountDownTimer();

        /**
         * 启动启动页service后台下载
         * @param pageBean
         */
        public void startWelcomePageService(Main_WelcomePageBean pageBean);

        /**
         * 待跳转的下个界面
         */
        public void toNextActivity();
    }

    abstract class Presenter extends Common_BasePresenter<View> {
        @Override
        public void onStart() {

        }
        /**
         * 请求启动页更新
         */
        public abstract void requestStartPageUpdate();
    }
}
