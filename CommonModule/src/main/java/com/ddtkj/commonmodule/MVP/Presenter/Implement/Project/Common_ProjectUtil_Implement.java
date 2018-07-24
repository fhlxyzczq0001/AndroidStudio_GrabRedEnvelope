package com.ddtkj.commonmodule.MVP.Presenter.Implement.Project;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.CookieManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.ddtkj.commonmodule.Base.Common_Application;
import com.ddtkj.commonmodule.HttpRequest.Common_HttpPath;
import com.ddtkj.commonmodule.HttpRequest.Common_HttpRequestMethod;
import com.ddtkj.commonmodule.HttpRequest.ResultListener.Common_ResultDataListener;
import com.ddtkj.commonmodule.HttpRequest.ResultListener.Common_ResultListener;
import com.ddtkj.commonmodule.Lintener.Common_UploadPicResultListener;
import com.ddtkj.commonmodule.MVP.Model.Bean.EventBusBean.Common_SyncCookie_EventBus;
import com.ddtkj.commonmodule.MVP.Model.Bean.ResponseBean.Common_CookieMapBean;
import com.ddtkj.commonmodule.MVP.Model.Bean.ResponseBean.Common_KeyValue;
import com.ddtkj.commonmodule.MVP.Model.Bean.ResponseBean.Common_RequestBean;
import com.ddtkj.commonmodule.MVP.Model.Bean.ResponseBean.Common_VersionBean;
import com.ddtkj.commonmodule.MVP.Model.Implement.Common_Base_HttpRequest_Implement;
import com.ddtkj.commonmodule.MVP.Model.Interface.Common_Base_HttpRequest_Interface;
import com.ddtkj.commonmodule.MVP.Presenter.Interface.Project.Common_ProjectUtil_Interface;
import com.ddtkj.commonmodule.MVP.Presenter.Interface.Project.Common_UserAll_Presenter_Interface;
import com.ddtkj.commonmodule.Public.Common_RouterUrl;
import com.ddtkj.commonmodule.R;
import com.ddtkj.commonmodule.Util.Common_CustomDialogBuilder;
import com.ddtkj.commonmodule.Util.Common_SharePer_UserInfo;
import com.ddtkj.commonmodule.Util.IntentUtil;
import com.example.permission_library.Permission_ProjectUtil_Interface;
import com.fenjuly.library.ArrowDownloadButton;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.just.agentweb.AgentWebConfig;
import com.utlis.lib.AppUtils;
import com.utlis.lib.FileUtils;
import com.utlis.lib.L;
import com.utlis.lib.OpenFileUtil;
import com.utlis.lib.StringUtils;
import com.utlis.lib.Textutils;
import com.utlis.lib.ToastUtils;
import com.utlis.lib.ToolsUtils;
import com.wang.avi.AVLoadingIndicatorView;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * 应用需求工具类实现
 *
 * @author: Administrator 杨重诚
 * @date: 2016/11/2:14:10
 */

public class Common_ProjectUtil_Implement implements Common_ProjectUtil_Interface {
    Common_Application mCommonApplication;
    IntentUtil intentUtil;
    Common_Base_HttpRequest_Interface mCommon_base_httpRequest_interface;//请求网络数据的model实现类
    boolean isServerState = false;//标识是否启动了维护页面
    String TAG = "Common_ProjectUtil_Implement";
    Common_CustomDialogBuilder customDialogBuilder;
    NiftyDialogBuilder dialogBuilder = null;
    Permission_ProjectUtil_Interface mPermissionProjectUtilInterface;
    Common_UserAll_Presenter_Interface mCommonUserAllPresenterInterface;

    public Common_ProjectUtil_Implement() {
        mCommonApplication = Common_Application.getInstance();
        intentUtil = new IntentUtil();

    }

    public static Common_ProjectUtil_Implement commonProjectUtilInterface;

    public static Common_ProjectUtil_Interface getInstance() {
        if (commonProjectUtilInterface == null) {
            //第一次check，避免不必要的同步
            synchronized (Common_ProjectUtil_Implement.class) {//同步
                if (commonProjectUtilInterface == null) {
                    //第二次check，保证线程安全
                    commonProjectUtilInterface = new Common_ProjectUtil_Implement();
                }
            }
        }
        return commonProjectUtilInterface;
    }

    /**
     * 根据URL跳转不同页面
     *
     * @param context
     * @param menuUrl
     * @param title
     */
    @Override
    public void urlIntentJudge(final Context context, String menuUrl, String title) {
        L.e("menuUrl:", menuUrl);
        final Bundle bundle = new Bundle();
        if (menuUrl.startsWith("http")) {
            //跳转至WebView页面
            if (menuUrl.contains("?barname=")) {
                title = menuUrl.substring(menuUrl.indexOf("?barname=") + "?barname=".length());
                menuUrl = menuUrl.substring(0, menuUrl.indexOf("?barname="));
            } else if (menuUrl.contains("&barname=")) {
                title = menuUrl.substring(menuUrl.indexOf("&barname=") + "&barname=".length());
                menuUrl = menuUrl.substring(0, menuUrl.indexOf("&barname="));
            }
            bundle.putString("barname", title);
            bundle.putString("url", menuUrl);
            bundle.putString("shareLink", "");
            bundle.putString("shareContent", "");
            bundle.putString("shareTitle", "");
            intentUtil.intent_RouterTo(context, Common_RouterUrl.MAIN_WebViewRouterUrl, bundle);
        }else {
            //无需校验，直接跳转
            intentUtil.intent_RouterTo(context, menuUrl);
        }
    }

    public interface SyncCookieListener {
        /**
         * 同步cookie回调
         *
         * @param isSucc
         */
        public void onResult(boolean isSucc);
    }

    /**
     * 将cookie同步到WebView
     *
     * @param mContext
     * @param url
     * @param syncCookieListener
     */
    @Override
    public void syncCookie(final Context mContext, final String url, final SyncCookieListener syncCookieListener) {
        setCookie(mContext, url, syncCookieListener);
    }

    /**
     * 设置Cookie
     *
     * @return
     */
    private void setCookie(Context mContext, String url, SyncCookieListener syncCookieListener) {
        AgentWebConfig.removeAllCookies();
        AgentWebConfig.removeSessionCookies();
        AgentWebConfig.syncCookie(StringUtils.urlSetCookie(url), "platform=app");
        if (mCommonApplication.getUseInfoVo() != null && mCommonApplication.getUseInfoVo().getUserId() != null) {
            AgentWebConfig.syncCookie(StringUtils.urlSetCookie(url), "islogin=true");
            //获取cookie缓存对象
            Common_CookieMapBean cookieMapBean = Common_SharePer_UserInfo.sharePre_GetCookieCache();
            if (null != cookieMapBean) {
                Iterator iter = cookieMapBean.getCookieMap().entrySet().iterator();
                while (iter.hasNext()) {
                     Map.Entry entry = (Map.Entry) iter.next();
                     String key = (String) entry.getKey();
                     HashSet<String> val = (HashSet<String>) entry.getValue();
                    if (null != val && val.size() > 0) {
                        for (String cookie : val) {
                            AgentWebConfig.syncCookie(StringUtils.urlSetCookie(url), cookie);
                        }
                    }
                  }
            }
        }

       /* CookieSyncManager.createInstance(mContext);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeSessionCookie();
        cookieManager.setCookie(StringUtils.urlSetCookie(url), "platform=app");
        if (mCommonApplication.getUseInfoVo() != null && mCommonApplication.getUseInfoVo().getUserId() != null) {
            cookieManager.setCookie(StringUtils.urlSetCookie(url), "islogin=true");
        }
        CookieSyncManager.getInstance().sync();*/


        syncCookieListener.onResult(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //这里发送粘性事件
                EventBus.getDefault().post(new Common_SyncCookie_EventBus(true));
            }
        }, 300);
    }

    /**
     * 请求应用版本最新版本
     *
     * @param source
     * @param resultCheckAppListener
     */
    @Override
    public void checkAppVersion(Context context, String source, ResultCheckAppListener resultCheckAppListener) {
        int code = AppUtils.getAppVersionCode(mCommonApplication.getApplication());
        checkAppVersion(context, checkAppVersion_Params(String.valueOf(code)), source, resultCheckAppListener);
    }

    public Map<String, Object> checkAppVersion_Params(String code) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("currentApkVersions", code);
        return params;
    }

    /**
     * 返回是否更新的标识状态
     */
    public interface ResultCheckAppListener {
        void onResult(boolean isUpdata);
    }

    /**
     * 获取请求接口
     */
    public Common_Base_HttpRequest_Interface getHttpInterface() {
        if (mCommon_base_httpRequest_interface == null) {
            mCommon_base_httpRequest_interface = new Common_Base_HttpRequest_Implement();
        }
        return mCommon_base_httpRequest_interface;
    }

    /**
     * 请求应用版本最新版本
     *
     * @param params
     * @param source 区分进入应用自动请求，手动更新abot
     */
    public void checkAppVersion(final Context context, Map<String, Object> params, final String source, final ResultCheckAppListener resultCheckAppListener) {
        getHttpInterface().requestData(context, Common_HttpPath.URL_API_CHECK_UPDATE, params, new Common_ResultDataListener() {
            @Override
            public void onResult(boolean isSucc, String msg, Common_RequestBean request_bean) {
                Common_VersionBean versionBean = null;
                if (isSucc) {
                    if (request_bean.getData() != null) {
                        JSONObject jsonObject = JSONObject.parseObject(request_bean.getData().toString());
                        versionBean = JSONObject.parseObject(jsonObject.getString("appupdate"),
                                Common_VersionBean.class);
                    }
                    if (versionBean != null) {
                        final String content="最新版本：v"+versionBean.getVersionsNow()+"\n"+
                                "当前版本：v"+AppUtils.getAppVersionName(context)+"\n"+
                                "更新内容："+versionBean.getRefreshContent();
                        final String url = versionBean.getRefreshUrl();
                        if (versionBean.getRefreshCode().equals("1")) {
                            //普通更新
                            showAppUpdateDialog_Common(context, content, url);
                            if (resultCheckAppListener != null)
                                resultCheckAppListener.onResult(true);//将更新标识为有更新状态
                        } else if (versionBean.getRefreshCode().equals("2")) {
                            //强制更新
                            showAppUpdateDialog_Forced(context, content, url);
                            if (resultCheckAppListener != null)
                                resultCheckAppListener.onResult(true);
                        } else if (source.equals("abot")) {
                            ToastUtils.RightImageToast(context, "暂无更新");
                            if (resultCheckAppListener != null)
                                resultCheckAppListener.onResult(false);
                        }
                    }
                }
            }
        }, false, Common_HttpRequestMethod.POST);
    }

    /**
     * 普通更新
     *
     * @param content
     */
    @Override
    public void showAppUpdateDialog_Common(final Context context, String content, final String url) {
        //普通更新

        if (customDialogBuilder == null) {
            customDialogBuilder = new Common_CustomDialogBuilder(context);
            customDialogBuilder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    customDialogBuilder = null;
                    L.e(TAG, "强制更新Dialog对话框被销毁");
                }
            });
        }
        if (customDialogBuilder.isShowing()) {
            L.e(TAG, "强制更新Dialog对话框已显示");
            return;
        } else {
            L.e(TAG, "强制更新Dialog对话框未显示");
        }
        final NiftyDialogBuilder dialogBuilder = customDialogBuilder.showDialog("软件更新", content, R.color.app_text_normal_color, true, "下次再说", R.color.app_text_gray2, "马上更新", R.color.app_color);
        TextView textView1 = (TextView) dialogBuilder.findViewById(R.id.button1);
        textView1.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
        TextView textView2 = (TextView) dialogBuilder.findViewById(R.id.button2);
        textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
        dialogBuilder.setButton2Click(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //马上更新
                L.e("update", "url:---" + url);
                startUpdateService(context, url, 0);
                dialogBuilder.dismiss();
            }
        });
    }

    /**
     * 强制更新
     *
     * @param content
     */
    @Override
    public void showAppUpdateDialog_Forced(final Context context, final String content, final String url) {
        //强制更新
        if (customDialogBuilder == null) {
            customDialogBuilder = new Common_CustomDialogBuilder(context);
            customDialogBuilder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    customDialogBuilder = null;
                    L.e(TAG, "强制更新Dialog对话框被销毁");
                }
            });
        }
        if (customDialogBuilder.isShowing()) {
            L.e(TAG, "强制更新Dialog对话框已显示");
            return;
        } else {
            L.e(TAG, "强制更新Dialog对话框未显示");
        }
        dialogBuilder = customDialogBuilder.showDialog("软件更新", content, R.color.app_text_normal_color, false, "下次再说", R.color.app_text_gray2, "马上更新", R.color.app_color);
        TextView textView1 = (TextView) dialogBuilder.findViewById(R.id.button1);
        textView1.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
        TextView textView2 = (TextView) dialogBuilder.findViewById(R.id.button2);
        textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
        dialogBuilder.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    return true;
                }
                return false;
            }
        });
        dialogBuilder.findViewById(R.id.icon_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //关闭应用
                dialogBuilder.dismiss();
                ((Activity) context).finish();
                System.exit(0);
            }
        });
        dialogBuilder.setButton1Click(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //关闭应用
                dialogBuilder.dismiss();
                ((Activity) context).finish();
                System.exit(0);
            }
        });
        dialogBuilder.setButton2Click(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //关闭应用
                dialogBuilder.withTitle(null)
                        .withDialogColor(context.getResources().getColor(R.color.white))
                        .setCustomView(R.layout.common_popupwindow_download_layout, context)
                        .withButton1Text(null)
                        .withButton2Text(null);
                TextView downloadTitle = (TextView) dialogBuilder.findViewById(R.id.downloadTitle);
                ArrowDownloadButton button = (ArrowDownloadButton) dialogBuilder.findViewById(R.id.arrow_download_button);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //马上更新
                        startUpdateService(context, url, 1);
                    }
                });
                //马上更新
                startUpdateService(context, url, 1);
            }
        });
    }

    /**
     * 获取下载的DialogBuilder
     *
     * @return
     */
    @Override
    public NiftyDialogBuilder getDownloadDialogBuilder() {
        return dialogBuilder;
    }

    /**
     * 启动下载service
     *
     * @param url
     */
    private void startUpdateService(Context context, String url, int isForced) {
        commonProjectUtilInterface = this;
        // 启动service后台下载
        Intent mIntent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("apkUrl", url);
        bundle.putString("apkName", context.getResources().getString(R.string.app_name));
        bundle.putInt("apkIcon", 0);
        bundle.putInt("isForced", isForced);
        mIntent.putExtras(bundle);

        mIntent.setAction(context.getPackageName() + ".Service.UploadService");
        mIntent.setPackage(context.getPackageName());
        context.startService(mIntent);
    }

    /**
     * 设置启动维护页标识的方法
     *
     * @param isServerState
     */
    public void setIsServerState(boolean isServerState) {
        this.isServerState = isServerState;
    }

    /**
     * 设置维护页
     *
     * @param context
     * @param msgs
     */
    @Override
    public void setServerState(final Context context, final String msgs) {
        if (isServerState) {
            return;
        }
        requestServerState(context, new ServerStateResultListener() {
            @Override
            public void onResult(boolean isSucc, String msg, String url) {
                if (Textutils.checkStringNoNull(url)) {
                    //跳转至WebView页面
                    Bundle bundle = new Bundle();
                    bundle.putString("barname", "维护页面");
                    bundle.putString("url", url);
                    new IntentUtil().intent_RouterTo(context, Common_RouterUrl.MAIN_WebViewRouterUrl, bundle);
                } else {
                    ToastUtils.WarnImageToast(context, "" + msg);
                }
            }
        });
    }

    /**
     * 请求维护页回调
     */
    public interface ServerStateResultListener {
        public void onResult(boolean isSucc, String msg, String request);
    }

    /**
     * 请求维护页面
     */
    public void requestServerState(Context context, final ServerStateResultListener serverStateResultListener) {
        setIsServerState(true);
        Map<String, Object> params = new HashMap<>();
        getHttpInterface().requestData(context, Common_HttpPath.URL_API_SERVER_STATUS, params, new Common_ResultDataListener() {
            @Override
            public void onResult(boolean isSucc, String msg, Common_RequestBean request_bean) {
                setIsServerState(false);
                if (isSucc && request_bean != null) {
                    serverStateResultListener.onResult(isSucc, msg, request_bean.getUrl());
                } else {
                    serverStateResultListener.onResult(isSucc, msg, "");
                }
            }
        }, false, Common_HttpRequestMethod.GET);
    }

    /**
     * 是否显示密码
     *
     * @param editText  输入框
     * @param imageView 图形控件
     */
    @Override
    public boolean isShowPassword(boolean isShowPassword, EditText editText, ImageView imageView) {
        if (isShowPassword) {
            //不显示
            isShowPassword = false;
            imageView.setImageResource(R.drawable.drawable_svg_icon_pwd_eye_close);
            // 显示为密码
            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            // 使光标始终在最后位置
            Editable etable = editText.getText();
            Selection.setSelection(etable, etable.length());
        } else {
            //不显示
            isShowPassword = true;
            imageView.setImageResource(R.drawable.drawable_svg_icon_pwd_eye_open);
            // 显示为普通文本
            editText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            // 使光标始终在最后位置
            Editable etable = editText.getText();
            Selection.setSelection(etable, etable.length());
        }
        return isShowPassword;
    }

    /**
     * 获取自定义Dialog
     *
     * @param context
     * @param canceledOnTouchOutside
     * @param content
     * @return
     */
    Dialog shapeLoadingDialog;
    @Override
    public Dialog getDialog(Context context, boolean canceledOnTouchOutside, String content) {
        Dialog mDialog = new Dialog(context, com.mingle.shapeloading.R.style.custom_dialog);
        View mDialogContentView = LayoutInflater.from(context).inflate(com.mingle.shapeloading.R.layout.layout_dialog2, null);
        AVLoadingIndicatorView mLoadingView = (AVLoadingIndicatorView) mDialogContentView.findViewById(com.mingle.shapeloading.R.id.indicator);
        TextView msg = (TextView) mDialogContentView.findViewById(com.mingle.shapeloading.R.id.msg);
        mLoadingView.setIndicator("LineSpinFadeLoaderIndicator");
        mDialog.setContentView(mDialogContentView);
        mDialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
        if (TextUtils.isEmpty(content)) {
            msg.setVisibility(GONE);
        } else {
            msg.setVisibility(VISIBLE);
        }
        msg.setText(content);
        return mDialog;
    }

    /**
     * 请求上传图片对外接口
     *
     * @param context
     * @param uploadPaths
     * @param resultListener
     */
    int uploadPicCount = 0;//记录上传文件数量
    String imageBase64String = "";//图片转String
    //记录图片提交失败时，是否二次提交
    private Map<Integer, Boolean> requestTime = new HashMap<>();

    /**
     * 请求上传图片对外接口
     * @param context 上下文
     * @param uploadPaths 上传图片数组 Common_KeyValue对象存放的是key:上传图片对应顺序 value：上传图片路径
     * @param picType 图片分类 picType '图片分类：01: 公司照片 02:身份证_抵押借款 03：结婚证 04：劳动合同 05：工牌、名片、工作证 06：社保 07:工资卡、常用银行流水 08:房产证 09:行驶证 10:支付宝截图 11:个人信用报告 12:信用卡对账单 13:学历学位证书 14:其他所有贷款协议/凭证 15:经营执照16:税务登记证17:组织机构证18:开户许可证19:贷款卡记录20:个人征信报告21:身份证_信用借款（新增）',
     * @param secondType 1正面 2：反面 3:手持
     * @param resultListener 上传回调接口
     * @param urlUploadFile 上传图片请求接口地址
     */
    @Override
    public void requestUploadFilePic(final Context context, final List<Common_KeyValue<Integer, String>> uploadPaths,final String picType, final String secondType, final Common_UploadPicResultListener resultListener,final String urlUploadFile) {
        if (uploadPicCount == 0) {
            if (shapeLoadingDialog != null)
                shapeLoadingDialog.dismiss();
            shapeLoadingDialog = getDialog(context, false, "正在上传，请勿操作。。。");
            shapeLoadingDialog.show();
        }
        if (uploadPaths == null || uploadPaths.size() <= 0) {
            uploadPicCount = 0;//恢复初始值
            shapeLoadingDialog.dismiss();
            resultListener.onResult(true, false, uploadPicCount, "请至少上传一张图片！", null);
            return;
        }
        L.e("imgPath", uploadPaths.size() + "***" + uploadPicCount);
        //获取图片路径
        String imgPath = uploadPaths.get(uploadPicCount).getValue();
        if (imgPath.startsWith("androidhttp:")) {//如果上传服务器的路径不允许带域名，则在UI界面手动将域名换成androidhttp来标识，目前只在众包项目中特殊处理
            imgPath = imgPath.substring(12);
            uploadPicCount++;
            if (uploadPicCount < uploadPaths.size()) {
                resultListener.onResult(false, true, uploadPaths.get(uploadPicCount - 1).getKey(), "", imgPath);//上传继续，false是未完成，继续上传，true是上传成功
                requestUploadFilePic(context, uploadPaths, picType,secondType, resultListener,urlUploadFile);
            } else {
                resultListener.onResult(true, true, uploadPaths.get(uploadPicCount - 1).getKey(),
                        "", imgPath);//上传完成
                uploadPicCount = 0;//恢复初始值
            }
        } else if (imgPath.startsWith("http")||imgPath.startsWith("https") || imgPath.startsWith("upload") || imgPath.startsWith("/upload")) {//网络路径，无需压缩和提交
            uploadPicCount++;
            if (uploadPicCount < uploadPaths.size()) {
                resultListener.onResult(false, true, uploadPaths.get(uploadPicCount - 1).getKey(), "", imgPath);//上传继续，false是未完成，继续上传，true是上传成功
                requestUploadFilePic(context, uploadPaths, picType,secondType, resultListener,urlUploadFile);
            } else {
                resultListener.onResult(true, true, uploadPaths.get(uploadPicCount - 1).getKey(),
                        "", imgPath);//上传完成
                uploadPicCount = 0;//恢复初始值
            }
        } else {
            //压缩图片
            imageCompression(context, imgPath, new Common_ResultListener() {
                @Override
                public void onResult(boolean isSucc, String msg, String request) {
                    if (isSucc) {
                        imageBase64String = ToolsUtils.GetImageStr(request);
                    } else {
                        imageBase64String = ToolsUtils.GetImageStr(uploadPaths.get(uploadPicCount).getValue());
                    }
                    L.e("************", msg);
                    //请求上传图片的方法
                    requestUploadFilePicMethod(context,uploadPicCount,uploadPaths,picType,secondType,resultListener, urlUploadFile);
                }
            });
        }
    }

    /**
     * 请求上传图片的方法
     * @param uploadPaths
     * @param currentIndex
     * @param resultListener
     */
    private void requestUploadFilePicMethod(final Context context,final int currentIndex,final List<Common_KeyValue<Integer, String>> uploadPaths,final String picType, final String secondType, final Common_UploadPicResultListener resultListener,final String urlUploadFile) {
        //记录上传失败的map对象参数，eg:currentIndex==1,第一次进来，requestTime不包含currentIndex键，所以走requestTime.put(currentIndex, true)，也就是当前图片上传如果失败，可以二次上传；
        //当图片上传失败，再次循环进入该方法，currentIndex==1不变，requestTime包含currentIndex键，所以走requestTime.put(currentIndex, false)，也就是当前图片如果再次上传失败，不可以继续上传；
        if (requestTime.containsKey(currentIndex)) {
            requestTime.put(currentIndex, false);
        } else {
            requestTime.put(currentIndex, true);
        }
        //设置上传图片的请求参数
        setUploadFilePic_Params(imageBase64String,picType,secondType);
        //请求上传图片接口
        requestUploadFilePicByUrl(context, getUploadFilePic_Params(), urlUploadFile, new Common_ResultDataListener() {
            @Override
            public void onResult(boolean isSucc, String msg, Common_RequestBean request_bean) {
                if (isSucc) {
                    //上传成功
                    uploadPicCount++;
                    if (uploadPicCount < uploadPaths.size()) {
                        //如果上传的图片下标<图片总数，继续上传
                        if (null != request_bean)
                            resultListener.onResult(false, true, uploadPaths.get(currentIndex).getKey(), msg, request_bean.getData().toString());//上传继续，false是所有图片上传未完成，继续上传，true是代表当前图片上传成功
                        //继续请求该接口
                        requestUploadFilePic(context, uploadPaths, picType,secondType,resultListener,urlUploadFile);
                    } else {
                        uploadPicCount = 0;//恢复初始值
                        if (null != request_bean)
                            if (shapeLoadingDialog != null)
                                shapeLoadingDialog.dismiss();
                        resultListener.onResult(true, true, uploadPaths.get(currentIndex).getKey(), msg, request_bean.getData().toString());//上传完成
                    }
                } else {
                    //上传失败
                    if (requestTime.containsKey(currentIndex) && requestTime.get(currentIndex)) {
                        //如果当前图片上传失败，则发起二次上传请求
                        requestUploadFilePic(context, uploadPaths, picType,secondType,resultListener,urlUploadFile);
                    } else {
                        //跳过当前上传失败图片，上传下一张图片
                        if (uploadPicCount < uploadPaths.size()) {
                            uploadPicCount++;
                            resultListener.onResult(false, false, currentIndex, msg, null);
                            requestUploadFilePic(context, uploadPaths, picType,secondType,resultListener,urlUploadFile);
                        } else {
                            uploadPicCount = 0;//恢复初始值
                            if (shapeLoadingDialog != null)
                                shapeLoadingDialog.dismiss();
                            resultListener.onResult(true, false, uploadPaths.get(currentIndex).getKey(), msg, null);
                        }
                    }
                }
            }
        });
    }
    /**
     * 设置请求上传图片接口的Params
     * @param imageBase64String 要上传图片的数据，base64编码
     * @param picType           图片分类 picType '图片分类：01: 公司照片 02:身份证_抵押借款 03：结婚证 04：劳动合同 05：工牌、名片、工作证 06：社保 07:工资卡、常用银行流水 08:房产证 09:行驶证 10:支付宝截图 11:个人信用报告 12:信用卡对账单 13:学历学位证书 14:其他所有贷款协议/凭证 15:经营执照16:税务登记证17:组织机构证18:开户许可证19:贷款卡记录20:个人征信报告21:身份证_信用借款（新增）',
     * @param secondType        1正面 2：反面 3:手持
     * @return
     */
    //记录图片上参数的对象
    Map<String, Object> paramsUploadFile;
    private Map<String, Object> setUploadFilePic_Params(String imageBase64String, String picType, String secondType) {
        if(paramsUploadFile==null){
            paramsUploadFile = new HashMap<String, Object>();
        }
        if(!TextUtils.isEmpty(imageBase64String)){
            paramsUploadFile.put("imageBase64String", imageBase64String);//要上传图片的数据，base64编码
        }
        if(!TextUtils.isEmpty(picType)){
            paramsUploadFile.put("picType", picType);//图片分类
        }
        if (!TextUtils.isEmpty(secondType)){
            paramsUploadFile.put("secondType", secondType);//1正面 2：反面 3:手持
        }
        return paramsUploadFile;
    }

    /**
     * 获取上传图片的请求参数
     * @return
     */
    private  Map<String, Object> getUploadFilePic_Params(){
        if(paramsUploadFile==null){
            paramsUploadFile = new HashMap<String, Object>();
        }
        return paramsUploadFile;
    }

    /**
     * 请求上传图片接口
     * @param context 上下文
     * @param params 请求参数
     * @param url 上传图片路径
     * @param uploadListener 上传回调
     */
    private void requestUploadFilePicByUrl(Context context, Map<String, Object> params, String url, final Common_ResultDataListener uploadListener) {
        if (null == url || url.trim().length() == 0) {
            url = Common_HttpPath.URL_API_UPLOAD_FILE_PIC;
        }
        getHttpInterface().requestData(context, url, params, uploadListener, false, Common_HttpRequestMethod.POST);
    }

    /**
     * 图片压缩方法
     *
     * @param context
     * @param path
     * @param resultStringListener
     */
    public void imageCompression(Context context, String path, final Common_ResultListener resultStringListener) {
        Luban.get(context)
                .load(new File(path))                     //传人要压缩的图片
                .putGear(Luban.THIRD_GEAR)      //设定压缩档次，默认三挡
                .setCompressListener(new OnCompressListener() { //设置回调

                    @Override
                    public void onStart() {
                        // TODO 压缩开始前调用，可以在方法内启动 loading UI
                        L.e("**************", "压缩图片开始");
                    }

                    @Override
                    public void onSuccess(File file) {
                        // TODO 压缩成功后调用，返回压缩后的图片文件
                        resultStringListener.onResult(true, "压缩成功", file.getAbsolutePath());
                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO 当压缩过去出现问题时调用
                        resultStringListener.onResult(false, "压缩失败", e.getMessage());
                    }
                }).launch();    //启动压缩
    }


    /**
     * 退出登录
     */
    @Override
    public void logOut() {
        Common_Application.getInstance().setUserInfoBean(null);
        L.e("tag", "退出登录");
        //清空5.0用户信息表
        Common_Application.getInstance().getUserSharedPreferences().clear();
        CookieManager.getInstance().removeAllCookie();
    }

    public void onDestroy() {
        mCommonApplication = null;
        /*if (dialogBuilder != null) {
            if(!dialogBuilder.isShowing())
            dialogBuilder.dismiss();
            dialogBuilder = null;
        }*/
        commonProjectUtilInterface = null;
    }

    /**
     * 下载文件
     *
     * @param context
     * @param saveFilePath
     * @param uploadFilePath
     */
    @Override
    public void downFile(final Context context, final String saveFilePath, final String uploadFilePath) {
        Common_CustomDialogBuilder customDialogBuilder = new Common_CustomDialogBuilder(context);
        final NiftyDialogBuilder dialogBuilder = customDialogBuilder.showDialog("文件下载", "确定下载该文件？", R.color.app_text_normal_color, false, "取消", R.color.app_text_gray1, "确定", R.color.app_color);
        TextView textView1 = (TextView) dialogBuilder.findViewById(R.id.button1);
        textView1.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
        TextView textView2 = (TextView) dialogBuilder.findViewById(R.id.button2);
        textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
        dialogBuilder.setButton2Click(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //确定下载
                downFile(context, saveFilePath, uploadFilePath, dialogBuilder);
            }
        });
    }

    /**
     * 下载文件
     *
     * @param context
     * @param saveFilePath
     * @param uploadFilePath
     * @param dialogBuilder
     */
    private void downFile(final Context context, String saveFilePath, String uploadFilePath, final NiftyDialogBuilder dialogBuilder) {
        //下载文件路径地址
        final String filePath = saveFilePath + "/" + uploadFilePath.substring(uploadFilePath.lastIndexOf("/") + 1);
        if (!new File(saveFilePath).exists()) {
            //文件夹不存在
            try {
                FileUtils.createSDCardDir(saveFilePath);// 创建文件夹
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return;
            }
        } else {
            //文件夹存在，判断文件是否存在
            File dir = new File(filePath);
            if (dir.exists()) {
                AppUtils.openFile(context, OpenFileUtil.openFile(context, filePath));
                dialogBuilder.dismiss();
                return;
            }
        }
        dialogBuilder.withTitle(null)
                .withDialogColor(context.getResources().getColor(R.color.white))
                .setCustomView(R.layout.common_popupwindow_download_layout, context)
                .withButton1Text(null)
                .withButton2Text(null);
        dialogBuilder.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    return true;
                }
                return false;
            }
        });
        getHttpInterface().FileDownloader(filePath, uploadFilePath, new Common_ResultDataListener() {
            @Override
            public void onResult(boolean isSucc, String msg, Common_RequestBean request_bean) {
                if (isSucc) {
                }
            }
        }, new Common_Base_HttpRequest_Implement.ProgressResultListener() {
            @Override
            public void onProgressChange(long fileSize, long downloadedSize) {
                TextView downloadTitle = (TextView) dialogBuilder.findViewById(R.id.downloadTitle);
                ArrowDownloadButton downloadButton = (ArrowDownloadButton) dialogBuilder.findViewById(R.id.arrow_download_button);
                if (downloadButton != null) {
                    downloadTitle.setText("正在下载，请耐心等待...");
                    downloadButton.setEnabled(false);
                    downloadButton.startAnimating();
                    downloadButton.setProgress((int) (downloadedSize * 1.0f / fileSize * 100));
                    if ((int) (downloadedSize * 1.0f / fileSize * 100) == 100) {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                //已在主线程中，可以更新UI
                                AppUtils.openFile(context, OpenFileUtil.openFile(context, filePath));
                                dialogBuilder.dismiss();
                            }
                        });
                        /*downloadButton.setEnabled(true);
                        downloadTitle.setText("重新下载");*/
                    }
                }
            }
        });
    }
    /**
     * 登录状态
     *
     * @return
     */
    @Override
    public boolean logingStatus() {
        return (null == mCommonApplication.getUseInfoVo() || null == mCommonApplication.getUseInfoVo() || mCommonApplication.getUseInfoVo().getUserId().isEmpty()) ? false : true;
    }
}
