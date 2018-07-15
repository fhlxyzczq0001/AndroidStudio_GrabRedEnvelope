package com.ddtkj.userinfomodule.MVP.Contract.Activity;

import com.ddtkj.commonmodule.Base.Common_BasePresenter;
import com.ddtkj.commonmodule.Base.Common_BaseView;
import com.ddtkj.commonmodule.HttpRequest.ResultListener.Common_ResultDataListener;
import com.ddtkj.userinfomodule.MVP.Model.Bean.ResponseBean.UserInfoModule_Bean_UnblockRecord;

import java.util.List;

/**
 *  解封账号记录列表
 *
 *  @Author: 杨重诚
 *  @CreatTime: 2018/7/6  16:24  
 */
public interface UserInfoModule_Act_UnblockRecord_List_Contract {

    interface View extends Common_BaseView {
        /**
         * 设置投资列表数据
         * @param investmentProductData
         */
        void setInvestmentProductData(List<UserInfoModule_Bean_UnblockRecord> investmentProductData);

        /**
         * 关闭刷新
         */
        void closeRefresh();
        /**
         * 账户锁定
         * @param user_id
         * @param commonResultDataListener
         */
        public  void requestLockuser(String user_id, final Common_ResultDataListener commonResultDataListener);
        /**
         * 账户解锁
         * @param user_id
         * @param commonResultDataListener
         */
        public  void requestUnLockuser(String user_id, final Common_ResultDataListener commonResultDataListener);
    }

    abstract class Presenter extends Common_BasePresenter<View> {
        /**
         * 初始化参数
         */
        public abstract void initData(int countHttpMethod);
        /**
         * 获取投资列表数据
         * @param type 类型
         */
        public abstract void requestInvestmentProductData(String type);
        /**
         * 重置page
         */
        public abstract void setPageNum(int page);
        /**
         * 获取page
         * @return
         */
        public abstract int getPageNum();
        /**
         * 账户锁定
         * @param user_id
         * @param commonResultDataListener
         */
        public abstract void requestLockuser(String user_id, final Common_ResultDataListener commonResultDataListener);
        /**
         * 账户解锁
         * @param user_id
         * @param commonResultDataListener
         */
        public abstract void requestUnLockuser(String user_id, final Common_ResultDataListener commonResultDataListener);
        /**
         * 用户搜索
         * @param user_id
         */
        public abstract void requestSearchuser(String user_id);
    }
}
