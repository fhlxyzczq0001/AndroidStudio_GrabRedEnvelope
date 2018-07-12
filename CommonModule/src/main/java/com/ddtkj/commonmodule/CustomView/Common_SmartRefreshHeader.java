package com.ddtkj.commonmodule.CustomView;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ddtkj.commonmodule.R;
import com.nineoldandroids.view.ViewHelper;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;

/**
 * 经典下拉头部
 * Created by SCWANG on 2017/5/28.
 */
@SuppressWarnings({"unused", "UnusedReturnValue"})
public class Common_SmartRefreshHeader extends FrameLayout implements RefreshHeader {

    static final String LOG_TAG = "PullToRefresh_Loading_Layout";

    private LinearLayout mInnerLayout;

    private  TextView mHeaderText;
    private  TextView mSubHeaderText;

    private CharSequence mPullLabel;
    private CharSequence mRefreshingLabel;
    private CharSequence mReleaseLabel;

    private ImageView mGoodsImage;
    private ImageView mPersonImage;
    private AnimationDrawable animP;
    
    public Common_SmartRefreshHeader(@NonNull Context context) {
        super(context);
        this.initView(context);
    }

    public Common_SmartRefreshHeader(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.initView(context);
    }

    public Common_SmartRefreshHeader(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initView(context);
    }
    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.common_customview_pulltorefresh_layout, this);

        mInnerLayout = (LinearLayout) findViewById(R.id.fl_inner);
        mHeaderText = (TextView) mInnerLayout.findViewById(R.id.pull_to_refresh_text);
        mSubHeaderText = (TextView) mInnerLayout.findViewById(R.id.pull_to_refresh_sub_text);
        mGoodsImage = (ImageView) mInnerLayout.findViewById(R.id.pull_to_refresh_goods);
        mPersonImage = (ImageView) mInnerLayout.findViewById(R.id.pull_to_refresh_people);

        // Load in labels
        mPullLabel = "下拉可以刷新";
        mRefreshingLabel = "正在加载中";
        mReleaseLabel = "松开后刷新";

        reset();
    }

    @Override
    public void onReleased(RefreshLayout refreshLayout, int height, int extendHeight) {

    }

    @NonNull
    @Override
    public View getView() {
        return this;//真实的视图就是自己，不能返回null
    }

    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;//指定为平移，不能null
    }

    @Override
    public void setPrimaryColors(int... colors) {

    }

    @Override
    public void onInitialized(RefreshKernel kernel, int height, int extendHeight) {

    }

    @Override
    public void onMoving(boolean isDragging,float scaleOfLayout, int offset, int headerHeight, int extendHeight) {
        scaleOfLayout = scaleOfLayout > 1.0f ? 1.0f : scaleOfLayout;

        if (mGoodsImage.getVisibility() != View.VISIBLE) {
            mGoodsImage.setVisibility(View.VISIBLE);
        }

        //透明度动画
        ObjectAnimator animAlphaP = ObjectAnimator.ofFloat(mPersonImage, "alpha", -1, 1).setDuration(300);
        animAlphaP.setCurrentPlayTime((long) (scaleOfLayout * 300));
        ObjectAnimator animAlphaG = ObjectAnimator.ofFloat(mGoodsImage, "alpha", -1, 1).setDuration(300);
        animAlphaG.setCurrentPlayTime((long) (scaleOfLayout * 300));

        //缩放动画
        ViewHelper.setPivotX(mPersonImage, 0);  // 设置中心点
        ViewHelper.setPivotY(mPersonImage, 0);
        ObjectAnimator animPX = ObjectAnimator.ofFloat(mPersonImage, "scaleX", 0, 1).setDuration(300);
        animPX.setCurrentPlayTime((long) (scaleOfLayout * 300));
        ObjectAnimator animPY = ObjectAnimator.ofFloat(mPersonImage, "scaleY", 0, 1).setDuration(300);
        animPY.setCurrentPlayTime((long) (scaleOfLayout * 300));

        ViewHelper.setPivotX(mGoodsImage, mGoodsImage.getMeasuredWidth());
        ObjectAnimator animGX = ObjectAnimator.ofFloat(mGoodsImage, "scaleX", 0, 1).setDuration(300);
        animGX.setCurrentPlayTime((long) (scaleOfLayout * 300));
        ObjectAnimator animGY = ObjectAnimator.ofFloat(mGoodsImage, "scaleY", 0, 1).setDuration(300);
        animGY.setCurrentPlayTime((long) (scaleOfLayout * 300));
    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {

    }

    @Override
    public void onStartAnimator(RefreshLayout layout, int height, int extendHeight) {
        mSubHeaderText.setText(mRefreshingLabel);

        if (animP == null) {
            mPersonImage.setImageResource(R.drawable.drawable_anim_refreshing);
            animP = (AnimationDrawable) mPersonImage.getDrawable();
        }
        animP.start();
        if (mGoodsImage.getVisibility() == View.VISIBLE) {
            mGoodsImage.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int onFinish(RefreshLayout layout, boolean success) {
        reset();
        return 500;
    }
    public final void reset() {
        if (animP != null) {
            animP.stop();
            animP = null;
        }
        mPersonImage.setImageResource(R.drawable.icon_refresh_loading1);
        if (mGoodsImage.getVisibility() == View.VISIBLE) {
            mGoodsImage.setVisibility(View.INVISIBLE);
        }
    }
    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    @Override
    public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState) {
        switch (newState) {
            case None:
            case PullDownToRefresh:
                mSubHeaderText.setText(mPullLabel);
                break;
            case Refreshing:
                mSubHeaderText.setText(mRefreshingLabel);

                if (animP == null) {
                    mPersonImage.setImageResource(R.drawable.drawable_anim_refreshing);
                    animP = (AnimationDrawable) mPersonImage.getDrawable();
                }
                animP.start();
                if (mGoodsImage.getVisibility() == View.VISIBLE) {
                    mGoodsImage.setVisibility(View.INVISIBLE);
                }
                break;
            case ReleaseToRefresh:
                mSubHeaderText.setText(mReleaseLabel);
                break;
        }
    }
}
