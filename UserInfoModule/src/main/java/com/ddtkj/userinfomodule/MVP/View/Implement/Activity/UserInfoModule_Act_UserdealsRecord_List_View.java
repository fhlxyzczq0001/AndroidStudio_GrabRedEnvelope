package com.ddtkj.userinfomodule.MVP.View.Implement.Activity;

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
import com.ddtkj.userinfomodule.Adapter.UserInfoModule_Adapter_Act_UserdealsRecord;
import com.ddtkj.userinfomodule.Base.UserInfo_BaseActivity;
import com.ddtkj.userinfomodule.MVP.Contract.Activity.UserInfoModule_Act_UserdealsRecord_List_Contract;
import com.ddtkj.userinfomodule.MVP.Model.Bean.ResponseBean.UserInfoModule_Bean_UserdealsRecord;
import com.ddtkj.userinfomodule.MVP.Presenter.Implement.Activity.UserInfoModule_Act_UserdealsRecord_List_Presenter;
import com.ddtkj.userinfomodule.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.utlis.lib.L;

import java.util.List;

/**
 *  本人流水列表
 *
 *  @Author: 杨重诚
 *  @CreatTime: 2018/2/13  17:41  
 */
@Route(Common_RouterUrl.USERINFO_UserdealsRouterUrl)
public class UserInfoModule_Act_UserdealsRecord_List_View extends UserInfo_BaseActivity<UserInfoModule_Act_UserdealsRecord_List_Contract.Presenter,
        UserInfoModule_Act_UserdealsRecord_List_Presenter> implements UserInfoModule_Act_UserdealsRecord_List_Contract.View  {
    //刷新布局
    private SmartRefreshLayout mSmartRefreshLayout;
    //父控件
    RelativeLayout lyPullRecy;
    //空视图RecyclerView
    private EmptyRecyclerView mEmptyRecyclerView;
    //总共有多少个请求网络数据的方法
    private int countHttpMethod = 1;
    //列表适配器
    private UserInfoModule_Adapter_Act_UserdealsRecord mVentureCapital2AdapterFraInvestmentList;
    //项目类型
    String type;
    private Common_ProjectUtil_Interface mCommonProjectUtilInterface;

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.userinfomodule_act_userdeals_record_list_layout);
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
        setActionbarBar("本人收益记录", R.color.app_gray, R.color.white, true,false);
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
    public void setInvestmentProductData(List<UserInfoModule_Bean_UserdealsRecord> investmentProductData){
        //设置Adapter
        if (mVentureCapital2AdapterFraInvestmentList == null) {
            mVentureCapital2AdapterFraInvestmentList = new UserInfoModule_Adapter_Act_UserdealsRecord(context,investmentProductData);
            mEmptyRecyclerView.setAdapter(mVentureCapital2AdapterFraInvestmentList);
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
