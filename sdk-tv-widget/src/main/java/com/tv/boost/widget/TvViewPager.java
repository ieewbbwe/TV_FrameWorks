package com.tv.boost.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Scroller;

import java.lang.reflect.Field;

/**
 * Created by owen on 16/9/5.
 */
public class TvViewPager extends ViewPager {
    private FixedSpeedScroller mScroller;
    
    public TvViewPager(Context context) {
        this(context, null);
    }

    public TvViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initScroller(context);
    }
    
    @Override
    public boolean isInEditMode() {
        return true;
    }
    
    private void initScroller(Context context) {
        try {
            mScroller = new FixedSpeedScroller(context, new AccelerateInterpolator());
            Field field = ViewPager.class.getDeclaredField("mScroller");
            field.setAccessible(true);
            field.set(this, mScroller);
        } catch (Exception e) {
            Log.e("TvViewPager", e.getMessage());
        }
    }

    /**
     * 设置ViewPager切换时的滚动时长
     * @param duration
     */
    public void setScrollerDuration(int duration){
        if(null != mScroller) {
            mScroller.setScrollDuration(duration);
        }
    }

    /**
     * 获取ViewPager切换时的滚动时长
     * @return
     */
    public int getScrollerDuration() {
        if(null != mScroller) {
            return mScroller.getScrollDuration();
        }
        return 0;
    }

    /**
     * 固定速度的Scroller
     * */
    public static class FixedSpeedScroller extends Scroller {
        private int mDuration = 600;

        public FixedSpeedScroller(Context context) {
            super(context);
        }

        public FixedSpeedScroller(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            // Ignore received duration, use fixed one instead
            super.startScroll(startX, startY, dx, dy, mDuration);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            // Ignore received duration, use fixed one instead
            super.startScroll(startX, startY, dx, dy, mDuration);
        }

        public void setScrollDuration(int time) {
            mDuration = time;
        }

        public int getScrollDuration() {
            return mDuration;
        }
    }

}
