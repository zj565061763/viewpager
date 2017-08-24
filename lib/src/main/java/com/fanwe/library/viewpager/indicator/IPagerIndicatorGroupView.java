package com.fanwe.library.viewpager.indicator;

/**
 * ViewPager指示器GroupView
 */
public interface IPagerIndicatorGroupView
{
    void setAdapter(IPagerIndicatorAdapter adapter);

    IPagerIndicatorAdapter getAdapter();

    IPagerIndicatorItemView getItemView(int position);

    void onPageCountChanged(int count);

    void onEnter(int position, float enterPercent, boolean leftToRight);

    void onLeave(int position, float leavePercent, boolean leftToRight);

    void onSelectedChanged(int position, boolean selected);

}
