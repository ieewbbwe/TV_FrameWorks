package com.tv.boost.adapter;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Spanned;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tv.boost.R;


/**
 * Created by owen on 15/8/26.
 * 通用ViewHolder类
 * 最好与CommonBaseAdapter配套使用
 */
public class CommonViewHolder {
    private final SparseArray<View> mViews;
    private int mPosition;
    private View mConvertView;
    private Dialog mDialog;
    private Context mContext;

    private CommonViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
        this.mContext = context;
        this.mPosition = position;
        this.mViews = new SparseArray<View>();
        if(layoutId > 0) {
            this.mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
            // setTag
            this.mConvertView.setTag(R.id.tag_view_hold, this);
        }
    }
    
    private CommonViewHolder(Context context, View convertView) {
        this(context, null, 0, 0);
        this.mConvertView = convertView;
        this.mConvertView.setTag(R.id.tag_view_hold, this);
    }
    
    private CommonViewHolder(Context context, Dialog dialog) {
        this(context, null, 0, 0);
        this.mDialog = dialog;
    }

    /**
     * 拿到一个ViewHolder对象
     *
     * @param context
     * @param convertView
     * @param parent
     * @param layoutId
     * @param position
     * @return
     */
    public static CommonViewHolder get(Context context, View convertView,
                                       ViewGroup parent, int layoutId, int position) {
        if (convertView == null || null == convertView.getTag(R.id.tag_view_hold)) {
            return new CommonViewHolder(context, parent, layoutId, position);
        }
        return ((CommonViewHolder) convertView.getTag(R.id.tag_view_hold)).setPosition(position);
    }

    public static CommonViewHolder get(Context context, ViewGroup parent, int layoutId) {
        return get(context, null, parent, layoutId, 0);
    }

    public static CommonViewHolder get(Context context, int layoutId) {
        return get(context, null, null, layoutId, 0);
    }

    public static CommonViewHolder get(Context context, View convertView) {
        return new CommonViewHolder(context, convertView);
    }
    
    public static CommonViewHolder get(Context context, Dialog dialog) {
        return new CommonViewHolder(context, dialog);
    }
    
    
    public View getConvertView()
    {
        return mConvertView;
    }

    public int getPosition()
    {
        return mPosition;
    }
    
    public CommonViewHolder setPosition(int position){
        this.mPosition = position;
        return this;
    }
    
    /**
     * 通过控件的Id获取对于的控件，如果没有则加入views
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null)
        {
            if(null != mConvertView)
                view = mConvertView.findViewById(viewId);
            else if(null != mDialog)
                view = mDialog.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 为TextView设置字符串
     *
     * @param viewId
     * @param text
     * @return
     */
    public CommonViewHolder setText(int viewId, String text)
    {
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }


    public CommonViewHolder setText(int viewId, Spanned spanned) {
        TextView view = getView(viewId);
        view.setText(spanned);
        return this;
    }

    /**
     * 为TextView设置字符串
     *
     * @param viewId
     * @param stringId
     * @param formatArgs
     * @return
     */
    public CommonViewHolder setText(int viewId, int stringId, Object... formatArgs)
    {
        TextView view = getView(viewId);
        view.setText(this.mContext.getString(stringId, formatArgs));
        return this;
    }

    /**
     * 为TextView设置字符串
     *
     * @param viewId
     * @param stringId
     * @return
     */
    public CommonViewHolder setText(int viewId, int stringId)
    {
        TextView view = getView(viewId);
        view.setText(stringId);
        return this;
    }
    /**
     * 为TextView设置字符串
     *
     * @param textViewId
     * @param singleLine
     * @return
     */
    public CommonViewHolder setSingleLine(int textViewId, boolean singleLine)
    {
        TextView view = getView(textViewId);
        view.setSingleLine(singleLine);
        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param drawableId
     * @return
     */
    public CommonViewHolder setImageResource(int viewId, int drawableId)
    {
        ImageView view = getView(viewId);
        view.setImageResource(drawableId);

        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param bm
     * @return
     */
    public CommonViewHolder setImageBitmap(int viewId, Bitmap bm)
    {
        ImageView view = getView(viewId);
        view.setImageBitmap(bm);
        return this;
    }
    
    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param bm
     * @return
     */
    public CommonViewHolder setImageDrawable(int viewId, Drawable bm)
    {
        ImageView view = getView(viewId);
        view.setImageDrawable(bm);
        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param drawableId
     * @return
     */
    public CommonViewHolder setBackgroundResource(int viewId, int drawableId)
    {
        View view = getView(viewId);
        view.setBackgroundResource(drawableId);
        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param drawable
     * @return
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public CommonViewHolder setBackground(int viewId, Drawable drawable)
    {
        View view = getView(viewId);
        view.setBackground(drawable);
        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param bm
     * @return
     */
    public CommonViewHolder setBackground(int viewId, Bitmap bm)
    {
        return this.setBackground(viewId, new BitmapDrawable(this.mContext.getResources(), bm));
    }

    /**
     * 设置View的Visibility
     * @param viewId
     * @param visibility
     * @return
     */
    public CommonViewHolder setVisibility(int viewId, int visibility){
        View view = getView(viewId);
        view.setVisibility(visibility);
        return this;
    }

    /**
     * 设置焦点监听
     * @param viewId
     * @param listener
     * @return
     */
    public CommonViewHolder setOnFocusChangeListener(int viewId, View.OnFocusChangeListener listener) {
        View view = getView(viewId);
        view.setOnFocusChangeListener(listener);
        return this;
    }

    /**
     * 设置点击监听
     * @param viewId
     * @param listener
     * @return
     */
    public CommonViewHolder setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }


}
