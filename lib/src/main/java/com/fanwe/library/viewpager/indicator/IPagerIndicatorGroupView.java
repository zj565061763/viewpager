package com.fanwe.library.viewpager.indicator;

import com.fanwe.library.viewpager.SDViewPagerInfoListener;

/**
 * Created by Administrator on 2017/8/23.
 */

public interface IPagerIndicatorGroupView extends SDViewPagerInfoListener.OnPageCountChangeCallback,
        SDViewPagerInfoListener.OnScrolledPercentChangeCallback,
        SDViewPagerInfoListener.OnSelectedChangeCallback
{

    IPagerIndicatorItemView getItemView(int position);

}
