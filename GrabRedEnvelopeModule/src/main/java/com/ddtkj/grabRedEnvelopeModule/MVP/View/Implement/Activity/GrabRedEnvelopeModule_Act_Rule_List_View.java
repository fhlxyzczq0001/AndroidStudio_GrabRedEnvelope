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
import com.ddtkj.commonmodule.MVP.Presenter.Implement.Project.Common_ProjectUtil_Implement;
import com.ddtkj.commonmodule.MVP.Presenter.Interface.Project.Common_ProjectUtil_Interface;
import com.ddtkj.commonmodule.Public.Common_PublicMsg;
import com.ddtkj.commonmodule.Public.Common_RouterUrl;
import com.ddtkj.grabRedEnvelopeModule.Adapter.GrabRedEnvelopeModule_Adapter_Act_Rule;
import com.ddtkj.grabRedEnvelopeModule.MVP.Contract.Activity.GrabRedEnvelopeModule_Act_Rule_List_Contract;
import com.ddtkj.grabRedEnvelopeModule.MVP.Model.Bean.ResponseBean.GrabRedEnvelopeModule_Bean_Announcement;
import com.ddtkj.grabRedEnvelopeModule.MVP.Model.Bean.ResponseBean.GrabRedEnvelopeModule_Bean_Rule;
import com.ddtkj.grabRedEnvelopeModule.MVP.Presenter.Implement.Activity.GrabRedEnvelopeModule_Act_Rule_List_Presenter;
import com.ddtkj.grabRedEnvelopeModule.R;
import com.ddtkj.userinfomodule.Base.UserInfo_BaseActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.utlis.lib.L;

import org.byteam.superadapter.OnItemClickListener;

import java.util.List;

/**
 *  规则列表
 *
 *  @Author: 杨重诚
 *  @CreatTime: 2018/2/13  17:41  
 */
@Route(Common_RouterUrl.GRAB_RED_ENVELOPE_RuleListRouterUrl)
public class GrabRedEnvelopeModule_Act_Rule_List_View extends UserInfo_BaseActivity<GrabRedEnvelopeModule_Act_Rule_List_Contract.Presenter,
        GrabRedEnvelopeModule_Act_Rule_List_Presenter> implements GrabRedEnvelopeModule_Act_Rule_List_Contract.View  {
    //刷新布局
    private SmartRefreshLayout mSmartRefreshLayout;
    //父控件
    RelativeLayout lyPullRecy;
    //空视图RecyclerView
    private EmptyRecyclerView mEmptyRecyclerView;
    //总共有多少个请求网络数据的方法
    private int countHttpMethod = 1;
    //列表适配器
    private GrabRedEnvelopeModule_Adapter_Act_Rule mVentureCapital2AdapterFraInvestmentList;
    //项目类型
    String type;
    private Common_ProjectUtil_Interface mCommonProjectUtilInterface;

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.grabredenvelopemodule_act_rule_list_layout);
    }

    @Override
    protected void initMyView() {
        mSmartRefreshLayout = findViewById(R.id.refreshLayout);
        lyPullRecy =  findViewById(R.id.lyPullRecy);
        mEmptyRecyclerView =  findViewById(R.id.emptyRecycle);
    }

    @Override
    protected void init() {
        mCommonProjectUtilInterface = new Common_ProjectUtil_Implement();
        //初始化RecyclerView
        initRecyclerView();
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
            type = mBundle.getString("type","");
        }
    }

    @Override
    protected void setTitleBar() {
        //设置Actionbar
        setActionbarBar("规则列表", R.color.app_gray, R.color.white, true,false);
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
    }



    /**
     * 设置投资列表数据
     * @param investmentProductData
     */
    @Override
    public void setInvestmentProductData(List<GrabRedEnvelopeModule_Bean_Rule> investmentProductData){
        //设置Adapter
        if (mVentureCapital2AdapterFraInvestmentList == null) {
            mVentureCapital2AdapterFraInvestmentList = new GrabRedEnvelopeModule_Adapter_Act_Rule(context,investmentProductData);
            mEmptyRecyclerView.setAdapter(mVentureCapital2AdapterFraInvestmentList);
            mVentureCapital2AdapterFraInvestmentList.setOnItemClickListener(new OnItemClickListener<GrabRedEnvelopeModule_Bean_Announcement>() {
                @Override
                public void onItemClick(View itemView, int viewType, int position, List <GrabRedEnvelopeModule_Bean_Announcement>mData) {
                    Bundle bundle=new Bundle();
                    bundle.putParcelable("announcementBean",mData.get(position));
                    getIntentTool().intent_RouterTo(context,Common_RouterUrl.GRAB_RED_ENVELOPE_RuleInfoRouterUrl,bundle);
                }
            });
        } else {
            if(mPresenter.getPageNum()==1){
                mVentureCapital2AdapterFraInvestmentList.setData(investmentProductData);
            }else {
                mVentureCapital2AdapterFraInvestmentList.addAll(investmentProductData);
            }
            mVentureCapital2AdapterFraInvestmentList.notifyDataSetChanged();
        }
    }
    /**
     * 关闭刷新控件
     */
    @Override
    public void closeRefresh() {
        L.e("======closeRefresh======",mPresenter.getPageNum()+"");
        if (mPresenter.getPageNum() == 1) {
            mSmartRefreshLayout.finishRefresh();
            mSmartRefreshLayout.setNoMoreData(false);
        } else {
            if (mVentureCapital2AdapterFraInvestmentList != null && (mVentureCapital2AdapterFraInvestmentList.getCount()% Common_PublicMsg.PAGING_SIZE)>0) {
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
        mPresenter.requestInvestmentProductData(type);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.setPageNum(1);//恢复默认请求页数是第一页
        //请求服务器数据的方法
        requestHttpMethod();
    }
}
