package com.tv.boost.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.OverScroller;
import android.widget.ScrollView;

import com.tv.boost.R;

import java.lang.reflect.Field;

import static android.R.attr.fadingEdge;

/**
 * Created by owen on 2016/10/21.
 */

public class TvVerticalScrollView extends ScrollView {
    private FixedSpeedScroller mScroller;
    private float mSelectedItemOffsetStart = 0;
    private float mSelectedItemOffsetEnd = 0;
    private boolean mIsSelectedCentered = false;
    
    public TvVerticalScrollView(Context context) {
        this(context, null);
    }

    public TvVerticalScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TvVerticalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initScroller(getContext());
        
        if(null != attrs) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TvScrollView, defStyleAttr, 0);
            if(null != a) {
                mSelectedItemOffsetStart = a.getDimension(R.styleable.TvScrollView_tsv_selected_offset_start, 0);
                mSelectedItemOffsetEnd = a.getDimension(R.styleable.TvScrollView_tsv_selected_offset_end, 0);
                mIsSelectedCentered = a.getBoolean(R.styleable.TvScrollView_tsv_is_selected_centered, false);
                setScrollerDuration(a.getInt(R.styleable.TvScrollView_tsv_scroller_duration, 600));
            }
        }
        
    }

    private void initScroller(Context context) {
        if(null != mScroller)
            return;
        try {
            mScroller = new FixedSpeedScroller(context, new AccelerateInterpolator());
            Field field = this.getClass().getDeclaredField("mScroller");
            field.setAccessible(true);
            field.set(this, mScroller);
        } catch (Exception e) {
            Log.e(this.getClass().getSimpleName(), e.getMessage());
        }
    }

    /**
     * 设置滚动时长
     * @param duration
     */
    public void setScrollerDuration(int duration){
        initScroller(getContext());
        if(null != mScroller) {
            mScroller.setScrollDuration(duration);
        }
    }

    /**
     * 获取滚动时长
     * @return
     */
    public int getScrollerDuration() {
        initScroller(getContext());
        if(null != mScroller) {
            return mScroller.getScrollDuration();
        }
        return 0;
    }

    public void setSelectedCentered(boolean selectedCentered) {
        mIsSelectedCentered = selectedCentered;
    }

    public boolean isSelectedCentered() {
        return mIsSelectedCentered;
    }

    public void setSelectedItemOffsetStart(float selectedItemOffsetStart) {
        mSelectedItemOffsetStart = selectedItemOffsetStart;
    }

    public float getSelectedItemOffsetStart() {
        return mSelectedItemOffsetStart;
    }

    public void setSelectedItemOffsetEnd(float selectedItemOffsetEnd) {
        mSelectedItemOffsetEnd = selectedItemOffsetEnd;
    }

    public float getSelectedItemOffsetEnd() {
        return mSelectedItemOffsetEnd;
    }

    //此处借鉴：https://github.com/FrozenFreeFall/Android-tv-widget
    @Override
    protected int computeScrollDeltaToGetChildRectOnScreen(Rect rect) {
        if (getChildCount() == 0)
            return 0;

        int height = getHeight();
        int screenTop = getScrollY();
        int screenBottom = screenTop + height;

        if(mIsSelectedCentered && null != rect && !rect.isEmpty()) {
            mSelectedItemOffsetStart = height - getPaddingTop() - getPaddingBottom() - rect.height();
            mSelectedItemOffsetStart /= 2f;
            mSelectedItemOffsetEnd = mSelectedItemOffsetStart;
        }
        
        if (rect.top > 0) {
            screenTop += mSelectedItemOffsetStart;
        }
        if (rect.bottom < getChildAt(0).getHeight()) {
            screenBottom -= mSelectedItemOffsetEnd;
        }
        
        int scrollYDelta = 0;
        if (rect.bottom > screenBottom && rect.top > screenTop) {
            if (rect.height() > height) {
                scrollYDelta += (rect.top - screenTop);
            } else {
                scrollYDelta += (rect.bottom - screenBottom);
            }
            int bottom = getChildAt(0).getBottom();
            int distanceToBottom = bottom - screenBottom;
            scrollYDelta = Math.min(scrollYDelta, distanceToBottom);
        } else if (rect.top < screenTop && rect.bottom < screenBottom) {
            if (rect.height() > height) {
                scrollYDelta -= (screenBottom - rect.bottom);
            } else {
                scrollYDelta -= (screenTop - rect.top);
            }
            scrollYDelta = Math.max(scrollYDelta, -getScrollY());
        }
        return scrollYDelta;
    }

    /**
     * 固定速度的Scroller
     * */
    public static class FixedSpeedScroller extends OverScroller {
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
