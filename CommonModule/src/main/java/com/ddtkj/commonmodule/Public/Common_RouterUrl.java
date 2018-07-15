package com.ddtkj.commonmodule.Public;

/**路由机制URL
 * Created by Administrator on 2017/6/3.
 */

public class Common_RouterUrl {
    //===============================APP相关============================================================
    //前缀
    public final static String ROUTER_HTTP="GrabRedEnvelopeRoute://";
    //主app项目Host
    final static String MAIN_APPHOST=ROUTER_HTTP+"GrabRedEnvelopeApp/";
    //webView页面
    public final static String MAIN_WebViewRouterUrl =MAIN_APPHOST+"interactionWebView";
    //启动页面
    public final static String MAIN_WelcomePageRouterUrl =MAIN_APPHOST+"welcomePage";
    //引导页面
    public final static String MAIN_GuideRouterUrl =MAIN_APPHOST+"guide";
    //网络异常页面
    public final static String MAIN_NetworkErrorRouterUrl =MAIN_APPHOST+"networkError";

//=========================================用户相关=============================================================
    //用户包名
    final static String USERINFO_APPHOST=ROUTER_HTTP+"userInfo/";
    //用户主界面
    public final static String USERINFO_MainActivityRouterUrl =USERINFO_APPHOST+"mainActivity";
    //登陆界面
    public final static String USERINFO_LogingRouterUrl =USERINFO_APPHOST+"log";
    //忘记密码界面
    public final static String USERINFO_ForgetPasswordRouterUrl =USERINFO_APPHOST+"forgetPassword";
    //注册界面
    public final static String USERINFO_RegisterRouterUrl =USERINFO_APPHOST+"userRegister";
    //三方登陆界面
    public final static String USERINFO_ThiryUserLogingRouterUrl =USERINFO_APPHOST+"thiryActivity";
    //提现记录界面
    public final static String USERINFO_WithdrawalsRecordRouterUrl =USERINFO_APPHOST+"WithdrawalsRecordActivity";
    //充值记录界面
    public final static String USERINFO_RechargeRecordRouterUrl =USERINFO_APPHOST+"RechargeRecordActivity";
    //红包记录界面
    public final static String USERINFO_RedEnvelopeRecordRouterUrl =USERINFO_APPHOST+"RedEnvelopeActivity";
    //消费记录界面
    public final static String USERINFO_ConsumptionRecordRouterUrl =USERINFO_APPHOST+"ConsumptionRecordActivity";
    //解封账号记录界面
    public final static String USERINFO_UnblockRecordRouterUrl =USERINFO_APPHOST+"UnblockRecordActivity";
    //用户信息界面
    public final static String USERINFO_UserInfoRouterUrl =USERINFO_APPHOST+"UserInfo";
    //提现界面
    public final static String USERINFO_WithdrawRouterUrl =USERINFO_APPHOST+"Withdraw";
    //充值界面
    public final static String USERINFO_RechargeRouterUrl =USERINFO_APPHOST+"Recharge";
    //修改密码界面
    public final static String USERINFO_ChangePasswordRouterUrl =USERINFO_APPHOST+"ChangePassword";
    //==========================================业务相关============================================================
    //业务包名
    final static String GRAB_RED_ENVELOPE_APPHOST=ROUTER_HTTP+"GrabRedEnvelopeModule/";
    //业务主界面
    public final static String GRAB_RED_ENVELOPE_MainActivityRouterUrl =GRAB_RED_ENVELOPE_APPHOST+"mainActivity";
    //红包房间列表界面
    public final static String GRAB_RED_ENVELOPE_RedRoomListRouterUrl =GRAB_RED_ENVELOPE_APPHOST+"RdRooemList";
    //红包群列表界面
    public final static String GRAB_RED_ENVELOPE_RedGroupListRouterUrl =GRAB_RED_ENVELOPE_APPHOST+"RdGroupList";
    //红包详情列表界面
    public final static String GRAB_RED_ENVELOPE_RedInfoListRouterUrl =GRAB_RED_ENVELOPE_APPHOST+"RdInfoList";
    //公告列表界面
    public final static String GRAB_RED_ENVELOPE_AnnouncementListRouterUrl =GRAB_RED_ENVELOPE_APPHOST+"AnnouncementList";
    //公告详情界面
    public final static String GRAB_RED_ENVELOPE_AnnouncementInfoRouterUrl =GRAB_RED_ENVELOPE_APPHOST+"AnnouncementInfo";
    //==============================拦截器====================================
    //用户信息界面拦截器
    public final static String INTERCEPTION_UserInfoRouterUrl =ROUTER_HTTP+"userInfo";

}
