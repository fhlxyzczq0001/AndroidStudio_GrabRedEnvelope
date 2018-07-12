package com.ddtkj.commonmodule.HttpRequest;

/**
 * 网络请求路径
 *
 * @ClassName: Common_HttpPath
 * @Description: TODO
 * @author: yzc
 * @date: 2016-4-24 上午10:38:33
 */
public class Common_HttpPath {
    //===============================项目全局相关===================================
    /**
     * 晋商贷维护界面
     */
    public static final String URL_API_SERVER_STATUS = "http://update.dadetong.com/jsd_jk/ServerStatus.json";
    /**
     * 检测更新
     */
    public static final String URL_API_CHECK_UPDATE = Common_HostPath.HTTP_HOST_PROJECT+"index/conVersion.html";
    /**
     * 请求启动页更新
     */
    public static final String URL_API_STARTPAGEUPDATE =Common_HostPath.HTTP_HOST_PROJECT+ "jsdmobile/dynamicAllocation/borrowHomeJson.html";
    /**
     * 请求获取地区(省市区)
     */
    public static final String URL_API_AREA_INFO = Common_HostPath.HTTP_HOST_PROJECT+"area_info.do";
    /**
     * 上传图片
     */
    public static String URL_API_UPLOAD_FILE_PIC = "member/borrow/uploadFilePic.html";
    //================================借款业务相关=================================================
    /**
     *房间列表
     */
    public static String URL_INVEST_LIST = Common_HostPath.HTTP_HOST_PROJECT+"index/conVersion.html";
    //================================用户相关=================================================
    /**
     * 个人中心主页 账户信息查询
     */
    public static String URL_API_ACCOUNT_INDEX = "member/borrowAccountIndex.html";
    /**
     * 用户是否绑定第三方账户
     */
    public static String URL_API_CAN_LOGIN = "user/login/cooperation.html";
    /**
     * 基本校验-是否开通第三方账户与交易密码
     */
    public static String URL_API_THIRD_PART_PWD_INFO = "/member/user/thirdPartPwdInfo.html";
    /**
     * 三方登录登录接口
     */
    public static String URL_DO_LOGIN_THIRYPARTY = "member/borrowAccountIndex.html";
}
