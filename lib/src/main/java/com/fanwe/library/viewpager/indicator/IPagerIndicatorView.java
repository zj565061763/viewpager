package com.fanwe.library.viewpager.indicator;

import com.fanwe.library.viewpager.indicator.model.PositionData;

/**
 * ViewPager指示器，可跟随指示器Item的view
 */
public interface IPagerIndicatorView
{
    /**
     * ViewPager页数变化回调
     *
     * @param count
     */
    void onPageCountChanged(int count);

    /**
     * ViewPager某一页进入回调
     *
     * @param position     第几页
     * @param enterPercent 进入百分比
     * @param leftToRight  true-ViewPager页从右边进入，false-ViewPager页从左边进入
     * @param positionData 对应要跟随的ItemView的位置信息
     */
    void onEnter(int position, float enterPercent, boolean leftToRight, PositionData positionData);

    /**
     * ViewPager某一页退出回调
     *
     * @param position     第几页
     * @param leavePercent 退出百分比
     * @param leftToRight  true-ViewPager页从左边退出，false-ViewPager页从右边退出
     * @param positionData 对应要跟随的ItemView的位置信息
     */
    void onLeave(int position, float leavePercent, boolean leftToRight, PositionData positionData);
}
