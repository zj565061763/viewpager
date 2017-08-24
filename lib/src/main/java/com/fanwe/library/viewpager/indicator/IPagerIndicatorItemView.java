package com.fanwe.library.viewpager.indicator;

import com.fanwe.library.viewpager.indicator.model.PositionData;

/**
 * ViewPager指示器ItemView
 */
public interface IPagerIndicatorItemView
{
    void onSelectedChanged(boolean selected);

    void onEnter(float enterPercent, boolean leftToRight);

    void onLeave(float leavePercent, boolean leftToRight);

    PositionData getPositionData();
}
