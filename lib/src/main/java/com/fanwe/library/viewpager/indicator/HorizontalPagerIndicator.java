package com.fanwe.library.viewpager.indicator;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;

import com.fanwe.library.viewpager.SDViewPagerInfoListener;

/**
 * Created by Administrator on 2017/8/23.
 */

public class HorizontalPagerIndicator extends FrameLayout
{
    public HorizontalPagerIndicator(Context context)
    {
        super(context);
        init();
    }

    public HorizontalPagerIndicator(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public HorizontalPagerIndicator(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    private HorizontalScrollView mHorizontalScrollView;

    private SDViewPagerInfoListener mViewPagerInfoListener = new SDViewPagerInfoListener();

    private void init()
    {
        addViewInternal();
        mViewPagerInfoListener.addOnPageCountChangeCallback(new SDViewPagerInfoListener.OnPageCountChangeCallback()
        {
            @Override
            public void onPageCountChanged(int oldCount, int newCount)
            {

            }
        });
    }

    private void addViewInternal()
    {
        mHorizontalScrollView = new HorizontalScrollView(getContext());
        mHorizontalScrollView.setHorizontalScrollBarEnabled(false);
        addView(mHorizontalScrollView);
    }

    public void setViewPager(ViewPager viewPager)
    {
        mViewPagerInfoListener.listen(viewPager);
    }
}
