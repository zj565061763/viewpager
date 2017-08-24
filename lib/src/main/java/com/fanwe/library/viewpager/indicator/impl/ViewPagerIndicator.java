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
import com.fanwe.library.viewpager.helper.SDViewPagerInfoListener;
import com.fanwe.library.viewpager.indicator.IPagerIndicatorAdapter;
import com.fanwe.library.viewpager.indicator.IPagerIndicatorItemView;
import com.fanwe.library.viewpager.indicator.IPagerIndicatorView;

/**
 * ViewPager指示器
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
    private LinearPagerIndicatorGroupView mGroupView;
    private ViewGroup mIndicatorContainer;

    private IPagerIndicatorView mPagerIndicatorView;

    private SDViewPagerInfoListener mViewPagerInfoListener = new SDViewPagerInfoListener();

    private void init()
    {
        LayoutInflater.from(getContext()).inflate(R.layout.lib_vpg_horizontal_pager_indicator, this, true);
        mHorizontalScrollView = (HorizontalScrollView) findViewById(R.id.view_scroll);
        mGroupView = (LinearPagerIndicatorGroupView) findViewById(R.id.view_group);
        mIndicatorContainer = (ViewGroup) findViewById(R.id.view_indicator_container);

        initViewPagerInfoListener();
    }

    private void initViewPagerInfoListener()
    {
        mViewPagerInfoListener.setOnPageCountChangeCallback(new SDViewPagerInfoListener.OnPageCountChangeCallback()
        {
            @Override
            public void onPageCountChanged(int count)
            {
                getPagerIndicatorGroupView().onPageCountChanged(count);

                if (getPagerIndicatorView() != null)
                {
                    getPagerIndicatorView().onPageCountChanged(count);
                }
            }
        });
        mViewPagerInfoListener.setOnPageSelectedChangeCallback(new SDViewPagerInfoListener.OnPageSelectedChangeCallback()
        {
            @Override
            public void onSelectedChanged(int position, boolean selected)
            {
                getPagerIndicatorGroupView().onSelectedChanged(position, selected);
                if (selected)
                {
                    IPagerIndicatorItemView itemView = getPagerIndicatorGroupView().getItemView(position);
                    if (itemView != null)
                    {
                        postScrollRunnable((View) itemView);
                    }
                }
            }
        });
        mViewPagerInfoListener.setOnPageScrolledPercentChangeCallback(new SDViewPagerInfoListener.OnPageScrolledPercentChangeCallback()
        {
            @Override
            public void onEnter(int position, float enterPercent, boolean leftToRight)
            {
                getPagerIndicatorGroupView().onEnter(position, enterPercent, leftToRight);

                if (getPagerIndicatorView() != null)
                {
                    IPagerIndicatorItemView itemView = getPagerIndicatorGroupView().getItemView(position);
                    if (itemView != null)
                    {
                        getPagerIndicatorView().onEnter(position, enterPercent, leftToRight, itemView.getPositionData());
                    }
                }
            }

            @Override
            public void onLeave(int position, float leavePercent, boolean leftToRight)
            {
                getPagerIndicatorGroupView().onLeave(position, leavePercent, leftToRight);

                if (getPagerIndicatorView() != null)
                {
                    IPagerIndicatorItemView itemView = getPagerIndicatorGroupView().getItemView(position);
                    if (itemView != null)
                    {
                        getPagerIndicatorView().onLeave(position, leavePercent, leftToRight, itemView.getPositionData());
                    }
                }
            }
        });
    }

    /**
     * 返回ViewPager指示器GroupView
     *
     * @return
     */
    private LinearPagerIndicatorGroupView getPagerIndicatorGroupView()
    {
        return mGroupView;
    }

    /**
     * 返回ViewPager指示器，可跟随指示器Item的view
     *
     * @return
     */
    public IPagerIndicatorView getPagerIndicatorView()
    {
        return mPagerIndicatorView;
    }

    /**
     * 设置ViewPager
     *
     * @param viewPager
     */
    public void setViewPager(ViewPager viewPager)
    {
        mViewPagerInfoListener.listen(viewPager);
    }

    /**
     * 设置适配器
     *
     * @param adapter
     */
    public void setAdapter(IPagerIndicatorAdapter adapter)
    {
        getPagerIndicatorGroupView().setAdapter(adapter);
    }

    /**
     * 设置ViewPager指示器，可跟随指示器Item的view
     *
     * @param pagerIndicatorView
     */
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
                    mIndicatorContainer.addView((View) pagerIndicatorView);
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
