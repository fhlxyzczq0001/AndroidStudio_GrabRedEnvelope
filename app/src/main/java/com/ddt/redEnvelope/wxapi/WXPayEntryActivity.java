package com.ddt.redEnvelope.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.ddtkj.commonmodule.Public.Common_PublicMsg;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.utlis.lib.L;
import com.utlis.lib.ToastUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * 微信支付回调界面
 */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler{
	
	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
	
    private IWXAPI api;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    	api = WXAPIFactory.createWXAPI(this, Common_PublicMsg.WX_APP_ID);
        api.handleIntent(getIntent(), this);
    }

//	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(final BaseResp resp) {
		L.d(TAG, "onPayFinish, errCode = " + resp.errCode);
		new Handler().post(new Runnable() {
			@Override
			public void run() {
				//这里发送粘性事件
				EventBus.getDefault().post(resp);
			}
		});
		// 支付结果回调...
		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			if(resp.errCode == 0){//支付成功
				ToastUtils.RightImageToast(WXPayEntryActivity.this,"支付成功");
			}
		}
		WXPayEntryActivity.this.finish();
	}
}