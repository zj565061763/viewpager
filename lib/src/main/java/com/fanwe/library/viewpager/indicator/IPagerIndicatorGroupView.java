package com.fanwe.library.viewpager.indicator;

/**
 * Created by Administrator on 2017/8/23.
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
