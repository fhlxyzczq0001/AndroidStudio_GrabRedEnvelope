package com.ddtkj.grabRedEnvelopeModule.Base;


import com.ddtkj.commonmodule.BuildConfig;
import com.ddtkj.grabRedEnvelopeModule.Base.Application.GrabRedEnvelopeModule_Application_Interface;
import com.ddtkj.grabRedEnvelopeModule.Base.Application.debug.GrabRedEnvelopeModule_Application;

/**
 * Created by Administrator on 2017/7/7.
 * 获取当前的application对象
 */

public class GrabRedEnvelopeModule_Application_Utils {
    static GrabRedEnvelopeModule_Application_Interface application_interface;

    public static GrabRedEnvelopeModule_Application_Interface getApplication(){
        if(BuildConfig.IsBuildMudle){
            application_interface = GrabRedEnvelopeModule_Application.getInstance();
        }else {
            application_interface = com.ddtkj.grabRedEnvelopeModule.Base.Application.release.GrabRedEnvelopeModule_Application.getInstance();
        }
        return application_interface;
    }
}
