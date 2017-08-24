package com.fanwe.library.viewpager.indicator;

import com.fanwe.library.viewpager.indicator.model.PositionData;

/**
 * ViewPager指示器，可跟随指示器Item的view
 */
public interface IPagerIndicatorView
{
    void onPageCountChanged(int count);

    void onEnter(int position, float enterPercent, boolean leftToRight, PositionData positionData);

    void onLeave(int position, float leavePercent, boolean leftToRight, PositionData positionData);
}
