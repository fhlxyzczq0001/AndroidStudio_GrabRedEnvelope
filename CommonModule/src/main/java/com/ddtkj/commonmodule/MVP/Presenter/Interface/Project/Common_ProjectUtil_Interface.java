package com.ddtkj.commonmodule.MVP.Presenter.Interface.Project;

import android.app.Dialog;
import android.content.Context;
import android.widget.EditText;
import android.widget.ImageView;

import com.ddtkj.commonmodule.Lintener.Common_UploadPicResultListener;
import com.ddtkj.commonmodule.MVP.Model.Bean.ResponseBean.Common_KeyValue;
import com.ddtkj.commonmodule.MVP.Model.Interface.Common_Base_HttpRequest_Interface;
import com.ddtkj.commonmodule.MVP.Presenter.Implement.Project.Common_ProjectUtil_Implement;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

import java.util.List;

/**项目相关的工具类接口
 * @author: Administrator 杨重诚
 * @date: 2016/11/2:14:09
 */

public interface Common_ProjectUtil_Interface {
    /**
     * 获取请求接口
     */
    public Common_Base_HttpRequest_Interface getHttpInterface();
    /**
     * 根据URL跳转不同页面
     * @param context
     * @param menuUrl
     * @param title
     */
    public void urlIntentJudge(Context context, String menuUrl, String title);
    /**
     * 将cookie同步到WebView
     * @return true 同步cookie成功，false同步cookie失败
     * @Author JPH
     */
    public void syncCookie(Context mContext, String url, Common_ProjectUtil_Implement.SyncCookieListener syncCookieListener);
    /**
     * 普通更新
     * @param content
     */
    public void showAppUpdateDialog_Common(Context context, String content, String url);
    /**
     * 强制更新
     * @param content
     */
    public void showAppUpdateDialog_Forced(Context context, String content, String url);
    /**
     * 应用检测更新
     */
    public void checkAppVersion(Context context, String source, Common_ProjectUtil_Implement.ResultCheckAppListener resultCheckAppListener);

    /**
     * 获取下载的DialogBuilder
     * @return
     */
    public NiftyDialogBuilder getDownloadDialogBuilder();

    /**
     * 设置维护页
     * @param context
     * @param msgs
     */
    public void setServerState(final Context context, final String msgs);
    /**
     * 是否显示密码
     * @param editText 输入框
     * @param imageView 图形控件
     */
    public boolean isShowPassword(boolean isShowPassword, EditText editText, ImageView imageView);
    /**
     * 请求上传图片对外接口
     * @param context 上下文
     * @param uploadPaths 上传图片数组 Common_KeyValue对象存放的是key:上传图片对应顺序 value：上传图片路径
     * @param picType 图片分类 picType '图片分类：01: 公司照片 02:身份证_抵押借款 03：结婚证 04：劳动合同 05：工牌、名片、工作证 06：社保 07:工资卡、常用银行流水 08:房产证 09:行驶证 10:支付宝截图 11:个人信用报告 12:信用卡对账单 13:学历学位证书 14:其他所有贷款协议/凭证 15:经营执照16:税务登记证17:组织机构证18:开户许可证19:贷款卡记录20:个人征信报告21:身份证_信用借款（新增）',
     * @param secondType 1正面 2：反面 3:手持
     * @param resultListener 上传回调接口
     * @param urlUploadFile 上传图片请求接口地址
     */
    public void requestUploadFilePic(final Context context, final List<Common_KeyValue<Integer, String>> uploadPaths,final String picType, final String secondType, final Common_UploadPicResultListener resultListener,final String urlUploadFile);
    /**
     * 退出登录
     */
    public void logOut();
    /**
     * 获取自定义Dialog
     * @param context
     * @param canceledOnTouchOutside
     * @param content
     * @return
     */
    public Dialog getDialog(Context context, boolean canceledOnTouchOutside, String content);
    /**
     * 下载文件
     * @param context
     * @param saveFilePath
     * @param uploadFilePath
     */
    public void downFile(Context context, String saveFilePath, String uploadFilePath);

    /**
     * 登录状态
     * @return
     */
    public boolean logingStatus();
}
