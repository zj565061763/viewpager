package com.fanwe.www.viewpager;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

import com.fanwe.library.viewpager.indicator.IPagerIndicatorItemView;
import com.fanwe.library.viewpager.indicator.model.PositionData;

/**
 * Created by Administrator on 2017/8/24.
 */

public class TextViewItemView extends TextView implements IPagerIndicatorItemView
{
    public TextViewItemView(Context context)
    {
        super(context);
        onSelectedChanged(false);
    }

    @Override
    public void onSelectedChanged(boolean selected)
    {
        if (selected)
        {
            setTextColor(Color.parseColor("#616161"));
        } else
        {
            setTextColor(Color.parseColor("#f57c00"));
        }
    }

    @Override
    public void onShowPercent(float showPercent, boolean isEnter, boolean isMoveLeft)
    {

    }

    private PositionData mPositionData = new PositionData();

    @Override
    public PositionData getPositionData()
    {
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
