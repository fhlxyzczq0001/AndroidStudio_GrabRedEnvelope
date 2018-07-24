package com.ddtkj.commonmodule.Public;

public class Common_PublicMsg {
	//  Post提交APP KEY定义
	public static final String Post_APPKEY_ANDROID = "AA438B55FCA9A106CCF88A36CB26DC5C";
	public static final String  Post_APPSECRET_ANDROID = "6A1A65A6D4EF833D";
	public static final String  Post_PARAM_APPKEY = "appkey";
	public static final String  Post_PARAM_SIGNA = "signa";
	public static final String  Post_PARAM_TS = "ts";
	public static final String  Post_APP_VERSION = "appversion";
	public static final String  Client = "client";
	public static final String  appType = "appType";

	public static long millisInFuture=120*1000;//倒计时时间
	/**
	 * 微信配置信息
	 */
	public static final String WX_APP_ID = "wxca2f5b031efecb3d"; // 自己微信应用的 appId
	public static final String WX_SECRET = "afe4d03cf1985da441bec295e1334e56"; // 自己微信应用的AppSecret
	/**
	 * QQ配置信息
	 */
	public static final String QQ_APPID = "1106132842"; // qq授权APPID
	public static final String QQ_APPKEY = "SSwwQdLt5uBzIBoa"; // qq授权APPKEY
	/**
	 * 微博配置信息
	 */
	public static final String WB_APPID = "931674432"; // 微博授权APPID
	public static final String WB_APPKEY = "c79faa983ea6d63218b4f16b2d501a48"; // 微博授权APPKEY
	/**
	 * TalkingData配置信息
	 */
	public static final String TCAgent_APPKEY="073B3162B07542159DBAF48196881257";// talkingdata
	public static final String TalkingDataAppCpa_APPKEY="89AA974703ED4976BC569F3E0040AEBD";// talkingdata广告统计
	/**
	 *阿里HTTPDNS配置信息
	 */
	public static final String HTTPDNS_APPKEY = "170061";//阿里HTTPDNSAPPkey
	public static final String HTTPDNS_SECRETKEY = "99069f345787ae2cf594400efb3bfb07";//阿里HTTPDNS参数secretKey是鉴权对应的secretKey
	/**
	 * 腾讯热更新配置信息
	 * 晋商贷借款测试 4592a7e4e6  晋商贷借款生产正式  6a25fcca4b
	 */
	public static final String TENCENT_BUGLY_APPKEY = "748a062498";//腾讯热更新APPKEY
	/**
	 *AES加密串
	 */
	public static final String AES_ENCRYPT_KEY = "588ADF00DD36A181E7802B203D09F358";//aes加密key
	/**
	 * 主路由地址
	 */
	public static String  ROUTER_MAINACTIVITY = Common_RouterUrl.GRAB_RED_ENVELOPE_MainActivityRouterUrl;//主activity路由地址
	/*分页每页显示数*/
	public static final int PAGING_SIZE = 10;

	public static boolean  INGROUPSTATUS ;
}
