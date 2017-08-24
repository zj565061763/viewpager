package com.fanwe.library.viewpager.indicator;

/**
 * ViewPager指示器ItemView的适配器
 */
public interface IPagerIndicatorAdapter
{
    /**
     * 返回ItemView
     *
     * @param position
     * @return
     */
    IPagerIndicatorItemView onCreateView(int position);
}
