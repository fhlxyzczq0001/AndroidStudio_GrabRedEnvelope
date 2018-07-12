package com.ddtkj.commonmodule.Receiver;


import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.ddtkj.commonmodule.Base.Common_Application;
import com.ddtkj.commonmodule.MVP.Model.Bean.ResponseBean.Common_UserInfoBean;
import com.ddtkj.commonmodule.MVP.Presenter.Implement.Project.Common_ProjectUtil_Implement;
import com.ddtkj.commonmodule.MVP.Presenter.Interface.Project.Common_ProjectUtil_Interface;
import com.ddtkj.commonmodule.Public.Common_RouterUrl;
import com.ddtkj.commonmodule.R;
import com.ddtkj.commonmodule.Util.Common_SharePer_GlobalInfo;
import com.ddtkj.commonmodule.Util.IntentUtil;
import com.utlis.lib.L;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cn.jpush.android.api.JPushInterface;

import static cn.jpush.android.api.JPushInterface.EXTRA_EXTRA;


/**
 * 极光推送自定义接收器(如果不定义这个 Receiver，则： 1) 默认用户会打开主界面 2) 接收不到自定义消息)
 *
 * @Author: 杨重诚
 * @CreatTime: 2018/1/2  14:22
 */
public class Common_JPushReceiver extends BroadcastReceiver {

    private static final String TAG = "JPush";
    private static boolean actIsTop = false;
    public Context mContext;

    @Override
    public void onReceive(Context context, Intent intent) {
        mContext = context;
        Bundle bundle = intent.getExtras();
        actIsTop = false;
        upActIsTop(context);
        if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            //接受到推送下来的自定义消息
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
          //接受到推送下来的通知
        }else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            // 用户打开了通知
            L.d(TAG, "[Common_JPushReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));
            JPushInterface.reportNotificationOpened(context, bundle.getString(JPushInterface.EXTRA_MSG_ID));
            sendActivity(context, bundle);
        }
    }

    /**
     * 判断当前应用是否在栈顶
     *
     * @param context
     */
    private static void upActIsTop(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasksInfo = activityManager.getRunningTasks(1);
        if (tasksInfo.size() > 0) {
            if (context.getPackageName().equals(tasksInfo.get(0).topActivity.getPackageName())) {
                actIsTop = true;
            }
        }
    }

    // 打印所有的 intent extra 数据  11-11 16:59:55.483: D/JPush(2922): key:cn.jpush.android.EXTRA, value: [通知 - 纯文本]

    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            //			if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
            //				sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            //			} else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
            //				sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            //			} else {
            if (key.equals("cn.jpush.android.EXTRA")) {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }

    /**
     * 处理通知被点击后的界面跳转
     *
     * @param context
     * @param bundle
     */
    public static void sendActivity(Context context, Bundle bundle) {
        upActIsTop(context);
        bundle.putBoolean("isLoging", false);
        Common_SharePer_GlobalInfo.sharePre_PutJpushBundle(bundle);
        if (!actIsTop) {
            intentToLoging(new IntentUtil(), context);
        } else {
            intentActivity(context, bundle);
        }
    }

    private static void intentActivity(Context context, Bundle bundle) {
        if (bundle.getString(EXTRA_EXTRA).isEmpty()) {
            L.i(TAG, "********This message has no Extra data **********");
            return;
        }
        try {
            JSONObject json = new JSONObject(bundle.getString(EXTRA_EXTRA));
            Common_UserInfoBean user = Common_Application.getInstance().getUseInfoVo();
            if (json.keys().hasNext()) {
                String key = json.keys().next().toString();
                String value = json.optString(key);
                IntentUtil intentUtil = new IntentUtil();
                if (key.equals("webPage")) {//网页
                    bundle.putString("barname", context.getResources().getString(R.string.app_name));
                    bundle.putString("url", value);
                    intentTo(intentUtil, context, Common_RouterUrl.MAIN_WebViewRouterUrl, bundle);
                } else if (key.equals("versionUpdat")) {//版本更新
                    //检测更新
                    Common_ProjectUtil_Interface commonProjectUtilInterface = new Common_ProjectUtil_Implement();
                    commonProjectUtilInterface.checkAppVersion(context, "abot", new Common_ProjectUtil_Implement.ResultCheckAppListener() {
                        @Override
                        public void onResult(boolean isUpdata) {
                            Common_SharePer_GlobalInfo.sharePre_PutIsUpdate(isUpdata);//将更新标识为有更新状态
                        }
                    });
                    return;
                } else if (key.equals("LocalInterface")) {//本地界面
                    intentTo(intentUtil, context, value, null);
                }
            }
        } catch (JSONException e) {
            L.e(TAG, "******Get message extra JSON error!******");
        }
    }

    /**
     * 跳转到登录页面
     *
     * @param intentUtil
     * @param context
     */
    static void intentToLoging(IntentUtil intentUtil, Context context) {
        Bundle bundle = Common_SharePer_GlobalInfo.sharePre_GetJpushBundle();
        bundle.putBoolean("isLoging", true);//标识Jpush是需要登录的页面
        Common_SharePer_GlobalInfo.sharePre_PutJpushBundle(bundle);
        intentUtil.intent_newTask_RouterTo(context, Common_RouterUrl.USERINFO_LogingRouterUrl);
    }

    /**
     * 页面跳转
     *
     * @param intentUtil
     * @param context
     * @param bundle
     */
    static void intentTo(IntentUtil intentUtil, Context context, String routerUrl, Bundle bundle) {
        if (bundle != null) {
            intentUtil.intent_newTask_RouterTo(context, routerUrl, bundle);
        } else {
            intentUtil.intent_newTask_RouterTo(context, routerUrl);
        }
        Common_SharePer_GlobalInfo.sharePre_PutJpushBundle(null);//将jpush存放信息设置为null
    }
}
