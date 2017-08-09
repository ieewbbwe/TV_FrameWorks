package com.tv.boost.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by owen on 15/7/28.
 */
public abstract class CommonRecyclerViewAdapter<T> extends RecyclerView.Adapter<CommonRecyclerViewHolder>{
    public interface OnItemListener{
        void onItemSelected(View itemView, int position);
        void onItemClick(View itemView, int position);
    }
    
    private Context mContext;
    private LayoutInflater mInflater;
    private OnItemListener mOnItemListener;
    private List<T> mDatas = new ArrayList<>();
    private boolean isShowAnim = true;
    private boolean isBindListener = true;

    public CommonRecyclerViewAdapter(Context context){
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public CommonRecyclerViewAdapter(Context context, List<T> datas){
        this(context);
        setDatas(datas);
    }
    
    public void setDatas(List<T> datas){
        this.mDatas = datas;
    }
    
    public void appendDatas(List<T> datas) {
        int positionStart = getItemCount() - 1;
        int itemCount = datas.size() - 1;
        this.mDatas.addAll(datas);
        notifyItemRangeInserted(positionStart, itemCount);
    }
    
    public void removeItem(int postion) {
        if(null != mDatas && postion < mDatas.size()) {
            mDatas.remove(postion);
        }
    }

    public void setBindListener(boolean bindListener) {
        isBindListener = bindListener;
    }

    @Override
    public CommonRecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return CommonRecyclerViewHolder.get(this.mContext, viewGroup, getItemLayoutId(viewType));
    }

    @Override
    public void onBindViewHolder(final CommonRecyclerViewHolder holder, int position) {
        if(isBindListener) {
            onBindItemListener(holder);
        }
        onBindItemHolder(holder, getItem(position), position);
    }
    
    private void onBindItemListener(final CommonRecyclerViewHolder holder){
        // 设置item的选择与点击监听
        holder.itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                v.setSelected(hasFocus);
                if (hasFocus) {
                    if (isShowAnim) {
                        v.animate().scaleX(1.10f).scaleY(1.10f).setDuration(130).start();
                    }

                    int pos = holder.getLayoutPosition();
                    if (null != holder.itemView.getParent() && holder.itemView.getParent() instanceof RecyclerView) {
                        ((RecyclerView) holder.itemView.getParent()).smoothScrollToPosition(pos);
                    }
                    if (null != mOnItemListener) {
                        mOnItemListener.onItemSelected(holder.itemView, pos);
                    }
                } else {
                    if (isShowAnim) {
                        v.animate().scaleX(1.0f).scaleY(1.0f).setDuration(130).start();
                    }
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mOnItemListener) {
                    int pos = holder.getLayoutPosition();
                    try {
                        mOnItemListener.onItemClick(holder.itemView, pos);
                    } catch (UndeclaredThrowableException E) {
                        E.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return null != this.mDatas ? this.mDatas.size() : 0;
    }

    public T getItem(int position) {
        return (null != mDatas && position < mDatas.size()) ? mDatas.get(position) : null;
    }

    public void setOnItemListener(OnItemListener listener) {
            this.mOnItemListener = listener;
    }

    public boolean isShowAnim() {
        return isShowAnim;
    }

    public void setShowAnim(boolean showAnim) {
        isShowAnim = showAnim;
    }

    public abstract int getItemLayoutId(int viewType);

    public abstract void onBindItemHolder(final CommonRecyclerViewHolder helper, T item, int position);
}
