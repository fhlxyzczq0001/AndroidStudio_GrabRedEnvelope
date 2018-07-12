package com.ddtkj.commonmodule.Util;

import android.app.Application;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.ddtkj.commonmodule.Base.Common_Application;
import com.ddtkj.commonmodule.R;
import com.glide_library.GlideApp;
import com.glide_library.GlideRoundTransform;


/**图片加载工具类
 * Created by Administrator on 2017/2/7.
 */

public class ImageLoaderUtils extends Application{
    //================================GlideApp加载其它资源图片不需要前缀，但是为了方便后期拓展和修改，请求其它资源加上前缀对象“”字符串==============
    public static final String RES_ASSETS = "";
    public static final String RES_SDCARD = "";
    public static final String RES_DRAWABLE = "";
    public static final String RES_HTTP = "";
    //===================================Netroid加载本地图片需要的前缀==============================
   /* public static final String RES_ASSETS = "assets://";
    public static final String RES_SDCARD = "sdcard://";
    public static final String RES_DRAWABLE = "drawable://";
    public static final String RES_HTTP = "http://";*/

    static ImageLoaderUtils mImageLoaderUtils;
    public static ImageLoaderUtils getInstance(Context context){
      if(mImageLoaderUtils==null){
          //第一次check，避免不必要的同步
          synchronized (ImageLoaderUtils.class){//同步
              if(mImageLoaderUtils==null){
                  //第二次check，保证线程安全
                  mImageLoaderUtils=new ImageLoaderUtils();
              }
          }
      }
        return mImageLoaderUtils;
    }

    public void displayImage(String url, ImageView imageView){
        GlideApp.with(Common_Application.getApplication())
                .load(url)
                .placeholder(R.drawable.icon_no_image)//默认加载图片
                .error(R.drawable.icon_no_image)//错误图片
                .skipMemoryCache(true)//跳过内存缓存
                .diskCacheStrategy(DiskCacheStrategy.ALL)//硬盘缓存策略
                .transition(GenericTransitionOptions.with(R.anim.anim_alpha_in)) // 动画渐变加载
                .into(imageView);
    }

    /**
     * 无缓存策略，适用于同一url对应不同图片内容
     * @param url
     * @param imageView
     */
    public void displayImageNoneCache(Object url, ImageView imageView){
        GlideApp.with(Common_Application.getApplication())
                .load(url)
                .placeholder(R.drawable.icon_no_image)//默认加载图片
                .error(R.drawable.icon_no_image)//错误图片
                .skipMemoryCache(true)//跳过内存缓存
                .diskCacheStrategy(DiskCacheStrategy.NONE)//无硬盘缓存
                .signature(new ObjectKey(System.currentTimeMillis()))//改变这个签名参数会不读取缓存重新请求
                .transition(GenericTransitionOptions.with(R.anim.anim_alpha_in)) // 动画渐变加载
                .into(imageView);
    }

    public void displayNoAnimateImage(String url, ImageView imageView){
        //Netroid.displayImage(url,imageView);
        GlideApp.with(Common_Application.getApplication())
                .load(url)
                .placeholder(R.drawable.icon_no_image)//默认加载图片
                .error(R.drawable.icon_no_image)//错误图片
                .skipMemoryCache(true)//跳过内存缓存
                .diskCacheStrategy(DiskCacheStrategy.ALL)//硬盘缓存策略
                .dontAnimate()
                .into(imageView);
    }
    public void displayImage(String url, ImageView imageView,int radius){
        //Netroid.displayImage(url,imageView);
        GlideApp.with(Common_Application.getApplication())
                .load(url)
                .placeholder(R.drawable.icon_no_image)//默认加载图片
                .error(R.drawable.icon_no_image)//错误图片
                .skipMemoryCache(true)//跳过内存缓存
                .diskCacheStrategy(DiskCacheStrategy.ALL)//硬盘缓存策略
                .transition(DrawableTransitionOptions.withCrossFade()) // 动画渐变加载
                .transform(new GlideRoundTransform(radius))
                .into(imageView);
    }
    public void displayImage(int resourceId, ImageView imageView){
        //Netroid.displayImage(url,imageView);
        GlideApp.with(Common_Application.getApplication())
                .load(resourceId)
                .placeholder(R.drawable.icon_no_image)//默认加载图片
                .error(R.drawable.icon_no_image)//错误图片
                .skipMemoryCache(true)//跳过内存缓存
                .diskCacheStrategy(DiskCacheStrategy.ALL)//硬盘缓存策略
                .transition(DrawableTransitionOptions.withCrossFade()) // 动画渐变加载
                .into(imageView);
    }
    public  void displayImage(String url, ImageView imageView, int defaultImageResId, int errorImageResId) {
        //Netroid.displayImage(url,imageView,defaultImageResId,errorImageResId);
        GlideApp.with(Common_Application.getApplication())
                .load(url)
                .placeholder(defaultImageResId)
                .error(errorImageResId)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .into(imageView);
    }
    /**
     * 用于设置显示照片大小
     * @param url
     * @param imageView
     * @param width
     * @param height
     */
    public  void displayImageCustom(String url, ImageView imageView,int width, int height) {
        //Netroid.imagepickDisplayImage(url,imageView,width,height);
        GlideApp.with(Common_Application.getApplication())
                .load(url)
                .placeholder(R.drawable.icon_no_image)
                .error(R.drawable.icon_no_image)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .override(width,height)
                .into(imageView);
    }

    /**
     * 加载Gif图
     * @param url
     * @param imageView
     */
    public void displayGifImage(String url, ImageView imageView){
        GlideApp.with(Common_Application.getApplication())
                .asGif()
                .load(url)
                .placeholder(R.drawable.icon_no_image)//默认加载图片
                .error(R.drawable.icon_no_image)//错误图片
                .skipMemoryCache(true)//跳过内存缓存
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC )//硬盘缓存策略
                .dontAnimate()
                .into(imageView);
    }
}
