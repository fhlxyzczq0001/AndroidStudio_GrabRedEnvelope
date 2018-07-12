package com.ddtkj.userinfomodule.Base;


import com.ddtkj.commonmodule.BuildConfig;
import com.ddtkj.userinfomodule.Base.Application.UserInfo_Application_Interface;
import com.ddtkj.userinfomodule.Base.Application.release.UserInfo_Application;

/**
 * Created by Administrator on 2017/7/7.
 * 获取当前的application对象
 */

public class UserInfo_Application_Utils {
    static UserInfo_Application_Interface application_interface;

    public static UserInfo_Application_Interface getApplication(){
        if(BuildConfig.IsBuildMudle){
            application_interface = UserInfo_Application.getInstance();
        }else {
            application_interface = UserInfo_Application.getInstance();
        }
        return application_interface;
    }
}
