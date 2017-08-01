package com.phonesj.news.ui.gold.adapter;

import com.phonesj.news.R;
import com.phonesj.news.model.bean.gold.GoldManagerItemBean;
import com.phonesj.news.ui.gold.fragment.GoldMainFragment;

import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Phone on 2017/7/29.
 */

public class GoldManagerAdapter extends RecyclerView.Adapter<GoldManagerAdapter.ViewHolder> {

    private Context mContext;
    private List<GoldManagerItemBean> datas;

    public GoldManagerAdapter(Context mContext, List<GoldManagerItemBean> datas) {
        this.mContext = mContext;
        this.datas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
            .from(mContext)
            .inflate(R.layout.item_gold_manager, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tvGoldManagerType.setText(GoldMainFragment.typeStr[datas.get(position).getIndex()]);
        holder.scGoldManagerSwitch.setChecked(datas.get(position).isSelected());
        holder.scGoldManagerSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                datas.get(holder.getAdapterPosition()).setSelected(b);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_gold_manager_type)
        TextView tvGoldManagerType;
        @BindView(R.id.sc_gold_manager_switch)
        SwitchCompat scGoldManagerSwitch;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
