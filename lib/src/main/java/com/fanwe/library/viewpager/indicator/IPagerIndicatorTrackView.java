package com.fanwe.library.viewpager.indicator;

import com.fanwe.library.viewpager.indicator.model.PositionData;

/**
 * ViewPager指示器，可跟随指示器Item的view
 */
public interface IPagerIndicatorTrackView
{
    /**
     * ViewPager页数变化回调
     *
     * @param count
     */
    void onPageCountChanged(int count);

    /**
     * ViewPager页面显示的百分比回调
     *
     * @param position     第几页
     * @param showPercent  显示的百分比[0-1]
     * @param isEnter      true-当前页面处于进入状态，false-当前页面处于离开状态
     * @param isMoveLeft   true-ViewPager内容向左移动，false-ViewPager内容向右移动
     * @param positionData 当前position对应ItemView的位置信息
     */
    void onShowPercent(int position, float showPercent, boolean isEnter, boolean isMoveLeft, PositionData positionData);
}
