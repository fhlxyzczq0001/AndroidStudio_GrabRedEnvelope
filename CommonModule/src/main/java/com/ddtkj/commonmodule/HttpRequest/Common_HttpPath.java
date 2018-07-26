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
    public static final String URL_API_CHECK_UPDATE = Common_HostPath.HTTP_HOST_PROJECT+"api/users/appupdate";
    /**
     * 请求启动页更新
     */
    public static final String URL_API_STARTPAGEUPDATE =Common_HostPath.HTTP_HOST_PROJECT+ "api/users/startupdate";
    /**
     * 请求获取地区(省市区)
     */
    public static final String URL_API_AREA_INFO = Common_HostPath.HTTP_HOST_PROJECT+"area_info.do";
    /**
     * 上传图片
     */
    public static String URL_API_UPLOAD_FILE_PIC = "member/borrow/uploadFilePic.html";
    //================================业务相关=================================================
    /**
     *房间列表
     */
    public static String URL_API_REDPACKET_HOUSEINFO = Common_HostPath.HTTP_HOST_PROJECT+"api/redpacket/houseinfo";
    /**
     *进入红包房间
     */
    public static String URL_API_REDPACKET_HOUSEIN= Common_HostPath.HTTP_HOST_PROJECT+"api/redpacket/housein";
    /**
     *退出红包房间
     */
    public static String URL_API_REDPACKET_HOUSEOUT= Common_HostPath.HTTP_HOST_PROJECT+"api/redpacket/houseout";
    /**
     *按房间编号和时间组合去取红包
     */
    public static String URL_API_REDPACKET_PACKETINFONEW= Common_HostPath.HTTP_HOST_PROJECT+"api/redpacket/packetinfonew";
    /**
     *红包是否可以抢
     */
    public static String URL_API_REDPACKET_ISSHOWHB= Common_HostPath.HTTP_HOST_PROJECT+"api/redpacket/isShowhb";
    /**
     *抢红包
     */
    public static String URL_API_REDPACKET_PACKETINFODETAIA= Common_HostPath.HTTP_HOST_PROJECT+"api/redpacket/packetinfodetail";
    /**
     *分享
     */
    public static String URL_API_USERSHARE= Common_HostPath.HTTP_HOST_PROJECT+"api/users/usershare";
    /**
     *公告列表
     */
    public static String URL_API_USERNOTIFICATION = Common_HostPath.HTTP_HOST_PROJECT+"api/users/usernotification";
    /**
     *红包详情列表
     */
    public static String URL_API_REDPACKET_INFOLIST = Common_HostPath.HTTP_HOST_PROJECT+"api/redpacket/hbgetdetail";
    /**
     *公告列表
     */
    public static String URL_INVEST_LIST = Common_HostPath.HTTP_HOST_PROJECT+"users/usernotification";
    //================================用户相关=================================================
    /**
     * 刷新用户信息
     */
    public static final String URL_API_TREFRESH_USER_INFO = Common_HostPath.HTTP_HOST_PROJECT+"api/users/getuserinfo";
    /**
     * 查询三方某个平台的绑定状态
     */
    public static final String URL_API_TREFRESH_OTHER_LOGING_INFO = Common_HostPath.HTTP_HOST_PROJECT+"api/main/otherLogin.do";
    /**
     * 设置个人用户信息
     */
    public static String URL_API_SETPROFILE = Common_HostPath.HTTP_HOST_PROJECT+"api/users/setprofile";
    /**
     * 充值记录
     */
    public static String URL_API_RECHARGEMONEY = Common_HostPath.HTTP_HOST_PROJECT+"api/redpacket/rechargemoney";
    /**
     * 提现记录
     */
    public static String URL_API_BRINGUPMONEY = Common_HostPath.HTTP_HOST_PROJECT+"api/redpacket/bringupmoney";
    /**
     * 领取红包记录
     */
    public static String URL_API_REDPACKET_GETMONEY = Common_HostPath.HTTP_HOST_PROJECT+"api/redpacket/getmoney";
    /**
     * 消费记录
     */
    public static String URL_API_REDPACKET_CONSUMEMONEY = Common_HostPath.HTTP_HOST_PROJECT+"api/redpacket/consumemoney";
    /**
     * 意见反馈
     */
    public static String URL_API_USERS_USERIDEAS = Common_HostPath.HTTP_HOST_PROJECT+"api/users/userideas";
    /**
     * 用户搜索
     */
    public static String URL_API_SEARCHUSER = Common_HostPath.HTTP_HOST_PROJECT+"api/users/searchuser";
    /**
     * 用户列表
     */
    public static String URL_API_USERLIST = Common_HostPath.HTTP_HOST_PROJECT+"api/users/userlist";
    /**
     * 账户锁定
     */
    public static String URL_API_LOCKUSER = Common_HostPath.HTTP_HOST_PROJECT+"api/users/lockuser";
    /**
     * 用户解锁
     */
    public static String URL_API_UNLOCKUSER = Common_HostPath.HTTP_HOST_PROJECT+"api/users/unlockuser";
    /**
     * 提现
     */
    public static String URL_API_WITHDRAW = Common_HostPath.HTTP_HOST_PROJECT+"api/redpacket/bringmoney";
    /**
     * 充值
     */
    public static String URL_API_RECHARGE = Common_HostPath.HTTP_HOST_PROJECT+"api/hbappwxpay/paymentAction";
    /**
     * 修改密码
     */
    public static String URL_API_CHANGE_PASSWORD = Common_HostPath.HTTP_HOST_PROJECT+"api/users/dealpasswordmodify";
    /**
     * 登陆
     */
    public static String URL_API_LOGING = Common_HostPath.HTTP_HOST_PROJECT+"api/users/wechatThird";
    /**
     * 分享成功回调
     */
    public static String URL_API_USER_SHARE_RESULT = Common_HostPath.HTTP_HOST_PROJECT+"api/users/usershareresult";
    /**
     * 规则
     */
    public static String URL_API_USER_RULES = Common_HostPath.HTTP_HOST_PROJECT+"api/users/userrules";
}
