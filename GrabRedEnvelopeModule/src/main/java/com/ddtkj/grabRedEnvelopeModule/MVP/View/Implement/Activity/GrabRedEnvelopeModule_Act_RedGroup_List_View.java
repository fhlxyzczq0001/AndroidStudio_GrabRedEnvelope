package com.ddtkj.grabRedEnvelopeModule.MVP.View.Implement.Activity;

import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.chenenyu.router.annotation.Route;
import com.customview.lib.CircleTextProgressbar;
import com.customview.lib.Common_WrapContentLinearLayoutManager;
import com.customview.lib.EmptyRecyclerView;
import com.ddtkj.commonmodule.HttpRequest.ResultListener.Common_ResultDataListener;
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
    String house_id;
    private Common_ProjectUtil_Interface mCommonProjectUtilInterface;
    private MediaPlayer mediaPlayer;
    //跳过进度条
    CircleTextProgressbar cusCircleTextProgressbar;
    private int allTime=20*1000;//总时间
    String mediaPlayerUrl="http://cuiniu.ycnxsm.com/caihong.mp3";
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
        cusCircleTextProgressbar=findViewById(R.id.cusCircleTextProgressbar);
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
            house_id = mBundle.getString("house_id","");
        }
    }

    @Override
    protected void setTitleBar() {
        //设置Actionbar
        setActionbarBar("红包群", R.color.app_gray, R.color.white, true,false);
    }

    @Override
    protected void getData() {

    }

    long oldRefreshTime;
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
        tvLeftTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.requestRedpacketHouseOut(house_id);
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
            mVentureCapital2AdapterFraInvestmentList = new GrabRedEnvelopeModule_Adapter_Act_RedGroupList(context,investmentProductData,null,this);
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
        if((System.currentTimeMillis()-oldRefreshTime)>20*1000){
            oldRefreshTime=System.currentTimeMillis();
            //请求理财列表数据
            mPresenter.initData(countHttpMethod);
            mPresenter.requestInvestmentProductData(house_id);
            startCountDownTimer();
        }else {
            //ToastUtils.WarnImageToast(context,"您刷新太频繁，请稍后再刷新！");
            closeRefresh();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Common_PublicMsg.INGROUPSTATUS=true;
        mPresenter.setPageNum(1);//恢复默认请求页数是第一页
        //请求服务器数据的方法
        requestHttpMethod();
        replay();
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
                        L.e("=====onPrepared=====","=====onPrepared=====");
                        if( Common_PublicMsg.INGROUPSTATUS){
                            L.e("=====播放=====","=====播放=====");
                            mediaPlayer.start();
                        }else {
                            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                                mediaPlayer.stop();
                                mediaPlayer.release();
                                mediaPlayer = null;
                            }
                        }
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
    protected void onPause() {
        super.onPause();
        Common_PublicMsg.INGROUPSTATUS=false;
        // 在activity结束的时候回收资源
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Common_PublicMsg.INGROUPSTATUS=false;
        // 在activity结束的时候回收资源
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        cusCircleTextProgressbar.stop();//停止倒计时
    }

    /**
     * 返回键的监听
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            mPresenter.requestRedpacketHouseOut(house_id);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void outHomeSuccess() {
        FinishA();
    }

    @Override
    public void requestRedpacketIsShowhb(String hb_id, Common_ResultDataListener commonResultDataListener) {
        mPresenter.requestRedpacketIsShowhb(hb_id,commonResultDataListener);
    }

    @Override
    public void requestRedpacketPacketinfodetail(String hb_id, Common_ResultDataListener commonResultDataListener) {
        mPresenter.requestRedpacketPacketinfodetail(hb_id,commonResultDataListener);
    }

    /**
     * 启动倒计时
     */
    private void startCountDownTimer() {
        // 和系统普通进度条一样，0-100。
        cusCircleTextProgressbar.setProgressType(CircleTextProgressbar.ProgressType.COUNT);
        // 改变外部边框颜色。
        cusCircleTextProgressbar.setOutLineColor(Color.parseColor("#20dbdbdb"));
        // 设置倒计时时间毫秒
        cusCircleTextProgressbar.setTimeMillis(allTime);
        // 改变圆心颜色。
        cusCircleTextProgressbar.setInCircleColor(Color.parseColor("#20000000"));
        //设置进度条颜色
        cusCircleTextProgressbar.setProgressColor(context.getResources().getColor(R.color.app_color));
        //设置进度条边宽
        cusCircleTextProgressbar.setProgressLineWidth(1);
        //设进度监听
        cusCircleTextProgressbar.setCountdownProgressListener(1, new CircleTextProgressbar.OnCountdownProgressListener() {
            @Override
            public void onProgress(int what, int progress) {
                if(progress==100){
                    //倒计时结束
                    cusCircleTextProgressbar.stop();//停止倒计时
                    requestHttpMethod();
                }else {
                    cusCircleTextProgressbar.setText(20-progress/5+"s");
                }
            }
        });
        //启动倒计时
        cusCircleTextProgressbar.reStart();
    }
}
