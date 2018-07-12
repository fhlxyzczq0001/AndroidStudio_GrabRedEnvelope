package com.ddtkj.grabRedEnvelopeModule.CustomView.RedAnimation;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ddtkj.grabRedEnvelopeModule.R;

/**
 * @author ChayChan
 * @description: 红包弹框
 * @date 2017/11/27  15:12
 */

public class RedPacketViewHolder {
    ImageView mIvClose;

    ImageView mIvAvatar;

    TextView mTvName;

    TextView mTvMsg;

    ImageView mIvOpen;

    private Context mContext;
    private OnRedPacketDialogClickListener mListener;

    private int[] mImgResIds = new int[]{
            R.drawable.icon_open_red_packet1,
            R.drawable.icon_open_red_packet2,
            R.drawable.icon_open_red_packet3,
            R.drawable.icon_open_red_packet4,
            R.drawable.icon_open_red_packet5,
            R.drawable.icon_open_red_packet6,
            R.drawable.icon_open_red_packet7,
            R.drawable.icon_open_red_packet7,
            R.drawable.icon_open_red_packet8,
            R.drawable.icon_open_red_packet9,
            R.drawable.icon_open_red_packet4,
            R.drawable.icon_open_red_packet10,
            R.drawable.icon_open_red_packet11,
    };
    private FrameAnimation mFrameAnimation;

    public RedPacketViewHolder(Context context, View view) {
         mContext = context;
         mIvClose=view.findViewById(R.id.iv_close);
         mIvAvatar=view.findViewById(R.id.iv_avatar);
         mTvName=view.findViewById(R.id.tv_name);
         mTvMsg=view.findViewById(R.id.tv_msg);
         mIvOpen=view.findViewById(R.id.iv_open);

        mIvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopAnim();
                if (mListener != null) {
                    mListener.onCloseClick();
                }
            }
        });
        mIvOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFrameAnimation != null) {
                    //如果正在转动，则直接返回
                    return;
                }

                startAnim();

                if (mListener != null) {
                    mListener.onOpenClick();
                }
            }
        });
    }

    public void setData(RedPacketEntity entity) {
        RequestOptions options = new RequestOptions();
        options.centerCrop()
                .circleCrop();

        Glide.with(mContext)
                .load(entity.avatar)
                .apply(options)
                .into(mIvAvatar);

        mTvName.setText(entity.name);
        mTvMsg.setText(entity.remark);
    }

    public void startAnim() {
        mFrameAnimation = new FrameAnimation(mIvOpen, mImgResIds, 65, true);
        mFrameAnimation.setAnimationListener(new FrameAnimation.AnimationListener() {
            @Override
            public void onAnimationStart() {
                Log.i("", "start");
            }

            @Override
            public void onAnimationEnd() {
                Log.i("", "end");
            }

            @Override
            public void onAnimationRepeat() {
                Log.i("", "repeat");
            }

            @Override
            public void onAnimationPause() {
                mIvOpen.setBackgroundResource(R.drawable.icon_open_red_packet1);
            }
        });
    }

    public void stopAnim() {
        if (mFrameAnimation != null) {
            mFrameAnimation.release();
            mFrameAnimation = null;
        }
    }

    public void setOnRedPacketDialogClickListener(OnRedPacketDialogClickListener listener) {
        mListener = listener;
    }
}
