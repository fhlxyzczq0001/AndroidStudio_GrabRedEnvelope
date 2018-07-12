package com.ddtkj.userinfomodule.MVP.Presenter.Implement.Activity;


import com.ddtkj.commonmodule.MVP.Model.Implement.Common_Base_HttpRequest_Implement;
import com.ddtkj.commonmodule.MVP.Model.Interface.Common_Base_HttpRequest_Interface;
import com.ddtkj.userinfomodule.MVP.Contract.Activity.UserInfo_Act_MainActivity_Contract;

/**
 * Created by ${杨重诚} on 2017/6/2.
 */

public class UserInfo_Act_MainActivity_Presenter extends UserInfo_Act_MainActivity_Contract.Presenter {
    Common_Base_HttpRequest_Interface common_base_httpRequest_interface;
    public UserInfo_Act_MainActivity_Presenter(){
        common_base_httpRequest_interface=new Common_Base_HttpRequest_Implement();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
