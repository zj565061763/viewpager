package com.fanwe.library.viewpager.indicator;

import com.fanwe.library.viewpager.indicator.model.PositionData;

/**
 * ViewPager指示器ItemView
 */
public interface IPagerIndicatorItemView
{
    /**
     * 选中或者非选中回调
     *
     * @param selected true-选中，false-未选中
     */
    void onSelectedChanged(boolean selected);

    /**
     * ViewPager页进入回调
     *
     * @param enterPercent 进入百分比
     * @param leftToRight  true-ViewPager页从右边进入，false-ViewPager页从左边进入
     */
    void onEnter(float enterPercent, boolean leftToRight);

    /**
     * ViewPager页退出回调
     *
     * @param leavePercent 退出百分比
     * @param leftToRight  true-ViewPager页从左边退出，false-ViewPager页从右边退出
     */
    void onLeave(float leavePercent, boolean leftToRight);

    /**
     * 返回ItemView的位置信息
     *
     * @return
     */
    PositionData getPositionData();
}
