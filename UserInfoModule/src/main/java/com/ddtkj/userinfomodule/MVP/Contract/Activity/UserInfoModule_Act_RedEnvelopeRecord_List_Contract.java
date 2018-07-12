package com.ddtkj.userinfomodule.MVP.Contract.Activity;

import com.ddtkj.commonmodule.Base.Common_BasePresenter;
import com.ddtkj.commonmodule.Base.Common_BaseView;
import com.ddtkj.userinfomodule.MVP.Model.Bean.ResponseBean.UserInfoModule_Bean_RedEnvelopeRecord;

import java.util.List;

/**
 *  红包记录列表
 *
 *  @Author: 杨重诚
 *  @CreatTime: 2018/7/6  16:24  
 */
public interface UserInfoModule_Act_RedEnvelopeRecord_List_Contract {

    interface View extends Common_BaseView {
        /**
         * 设置投资列表数据
         * @param investmentProductData
         */
        void setInvestmentProductData(List<UserInfoModule_Bean_RedEnvelopeRecord> investmentProductData);

        /**
         * 关闭刷新
         */
        void closeRefresh();

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

    }
}
