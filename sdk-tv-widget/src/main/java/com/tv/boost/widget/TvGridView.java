package com.tv.boost.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListAdapter;


public class TvGridView extends GridView implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {
    private OnItemListener mItemListener;
    private OnItemClickListener mOnItemClickListener;
    private OnItemSelectedListener mOnItemSelectedListener;

    private int mTempPosition = 0;
    /**
     * 记录上次选中的gird item view
     */
    private View mPreView = null;
    /**
     * 是否显示item选中后的动画效果
     */
    private boolean isShowAnim = true;
    /**
     * 是否需要手动设置onItemSelectedListener
     */
    private boolean hasChangeItemListener;


    public TvGridView(Context context) {
        this(context, null);
    }

    public TvGridView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TvGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TvGridView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        setChildrenDrawingOrderEnabled(isShowAnim);
    }


    @Override
    public void setAdapter(final ListAdapter adapter) {
        super.setAdapter(adapter);
        if (adapter == null || adapter.getCount() < 0)
            return;

        postDelayed(new Runnable() {
            @Override
            public void run() {
                if (adapter.getCount() > 0) {
                    changeItemSelected();
                } else if (adapter.getCount() == 0) {
                    adapter.registerDataSetObserver(new DataSetObserver() {
                        @Override
                        public void onChanged() {
                            changeItemSelected();
                            adapter.unregisterDataSetObserver(this);
                        }
                    });
                }
            }
        }, 500);

        post(new Runnable() {
            @Override
            public void run() {
                TvGridView.super.setOnItemSelectedListener(TvGridView.this);
                TvGridView.super.setOnItemClickListener(TvGridView.this);
            }
        });
    }

    /**
     * 修复adapter第一个item无法响应选择监听的问题
     */
    private void changeItemSelected() {
        if (this.hasFocus() && hasChangeItemListener) {

            hasChangeItemListener = false;

            onItemSelected(this, getSelectedView(), getSelectedItemPosition(), 0);
        }
    }

    @Override
    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    @Override
    public void setOnItemSelectedListener(OnItemSelectedListener listener) {
        mOnItemSelectedListener = listener;
    }

    @Override
    public boolean isInEditMode() {
        return true;
    }

    @Override
    public boolean isInTouchMode() {
        // 解决4.4以上版本抢焦点的问题
        if (Build.VERSION.SDK_INT == 19) {
            return !(hasFocus() && !super.isInTouchMode());
        } else {
            return super.isInTouchMode();
        }
    }

    @Override
    protected int getChildDrawingOrder(int childCount, int i) {
        if (!isShowAnim)
            return super.getChildDrawingOrder(childCount, i);

        mTempPosition = getSelectedItemPosition() - getFirstVisiblePosition();
        if (mTempPosition < 0) {
            return i;
        } else {
            if (i == childCount - 1) {//这是最后一个需要刷新的item
                if (mTempPosition > i) {
                    mTempPosition = i;
                }
                return mTempPosition;
            }
            if (i == mTempPosition) {//这是原本要在最后一个刷新的item
                return childCount - 1;
            }
        }
        return i;
    }

    public boolean isShowAnim() {
        return isShowAnim;
    }

    public void setShowAnim(boolean showAnim) {
        isShowAnim = showAnim;
    }

    public void setOnItemListener(OnItemListener listener) {
        if (null != listener) {
            this.mItemListener = listener;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        if (null != mItemListener) {
            mItemListener.onItemClick(parent, view, position);
        }
        if (null != mOnItemClickListener) {
            mOnItemClickListener.onItemClick(parent, view, position, id);
        }
    }

    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);

        if (gainFocus && getSelectedItemPosition() == -1) {
            hasChangeItemListener = true;
            return;
        }

        hasChangeItemListener = false;

        if (gainFocus && (mPreView == null || getSelectedItemPosition() == getPositionForView(mPreView))) {
            onItemSelected(this, getSelectedView(), getSelectedItemPosition(), 0);
        } else if (isShowAnim && !gainFocus && mPreView != null) {
            mPreView.animate().scaleX(1.0f).scaleY(1.0f).setDuration(130).start();
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (!parent.hasFocus()) return;

        if (null != mPreView) {
            if (isShowAnim)
                mPreView.animate().scaleX(1.0f).scaleY(1.0f).setDuration(130).start();

            mPreView = null;
        }
        if (null != view) {

            if (isShowAnim)
                view.animate().scaleX(1.10f).scaleY(1.10f).setDuration(130).start();

            if (null != mItemListener) {
                mItemListener.onItemSelected(parent, view, position);
            }
            mPreView = view;
        }
        if (null != mOnItemSelectedListener) {
            mOnItemSelectedListener.onItemSelected(parent, view, position, id);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        if (null != mPreView) {
            if (isShowAnim)
                mPreView.animate().scaleX(1.0f).scaleY(1.0f).setDuration(130).start();

            mPreView = null;
        }

        if (null != mOnItemSelectedListener) {
            mOnItemSelectedListener.onNothingSelected(parent);
        }
    }

}
