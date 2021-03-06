package com.ddtkj.grabRedEnvelopeModule.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ddtkj.grabRedEnvelopeModule.MVP.Model.Bean.ResponseBean.GrabRedEnvelopeModule_Bean_RedRoomListInfo;
import com.ddtkj.grabRedEnvelopeModule.R;
import com.utlis.lib.Textutils;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 *  首页投资列表适配器
 *
 *  @Author: 杨重诚
 *  @CreatTime: 2018/3/27  17:46
 */

public class GrabRedEnvelopeModule_Adapter_Act_InvestmentList extends SuperAdapter<GrabRedEnvelopeModule_Bean_RedRoomListInfo> {
    public GrabRedEnvelopeModule_Adapter_Act_InvestmentList(Context context, List<GrabRedEnvelopeModule_Bean_RedRoomListInfo> datas) {
        super(context, datas, R.layout.grabredenvelopemodule_act_item_redroom_list_layout);
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
    public void onBind(final SuperViewHolder holder, int viewType, int layoutPosition, final GrabRedEnvelopeModule_Bean_RedRoomListInfo bean) {
        if (holder instanceof ViewHolder) {
            final ViewHolder holder1 = (ViewHolder) holder;
            holder1.tvName.setText(Textutils.checkText(bean.getHousename()));
            holder1.tvNum.setText("在线玩家"+Textutils.checkText(bean.getHousestatus())+"位");
            if(layoutPosition<getData().size()-1){
                holder1.viewLine.setVisibility(View.VISIBLE);
            }else {
                holder1.viewLine.setVisibility(View.GONE);
            }
        }
    }
    public class ViewHolder extends SuperViewHolder {
        private TextView tvName;
        private TextView tvNum;
        private View viewLine;
        public ViewHolder(View itemView) {
            super(itemView);
            tvName=itemView.findViewById(R.id.tvName);
            tvNum=itemView.findViewById(R.id.tvNum);
            viewLine=itemView.findViewById(R.id.viewLine);
        }
    }
}
