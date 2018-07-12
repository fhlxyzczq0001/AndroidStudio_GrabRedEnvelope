package com.ddtkj.grabRedEnvelopeModule.MVP.View.Implement.Activity;

import android.media.AudioManager;
import android.media.MediaPlayer;
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
import com.ddtkj.grabRedEnvelopeModule.Adapter.GrabRedEnvelopeModule_Adapter_Act_RedGroupList;
import com.ddtkj.grabRedEnvelopeModule.Base.GrabRedEnvelopeModule_BaseActivity;
import com.ddtkj.grabRedEnvelopeModule.MVP.Contract.Activity.GrabRedEnvelopeModule_Act_RedGroup_List_Contract;
import com.ddtkj.grabRedEnvelopeModule.MVP.Model.Bean.ResponseBean.GrabRedEnvelopeModule_Bean_RedGroupListInfo;
import com.ddtkj.grabRedEnvelopeModule.MVP.Presenter.Implement.Activity.GrabRedEnvelopeModule_Act_RedGroup_List_Presenter;
import com.ddtkj.grabRedEnvelopeModule.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.utlis.lib.L;
import com.utlis.lib.ViewUtils;

import java.util.List;

/**
 *  红包群列表
 *
 *  @Author: 杨重诚
 *  @CreatTime: 2018/2/13  17:41  
 */
@Route(Common_RouterUrl.GRAB_RED_ENVELOPE_RedGroupListRouterUrl)
public class GrabRedEnvelopeModule_Act_RedGroup_List_View extends GrabRedEnvelopeModule_BaseActivity<GrabRedEnvelopeModule_Act_RedGroup_List_Contract.Presenter,
        GrabRedEnvelopeModule_Act_RedGroup_List_Presenter> implements GrabRedEnvelopeModule_Act_RedGroup_List_Contract.View  {
    //刷新布局
    private SmartRefreshLayout mSmartRefreshLayout;
    //父控件
    RelativeLayout lyPullRecy;
    //空视图RecyclerView
    private EmptyRecyclerView mEmptyRecyclerView;
    //总共有多少个请求网络数据的方法
    private int countHttpMethod = 1;
    //列表适配器
    private GrabRedEnvelopeModule_Adapter_Act_RedGroupList mVentureCapital2AdapterFraInvestmentList;
    //项目类型
    String type;
    private Common_ProjectUtil_Interface mCommonProjectUtilInterface;
    private MediaPlayer mediaPlayer;
    String mediaPlayerUrl="http://sc1.111ttt.cn:8282/2018/1/03m/13/396131229550.m4a?tflag=1519095601&pin=6cd414115fdb9a950d827487b16b5f97#.mp3";
    @Override
    public void onClick(View v) {
        super.onClick(v);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.grabredenvelopemodule_act_redgroup_list_layout);
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
        play();
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
        setActionbarBar("10元房间", R.color.app_gray, R.color.white, true,false);
        tvRightTitleRight.setVisibility(View.VISIBLE);
        tvRightTitleRight.setCompoundDrawables(ViewUtils.getDrawableSvg(context,R.drawable.drawable_svg_icon_share), null, null, null);
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
    public void setInvestmentProductData(List<GrabRedEnvelopeModule_Bean_RedGroupListInfo> investmentProductData){
        //设置Adapter
        if (mVentureCapital2AdapterFraInvestmentList == null) {
            mVentureCapital2AdapterFraInvestmentList = new GrabRedEnvelopeModule_Adapter_Act_RedGroupList(context,investmentProductData,null);
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

    /**
     * 播放音乐
     */
    protected void play() {
            try {
                mediaPlayer = new MediaPlayer();
                // 设置指定的流媒体地址
                mediaPlayer.setDataSource(mediaPlayerUrl);
                // 设置音频流的类型
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                // 通过异步的方式装载媒体资源
                mediaPlayer.prepareAsync();
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        // 装载完毕 开始播放流媒体
                        mediaPlayer.start();
                    }
                });
                // 设置循环播放
                mediaPlayer.setLooping(true);
                /*mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        // 在播放完毕被回调
                    }

                });*/
                mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                    @Override
                    public boolean onError(MediaPlayer mp, int what, int extra) {
                        // 如果发生错误，重新播放
                        replay();
                        return false;
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    /**
     * 暂停
     */
    protected void pause() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }

    }

    /**
     * 重新播放
     */
    protected void replay() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.seekTo(0);
            return;
        }
        play();
    }

    /**
     * 停止播放
     */
    protected void stop() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }

    }


    @Override
    protected void onDestroy() {
        // 在activity结束的时候回收资源
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        super.onDestroy();
    }
}
