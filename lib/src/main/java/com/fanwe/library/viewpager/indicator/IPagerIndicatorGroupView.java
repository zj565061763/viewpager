package com.fanwe.library.viewpager.indicator;

/**
 * ViewPager指示器GroupView
 */
public interface IPagerIndicatorGroupView
{
    /**
     * 设置适配器
     *
     * @param adapter
     */
    void setAdapter(IPagerIndicatorAdapter adapter);

    /**
     * 返回适配器
     *
     * @return
     */
    IPagerIndicatorAdapter getAdapter();

    /**
     * 返回position位置对应的ItemView
     *
     * @param position
     * @return
     */
    IPagerIndicatorItemView getItemView(int position);

    /**
     * ViewPager页数变化回调
     *
     * @param count
     */
    void onPageCountChanged(int count);

    /**
     * ViewPager页面显示的百分比回调
     *
     * @param position    第几页
     * @param showPercent 显示的百分比[0-1]
     * @param isEnter     true-当前页面处于进入状态，false-当前页面处于离开状态
     * @param leftToRight true-ViewPager内容向左移动，false-ViewPager内容向右移动
     */
    void onShowPercent(int position, float showPercent, boolean isEnter, boolean leftToRight);

    /**
     * ViewPager某一页选中或者非选中回调
     *
     * @param position 第几页
     * @param selected true-选中，false-未选中
     */
    void onSelectedChanged(int position, boolean selected);

}
