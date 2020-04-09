package com.sd.www.viewpager.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import com.sd.lib.viewpager.helper.ext.FPagerChildListener;

public class SimpleTabView0 extends SimpleTabView
{
    public SimpleTabView0(Context context)
    {
        super(context);
    }

    public SimpleTabView0(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public SimpleTabView0(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    public static final String TAG = SimpleTabView0.class.getSimpleName();

    @Override
    protected void init()
    {
        super.init();
        getTv_content().setText("0");
    }

    private final FPagerChildListener mPagerChildListener = new FPagerChildListener(this)
    {
        @Override
        protected void onPageSelectChanged(boolean selected)
        {
            Log.i(TAG, "onPageSelectChanged selected:" + selected);
        }
    };

    @Override
    protected void onAttachedToWindow()
    {
        super.onAttachedToWindow();
        mPagerChildListener.start();
    }

    @Override
    protected void onDetachedFromWindow()
    {
        super.onDetachedFromWindow();
        mPagerChildListener.stop();
    }
}
