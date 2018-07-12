package com.ddtkj.commonmodule.Util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.TypedValue;

import com.example.permission_library.Permission_ProjectUtil_Implement;
import com.example.permission_library.Permission_ProjectUtil_Interface;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.utlis.lib.L;

import java.util.List;

/**
 * 媒体工具类
 *
 * @author: Administrator 杨重诚
 * @date: 2016/11/11:10:26
 */

public class MediaUtils {
    static Permission_ProjectUtil_Interface mPermissionProjectUtilInterface;

    /**
     * 打开相机或相册
     *
     * @param directlyOpenCamera 是否直接启动相机
     */
    public static void openMedia(final Context context, final boolean directlyOpenCamera) {
        if (mPermissionProjectUtilInterface == null)
            mPermissionProjectUtilInterface = new Permission_ProjectUtil_Implement();
        mPermissionProjectUtilInterface.onePermissions(context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,}, new String[]{"读写SD卡", "相机相册",}, new Permission_ProjectUtil_Implement.PermissionsResultListener()
        {
            @Override
            public void onResult(boolean isSucc, List<String> failPermissions) {
                if (isSucc) {
                    ImagePicker imagePicker = ImagePicker.getInstance();
                    imagePicker.setImageLoader(new ImagepickerImageLoader());
                    imagePicker.setMultiMode(false);
                    imagePicker.setShowCamera(false);
                    imagePicker.setCrop(true);
                    imagePicker.setSaveRectangle(false);
                    imagePicker.setDirectlyOpenCamera(directlyOpenCamera);

                    imagePicker.setStyle(CropImageView.Style.CIRCLE);
                    Integer radius = 150;
                    radius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, radius, context.getResources().getDisplayMetrics());
                    imagePicker.setFocusWidth(radius * 2);
                    imagePicker.setFocusHeight(radius * 2);

                    imagePicker.setOutPutX(Integer.valueOf(radius * 2));
                    imagePicker.setOutPutY(Integer.valueOf(radius * 2));

                    Intent intent = new Intent(context, ImageGridActivity.class);
                    ((Activity) context).startActivityForResult(intent, 100);
                }
            }
        }, true);
    }

    /**
     * 打开相机或相册
     *
     * @param directlyOpenCamera 是否直接启动相机
     *                           style 裁切样式
     */
    public static void openMedia(final Context context, final boolean directlyOpenCamera, final CropImageView.Style style) {
        if (mPermissionProjectUtilInterface == null)
            mPermissionProjectUtilInterface = new Permission_ProjectUtil_Implement();
        mPermissionProjectUtilInterface.onePermissions(context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,}, new String[]{"读写SD卡", "相机相册",}, new Permission_ProjectUtil_Implement.PermissionsResultListener()
        {
            @Override
            public void onResult(boolean isSucc, List<String> failPermissions) {
                L.e("=====isSucc=====",isSucc+"");
                if (isSucc) {
                    ImagePicker imagePicker = ImagePicker.getInstance();
                    imagePicker.setImageLoader(new ImagepickerImageLoader());
                    imagePicker.setMultiMode(false);
                    imagePicker.setShowCamera(false);
                    imagePicker.setCrop(true);
                    imagePicker.setSaveRectangle(false);
                    imagePicker.setDirectlyOpenCamera(directlyOpenCamera);

                    imagePicker.setStyle(style);
                    Integer radius = 150;
                    radius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, radius, context.getResources().getDisplayMetrics());
                    imagePicker.setFocusWidth(radius * 2);
                    imagePicker.setFocusHeight(radius * 2);

                    imagePicker.setOutPutX(Integer.valueOf(radius * 2));
                    imagePicker.setOutPutY(Integer.valueOf(radius * 2));

                    Intent intent = new Intent(context, ImageGridActivity.class);
                    ((Activity) context).startActivityForResult(intent, 100);
                }
            }
        }, true);
    }

    /**
     * 打开相机或相册
     *
     * @param directlyOpenCamera 是否直接启动相机
     * @param isCrop             是否裁切
     */
    public static void openMedia(final Context context, final boolean directlyOpenCamera, final boolean isCrop) {
        if (mPermissionProjectUtilInterface == null)
            mPermissionProjectUtilInterface = new Permission_ProjectUtil_Implement();
        mPermissionProjectUtilInterface.onePermissions(context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,}, new String[]{"读写SD卡", "相机相册",}, new Permission_ProjectUtil_Implement.PermissionsResultListener()
        {
            @Override
            public void onResult(boolean isSucc, List<String> failPermissions) {
                if (isSucc) {
                    ImagePicker imagePicker = ImagePicker.getInstance();
                    imagePicker.setImageLoader(new ImagepickerImageLoader());
                    imagePicker.setMultiMode(false);
                    imagePicker.setShowCamera(false);
                    imagePicker.setCrop(isCrop);
                    imagePicker.setSaveRectangle(false);
                    imagePicker.setDirectlyOpenCamera(directlyOpenCamera);

                    Integer radius = 150;
                    radius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, radius, context.getResources().getDisplayMetrics());
                    imagePicker.setFocusWidth(radius * 2);
                    imagePicker.setFocusHeight(radius * 2);

                    imagePicker.setOutPutX(Integer.valueOf(radius * 2));
                    imagePicker.setOutPutY(Integer.valueOf(radius * 2));

                    Intent intent = new Intent(context, ImageGridActivity.class);
                    ((Activity) context).startActivityForResult(intent, 100);
                }
            }
        }, true);
    }

    /**
     * 打开相机或相册，支持多选
     *
     * @param context
     * @param directlyOpenCamera 是否直接启动相机
     * @param selectLimit        选择图片数量
     */
    public static void openMediaMore(final Context context, final boolean directlyOpenCamera, final int selectLimit) {
        if (mPermissionProjectUtilInterface == null)
            mPermissionProjectUtilInterface = new Permission_ProjectUtil_Implement();
        mPermissionProjectUtilInterface.onePermissions(context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,}, new String[]{"读写SD卡", "相机相册",}, new Permission_ProjectUtil_Implement.PermissionsResultListener()
        {
            @Override
            public void onResult(boolean isSucc, List<String> failPermissions) {
                if (isSucc) {
                    ImagePicker imagePicker = ImagePicker.getInstance();
                    imagePicker.setImageLoader(new ImagepickerImageLoader());
                    imagePicker.setMultiMode(true);
                    imagePicker.setSelectLimit(selectLimit);
                    imagePicker.setShowCamera(false);
                    imagePicker.setCrop(false);
                    imagePicker.setSaveRectangle(false);
                    imagePicker.setDirectlyOpenCamera(directlyOpenCamera);

                    imagePicker.setStyle(CropImageView.Style.CIRCLE);
                    Integer radius = 150;
                    radius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, radius, context.getResources().getDisplayMetrics());
                    imagePicker.setFocusWidth(radius * 2);
                    imagePicker.setFocusHeight(radius * 2);

                    imagePicker.setOutPutX(Integer.valueOf(radius * 2));
                    imagePicker.setOutPutY(Integer.valueOf(radius * 2));

                    Intent intent = new Intent(context, ImageGridActivity.class);
                    ((Activity) context).startActivityForResult(intent, 100);
                }
            }
        }, true);
    }

    /**
     * 打开相册带相机，支持多选，不可裁切
     *
     * @param context
     * @param selectLimit 选择图片数量
     */
    public static void openMediaMore(final Context context, final int selectLimit) {
        if (mPermissionProjectUtilInterface == null)
            mPermissionProjectUtilInterface = new Permission_ProjectUtil_Implement();
        mPermissionProjectUtilInterface.onePermissions(context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                new String[]{"读写SD卡"}, new Permission_ProjectUtil_Implement.PermissionsResultListener()
        {
            @Override
            public void onResult(boolean isSucc, List<String> failPermissions) {
                if (isSucc) {
                    ImagePicker imagePicker = ImagePicker.getInstance();
                    imagePicker.setImageLoader(new ImagepickerImageLoader());
                    imagePicker.setMultiMode(true);
                    imagePicker.setSelectLimit(selectLimit);
                    imagePicker.setShowCamera(true);
                    imagePicker.setCrop(false);
                    imagePicker.setSaveRectangle(false);
                    imagePicker.setDirectlyOpenCamera(false);

                    imagePicker.setStyle(CropImageView.Style.CIRCLE);
                    Integer radius = 150;
                    radius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, radius, context.getResources().getDisplayMetrics());
                    imagePicker.setFocusWidth(radius * 2);
                    imagePicker.setFocusHeight(radius * 2);

                    imagePicker.setOutPutX(Integer.valueOf(radius * 2));
                    imagePicker.setOutPutY(Integer.valueOf(radius * 2));

                    Intent intent = new Intent(context, ImageGridActivity.class);
                    ((Activity) context).startActivityForResult(intent, 100);
                }
            }
        }, true);
    }

    /**
     * 打开相机或相册-fragment
     *
     * @param directlyOpenCamera 是否直接启动相机
     * @param isCrop             是否裁切
     */
    public static void openMedia(final Fragment fragment, final boolean directlyOpenCamera, final boolean isCrop) {
        if (mPermissionProjectUtilInterface == null)
            mPermissionProjectUtilInterface = new Permission_ProjectUtil_Implement();
        mPermissionProjectUtilInterface.onePermissions(fragment.getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,}, new String[]{"读写SD卡", "相机相册",}, new Permission_ProjectUtil_Implement.PermissionsResultListener()
        {
            @Override
            public void onResult(boolean isSucc, List<String> failPermissions) {
                if (isSucc) {
                    ImagePicker imagePicker = ImagePicker.getInstance();
                    imagePicker.setImageLoader(new ImagepickerImageLoader());
                    imagePicker.setMultiMode(false);
                    imagePicker.setShowCamera(false);
                    imagePicker.setCrop(isCrop);
                    imagePicker.setSaveRectangle(false);
                    imagePicker.setDirectlyOpenCamera(directlyOpenCamera);

                    Integer radius = 150;
                    radius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, radius, fragment.getResources().getDisplayMetrics());
                    imagePicker.setFocusWidth(radius * 2);
                    imagePicker.setFocusHeight(radius * 2);

                    imagePicker.setOutPutX(Integer.valueOf(radius * 2));
                    imagePicker.setOutPutY(Integer.valueOf(radius * 2));

                    Intent intent = new Intent(fragment.getActivity(), ImageGridActivity.class);
                    fragment.startActivityForResult(intent, 100);
                }
            }
        }, true);
    }

    /**
     * 打开相册带相机，支持多选，不可裁切
     *
     * @param fragment
     * @param selectLimit 选择图片数量
     */
    public static void openMediaMore(final Fragment fragment, final int selectLimit) {
        if (mPermissionProjectUtilInterface == null)
            mPermissionProjectUtilInterface = new Permission_ProjectUtil_Implement();
        mPermissionProjectUtilInterface.onePermissions(fragment.getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                new String[]{"读写SD卡"}, new Permission_ProjectUtil_Implement.PermissionsResultListener()
        {
            @Override
            public void onResult(boolean isSucc, List<String> failPermissions) {
                if (isSucc) {
                    ImagePicker imagePicker = ImagePicker.getInstance();
                    imagePicker.setImageLoader(new ImagepickerImageLoader());
                    imagePicker.setMultiMode(true);
                    imagePicker.setSelectLimit(selectLimit);
                    imagePicker.setShowCamera(true);
                    imagePicker.setCrop(false);
                    imagePicker.setSaveRectangle(false);
                    imagePicker.setDirectlyOpenCamera(false);

                    imagePicker.setStyle(CropImageView.Style.CIRCLE);
                    Integer radius = 150;
                    radius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, radius, fragment.getResources().getDisplayMetrics());
                    imagePicker.setFocusWidth(radius * 2);
                    imagePicker.setFocusHeight(radius * 2);

                    imagePicker.setOutPutX(Integer.valueOf(radius * 2));
                    imagePicker.setOutPutY(Integer.valueOf(radius * 2));

                    Intent intent = new Intent(fragment.getActivity(), ImageGridActivity.class);
                    fragment.startActivityForResult(intent, 100);
                }
            }
        }, true);
    }
}
