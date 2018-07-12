package com.ddtkj.commonmodule.MVP.Presenter.Implement.Activity;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.webkit.ValueCallback;

import com.ddtkj.commonmodule.MVP.Contract.Activity.Common_Act_WebView_Contract;
import com.ddtkj.commonmodule.MVP.Model.Implement.Common_Base_HttpRequest_Implement;
import com.ddtkj.commonmodule.MVP.Model.Interface.Common_Base_HttpRequest_Interface;
import com.ddtkj.commonmodule.Util.Common_CustomDialogBuilder;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.utlis.lib.FileSizeUtil;
import com.utlis.lib.ImageFactory;
import com.utlis.lib.L;

import java.io.File;
import java.util.ArrayList;

/**
 *  Common_Act_WebView_Presenter
 *
 *  @Author: 杨重诚
 *  @CreatTime: 2018/3/8  11:09  
 */

public class Common_Act_WebView_Presenter extends Common_Act_WebView_Contract.Presenter {
    Common_Base_HttpRequest_Interface mCommonBaseHttpRequestInterface;
    Common_CustomDialogBuilder common_customDialogBuilder;
    Bitmap riskBitMap;//标详情-风险控制-风险备用展示的图片
    private int pageInviteAward = 1;//邀请好友请求的page
    private int pageSizeInviteAward = 10;//邀请好友请求的page
    public Common_Act_WebView_Presenter(){
        mCommonBaseHttpRequestInterface=new Common_Base_HttpRequest_Implement();
    }
    /**
     * 上传图片
     * @param data
     */
    @Override
    public void uploadImage(Intent data,ValueCallback mUploadMessage,Bitmap uploadBitmap) {
        ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
        if (null != images && images.size() > 0) {
            ImageItem imageItem = images.get(0);
            if (null == mUploadMessage)
                return;
            Uri uri=null;
            //如果大于1024kb压缩
            if(FileSizeUtil.getFileOrFilesSize(imageItem.path, FileSizeUtil.SIZETYPE_KB)>1024){
                L.e("==============","图片大于1024，压缩");
                uploadBitmap= ImageFactory.ratio(imageItem.path,1024,600);
                try {
                    uri = Uri.parse(MediaStore.Images.Media.insertImage(context.getContentResolver(), uploadBitmap, null, null));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else {
                L.e("==============","图片不压缩");
                uri = Uri.fromFile(new File(imageItem.path));
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mUploadMessage.onReceiveValue(new Uri[]{uri});
            } else {
                mUploadMessage.onReceiveValue(uri);
            }
            mUploadMessage = null;
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (riskBitMap != null) {
            riskBitMap.recycle();
        }
    }
}
