package com.fanwe.library.viewpager.indicator;

import com.fanwe.library.viewpager.indicator.model.PositionData;

/**
 * Created by Administrator on 2017/8/23.
 */
public interface IPagerIndicatorItemView
{
    void onSelectedChanged(boolean selected);

    void onEnter(float enterPercent, boolean leftToRight);

    void onLeave(float enterPercent, boolean leftToRight);

    PositionData getPositionData();
}
