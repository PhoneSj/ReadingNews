package com.phonesj.news.ui.zhihu.adapter;

import com.phonesj.news.R;
import com.phonesj.news.component.ImageLoader;
import com.phonesj.news.model.bean.zhihu.CommonBean;
import com.phonesj.news.util.DateUtil;
import com.phonesj.news.widget.CircleImageView;

import java.util.List;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Phone on 2017/7/20.
 */

public class CommonAdapter extends RecyclerView.Adapter<CommonAdapter.ViewHolder> {

    private static final int MAX_LINE = 2;  //起始最多显示2行
    private Context mContext;
    private List<CommonBean.CommentsBean> datas;

    public CommonAdapter(Context mContext, List<CommonBean.CommentsBean> datas) {
        this.mContext = mContext;
        this.datas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_comment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        ImageLoader.load(mContext, datas.get(position).getAvatar(), holder.civCommentFace);
        holder.tvCommentName.setText(datas.get(position).getAuthor());
        holder.tvCommentContent.setText(datas.get(position).getContent());
        holder.tvCommentTime.setText(DateUtil.formatTime2String(datas.get(position).getTime()));
        holder.tvCommentLike.setText(String.valueOf(datas.get(position).getLikes()));

        final CommonBean.CommentsBean.ReplyToBean replyToBean = datas.get(position).getReply_to();
        if (replyToBean != null && replyToBean.getId() != 0) {
            holder.tvCommentReply.setVisibility(View.VISIBLE);
            SpannableString ss = new SpannableString("@" + replyToBean.getAuthor() + ":" + replyToBean
                .getContent());
            //分段设置颜色
            ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.comment_at)), 0, replyToBean
                .getAuthor()
                .length() + 2, Spanned.SPAN_INCLUSIVE_INCLUSIVE);

            holder.tvCommentReply.setText(ss);

            if (replyToBean.getExpandState() == OnStateClickListener.STATE_NULL) {
                holder.tvCommentReply.post(new Runnable() {
                    @Override
                    public void run() {
                        if (holder.tvCommentReply.getLineCount() > MAX_LINE) {
                            holder.tvCommentReply.setMaxLines(MAX_LINE);
                            holder.tvCommentExpand.setVisibility(View.VISIBLE);
                            holder.tvCommentExpand.setText("展开");
                            holder.tvCommentExpand.setOnClickListener(new OnStateClickListener(replyToBean, holder.tvCommentReply));
                            replyToBean.setExpandState(OnStateClickListener.STATE_SHRINK);
                        } else {
                            holder.tvCommentExpand.setVisibility(View.GONE);
                            replyToBean.setExpandState(OnStateClickListener.STATE_NONE);
                        }
                    }
                });
            } else if (replyToBean.getExpandState() == OnStateClickListener.STATE_NONE) {
                holder.tvCommentExpand.setVisibility(View.GONE);
            } else if (replyToBean.getExpandState() == OnStateClickListener.STATE_EXPAND) {
                holder.tvCommentExpand.setVisibility(View.VISIBLE);
                holder.tvCommentExpand.setText("收起");
                holder.tvCommentExpand.setOnClickListener(new OnStateClickListener(replyToBean, holder.tvCommentReply));
                holder.tvCommentReply.setMaxLines(MAX_LINE);
            } else if (replyToBean.getExpandState() == OnStateClickListener.STATE_SHRINK) {
                holder.tvCommentExpand.setVisibility(View.VISIBLE);
                holder.tvCommentExpand.setText("展开");
                holder.tvCommentExpand.setOnClickListener(new OnStateClickListener(replyToBean, holder.tvCommentReply));
                holder.tvCommentReply.setMaxLines(MAX_LINE);
            }
        } else {
            holder.tvCommentReply.setVisibility(View.GONE);
            holder.tvCommentExpand.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.civ_comment_face)
        CircleImageView civCommentFace;
        @BindView(R.id.tv_comment_name)
        TextView tvCommentName;
        @BindView(R.id.tv_comment_content)
        TextView tvCommentContent;
        @BindView(R.id.tv_comment_reply)
        TextView tvCommentReply;
        @BindView(R.id.tv_comment_time)
        TextView tvCommentTime;
        @BindView(R.id.tv_comment_expand)
        TextView tvCommentExpand;
        @BindView(R.id.tv_comment_like)
        TextView tvCommentLike;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private class OnStateClickListener implements View.OnClickListener {

        static final int STATE_NULL = 0;    //未知
        static final int STATE_NONE = 1;    //无需展开
        static final int STATE_EXPAND = 2;  //已展开
        static final int STATE_SHRINK = 3;  //已收缩

        private CommonBean.CommentsBean.ReplyToBean bean;
        private TextView targetVieww;

        public OnStateClickListener(CommonBean.CommentsBean.ReplyToBean bean, TextView targetVieww) {
            this.bean = bean;
            this.targetVieww = targetVieww;
        }

        @Override
        public void onClick(View view) {
            TextView sourceView = (TextView) view;
            if (bean.getExpandState() == STATE_SHRINK) {
                targetVieww.setMaxLines(Integer.MAX_VALUE);
                targetVieww.setEllipsize(null);
                bean.setExpandState(STATE_EXPAND);
                sourceView.setText("收起");
            } else {
                targetVieww.setMaxLines(MAX_LINE);
                targetVieww.setEllipsize(TextUtils.TruncateAt.END);//过长时显示省略号显示在末尾
                bean.setExpandState(STATE_SHRINK);
                sourceView.setText("展开");
            }
        }
    }
}
