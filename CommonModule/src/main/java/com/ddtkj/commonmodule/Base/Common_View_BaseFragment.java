package com.ddtkj.commonmodule.Base;

import com.utlis.lib.TUtil;

/**
 * Created by ${杨重诚} on 2017/6/8.
 */

public abstract class Common_View_BaseFragment<T extends Common_BasePresenter,TT extends Common_BasePresenter> extends Common_BaseFragment {
    protected Common_Application mCommonApplication;
    public T mPresenter;
    @Override
    protected void initApplication() {
        mCommonApplication = Common_Application.getInstance();
        mPresenter = TUtil.getT(this, 1);
        if (this instanceof Common_BaseView) mPresenter.setVM(context, this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
        mPresenter=null;
        context=null;
    }
}
