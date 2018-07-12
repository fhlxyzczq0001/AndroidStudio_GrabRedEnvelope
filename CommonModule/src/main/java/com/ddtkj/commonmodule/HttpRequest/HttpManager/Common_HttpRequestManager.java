package com.ddtkj.commonmodule.HttpRequest.HttpManager;

import android.content.Context;

import com.ddtkj.commonmodule.Base.Common_Application;
import com.trello.rxlifecycle.android.ActivityEvent;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.utlis.lib.L;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.Api.BaseApi;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.exception.FactoryException;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.exception.RetryWhenNetworkException;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener.HttpOnNextListener;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.subscribers.ProgressSubscriber;

import java.lang.ref.SoftReference;

import retrofit2.Retrofit;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * http交互处理类
 * Created by WZG on 2016/7/16.
 */
public class Common_HttpRequestManager {
    /*单利对象*/
    private volatile static Common_HttpRequestManager INSTANCE;
    /*单利对象*/
    private volatile static Retrofit myRetrofit;
    /*软引用對象*/
    private HttpOnNextListener onNextListener;
    private SoftReference<RxAppCompatActivity> appCompatActivity;

    public Common_HttpRequestManager(HttpOnNextListener onNextListener, Context appCompatActivity) {
        this.onNextListener = onNextListener;
        if(appCompatActivity instanceof RxAppCompatActivity){
            this.appCompatActivity = new SoftReference((RxAppCompatActivity)appCompatActivity);
        }
    }
    /**
     * 处理http请求
     *
     */
    public Subscription doHttpDeal(BaseApi basePar) {
        Subscription subscription = null;
        /*rx处理*/
        ProgressSubscriber subscriber = new ProgressSubscriber(basePar, onNextListener, appCompatActivity);
        Retrofit retrofit= Common_Application.getInstance().getRetrofit(basePar);
        if(appCompatActivity!=null&&appCompatActivity.get()!=null){
            Observable observable = basePar.getObservable(retrofit)
                /*失败后的retry配置*/
                    .retryWhen(new RetryWhenNetworkException())
                /*异常处理*/
                    .onErrorResumeNext(funcException)
                /*生命周期管理 如果自动管理，可能会出现A跳转到B，A的请求还没完成就取消了，B拿不到A的请求结果，所以最好手动设置在activity onDestroy的时候取消订阅*/
                   /* .compose(appCompatActivity.get().bindToLifecycle())*/
                    //Note:手动设置在activity onDestroy的时候取消订阅
                /*.filter(new Func1() {
                    @Override
                    public Object call(Object o) {
                        return basePar.isShowProgress();
                    }
                })*/
                    .compose(appCompatActivity.get().bindUntilEvent(ActivityEvent.DESTROY))
                     /*http请求线程*/
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    /*回调线程*/
                    .observeOn(AndroidSchedulers.mainThread());

             /*数据回调*/
            subscription=observable.subscribe(subscriber);
        }else {
            Observable observable = basePar.getObservable(retrofit)
                /*失败后的retry配置*/
                    .retryWhen(new RetryWhenNetworkException())
                /*异常处理*/
                    .onErrorResumeNext(funcException)
                /*生命周期管理*/
                    //.compose(appCompatActivity.get().bindToLifecycle())
                    //Note:手动设置在activity onDestroy的时候取消订阅
               /* .filter(new Func1() {
                    @Override
                    public Object call(Object o) {
                        return basePar.isShowProgress();
                    }
                });
                    .compose(appCompatActivity.get().bindUntilEvent(ActivityEvent.DESTROY))*/
                     /*http请求线程*/
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    /*回调线程*/
                    .observeOn(AndroidSchedulers.mainThread());

             /*数据回调*/
            subscription=observable.subscribe(subscriber);
        }
        return subscription;
    }
    /**
     * 异常处理
     */
    Func1 funcException = new Func1<Throwable, Observable>() {
        @Override
        public Observable call(Throwable throwable) {
            L.e("====throwable.getMessage()======",throwable.getMessage());
           /* if(throwable.getMessage().contains("400")
                    ||throwable.getMessage().contains("401")
                    ||throwable.getMessage().contains("403")
                    ||throwable.getMessage().contains("404")
                    ||throwable.getMessage().contains("408")
                    ||throwable.getMessage().contains("500")
                    ||throwable.getMessage().contains("501")
                    ||throwable.getMessage().contains("502")
                    ||throwable.getMessage().contains("503")
                    ||throwable.getMessage().contains("504")
                    ||throwable.getMessage().contains("505")){
                return Observable.empty();
            }*/
            return Observable.error(FactoryException.analysisExcetpion(throwable));
        }
    };
}
