package com.videoworks.tvnews.ui.home;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tv.boost.widget.TvViewPager;
import com.tv.boost.widget.focus.FocusBorder;
import com.tv.boost.widget.tablayout.TvTabLayout;
import com.videoworks.tvnews.NActivity;
import com.videoworks.tvnews.R;
import com.videoworks.tvnews.model.CategoryBean;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Create by mxh on 2017/8/8
 * Describe：应用首页
 */
public class HomeActivity extends NActivity implements HomeContract.View {

    @Inject
    HomePresenter mHomePresenter;

    @Bind(R.id.m_sys_hour_time_tv)
    TextView mSysHourTimeTv;
    @Bind(R.id.m_sys_time_minute_tv)
    TextView mSysTimeMinuteTv;
    @Bind(R.id.m_locate_tv)
    TextView mLocateTv;
    @Bind(R.id.m_search_button_iv)
    ImageView mSearchButtonIv;
    @Bind(R.id.m_content_tb)
    TvTabLayout mContentTb;
    @Bind(R.id.m_search_bar_ll)
    RelativeLayout mSearchBarLl;
    @Bind(R.id.m_content_vp)
    TvViewPager mContentVp;
    private FocusBorder focusBorder;
    private HomePagerAdapter mPagerAdapter;
    private List<CategoryBean> categoryBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        overridePendingTransition(R.anim.push_fadein_in, R.anim.push_fadein_out);

    }

    /**
     * 初始化控件，查找View
     */
    @Override
    protected void initComp() {
        ButterKnife.bind(this);
        initBorder(true);

    }

    /**
     * 初始化监听器
     */
    @Override
    protected void initListener() {
        mSearchBarLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        focusBorder.boundGlobalFocusListener(new FocusBorder.OnFocusCallback() {
            @Override
            public FocusBorder.Options onFocus(View oldFocus, View newFocus) {
                if (newFocus != null && oldFocus != null) {
                    switch (newFocus.getId()) {
                        case R.id.m_search_bar_ll:
                            return FocusBorder.OptionsFactory.get(1f, 1f, 90f);
                    }
                    focusBorder.setVisible(false);
                }
                return null;

            }
        });
    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {
        /*---------测试数据-----------*/
        categoryBeanList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            categoryBeanList.add(new CategoryBean("热点", "" + i, "" + "标题" + i));
            mContentTb.addTab(mContentTb.newTab().setText("标题" + i), i == 0);
        }
        /*----------------------------*/
        mPagerAdapter = new HomePagerAdapter();
        mPagerAdapter.setData(categoryBeanList);
        mContentVp.setAdapter(mPagerAdapter);
        mContentTb.setupWithViewPager(mContentVp);
    }

    private void initBorder(boolean isColorful) {
        focusBorder = new FocusBorder.Builder().asColor()
                .shadowWidth(TypedValue.COMPLEX_UNIT_DIP, 18f) //阴影宽度(方式二)
                .shadowColor(getResources().getColor(R.color.colorPrimaryDark)) //阴影颜色
                .borderWidth(TypedValue.COMPLEX_UNIT_DIP, 2f) //边框宽度
                .borderColor(getResources().getColor(R.color.white)) //边框颜色
                .build(this);
    }
}
