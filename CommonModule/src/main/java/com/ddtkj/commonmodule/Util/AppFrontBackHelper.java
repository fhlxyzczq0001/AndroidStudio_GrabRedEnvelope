package com.ddtkj.commonmodule.Util;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/**
 * 创建日期：2018-03-21 on 10:55
 * 描述:应用前后台切换状态监听帮助类，只能在Application中使用
 * 作者:张帆 Administrator
 */
public class AppFrontBackHelper {

    private OnAppStatusListener mOnAppStatusListener;

    public AppFrontBackHelper() {
    }

    /**
     * 注册状态监听，仅在Application中使用
     * @param application
     * @param listener
     */
    public void register(Application application, OnAppStatusListener listener){
        mOnAppStatusListener = listener;
        application.registerActivityLifecycleCallbacks(activityLifecycleCallbacks);
    }

    public void unRegister(Application application){
        application.unregisterActivityLifecycleCallbacks(activityLifecycleCallbacks);
    }

    private Application.ActivityLifecycleCallbacks activityLifecycleCallbacks = new Application.ActivityLifecycleCallbacks() {
        //打开的Activity数量统计
        private int activityStartCount = 0;

        @Override
        public void onActivityCreated(Activity activity, Bundle bundle) {

        }

        @Override
        public void onActivityStarted(Activity activity) {
            activityStartCount++;
            if (activityStartCount == 1){
                //从后台切到前台
                if(mOnAppStatusListener != null){
                    mOnAppStatusListener.onFront();
                }
            }
        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {
            activityStartCount--;
            //数值从1到0说明是从前台切到后台
            if (activityStartCount == 0){
                //从前台切到后台
                if(mOnAppStatusListener != null){
                    mOnAppStatusListener.onBack();
                }
            }
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {

        }
    };

    public interface OnAppStatusListener{
        void onFront();//从后台切到前台
        void onBack();//从前台切到后台
    }
}
