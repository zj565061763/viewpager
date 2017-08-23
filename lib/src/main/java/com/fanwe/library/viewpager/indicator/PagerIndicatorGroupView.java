package com.fanwe.library.viewpager.indicator;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.fanwe.library.viewpager.SDViewPagerInfoListener;

/**
 * Created by Administrator on 2017/8/23.
 */

public abstract class PagerIndicatorGroupView extends FrameLayout implements IPagerIndicatorGroupView
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

    private IPagerIndicatorAdapter mAdapter;

    private SDViewPagerInfoListener mViewPagerInfoListener = new SDViewPagerInfoListener();

    private void init()
    {
        mViewPagerInfoListener.addOnPageCountChangeCallback(this);
        mViewPagerInfoListener.addOnScrolledPercentChangeCallback(this);
        mViewPagerInfoListener.addOnSelectedChangeCallback(this);
    }

    public void setViewPager(ViewPager viewPager)
    {
        mViewPagerInfoListener.listen(viewPager);
    }

    public ViewPager getViewPager()
    {
        return mViewPagerInfoListener.getViewPager();
    }

    public void setAdapter(IPagerIndicatorAdapter adapter)
    {
        mAdapter = adapter;
    }

    public IPagerIndicatorAdapter getAdapter()
    {
        return mAdapter;
    }

    @Override
    public void onEnter(int position, float enterPercent, boolean leftToRight)
    {
        IPagerIndicatorItemView itemView = getItemView(position);
        if (itemView != null)
        {
            itemView.onEnter(enterPercent, leftToRight);
        }
    }

    @Override
    public void onLeave(int position, float leavePercent, boolean leftToRight)
    {
        IPagerIndicatorItemView itemView = getItemView(position);
        if (itemView != null)
        {
            itemView.onLeave(leavePercent, leftToRight);
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
}
