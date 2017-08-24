package com.fanwe.library.viewpager.indicator;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;

import com.fanwe.library.viewpager.R;
import com.fanwe.library.viewpager.helper.SDViewPagerInfoListener;
import com.fanwe.library.viewpager.indicator.impl.LinearPagerIndicatorGroupView;

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

    private static final String TAG = "ViewPagerIndicator";

    private HorizontalScrollView mHorizontalScrollView;
    private LinearPagerIndicatorGroupView mPagerIndicatorGroupView;
    private ViewGroup mPagerIndicatorTrackContainer;

    private IPagerIndicatorTrackView mPagerIndicatorTrackView;

    private SDViewPagerInfoListener mViewPagerInfoListener = new SDViewPagerInfoListener();

    private boolean mIsDebug;

    private void init()
    {
        LayoutInflater.from(getContext()).inflate(R.layout.lib_vpg_horizontal_pager_indicator, this, true);
        mHorizontalScrollView = (HorizontalScrollView) findViewById(R.id.view_scroll);
        mPagerIndicatorGroupView = (LinearPagerIndicatorGroupView) findViewById(R.id.view_group);
        mPagerIndicatorTrackContainer = (ViewGroup) findViewById(R.id.fl_container_pager_indicator_track);

        initViewPagerInfoListener();
    }

    public void setDebug(boolean debug)
    {
        mIsDebug = debug;
    }

    private void initViewPagerInfoListener()
    {
        mViewPagerInfoListener.setOnPageCountChangeCallback(new SDViewPagerInfoListener.OnPageCountChangeCallback()
        {
            @Override
            public void onPageCountChanged(int count)
            {
                if (mIsDebug)
                {
                    Log.i(TAG, "onPageCountChanged:" + count);
                }
                getPagerIndicatorGroupView().onPageCountChanged(count);

                if (getPagerIndicatorTrackView() != null)
                {
                    getPagerIndicatorTrackView().onPageCountChanged(count);
                }
            }
        });
        mViewPagerInfoListener.setOnPageSelectedChangeCallback(new SDViewPagerInfoListener.OnPageSelectedChangeCallback()
        {
            @Override
            public void onSelectedChanged(int position, boolean selected)
            {
                if (mIsDebug)
                {
                    Log.i(TAG, "onSelectedChanged:" + position + "," + selected);
                }
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
            public void onShowPercent(int position, float showPercent, boolean isEnter, boolean isMoveLeft)
            {
                if (mIsDebug)
                {
                    if (isEnter)
                    {
                        Log.i(TAG, "Enter  " + position + "  " + showPercent + "  " + isMoveLeft);
                    } else
                    {
                        Log.e(TAG, "Leave  " + position + "  " + showPercent + "  " + isMoveLeft);
                    }
                }
                getPagerIndicatorGroupView().onShowPercent(position, showPercent, isEnter, isMoveLeft);

                if (getPagerIndicatorTrackView() != null)
                {
                    IPagerIndicatorItemView itemView = getPagerIndicatorGroupView().getItemView(position);
                    if (itemView != null)
                    {
                        getPagerIndicatorTrackView().onShowPercent(position, showPercent, isEnter, isMoveLeft, itemView.getPositionData());
                    }
                }
            }
        });
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
     * 返回ViewPager指示器GroupView
     *
     * @return
     */
    private LinearPagerIndicatorGroupView getPagerIndicatorGroupView()
    {
        return mPagerIndicatorGroupView;
    }

    /**
     * 返回ViewPager指示器，可跟随指示器Item的view
     *
     * @return
     */
    public IPagerIndicatorTrackView getPagerIndicatorTrackView()
    {
        return mPagerIndicatorTrackView;
    }

    /**
     * 设置ViewPager指示器，可跟随指示器Item的view
     *
     * @param pagerIndicatorTrackView
     */
    public void setPagerIndicatorTrackView(IPagerIndicatorTrackView pagerIndicatorTrackView)
    {
        if (mPagerIndicatorTrackView != pagerIndicatorTrackView)
        {
            if (mPagerIndicatorTrackView != null)
            {
                mPagerIndicatorTrackContainer.removeAllViews();
            }
            mPagerIndicatorTrackView = pagerIndicatorTrackView;
            if (pagerIndicatorTrackView != null)
            {
                if (mPagerIndicatorTrackView instanceof View)
                {
                    mPagerIndicatorTrackContainer.addView((View) pagerIndicatorTrackView);
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
