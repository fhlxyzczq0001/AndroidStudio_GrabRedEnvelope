package com.ddtkj.commonmodule.Public;

import android.os.Environment;

import com.ddtkj.commonmodule.Base.Common_Application;

/**
 * SD卡文件存储路径
 * @ClassName: SD_FilePath 
 * @Description: TODO
 * @author: yzc
 * @date: 2016-4-24 上午10:40:50
 */
public class Common_SD_FilePath {
    public static String appSdcardDir = Environment.getExternalStorageDirectory()
            .getPath()+"/"+ Common_Application.getApplication().getPackageName();// 应用SD卡根目录绝对路径
    public static String welcomePagePath = appSdcardDir+"/welcomePagePath";// 启动页保存路径
    public static String apkPath = appSdcardDir+"/apkPath";// apk下载保存路径
    public static String homeNavigationBjPath = appSdcardDir+"/homeNavigImg/bottom_bj.jpg";// 首页底部导航按钮背景
}
