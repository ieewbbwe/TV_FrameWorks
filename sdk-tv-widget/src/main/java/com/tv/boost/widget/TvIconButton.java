package com.tv.boost.widget;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.tv.boost.R;


/**
 * Created by owen on 16/9/27.
 */
public class TvIconButton extends FrameLayout {
    private TextView mTextView;
    private ImageView mImageView;

    private ValueAnimator mAnimator1;
    private ValueAnimator mAnimator2;
    private int mTextWidth = 0;
    private int mWidth;
    private int mMoveOffset;
    
    public TvIconButton(Context context) {
        this(context, null);
    }

    public TvIconButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TvIconButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mTextView = new TextView(context);
        mTextView.setSingleLine(true);
        mImageView = new ImageView(context);
        mImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);

        LayoutParams params1 = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        LayoutParams params2 = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params1.gravity = Gravity.CENTER_VERTICAL| Gravity.RIGHT;
        params2.gravity = Gravity.CENTER_VERTICAL| Gravity.LEFT;

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.IconButton, defStyleAttr, 0);
        if(a != null) {
            mTextView.setText(a.getText(R.styleable.IconButton_tv_text));
            ColorStateList colorStateList = a.getColorStateList(R.styleable.IconButton_tv_textColor);
            if(null != colorStateList) {
                mTextView.setTextColor(colorStateList);
            }
            mTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, a.getDimensionPixelOffset(R.styleable.IconButton_tv_textSize, 20));
            final int padding = a.getDimensionPixelOffset(R.styleable.IconButton_tv_textPadding, 0);
            mTextView.setPadding(padding, padding, padding, padding);
            int textBg = a.getResourceId(R.styleable.IconButton_tv_textBg, 0);
            if(textBg != 0) {
                mTextView.setBackgroundResource(textBg);
            }
            mMoveOffset = a.getDimensionPixelOffset(R.styleable.IconButton_tv_iconMoveOffset, 0);
            mImageView.setImageResource(a.getResourceId(R.styleable.IconButton_tv_icon, 0));
            params2.width = a.getDimensionPixelOffset(R.styleable.IconButton_tv_iconWidth, LayoutParams.WRAP_CONTENT);
            params2.height = a.getDimensionPixelOffset(R.styleable.IconButton_tv_iconHeight, LayoutParams.WRAP_CONTENT);
            a.recycle();
        }

        addView(mTextView, params1);
        addView(mImageView, params2);

    }

    private void initAnima() {
        mMoveOffset = Math.max(mMoveOffset, mTextWidth);
        
        mAnimator1 = ObjectAnimator.ofInt(this, "width", mWidth, mWidth + mMoveOffset).setDuration(300);

        mAnimator2 = ObjectAnimator.ofInt(mWidth + mMoveOffset, mWidth).setDuration(300);
        mAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                final int width = (int) valueAnimator.getAnimatedValue();
                if(width < getWidth()) {
                    setWidth(width);
                }
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(0 == mWidth && getWidth() > 0) {
            mWidth = getWidth();
            mTextView.post(new Runnable() {
                @Override
                public void run() {
                    if(mTextWidth == 0) {
                        mTextWidth = mTextView.getWidth();
                        initAnima();
                    }
                }
            });
        }
    }

    @Override
    public boolean isInEditMode() {
        return true;
    }

    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
        if(null == mAnimator2 || null == mAnimator1)
            return;
        if(gainFocus) {
            if(mAnimator2.isRunning()) {
                mAnimator2.cancel();
            }
            mAnimator1.start();
        } else {
            if(mAnimator1.isRunning()) {
                mAnimator1.cancel();
            }
            mAnimator2.start();
        }
    }

    public void setWidth(int width) {
        ViewGroup.LayoutParams params = getLayoutParams();
        params.width = width;
        setLayoutParams(params);
    }

}
