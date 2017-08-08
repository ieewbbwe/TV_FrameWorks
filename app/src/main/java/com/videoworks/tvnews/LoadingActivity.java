package com.videoworks.tvnews;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.videoworks.tvnews.ui.home.HomeActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mxh on 2017/8/8.
 * Describe：广告页面
 */

public class LoadingActivity extends NActivity {

    @Bind(R.id.m_loading_iv)
    ImageView mLoadingIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
    }

    /**
     * 初始化控件，查找View
     */
    @Override
    protected void initComp() {
        ButterKnife.bind(this);
    }

    /**
     * 初始化监听器
     */
    @Override
    protected void initListener() {

    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {
        //加载一些初始化数据
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                updateInitData();
            }
        }, 1500);
    }

    private void updateInitData() {
        pushActivity(HomeActivity.class, true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
