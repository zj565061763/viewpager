package com.fanwe.library.viewpager.indicator;

/**
 * ViewPager指示器ItemView的适配器
 */
public interface IPagerIndicatorAdapter
{
    /**
     * 返回ItemView
     *
     * @return
     */
    IPagerIndicatorItemView onCreateView();
}
