package com.fanwe.library.viewpager.indicator.impl;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.fanwe.library.viewpager.indicator.IPagerIndicatorItemView;
import com.fanwe.library.viewpager.indicator.model.PositionData;

/**
 * Created by Administrator on 2017/8/23.
 */

public abstract class PagerIndicatorItemView extends FrameLayout implements IPagerIndicatorItemView
{
    public PagerIndicatorItemView(Context context)
    {
        super(context);
    }

    public PagerIndicatorItemView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public PagerIndicatorItemView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    private PositionData mPositionData;

    @Override
    public void onEnter(float enterPercent, boolean leftToRight)
    {

    }

    @Override
    public void onLeave(float leavePercent, boolean leftToRight)
    {

    }

    @Override
    public PositionData getPositionData()
    {
        if (mPositionData == null)
        {
            mPositionData = new PositionData();
        }
        return mPositionData;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom)
    {
        super.onLayout(changed, left, top, right, bottom);

        getPositionData().left = getLeft();
        getPositionData().top = getTop();
        getPositionData().right = getRight();
        getPositionData().bottom = getBottom();
    }
}
