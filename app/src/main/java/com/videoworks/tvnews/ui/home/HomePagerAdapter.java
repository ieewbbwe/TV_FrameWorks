package com.videoworks.tvnews.ui.home;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.android_mobile.core.utiles.Lg;
import com.videoworks.tvnews.model.CategoryBean;
import com.videoworks.tvnews.ui.home.pager.BasePager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mxh on 2017/8/10.
 * Describe：首页滑动适配器
 */

public class HomePagerAdapter extends PagerAdapter {

    private List<CategoryBean> showColumns;
    private Map<CategoryBean, BasePager> columnPagerMap = new HashMap<>();

    public void setData(List<CategoryBean> categorys) {
        this.showColumns = categorys;
    }

    @Override
    public int getCount() {
        return showColumns == null ? 0 : showColumns.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        CategoryBean columnItem = showColumns.get(position);
        Lg.print("picher", "页面改变实例化条目：" + position + "条目名：" + columnItem.getDescription());
        BasePager basePager;
        if (columnPagerMap.get(columnItem) == null) {
            basePager = BasePager.getImpl(columnItem);
            basePager.init(container.getContext(), columnItem);
            columnPagerMap.put(columnItem, basePager);
        } else {
            basePager = columnPagerMap.get(columnItem);
        }
        container.addView(basePager.getView());
        return basePager.getView();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return showColumns == null ? "" : showColumns.get(position).getName();
    }
}
