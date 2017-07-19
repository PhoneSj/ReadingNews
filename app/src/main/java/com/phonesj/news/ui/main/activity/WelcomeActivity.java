package com.phonesj.news.ui.main.activity;

import com.bumptech.glide.Glide;
import com.phonesj.news.R;
import com.phonesj.news.base.BaseActivity;
import com.phonesj.news.base.contract.main.WelcomeContract;
import com.phonesj.news.component.ImageLoader;
import com.phonesj.news.model.bean.WelcomeBean;
import com.phonesj.news.presenter.main.WelcomePresenter;

import android.content.Intent;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;

/**
 * Created by Phone on 2017/7/14.
 */

public class WelcomeActivity extends BaseActivity<WelcomePresenter> implements WelcomeContract.View {

    @BindView(R.id.iv_welcome_bg)
    ImageView iv_welcome_bg;
    @BindView(R.id.tv_welcome_author)
    TextView tv_welcome_author;

    @Override
    protected int getLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initEventAndData() {
        mPresenter.getWelcomeData();
    }

    @Override
    public void showContent(WelcomeBean welcomeBean) {
        ImageLoader.load(this, welcomeBean.getImg(), iv_welcome_bg);
        tv_welcome_author.setText(welcomeBean.getText());
    }

    @Override
    public void jumpToMain() {
        Log.i("phone", "jumpToMain");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    protected void onDestroy() {
        //清楚该控件的图片加载请求
        Glide.clear(iv_welcome_bg);
        super.onDestroy();
    }
}
