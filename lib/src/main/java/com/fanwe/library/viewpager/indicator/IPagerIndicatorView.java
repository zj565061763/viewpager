package com.fanwe.library.viewpager.indicator;

import com.fanwe.library.viewpager.indicator.model.PositionData;

/**
 * Created by Administrator on 2017/8/23.
 */

public interface IPagerIndicatorView
{
    void onPageCountChanged(int count);

    void onEnter(int position, float enterPercent, boolean leftToRight, PositionData positionData);

    void onLeave(int position, float leavePercent, boolean leftToRight, PositionData positionData);
}
