package com.ddtkj.commonmodule.MVP.Contract.Activity;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.webkit.ValueCallback;

import com.ddtkj.commonmodule.Base.Common_BasePresenter;
import com.ddtkj.commonmodule.Base.Common_BaseView;
import com.ddtkj.commonmodule.MVP.Presenter.Interface.Project.Common_WebView_Presenter_Javascript_Interface;

import mvpdemo.com.unmeng_share_librarys.UmengShareBean;

/**
 *  Common_Act_WebView_Contract
 *
 *  @Author: 杨重诚
 *  @CreatTime: 2018/3/6  9:56  
 */

public interface Common_Act_WebView_Contract {
    interface View extends Common_BaseView {
        /**
         * webview重新加载
         */
        public void reload();

        /**
         * webview加载
         * @return
         */
        public void webViewLoadUrl(String url);
        /**
         * 获取js接口
         * @return
         */
        public Common_WebView_Presenter_Javascript_Interface getJavascriptInterface();
        /**
         * 获取Handler
         * @return
         */
        public Handler getHandler();
        /**
         * 友盟分享调用接口
         * @param shareParameterBean
         */
        public void openShare(UmengShareBean shareParameterBean);
        /**
         * 分享
         * @param shareJsonStr
         */
        public void setShareData(String shareJsonStr);
        /**
         * 隐藏分享按钮
         */
        public void hideAcbarBtnShare();
        /**
         * 显示分享按钮
         */
        public void showAcbarBtnShare();
    }
    abstract class Presenter extends Common_BasePresenter<View> {
        /**
         * 上传图片
         * @param data
         */
        public abstract void uploadImage(Intent data, ValueCallback mUploadMessage, Bitmap uploadBitmap);
        @Override
        public void onStart() {

        }
    }
}
