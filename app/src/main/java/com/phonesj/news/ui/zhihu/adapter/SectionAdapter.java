package com.phonesj.news.ui.zhihu.adapter;

import com.phonesj.news.R;
import com.phonesj.news.component.ImageLoader;
import com.phonesj.news.model.bean.zhihu.SectionBean;

import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Phone on 2017/7/19.
 */

public class SectionAdapter extends RecyclerView.Adapter<SectionAdapter.ViewHolder> {

    private Context mContext;
    private List<SectionBean.DataBean> datas;

    public SectionAdapter(Context mContext, List<SectionBean.DataBean> datas) {
        this.mContext = mContext;
        this.datas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_section, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // TODO: 2017/7/19
        //        //Glide在加载GridView等时,由于ImageView和Bitmap实际大小不符合,第一次时加载可能会变形(我这里出现了放大),必须在加载前再次固定ImageView大小
        //        ViewGroup.LayoutParams lp = holder.sectionBg.getLayoutParams();
        //        lp.width = (App.SCREEN_WIDTH - SystemUtil.dp2px(mContext,12)) / 2;
        //        lp.height = SystemUtil.dp2px(mContext,120);

        ImageLoader.load(mContext, datas.get(position).getThumbnail(), holder.sectionBg);
        holder.sectionKind.setText(datas.get(position).getName());
        holder.sectionDes.setText(datas.get(position).getDescription());

        // TODO: 2017/7/19 监听器
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.section_bg)
        ImageView sectionBg;
        @BindView(R.id.section_kind)
        TextView sectionKind;
        @BindView(R.id.section_des)
        TextView sectionDes;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
