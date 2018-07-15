package com.ddtkj.grabRedEnvelopeModule.MVP.Contract.Activity;

import com.ddtkj.commonmodule.Base.Common_BasePresenter;
import com.ddtkj.commonmodule.Base.Common_BaseView;
import com.ddtkj.commonmodule.HttpRequest.ResultListener.Common_ResultDataListener;
import com.ddtkj.grabRedEnvelopeModule.MVP.Model.Bean.ResponseBean.GrabRedEnvelopeModule_Bean_RedGroupListInfo;

import java.util.List;

/**
 *  红包房间列表
 *
 *  @Author: 杨重诚
 *  @CreatTime: 2018/7/6  16:24  
 */
public interface GrabRedEnvelopeModule_Act_RedGroup_List_Contract {

    interface View extends Common_BaseView {
        /**
         * 设置投资列表数据
         * @param investmentProductData
         */
        void setInvestmentProductData(List<GrabRedEnvelopeModule_Bean_RedGroupListInfo> investmentProductData);

        /**
         * 关闭刷新
         */
        void closeRefresh();

        /**
         * 退出房间
         */
        void outHomeSuccess();
        /**
         * .红包是否可以抢
         * @param hb_id
         */
        public  void requestRedpacketIsShowhb(String hb_id, Common_ResultDataListener commonResultDataListener);
        /**
         * .抢红包
         * @param hb_id
         */
        public  void requestRedpacketPacketinfodetail(String hb_id, Common_ResultDataListener commonResultDataListener);
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
         * 退出红包房间
         */
        public abstract void requestRedpacketHouseOut(String house_id);

        /**
         * .红包是否可以抢
         * @param hb_id
         */
        public abstract void requestRedpacketIsShowhb(String hb_id, Common_ResultDataListener commonResultDataListener);
        /**
         * .抢红包
         * @param hb_id
         */
        public abstract void requestRedpacketPacketinfodetail(String hb_id, Common_ResultDataListener commonResultDataListener);
    }
}
