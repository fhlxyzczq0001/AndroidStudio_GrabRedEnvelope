package com.ddt.redEnvelope.Util;

import com.alibaba.fastjson.JSON;
import com.ddt.redEnvelope.Base.My_Application;
import com.ddtkj.commonmodule.Public.Common_SharedPreferences_Key;
import com.ddt.redEnvelope.MVP.Model.Bean.RequestBean.Main_WelcomePageBean;
import com.utlis.lib.SharePre;

import static com.alibaba.fastjson.JSON.parseObject;

public class Main_SharePer_SdCard_Info {
    //初始化SharedPreferences
    static SharePre sharePre = My_Application.getInstance().getSdCardSharedPreferences();

    public static SharePre getSharePre(){
        return sharePre;
    }
    /**
     * 存放启动页对象
     * @param pageBean
     */
    public static void sharePre_PutWelcomePageBean(Main_WelcomePageBean pageBean) {
        String JSONS="";
        if(pageBean!=null){
             JSONS = JSON.toJSONString(pageBean);
        }
        sharePre.put(Common_SharedPreferences_Key.WELCOME_PAGE_BEAN, JSONS);
        sharePre.commit();
    }

    /**
     * 获取启动页对象
     * @return
     */
    public static Main_WelcomePageBean sharePre_GetWelcomePageBean() {
        String JSONS = sharePre.getString(Common_SharedPreferences_Key.WELCOME_PAGE_BEAN,
                "");
        return parseObject(JSONS, Main_WelcomePageBean.class);
    }

}
