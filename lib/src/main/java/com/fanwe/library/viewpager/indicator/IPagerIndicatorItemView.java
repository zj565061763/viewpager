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
     * ViewPager页面显示的百分比回调
     *
     * @param showPercent 显示的百分比[0-1]
     * @param isEnter     true-当前页面处于进入状态，false-当前页面处于离开状态
     * @param isMoveLeft  true-ViewPager内容向左移动，false-ViewPager内容向右移动
     */
    void onShowPercent(float showPercent, boolean isEnter, boolean isMoveLeft);

    /**
     * 返回ItemView的位置信息
     *
     * @return
     */
    PositionData getPositionData();
}
