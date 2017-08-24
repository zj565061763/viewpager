package com.fanwe.library.viewpager.indicator;

import android.view.ViewGroup;

/**
 * ViewPager指示器ItemView的适配器
 */
public interface IPagerIndicatorAdapter
{
    /**
     * 返回ItemView
     *
     * @param position
     * @param viewParent
     * @return
     */
    IPagerIndicatorItemView onCreateView(int position, ViewGroup viewParent);
}
