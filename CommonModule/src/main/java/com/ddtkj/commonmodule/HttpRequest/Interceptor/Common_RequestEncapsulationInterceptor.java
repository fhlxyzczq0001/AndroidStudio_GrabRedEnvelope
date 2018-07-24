package com.ddtkj.commonmodule.HttpRequest.Interceptor;

import android.text.TextUtils;

import com.ddtkj.commonmodule.Base.Common_Application;
import com.ddtkj.commonmodule.BuildConfig;
import com.ddtkj.commonmodule.MVP.Model.Bean.ResponseBean.Common_CookieMapBean;
import com.ddtkj.commonmodule.Util.Common_SharePer_UserInfo;
import com.utlis.lib.L;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 请求封装拦截器——（重定向/封装请求头/缓存）
 */
public class Common_RequestEncapsulationInterceptor implements Interceptor{
    @Override
    public Response intercept(Chain chain) throws IOException
    {
        //获取请求对象
        Request oldRequest = chain.request();
        //获取原始请求Host
        String oldRequestHost=oldRequest.url().host();
        //如果是get请求，不进行封装，直接返回
        if(oldRequest.method().contains("GET")){
            return chain.proceed(oldRequest);
        }
        //===========================如果是POST请求，拦截请求================================
        //创建新的请求路径，默认值为原始请求路径
        String newRequestUrl = oldRequest.url().toString();
        L.e("=======拦截器——原始请求路径========", newRequestUrl);
        //如果是开启HttpDns请求
        if(BuildConfig.IsRequestHttpDns){
            //从缓存中取出域名对应的Dns的Ip地址
            String IpDns= Common_Application.httpDnsTimeMap.get(oldRequestHost).entrySet().iterator().next().getKey();
            if(!TextUtils.isEmpty(IpDns)){
                //将原始请求url中域名替换成Ip
                newRequestUrl = newRequestUrl.replaceFirst(new URL(newRequestUrl).getHost(), IpDns);
            }
        }
        //=============================封装请求头===============================
        //创建新的请求头
        Request.Builder newHeaderBuilder = oldRequest.newBuilder().url(newRequestUrl);
        //给请求头设置原始请求的Host
        newHeaderBuilder.header("Host", oldRequestHost);
        //=================获取cookie缓存对象==============================
        HashSet<String> cookie_session;
        //请求头中拼接Cookie
        cookie_session=getCookie(oldRequestHost);
        if(null!=cookie_session&&!cookie_session.isEmpty()){
            for(String cookie:cookie_session){
                newHeaderBuilder.addHeader("Cookie", cookie);
            }
        }
        //==============================撤销拦截，继续请求==============================================
        //继续请求服务器并返回response
        Response response = null;
        try {
            response = chain.proceed(newHeaderBuilder.build());
        } catch (IOException e) {
            e.printStackTrace();
        }
        //获取返回状态码
        int responseCode=response.code();
        //=================================获取Response返回的cookie拦截,保存cookie===========================
        saveCookie(response.headers(),oldRequestHost);
        //=================================重定向拦截===========================
        while (responseCode== 301||responseCode == 302){
            //获取重定向中返回请求头
            Headers LocationHeaders=response.headers();
            //获取重定向地址
            String location=LocationHeaders.get("Location");
            //创建URL对象
            URL url= null;
            try {
                url = new URL(location);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            //封装新的请求对象
            Request.Builder builder = oldRequest.newBuilder().url(location);
            //=================获取cookie缓存对象==============================
            HashSet<String> locationCookieSession;
            //重定向请求头中拼接Cookie
            locationCookieSession=getCookie(url.getHost());
            if(null!=locationCookieSession&&!locationCookieSession.isEmpty()){
                for(String cookie:locationCookieSession){
                    builder.addHeader("Cookie", cookie);
                }
            }
            builder.get();
            //添加请求头（addHeader是不覆盖请求参数，header是会覆盖之前的请求参数）
           /* builder.addHeader("Cookie" ,"val1");
            builder.addHeader("Cookie" ,"val2");
            builder.addHeader("valse","123");
            builder.header("valse","222");
            //如果是post请求，封装请求体参数
            RequestBody requestBodyPost = new FormBody.Builder()
                    .add("page", "1")
                    .add("code", "news")
                    .add("pageSize", "20")
                    .add("parentid", "0")
                    .add("type", "1")
                    .build();
            //请求方法
            builder.post(requestBodyPost);*/
            //新的请求对象
            Request newRequest=builder.build();
            try {
                //继续请求服务器并返回response
                response=chain.proceed(newRequest);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //获取返回状态码
            responseCode=response.code();
            //===================缓存cookie=================================
            saveCookie(response.headers(),url.getHost());
        }
        return response;
    }

    /**
     * 保存cookie至本地缓存
     * @param head
     * @param urlHost
     */
    private synchronized void saveCookie(Headers head,String urlHost){
        L.e("======保存saveCookie======",urlHost);
        String keyHost=urlHost;
        //获取本地缓存中域名对应的Cookie对象数组
        HashSet<String> set_cookie =getCookie(keyHost);
        if(set_cookie==null){
            set_cookie=new HashSet<>();
        }
        //遍历Headers中的内容
        for(int i=0; i<head.size(); i++){
            //遍历所有返回的头信息内容
            if( head.name(i).equals("Set-Cookie")){
                //如果头信息的名称==Set-Cookie
                String setCookieSub="";
                if(head.value(i).contains("=")){
                    //截取Set-Cookie第一个=前的信息
                    setCookieSub=head.value(i).substring(0,head.value(i).indexOf("="));
                }
                //如果包含这些去循环缓存的set_cookie
                if(head.value(i).contains("uid=")||head.value(i).contains("JSESSIONID=")||head.value(i).contains("CASTGC=")){
                    for(String cookie:set_cookie){
                        //如果cookie包含setCookieSub,移除缓存对象
                        if(cookie.contains(setCookieSub)){
                            set_cookie.remove(cookie);
                            break;
                        }
                    }
                    //添加新的cookie对象信息
                    set_cookie.add(head.value(i));
                }
            }
        }
        //如果set_cookie不为空，则存在本地SharePer中
        if(null!=set_cookie&&!set_cookie.isEmpty()){
            Common_CookieMapBean setCookie_MapBean= Common_SharePer_UserInfo.sharePre_GetCookieCache();
            if(setCookie_MapBean==null){
                setCookie_MapBean=new Common_CookieMapBean();
                setCookie_MapBean.setCookieMap(new HashMap<String,HashSet<String>>());
            }
            setCookie_MapBean.getCookieMap().put(keyHost,set_cookie);
            Common_SharePer_UserInfo.sharePre_PutCookieCache(setCookie_MapBean);
        }
    }

    /**
     * 获取本地Cookie数组对象
     * @param urlHost
     * @return
     */
    private  HashSet<String> getCookie(String urlHost){
        String keyHost=urlHost;
        //获取cookie缓存对象
        Common_CookieMapBean cookieMapBean= Common_SharePer_UserInfo.sharePre_GetCookieCache();
        if(null != cookieMapBean && !"".equals(cookieMapBean)){
            HashSet<String> hosts_cookie= cookieMapBean.getCookieMap().get(keyHost);
            if(null!=hosts_cookie&&!hosts_cookie.isEmpty()){
                return hosts_cookie;
            }
        }
        return null;
    }
}
