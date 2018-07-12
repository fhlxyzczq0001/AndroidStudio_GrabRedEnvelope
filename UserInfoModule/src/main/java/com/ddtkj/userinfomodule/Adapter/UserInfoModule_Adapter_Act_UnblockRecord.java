package com.ddtkj.userinfomodule.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.ddtkj.userinfomodule.MVP.Model.Bean.ResponseBean.UserInfoModule_Bean_UnblockRecord;
import com.ddtkj.userinfomodule.R;
import com.utlis.lib.Textutils;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 *  解封账号适配器
 *
 *  @Author: 杨重诚
 *  @CreatTime: 2018/3/27  17:46
 */

public class UserInfoModule_Adapter_Act_UnblockRecord extends SuperAdapter<UserInfoModule_Bean_UnblockRecord> {
    public UserInfoModule_Adapter_Act_UnblockRecord(Context context, List<UserInfoModule_Bean_UnblockRecord> datas) {
        super(context, datas, R.layout.userinfomodule_act_item_unblock_record_list_layout);
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
    public void onBind(final SuperViewHolder holder, int viewType, int layoutPosition, final UserInfoModule_Bean_UnblockRecord bean) {
        if (holder instanceof ViewHolder) {
            final ViewHolder holder1 = (ViewHolder) holder;

            holder1.tvName.setText("用户名："+Textutils.checkText(bean.getUsername()));
            holder1.cb.setChecked(bean.getStatus().contains("normal")?true:false);
            holder1.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(b){
                        //请求解除封锁
                    }else {
                        //请求封锁
                    }
                }
            });
        }
    }
    public class ViewHolder extends SuperViewHolder {
        private TextView tvName;
        private CheckBox cb;
        public ViewHolder(View itemView) {
            super(itemView);
            tvName=itemView.findViewById(R.id.tvName);
            cb=itemView.findViewById(R.id.cb);
        }
    }
}
