package com.videoworks.tvnews.ui.home.pager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;

import com.videoworks.tvnews.model.CategoryBean;

/**
 * Created by mxh on 2017/8/10.
 * Describe：首页面共用类
 */

public abstract class BasePager {

    private Context context;
    private LayoutInflater inflater;
    private View view;

    protected abstract int create();

    protected abstract void initComp();

    protected abstract void initListener();

    protected abstract void initData();

    /**
     * 页面初始化
     *
     * @param context   上下文
     * @param columInfo 列表条目
     */
    public void init(Context context, CategoryBean columInfo) {
        inflater = LayoutInflater.from(context);
        view = inflater.inflate(create(), null, false);
        init();
    }

    private void init() {
        initComp();
        initListener();
        initData();
    }

    public View getView() {
        return view;
    }

    @NonNull
    public static BasePager getImpl(CategoryBean columInfo) {
        String name = columInfo.getName();
        switch (name) {
            case "热点":
                return new HotPointPager();
            case "推荐":
                return new HotPointPager();
            default:
                return new HotPointPager();
        }
    }
}
