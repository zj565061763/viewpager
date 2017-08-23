package com.fanwe.library.viewpager.indicator.impl;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;

import com.fanwe.library.viewpager.R;
import com.fanwe.library.viewpager.SDViewPagerInfoListener;
import com.fanwe.library.viewpager.indicator.IPagerIndicatorAdapter;
import com.fanwe.library.viewpager.indicator.IPagerIndicatorItemView;
import com.fanwe.library.viewpager.indicator.IPagerIndicatorView;

/**
 * Created by Administrator on 2017/8/23.
 */

public class ViewPagerIndicator extends FrameLayout

{
    public ViewPagerIndicator(Context context)
    {
        super(context);
        init();
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    private HorizontalScrollView mHorizontalScrollView;
    private HorizontalPagerIndicatorGroupView mGroupView;
    private ViewGroup mIndicatorContainer;

    private IPagerIndicatorView mPagerIndicatorView;

    private SDViewPagerInfoListener mViewPagerInfoListener = new SDViewPagerInfoListener();

    private void init()
    {
        LayoutInflater.from(getContext()).inflate(R.layout.lib_vpg_horizontal_pager_indicator, this, true);
        mHorizontalScrollView = (HorizontalScrollView) findViewById(R.id.view_scroll);
        mGroupView = (HorizontalPagerIndicatorGroupView) findViewById(R.id.view_group);
        mIndicatorContainer = (ViewGroup) findViewById(R.id.view_indicator_container);

        initViewPagerInfoListener();
    }

    private void initViewPagerInfoListener()
    {
        mViewPagerInfoListener.addOnPageCountChangeCallback(new SDViewPagerInfoListener.OnPageCountChangeCallback()
        {
            @Override
            public void onPageCountChanged(int count)
            {
                mGroupView.onPageCountChanged(count);
            }
        });
        mViewPagerInfoListener.addOnSelectedChangeCallback(new SDViewPagerInfoListener.OnSelectedChangeCallback()
        {
            @Override
            public void onSelectedChanged(int position, boolean selected)
            {
                mGroupView.onSelectedChanged(position, selected);
                if (selected)
                {
                    IPagerIndicatorItemView itemView = mGroupView.getItemView(position);
                    postScrollRunnable((View) itemView);
                }
            }
        });
        mViewPagerInfoListener.addOnScrolledPercentChangeCallback(new SDViewPagerInfoListener.OnScrolledPercentChangeCallback()
        {
            @Override
            public void onEnter(int position, float enterPercent, boolean leftToRight)
            {
                mGroupView.onEnter(position, enterPercent, leftToRight);
            }

            @Override
            public void onLeave(int position, float leavePercent, boolean leftToRight)
            {
                mGroupView.onLeave(position, leavePercent, leftToRight);
            }
        });
    }

    public void setViewPager(ViewPager viewPager)
    {
        mViewPagerInfoListener.listen(viewPager);
    }

    public void setAdapter(IPagerIndicatorAdapter adapter)
    {
        mGroupView.setAdapter(adapter);
    }

    public void setPagerIndicatorView(IPagerIndicatorView pagerIndicatorView)
    {
        if (mPagerIndicatorView != pagerIndicatorView)
        {
            if (mPagerIndicatorView != null)
            {
                mIndicatorContainer.removeAllViews();
            }
            mPagerIndicatorView = pagerIndicatorView;
            if (pagerIndicatorView != null)
            {
                if (mPagerIndicatorView instanceof View)
                {
                    mIndicatorContainer.addView((View) mPagerIndicatorView);
                } else
                {
                    throw new IllegalArgumentException("pagerIndicatorView must be instance of view");
                }
            }
        }
    }

    private Runnable mScrollRunnable;

    private void postScrollRunnable(final View child)
    {
        removeScrollRunnable();
        mScrollRunnable = new Runnable()
        {
            public void run()
            {
                int scrollX = child.getLeft() - (getWidth() - child.getWidth()) / 2;
                mHorizontalScrollView.smoothScrollTo(scrollX, 0);
                mScrollRunnable = null;
            }
        };
        postScrollRunnable();
    }

    private void removeScrollRunnable()
    {
        if (mScrollRunnable != null)
        {
            removeCallbacks(mScrollRunnable);
            mScrollRunnable = null;
        }
    }

    private void postScrollRunnable()
    {
        if (mScrollRunnable != null)
        {
            post(mScrollRunnable);
        }
    }

    @Override
    public void onAttachedToWindow()
    {
        super.onAttachedToWindow();
        postScrollRunnable();
    }

    @Override
    public void onDetachedFromWindow()
    {
        super.onDetachedFromWindow();
        removeScrollRunnable();
    }

}
