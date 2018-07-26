package com.ddtkj.grabRedEnvelopeModule.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ddtkj.commonmodule.Util.ImageLoaderUtils;
import com.ddtkj.grabRedEnvelopeModule.MVP.Model.Bean.ResponseBean.GrabRedEnvelopeModule_Bean_RedInfoListInfo;
import com.ddtkj.grabRedEnvelopeModule.R;
import com.utlis.lib.Textutils;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * 红包详情配器
 *
 *  @Author: 杨重诚
 *  @CreatTime: 2018/3/27  17:46
 */

public class GrabRedEnvelopeModule_Adapter_Act_RedInfoList extends SuperAdapter<GrabRedEnvelopeModule_Bean_RedInfoListInfo> {
    public GrabRedEnvelopeModule_Adapter_Act_RedInfoList(Context context, List<GrabRedEnvelopeModule_Bean_RedInfoListInfo> datas) {
        super(context, datas, R.layout.grabredenvelopemodule_act_item_redinfo_list_layout);
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
    public void onBind(final SuperViewHolder holder, int viewType, int layoutPosition, final GrabRedEnvelopeModule_Bean_RedInfoListInfo bean) {
        if (holder instanceof ViewHolder) {
            final ViewHolder holder1 = (ViewHolder) holder;
            ImageLoaderUtils.getInstance(mContext).displayImage(bean.getAvatar(),holder1.imgIcon);
            holder1.tvName.setText(Textutils.checkText(bean.getNikeName()));
            holder1.tvTime.setText(Textutils.checkText(bean.getTime()));
            holder1.tvPrice.setText(Textutils.checkText(bean.getRedPrice()));

            if(bean.getStatus().equals("1")){
                holder1.imgMediumPackage.setVisibility(View.VISIBLE);
            }else {
                holder1.imgMediumPackage.setVisibility(View.GONE);
            }

            if(layoutPosition<getData().size()-1){
                holder1.viewLine.setVisibility(View.VISIBLE);
            }else {
                holder1.viewLine.setVisibility(View.GONE);
            }
        }
    }
    public class ViewHolder extends SuperViewHolder {
        private ImageView imgIcon;
        private TextView tvName;
        private TextView tvTime;
        private TextView tvPrice;
        private View viewLine;
        private ImageView imgMediumPackage;
        public ViewHolder(View itemView) {
            super(itemView);
            imgIcon=itemView.findViewById(R.id.imgIcon);
            tvName=itemView.findViewById(R.id.tvName);
            tvTime=itemView.findViewById(R.id.tvTime);
            tvPrice=itemView.findViewById(R.id.tvPrice);
            viewLine=itemView.findViewById(R.id.viewLine);
            imgMediumPackage=itemView.findViewById(R.id.imgMediumPackage);
        }
    }
}
