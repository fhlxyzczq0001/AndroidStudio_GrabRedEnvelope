package com.ddtkj.grabRedEnvelopeModule.MVP.Presenter.Implement.Fragment;


import com.ddtkj.commonmodule.MVP.Model.Implement.Common_Base_HttpRequest_Implement;
import com.ddtkj.commonmodule.MVP.Model.Interface.Common_Base_HttpRequest_Interface;
import com.ddtkj.grabRedEnvelopeModule.MVP.Contract.Fragment.GrabRedEnvelopeModule_Fra_User_Contract;

/**
 *  我的
 *
 *  @Author: 杨重诚
 *  @CreatTime: 2018/7/6  12:34  
 */

public class GrabRedEnvelopeModule_Fra_User_Presenter extends GrabRedEnvelopeModule_Fra_User_Contract.Presenter {
    Common_Base_HttpRequest_Interface common_base_httpRequest_interface;
    public GrabRedEnvelopeModule_Fra_User_Presenter(){
        common_base_httpRequest_interface=new Common_Base_HttpRequest_Implement();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
