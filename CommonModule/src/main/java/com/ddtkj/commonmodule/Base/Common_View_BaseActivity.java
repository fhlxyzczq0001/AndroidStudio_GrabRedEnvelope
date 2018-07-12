package com.ddtkj.commonmodule.Base;

import com.utlis.lib.TUtil;

public abstract class Common_View_BaseActivity<T extends Common_BasePresenter,TT extends Common_BasePresenter> extends Common_BaseActivity {
    protected Common_Application mCommonApplication;
    public T mPresenter;
    @Override
    protected void initApplication() {
        mCommonApplication = Common_Application.getInstance();
        mPresenter = TUtil.getT(this, 1);
        if (this instanceof Common_BaseView) mPresenter.setVM(this, this);
    }

    @Override
    protected void onMyPause() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mPresenter!=null){
            mPresenter.onDestroy();
            mPresenter=null;
        }
        context=null;
    }
}
