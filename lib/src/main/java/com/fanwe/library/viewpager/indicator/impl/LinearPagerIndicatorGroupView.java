package com.fanwe.library.viewpager.indicator.impl;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.fanwe.library.viewpager.indicator.IPagerIndicatorAdapter;
import com.fanwe.library.viewpager.indicator.IPagerIndicatorGroupView;
import com.fanwe.library.viewpager.indicator.IPagerIndicatorItemView;

/**
 * Created by Administrator on 2017/8/23.
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

    private IPagerIndicatorAdapter mInternalPagerIndicatorAdapter = new IPagerIndicatorAdapter()
    {
        @Override
        public IPagerIndicatorItemView onCreateView()
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
        final int childCount = getChildCount();
        if (count > childCount)
        {
            final IPagerIndicatorAdapter adapter = getAdapter();
            if (adapter != null)
            {
                for (int i = 0; i < count - childCount; i++)
                {
                    IPagerIndicatorItemView itemView = adapter.onCreateView();
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
    public void onShowPercent(int position, float showPercent, boolean isEnter, boolean leftToRight)
    {
        IPagerIndicatorItemView itemView = getItemView(position);
        if (itemView != null)
        {
            itemView.onShowPercent(showPercent, isEnter, leftToRight);
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
