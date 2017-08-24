package com.fanwe.library.viewpager.indicator.impl;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.fanwe.library.viewpager.indicator.IPagerIndicatorAdapter;
import com.fanwe.library.viewpager.indicator.IPagerIndicatorGroupView;
import com.fanwe.library.viewpager.indicator.IPagerIndicatorItemView;
import com.fanwe.library.viewpager.indicator.IPagerIndicatorTrackView;

/**
 * 线性的ViewPager指示器GroupView
 */
public class LinearPagerIndicatorGroupView extends LinearLayout implements IPagerIndicatorGroupView
{
    public LinearPagerIndicatorGroupView(Context context)
    {
        super(context);
        init();
    }

    public LinearPagerIndicatorGroupView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public LinearPagerIndicatorGroupView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    private IPagerIndicatorAdapter mAdapter;
    private IPagerIndicatorTrackView mPagerIndicatorTrackView;

    private void init()
    {
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);
    }

    @Override
    public void setAdapter(IPagerIndicatorAdapter adapter)
    {
        mAdapter = adapter;
    }

    @Override
    public IPagerIndicatorAdapter getAdapter()
    {
        if (mAdapter != null)
        {
            return mAdapter;
        } else
        {
            return mInternalPagerIndicatorAdapter;
        }
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
        public IPagerIndicatorItemView onCreateView(int position)
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
        onCreateOrRemoveView(count);
        if (getPagerIndicatorTrackView() != null)
        {
            getPagerIndicatorTrackView().onPageCountChanged(count);
        }
    }

    /**
     * 根据当前count来决定增加或者移除view
     *
     * @param count
     */
    protected void onCreateOrRemoveView(int count)
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
                    IPagerIndicatorItemView itemView = adapter.onCreateView(childCount + i);
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
}
