package com.ddtkj.grabRedEnvelopeModule.Base;

import com.ddtkj.commonmodule.Base.Common_BaseActivity;
import com.ddtkj.commonmodule.Base.Common_BasePresenter;
import com.ddtkj.commonmodule.Base.Common_BaseView;
import com.ddtkj.grabRedEnvelopeModule.Base.Application.GrabRedEnvelopeModule_Application_Interface;
import com.utlis.lib.TUtil;

public abstract class GrabRedEnvelopeModule_BaseActivity<T extends Common_BasePresenter,TT extends Common_BasePresenter> extends Common_BaseActivity {
    protected GrabRedEnvelopeModule_Application_Interface mVentureCapital2ApplicationInterface;
    public T mPresenter;
    @Override
    protected void initApplication() {
        mVentureCapital2ApplicationInterface = GrabRedEnvelopeModule_Application_Utils.getApplication();
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
