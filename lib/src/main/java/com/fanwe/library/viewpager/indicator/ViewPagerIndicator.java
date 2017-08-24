package com.fanwe.library.viewpager.indicator;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.fanwe.library.viewpager.R;
import com.fanwe.library.viewpager.helper.SDViewPagerInfoListener;
import com.fanwe.library.viewpager.indicator.impl.PagerIndicatorGroupView;
import com.fanwe.library.viewpager.indicator.view.TrackHorizontalScrollView;

/**
 * Created by Administrator on 2017/8/24.
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

    private TrackHorizontalScrollView mHorizontalScrollView;

    private PagerIndicatorGroupView mPagerIndicatorGroupView;
    private ViewGroup mPagerIndicatorTrackContainer;

    private SDViewPagerInfoListener mViewPagerInfoListener = new SDViewPagerInfoListener();

    private void init()
    {
        LayoutInflater.from(getContext()).inflate(R.layout.lib_vpg_pager_indicator, this, true);
        mHorizontalScrollView = (TrackHorizontalScrollView) findViewById(R.id.view_scroll);
        mPagerIndicatorGroupView = (PagerIndicatorGroupView) findViewById(R.id.view_indicator_group);
        mPagerIndicatorTrackContainer = (ViewGroup) findViewById(R.id.view_indicator_track_container);

        mViewPagerInfoListener.setOnPageSelectedChangeCallback(mInternalOnPageSelectedChangeCallback);
    }

    public void setDebug(boolean debug)
    {
        mPagerIndicatorGroupView.setDebug(debug);
    }

    /**
     * 设置ViewPager
     *
     * @param viewPager
     */
    public void setViewPager(ViewPager viewPager)
    {
        mPagerIndicatorGroupView.setViewPager(viewPager);
        mViewPagerInfoListener.setViewPager(viewPager);
    }

    /**
     * 设置适配器
     *
     * @param adapter
     */
    public void setAdapter(IPagerIndicatorAdapter adapter)
    {
        mPagerIndicatorGroupView.setAdapter(adapter);
    }

    /**
     * 设置可追踪指示器Item的view
     *
     * @param pagerIndicatorTrackView
     */
    public void setPagerIndicatorTrackView(IPagerIndicatorTrackView pagerIndicatorTrackView)
    {
        final IPagerIndicatorTrackView oldView = mPagerIndicatorGroupView.getPagerIndicatorTrackView();
        if (oldView != pagerIndicatorTrackView)
        {
            if (oldView != null)
            {
                mPagerIndicatorTrackContainer.removeAllViews();
            }
            mPagerIndicatorGroupView.setPagerIndicatorTrackView(pagerIndicatorTrackView);
            if (pagerIndicatorTrackView != null)
            {
                if (pagerIndicatorTrackView instanceof View)
                {
                    mPagerIndicatorTrackContainer.addView((View) pagerIndicatorTrackView);
                } else
                {
                    throw new IllegalArgumentException("pagerIndicatorView must be instance of view");
                }
            }
        }
    }

    private SDViewPagerInfoListener.OnPageSelectedChangeCallback mInternalOnPageSelectedChangeCallback = new SDViewPagerInfoListener.OnPageSelectedChangeCallback()
    {
        @Override
        public void onSelectedChanged(int position, boolean selected)
        {
            if (selected)
            {
                IPagerIndicatorItemView itemView = mPagerIndicatorGroupView.getItemView(position);
                if (itemView != null)
                {
                    mHorizontalScrollView.smoothScrollTo((View) itemView);
                }
            }
        }
    };

}
