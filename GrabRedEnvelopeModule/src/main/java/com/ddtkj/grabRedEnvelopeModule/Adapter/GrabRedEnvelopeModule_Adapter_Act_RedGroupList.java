package com.ddtkj.grabRedEnvelopeModule.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ddtkj.commonmodule.Public.Common_RouterUrl;
import com.ddtkj.commonmodule.Util.IntentUtil;
import com.ddtkj.grabRedEnvelopeModule.CustomView.RedAnimation.CustomDialog;
import com.ddtkj.grabRedEnvelopeModule.CustomView.RedAnimation.OnRedPacketDialogClickListener;
import com.ddtkj.grabRedEnvelopeModule.CustomView.RedAnimation.RedPacketEntity;
import com.ddtkj.grabRedEnvelopeModule.CustomView.RedAnimation.RedPacketViewHolder;
import com.ddtkj.grabRedEnvelopeModule.MVP.Model.Bean.ResponseBean.GrabRedEnvelopeModule_Bean_RedGroupListInfo;
import com.ddtkj.grabRedEnvelopeModule.R;
import com.utlis.lib.Textutils;

import org.byteam.superadapter.IMulItemViewType;
import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 *  红包群适配器
 *
 *  @Author: 杨重诚
 *  @CreatTime: 2018/3/27  17:46
 */

public class GrabRedEnvelopeModule_Adapter_Act_RedGroupList extends SuperAdapter<GrabRedEnvelopeModule_Bean_RedGroupListInfo> {
    public GrabRedEnvelopeModule_Adapter_Act_RedGroupList(Context context, List<GrabRedEnvelopeModule_Bean_RedGroupListInfo> datas,IMulItemViewType<GrabRedEnvelopeModule_Bean_RedGroupListInfo> multiItemViewType) {
        super(context, datas, multiItemViewType);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public SuperViewHolder onCreate(@Nullable View convertView, ViewGroup parent, int viewType) {
        SuperViewHolder superViewHolder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(mMulItemViewType == null ?
                    mLayoutResId : mMulItemViewType.getLayoutId(viewType), parent, false);
            superViewHolder = new ViewHolder(convertView);
            convertView.setTag(superViewHolder);
        } else {
            superViewHolder = (SuperViewHolder) convertView.getTag();
        }
        return superViewHolder;
    }

    @Override
    public void onBind(final SuperViewHolder holder, int viewType, int layoutPosition, final GrabRedEnvelopeModule_Bean_RedGroupListInfo bean) {
        if (holder instanceof ViewHolder) {
            final ViewHolder holder1 = (ViewHolder) holder;
            holder1.tvTime.setText(Textutils.checkText(bean.getEndtime()));
            holder1.imgBtnRedEnvelope.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //请求接口判断当前用户是否可以抢红包，可以抢弹出抢红包弹窗，否则直接进入详情界面
                    RedPacketEntity entity = new RedPacketEntity("chaychan", "http://upload.51qianmai.com/20171205180511192.png", "大吉大利，今晚吃鸡");
                    showRedPacketDialog(entity);
                }
            });
        }
    }
    private View mRedPacketDialogView;
    private RedPacketViewHolder mRedPacketViewHolder;
    private CustomDialog mRedPacketDialog;
    public void showRedPacketDialog(RedPacketEntity entity) {
        if (mRedPacketDialogView == null) {
            mRedPacketDialogView = View.inflate(mContext, R.layout.dialog_red_packet, null);
            mRedPacketViewHolder = new RedPacketViewHolder(mContext, mRedPacketDialogView);
            mRedPacketDialog = new CustomDialog(mContext, mRedPacketDialogView, R.style.custom_dialog);
            mRedPacketDialog.setCancelable(false);
        }

        mRedPacketViewHolder.setData(entity);
        mRedPacketViewHolder.setOnRedPacketDialogClickListener(new OnRedPacketDialogClickListener() {
            @Override
            public void onCloseClick() {
                mRedPacketDialog.dismiss();
            }
            @Override
            public void onOpenClick() {
                //领取红包,调用接口
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRedPacketViewHolder.stopAnim();
                        mRedPacketDialog.dismiss();
                        new IntentUtil().intent_RouterTo(mContext, Common_RouterUrl.GRAB_RED_ENVELOPE_RedInfoListRouterUrl);
                    }
                },3000);
            }
        });
        mRedPacketDialog.show();
    }
    public class ViewHolder extends SuperViewHolder {
        private TextView tvTime;
        private ImageView imgBtnRedEnvelope;
        public ViewHolder(View itemView) {
            super(itemView);
            tvTime=itemView.findViewById(R.id.tvTime);
            imgBtnRedEnvelope=itemView.findViewById(R.id.imgBtnRedEnvelope);
        }
    }

    @Override
    protected IMulItemViewType<GrabRedEnvelopeModule_Bean_RedGroupListInfo> offerMultiItemViewType() {
        return new IMulItemViewType<GrabRedEnvelopeModule_Bean_RedGroupListInfo>() {
            @Override
            public int getViewTypeCount() {
                return 2;
            }
            @Override
            public int getItemViewType(int position, GrabRedEnvelopeModule_Bean_RedGroupListInfo bean) {
                int viewType=1;
                if(position%2==0){
                    viewType= 1;
                }else {
                    viewType= 2;
                }
                return viewType;
            }
            @Override
            public int getLayoutId(int viewType) {
                if (viewType == 1) {
                    return R.layout.grabredenvelopemodule_act_item_redgroupleft_list_layout;
                }else {
                    return R.layout.grabredenvelopemodule_act_item_redgroupright_list_layout;
                }
            }
        };
    }
}
