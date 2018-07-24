package com.ddtkj.grabRedEnvelopeModule.Adapter;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ddtkj.commonmodule.HttpRequest.ResultListener.Common_ResultDataListener;
import com.ddtkj.commonmodule.MVP.Model.Bean.ResponseBean.Common_RequestBean;
import com.ddtkj.commonmodule.Public.Common_RouterUrl;
import com.ddtkj.commonmodule.Util.Common_CustomDialogBuilder;
import com.ddtkj.commonmodule.Util.IntentUtil;
import com.ddtkj.grabRedEnvelopeModule.CustomView.RedAnimation.OnRedPacketDialogClickListener;
import com.ddtkj.grabRedEnvelopeModule.CustomView.RedAnimation.RedPacketEntity;
import com.ddtkj.grabRedEnvelopeModule.CustomView.RedAnimation.RedPacketViewHolder;
import com.ddtkj.grabRedEnvelopeModule.MVP.Contract.Activity.GrabRedEnvelopeModule_Act_RedGroup_List_Contract;
import com.ddtkj.grabRedEnvelopeModule.MVP.Model.Bean.ResponseBean.GrabRedEnvelopeModule_Bean_RedGroupListInfo;
import com.ddtkj.grabRedEnvelopeModule.R;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
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
    GrabRedEnvelopeModule_Act_RedGroup_List_Contract.View mParentView;
    public GrabRedEnvelopeModule_Adapter_Act_RedGroupList(Context context, List<GrabRedEnvelopeModule_Bean_RedGroupListInfo> datas,
                                                          IMulItemViewType<GrabRedEnvelopeModule_Bean_RedGroupListInfo> multiItemViewType, GrabRedEnvelopeModule_Act_RedGroup_List_Contract.View mParentView) {
        super(context, datas, multiItemViewType);
        this.mParentView=mParentView;
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
                    mParentView.requestRedpacketIsShowhb(bean.getId(), new Common_ResultDataListener() {
                        @Override
                        public void onResult(boolean isSucc, String msg, Common_RequestBean request_bean) {
                            if(isSucc){
                                Resources r =mContext.getResources();
                                Uri uri =  Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                                        + r.getResourcePackageName(R.mipmap.icon_launcher) + "/"
                                        + r.getResourceTypeName(R.mipmap.icon_launcher) + "/"
                                        + r.getResourceEntryName(R.mipmap.icon_launcher));
                                RedPacketEntity entity = new RedPacketEntity("chaychan", uri.toString(), "大吉大利，今晚吃鸡");
                                showRedPacketDialog(entity,bean);
                            }
                        }
                    });

                  /*  Resources r =mContext.getResources();
                    Uri uri =  Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                            + r.getResourcePackageName(R.mipmap.icon_launcher) + "/"
                            + r.getResourceTypeName(R.mipmap.icon_launcher) + "/"
                            + r.getResourceEntryName(R.mipmap.icon_launcher));
                    RedPacketEntity entity = new RedPacketEntity("chaychan", uri.toString(), "大吉大利，今晚吃鸡");
                    showRedPacketDialog(entity,bean);*/
                }
            });
            holder1.tvDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new IntentUtil().intent_RouterTo(mContext, Common_RouterUrl.GRAB_RED_ENVELOPE_RedInfoListRouterUrl+"?hb_id="+bean.getId());
                }
            });
        }
    }
    private View mRedPacketDialogView;
    private RedPacketViewHolder mRedPacketViewHolder;
    private Common_CustomDialogBuilder mRedPacketDialog;
    public void showRedPacketDialog(RedPacketEntity entity, final GrabRedEnvelopeModule_Bean_RedGroupListInfo bean) {
        if (mRedPacketDialogView == null) {
            mRedPacketDialogView = View.inflate(mContext, R.layout.dialog_red_packet, null);
            mRedPacketViewHolder = new RedPacketViewHolder(mContext, mRedPacketDialogView);
            mRedPacketDialog = new Common_CustomDialogBuilder(mContext);
        }
        final NiftyDialogBuilder dialogBuilder =  mRedPacketDialog.showDialog(mRedPacketDialogView);
        //设置弹窗圆角背景
        dialogBuilder.getParentPanel().setBackgroundColor(Color.parseColor("#00000000"));
        mRedPacketViewHolder.setData(entity);
        mRedPacketViewHolder.setOnRedPacketDialogClickListener(new OnRedPacketDialogClickListener() {
            @Override
            public void onCloseClick() {
                dialogBuilder.dismiss();
            }
            @Override
            public void onOpenClick() {
                //领取红包,调用接口
                mParentView.requestRedpacketPacketinfodetail(bean.getId(), new Common_ResultDataListener() {
                    @Override
                    public void onResult(boolean isSucc, String msg, Common_RequestBean request_bean) {
                        if(isSucc){
                            mRedPacketViewHolder.stopAnim();
                            dialogBuilder.dismiss();
                            new IntentUtil().intent_RouterTo(mContext, Common_RouterUrl.GRAB_RED_ENVELOPE_RedInfoListRouterUrl+"?hb_id="+bean.getId());
                        }
                    }
                });

              /*  new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRedPacketViewHolder.stopAnim();
                        dialogBuilder.dismiss();
                        new IntentUtil().intent_RouterTo(mContext, Common_RouterUrl.GRAB_RED_ENVELOPE_RedInfoListRouterUrl+"?hb_id="+bean.getId());
                    }
                },3000);*/
            }
        });
        dialogBuilder.show();
    }
    public class ViewHolder extends SuperViewHolder {
        private TextView tvTime;
        private TextView tvDetails;
        private ImageView imgBtnRedEnvelope;
        public ViewHolder(View itemView) {
            super(itemView);
            tvTime=itemView.findViewById(R.id.tvTime);
            tvDetails=itemView.findViewById(R.id.tvDetails);
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
