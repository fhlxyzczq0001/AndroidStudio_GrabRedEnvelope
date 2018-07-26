package com.ddtkj.userinfomodule.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ddtkj.userinfomodule.MVP.Model.Bean.ResponseBean.UserInfoModule_Bean_UserdealsRecord;
import com.ddtkj.userinfomodule.R;
import com.utlis.lib.Textutils;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 *  本人流水记录适配器
 *
 *  @Author: 杨重诚
 *  @CreatTime: 2018/3/27  17:46
 */

public class UserInfoModule_Adapter_Act_UserdealsRecord extends SuperAdapter<UserInfoModule_Bean_UserdealsRecord> {
    public UserInfoModule_Adapter_Act_UserdealsRecord(Context context, List<UserInfoModule_Bean_UserdealsRecord> datas) {
        super(context, datas, R.layout.userinfomodule_act_item_userdeals_record_list_layout);
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
    public void onBind(final SuperViewHolder holder, int viewType, int layoutPosition, final UserInfoModule_Bean_UserdealsRecord bean) {
        if (holder instanceof ViewHolder) {
            final ViewHolder holder1 = (ViewHolder) holder;
            holder1.tvName.setText(Textutils.checkText(bean.getLevel()));
            holder1.tvTime.setText(Textutils.checkText(bean.getDate()));
            holder1.tvTotalMoney.setText("总额："+Textutils.checkText(bean.getTotalmoney()));
            holder1.tvGetmoney.setText("得到："+Textutils.checkText(bean.getGetmoney()));
            holder1.tvConsumemoney.setText("消费："+Textutils.checkText(bean.getConsumemoney()));

            if(layoutPosition<getData().size()-1){
                holder1.viewLine.setVisibility(View.VISIBLE);
            }else {
                holder1.viewLine.setVisibility(View.GONE);
            }
        }
    }
    public class ViewHolder extends SuperViewHolder {
        private TextView tvName;
        private TextView tvTime;
        private TextView tvTotalMoney;
        private TextView tvGetmoney;
        private TextView tvConsumemoney;
        private View viewLine;
        public ViewHolder(View itemView) {
            super(itemView);
            tvName=itemView.findViewById(R.id.tvName);
            tvTime=itemView.findViewById(R.id.tvTime);
            tvTotalMoney=itemView.findViewById(R.id.tvTotalMoney);
            tvGetmoney=itemView.findViewById(R.id.tvGetmoney);
            tvConsumemoney=itemView.findViewById(R.id.tvConsumemoney);
            viewLine=itemView.findViewById(R.id.viewLine);
        }
    }
}
