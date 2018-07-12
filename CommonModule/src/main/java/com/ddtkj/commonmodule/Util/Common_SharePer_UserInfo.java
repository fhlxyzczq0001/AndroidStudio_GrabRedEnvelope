package com.ddtkj.commonmodule.Util;

import com.alibaba.fastjson.JSONObject;
import com.ddtkj.commonmodule.Base.Common_Application;
import com.ddtkj.commonmodule.MVP.Model.Bean.ResponseBean.Common_CookieMapBean;
import com.ddtkj.commonmodule.Public.Common_SharedPreferences_Key;
import com.utlis.lib.SharePre;

import static com.alibaba.fastjson.JSON.parseObject;

public class Common_SharePer_UserInfo {
    //初始化SharedPreferences
    static SharePre sharePre = Common_Application.getInstance().getUserSharedPreferences();
    /**
     * 存放cookie缓存
     */
    public static void sharePre_PutCookieCache(Common_CookieMapBean cookieMapBean) {
        String JSONS = JSONObject.toJSONString(cookieMapBean);
        sharePre.put(Common_SharedPreferences_Key.COOKIE_CACHE, JSONS);
        sharePre.commit();
    }

    /**
     * 读取Cookie缓存
     *
     * @return
     */
    public static Common_CookieMapBean sharePre_GetCookieCache() {
        String JSONS = sharePre.getString(Common_SharedPreferences_Key.COOKIE_CACHE,
                "");
        return parseObject(JSONS, Common_CookieMapBean.class);
    }
}
