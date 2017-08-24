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
     * 页数变化回调
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
     */
    void onEnter(int position, float enterPercent, boolean leftToRight);

    /**
     * ViewPager某一页退出回调
     *
     * @param position     第几页
     * @param leavePercent 退出百分比
     * @param leftToRight  true-ViewPager页从左边退出，false-ViewPager页从右边退出
     */
    void onLeave(int position, float leavePercent, boolean leftToRight);

    /**
     * ViewPager某一页选中或者非选中回调
     *
     * @param position 第几页
     * @param selected true-选中，false-未选中
     */
    void onSelectedChanged(int position, boolean selected);

}
