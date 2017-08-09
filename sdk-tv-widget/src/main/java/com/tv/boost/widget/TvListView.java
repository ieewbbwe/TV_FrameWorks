package com.tv.boost.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by owen on 15/11/24.
 */
public class TvListView extends ListView implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {

    private OnItemListener mItemListener;
    private OnItemClickListener mOnItemClickListener;
    private OnItemSelectedListener mOnItemSelectedListener;

    /**
     * 是否需要手动设置onItemSelectedListener
     */
    private boolean hasChangeItemListener;

    /**
     * 上次选中的position
     */
    private int mOldSelectedPosition = INVALID_POSITION;

    public TvListView(Context context) {
        super(context);
    }

    public TvListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TvListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TvListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
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
                TvListView.super.setOnItemSelectedListener(TvListView.this);
                TvListView.super.setOnItemClickListener(TvListView.this);
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
        boolean result = super.isInTouchMode();
        // 解决4.4以上版本抢焦点的问题
        if (Build.VERSION.SDK_INT >= 19) {
            result = !(hasFocus() && !super.isInTouchMode());
        }
        return result;
    }

    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
        // 解决选中后无状态表达的问题，selector中使用activated代表选中后焦点移走
        if(null != getSelectedView()) {
            getSelectedView().setActivated(false);
        }
        if(!gainFocus) {
            if (null != getSelectedView()) {
                getSelectedView().setActivated(true);
            }
        }

        if (gainFocus && getSelectedItemPosition() == -1) {
            hasChangeItemListener = true;
            return;
        }
        hasChangeItemListener = false;
        
        if(gainFocus && (mOldSelectedPosition == INVALID_POSITION || mOldSelectedPosition == getSelectedItemPosition())) {
            onItemSelected(this, getSelectedView(), getSelectedItemPosition(), 0);
        }
    }

    public void setOnItemListener(OnItemListener listener){
        if(null != listener){
            this.mItemListener = listener;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(null != mItemListener){
            mItemListener.onItemClick(parent, view, position);
        }

        if(null != mOnItemClickListener) {
            mOnItemClickListener.onItemClick(parent, view, position, id);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mOldSelectedPosition = position;
        
        if(null != mItemListener){
            mItemListener.onItemSelected(parent, view, position);
        }

        if(null != mOnItemSelectedListener) {
            mOnItemSelectedListener.onItemSelected(parent, view, position, id);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
