package com.fanwe.library.viewpager.indicator;

/**
 * Created by Administrator on 2017/8/23.
 */

public interface ISDPagerIndicatorItemView
{
    void onSelectedChanged(boolean selected);

    void onEnter(float enterPercent, boolean leftToRight);

    void onLeave(float enterPercent, boolean leftToRight);
}
