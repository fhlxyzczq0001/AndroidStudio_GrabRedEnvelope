package com.ddtkj.grabRedEnvelopeModule.MVP.View.Implement.Activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.chenenyu.router.annotation.Route;
import com.customview.lib.Common_WrapContentLinearLayoutManager;
import com.customview.lib.EmptyRecyclerView;
import com.ddtkj.commonmodule.Lintener.Common_WXShareLintener;
import com.ddtkj.commonmodule.MVP.Model.Bean.EventBusBean.Common_Share_EventBus;
import com.ddtkj.commonmodule.Public.Common_PublicMsg;
import com.ddtkj.commonmodule.Public.Common_RouterUrl;
import com.ddtkj.grabRedEnvelopeModule.Adapter.GrabRedEnvelopeModule_Adapter_Act_InvestmentList;
import com.ddtkj.grabRedEnvelopeModule.Base.GrabRedEnvelopeModule_BaseActivity;
import com.ddtkj.grabRedEnvelopeModule.MVP.Contract.Activity.GrabRedEnvelopeModule_Act_RedRoom_List_Contract;
import com.ddtkj.grabRedEnvelopeModule.MVP.Model.Bean.ResponseBean.GrabRedEnvelopeModule_Bean_RedRoomListInfo;
import com.ddtkj.grabRedEnvelopeModule.MVP.Model.Bean.ResponseBean.GrabRedEnvelopeModule_Bean_Share;
import com.ddtkj.grabRedEnvelopeModule.MVP.Presenter.Implement.Activity.GrabRedEnvelopeModule_Act_RedRoom_List_Presenter;
import com.ddtkj.grabRedEnvelopeModule.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.umeng.socialize.media.UMImage;
import com.utlis.lib.L;
import com.utlis.lib.ViewUtils;

import org.byteam.superadapter.OnItemClickListener;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import mvpdemo.com.unmeng_share_librarys.UmengShare;

/**
 * 投资列表
 *
 * @Author: 杨重诚
 * @CreatTime: 2018/2/13  17:41
 */
@Route(Common_RouterUrl.GRAB_RED_ENVELOPE_RedRoomListRouterUrl)
public class GrabRedEnvelopeModule_Act_RedRoom_List_View extends GrabRedEnvelopeModule_BaseActivity<GrabRedEnvelopeModule_Act_RedRoom_List_Contract.Presenter,
        GrabRedEnvelopeModule_Act_RedRoom_List_Presenter> implements GrabRedEnvelopeModule_Act_RedRoom_List_Contract.View {
    //刷新布局
    private SmartRefreshLayout mSmartRefreshLayout;
    //父控件
    RelativeLayout lyPullRecy;
    //空视图RecyclerView
    private EmptyRecyclerView mEmptyRecyclerView;
    //总共有多少个请求网络数据的方法
    private int countHttpMethod = 1;
    //列表适配器
    private GrabRedEnvelopeModule_Adapter_Act_InvestmentList mVentureCapital2AdapterFraInvestmentList;
    String category;

    GrabRedEnvelopeModule_Bean_Share shareData = new GrabRedEnvelopeModule_Bean_Share();

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.tvRightTitleRight) {
            //调起分享页面
            UMImage image = new UMImage(context, com.ddtkj.commonmodule.R.mipmap.icon_launcher);
            new UmengShare().openShareWeiXin(context, shareData.getTitile(), shareData.getComment(), shareData.getLinkurl(), image, new Common_WXShareLintener());
        }
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.grabredenvelopemodule_act_redroom_list_layout);
    }

    @Override
    protected void initMyView() {
        mSmartRefreshLayout = findViewById(R.id.refreshLayout);
        lyPullRecy = findViewById(R.id.lyPullRecy);
        mEmptyRecyclerView = findViewById(R.id.emptyRecycle);
    }

    @Override
    protected void init() {
        //初始化RecyclerView
        initRecyclerView();
        //请求分享
        mPresenter.requestShareData();
    }

    /**
     * 初始化RecyclerView
     */
    public void initRecyclerView() {
        //设置空视图
        View emptyView = LayoutInflater.from(context).inflate(R.layout.common_include_emptylist, null);
        emptyView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        lyPullRecy.addView(emptyView);
        mEmptyRecyclerView.setEmptyView(emptyView);
        mEmptyRecyclerView.setLayoutManager(new Common_WrapContentLinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void getBundleValues(Bundle mBundle) {
        super.getBundleValues(mBundle);
        if (mBundle != null) {
            category = mBundle.getString("category", "");
        }
    }

    @Override
    protected void setTitleBar() {
        //设置Actionbar
        setActionbarBar(category + "钻房间", R.color.app_gray, R.color.white, true, false);
        tvRightTitleRight.setVisibility(View.VISIBLE);
        tvRightTitleRight.setCompoundDrawables(ViewUtils.getDrawableSvg(context, R.drawable.drawable_svg_icon_share), null, null, null);
    }

    @Override
    protected void getData() {

    }

    @Override
    protected void setListeners() {
        //==================刷新列表监听===============================
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                // 下拉刷新
                mPresenter.setPageNum(1);//恢复默认请求页数是第一页
                //请求服务器数据的方法
                requestHttpMethod();
            }
        });
        mSmartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                //上拉加载更多
                int classGoods_page = mPresenter.getPageNum();
                classGoods_page++;//页数加一
                mPresenter.setPageNum(classGoods_page);
                requestHttpMethod();
            }
        });
        tvRightTitleRight.setOnClickListener(this);
    }


    /**
     * 设置投资列表数据
     *
     * @param investmentProductData
     */
    @Override
    public void setInvestmentProductData(List<GrabRedEnvelopeModule_Bean_RedRoomListInfo> investmentProductData) {
        //设置Adapter
        if (mVentureCapital2AdapterFraInvestmentList == null) {
            mVentureCapital2AdapterFraInvestmentList = new GrabRedEnvelopeModule_Adapter_Act_InvestmentList(context, investmentProductData);
            mEmptyRecyclerView.setAdapter(mVentureCapital2AdapterFraInvestmentList);
        } else {
            if (mPresenter.getPageNum() == 1) {
                mVentureCapital2AdapterFraInvestmentList.setData(investmentProductData);
            } else {
                mVentureCapital2AdapterFraInvestmentList.addAll(investmentProductData);
            }
            mVentureCapital2AdapterFraInvestmentList.notifyDataSetChanged();
        }
        mVentureCapital2AdapterFraInvestmentList.setOnItemClickListener(new OnItemClickListener<GrabRedEnvelopeModule_Bean_RedRoomListInfo>() {
            @Override
            public void onItemClick(View itemView, int viewType, int position, List<GrabRedEnvelopeModule_Bean_RedRoomListInfo> mData) {
                mPresenter.requestRedpacketHousein(mData.get(position).getId());
            }
        });
    }

    /**
     * 关闭刷新控件
     */
    @Override
    public void closeRefresh() {
        L.e("======closeRefresh======", mPresenter.getPageNum() + "");
        if (mPresenter.getPageNum() == 1) {
            mSmartRefreshLayout.finishRefresh();
            mSmartRefreshLayout.setNoMoreData(false);
        } else {
            if (mVentureCapital2AdapterFraInvestmentList != null && (mVentureCapital2AdapterFraInvestmentList.getCount() % Common_PublicMsg.PAGING_SIZE) > 0) {
                mSmartRefreshLayout.finishLoadMoreWithNoMoreData();//将不会再次触发加载更多事件
            } else {
                mSmartRefreshLayout.finishLoadMore();
            }
        }
    }

    /**
     * 请求服务器方法
     */
    private void requestHttpMethod() {
        //请求理财列表数据
        mPresenter.initData(countHttpMethod);
        mPresenter.requestInvestmentProductData(category);
    }

    @Override
    public void onResume() {
        super.onResume();
        // 注册EventBus
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        mPresenter.setPageNum(1);//恢复默认请求页数是第一页
        //请求服务器数据的方法
        requestHttpMethod();
    }

    @Override
    public void redpacketHouseinSuccess(String house_id) {
        getIntentTool().intent_RouterTo(context, Common_RouterUrl.GRAB_RED_ENVELOPE_RedGroupListRouterUrl + "?house_id=" + house_id);
    }

    @Override
    public void setShareData(GrabRedEnvelopeModule_Bean_Share shareData) {
        if (shareData != null)
            this.shareData = shareData;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 注销EventBus
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
    //-----------------------------------Eventbus------------------------------------------

    /**
     * 友盟第三方分享成功
     *
     * @param eventBus
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void umengAuthSuccess(Common_Share_EventBus eventBus) {
        if (!eventBus.isReceive()) {
            eventBus.setReceive(true);
        } else {
            return;
        }
        switch (eventBus.getSareCode()) {
            case SHARE_FAVORITE:
                mPresenter.requestShareResult();
                break;
            case SHARE_SUCCESS:
                mPresenter.requestShareResult();
                break;
        }
    }
}
