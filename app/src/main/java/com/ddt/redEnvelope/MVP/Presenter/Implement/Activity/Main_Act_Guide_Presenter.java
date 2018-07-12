package com.ddt.redEnvelope.MVP.Presenter.Implement.Activity;

import com.ddt.redEnvelope.MVP.Contract.Activity.Main_Act_Guide_Contract;
import com.ddt.redEnvelope.R;

/**
 * Created by ${杨重诚} on 2017/6/2.
 */

public class Main_Act_Guide_Presenter extends Main_Act_Guide_Contract.Presenter {
    /**
     * 获取默认引导页
     * @Title: setDefaultGuidPage
     * @Description: TODO
     * @return: void
     */
    @Override
    public int[]  getDefaultGuidPage(){
        return new int[]{R.drawable.bg_guide1,R.drawable.bg_guide2,R.drawable.bg_guide3};
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
