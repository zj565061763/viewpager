package com.fanwe.library.viewpager.indicator.impl;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.fanwe.library.viewpager.helper.SDViewPagerInfoListener;
import com.fanwe.library.viewpager.indicator.IPagerIndicatorAdapter;
import com.fanwe.library.viewpager.indicator.IPagerIndicatorGroupView;
import com.fanwe.library.viewpager.indicator.IPagerIndicatorItemView;
import com.fanwe.library.viewpager.indicator.IPagerIndicatorTrackView;

/**
 * ViewPager指示器GroupView
 */
public class PagerIndicatorGroupView extends LinearLayout implements IPagerIndicatorGroupView
{
    public PagerIndicatorGroupView(Context context)
    {
        super(context);
        init();
    }

    public PagerIndicatorGroupView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public PagerIndicatorGroupView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    private static final String TAG = "LinearPagerIndicatorGroupView";

    private IPagerIndicatorAdapter mAdapter;
    private IPagerIndicatorTrackView mPagerIndicatorTrackView;

    private SDViewPagerInfoListener mViewPagerInfoListener = new SDViewPagerInfoListener();

    private boolean mIsDebug;

    private void init()
    {
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);

        setAdapter(mInternalPagerIndicatorAdapter);
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
                PagerIndicatorGroupView.this.onPageCountChanged(count);
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
                PagerIndicatorGroupView.this.onSelectedChanged(position, selected);
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
                PagerIndicatorGroupView.this.onShowPercent(position, showPercent, isEnter, isMoveLeft);
            }
        });
    }

    @Override
    public void setViewPager(ViewPager viewPager)
    {
        mViewPagerInfoListener.setViewPager(viewPager);
    }

    @Override
    public void setAdapter(IPagerIndicatorAdapter adapter)
    {
        mAdapter = adapter;
    }

    @Override
    public IPagerIndicatorAdapter getAdapter()
    {
        return mAdapter;
    }

    @Override
    public void setPagerIndicatorTrackView(IPagerIndicatorTrackView pagerIndicatorTrackView)
    {
        mPagerIndicatorTrackView = pagerIndicatorTrackView;
    }

    @Override
    public IPagerIndicatorTrackView getPagerIndicatorTrackView()
    {
        return mPagerIndicatorTrackView;
    }

    private IPagerIndicatorAdapter mInternalPagerIndicatorAdapter = new IPagerIndicatorAdapter()
    {
        @Override
        public IPagerIndicatorItemView onCreateView(int position, ViewGroup viewParent)
        {
            return new ImagePagerIndicatorItemView(getContext());
        }
    };

    @Override
    public IPagerIndicatorItemView getItemView(int position)
    {
        if (position < 0)
        {
            return null;
        }
        final int childCount = getChildCount();
        if (position >= childCount)
        {
            return null;
        }

        return (IPagerIndicatorItemView) getChildAt(position);
    }

    @Override
    public void onPageCountChanged(int count)
    {
        onCreateOrRemoveItemView(count);
        if (getPagerIndicatorTrackView() != null)
        {
            getPagerIndicatorTrackView().onPageCountChanged(count);
        }
    }

    @Override
    public void onShowPercent(int position, float showPercent, boolean isEnter, boolean isMoveLeft)
    {
        IPagerIndicatorItemView itemView = getItemView(position);
        if (itemView != null)
        {
            itemView.onShowPercent(showPercent, isEnter, isMoveLeft);

            if (getPagerIndicatorTrackView() != null)
            {
                getPagerIndicatorTrackView().onShowPercent(position, showPercent, isEnter, isMoveLeft, itemView.getPositionData());
            }
        }
    }

    @Override
    public void onSelectedChanged(int position, boolean selected)
    {
        IPagerIndicatorItemView itemView = getItemView(position);
        if (itemView != null)
        {
            itemView.onSelectedChanged(selected);
        }
    }

    /**
     * 根据当前count来决定增加或者移除view
     *
     * @param count
     */
    protected void onCreateOrRemoveItemView(int count)
    {
        final int childCount = getChildCount();
        if (count > childCount)
        {
            final IPagerIndicatorAdapter adapter = getAdapter();
            if (adapter != null)
            {
                final int createCount = count - childCount;
                for (int i = 0; i < createCount; i++)
                {
                    IPagerIndicatorItemView itemView = adapter.onCreateView(childCount + i, this);
                    if (!(itemView instanceof View))
                    {
                        throw new IllegalArgumentException("onCreateView() must return instance of view");
                    }
                    final View view = (View) itemView;
                    addView(view);
                }
            }
        } else if (count < childCount)
        {
            for (int i = childCount - 1; i >= count; i--)
            {
                removeViewAt(i);
            }
        }
    }
}
