package com.sd.lib.viewpager.utils;

public class FGridPageHelper
{
    /**
     * 一共有多少个item
     */
    private int mItemCount;
    /**
     * 每页要显示的item数量
     */
    private int mItemCountPerPage = 1;

    /**
     * 设置一共有多少个item
     *
     * @param count
     */
    public void setItemCount(int count)
    {
        mItemCount = count;
    }

    /**
     * 设置每页要显示的item数量
     *
     * @param count
     */
    public void setItemCountPerPage(int count)
    {
        mItemCountPerPage = count;
    }

    /**
     * 返回一共有几页
     *
     * @return
     */
    public int getPageCount()
    {
        final int page = mItemCount / mItemCountPerPage;
        final int left = mItemCount % mItemCountPerPage;

        return left == 0 ? page : page + 1;
    }

    /**
     * 返回该页有几个item
     *
     * @param pageIndex
     * @return
     */
    public int getPageItemCount(int pageIndex)
    {
        final int pageCount = getPageCount();
        if (pageCount <= 0)
            return 0;

        if (pageIndex < 0 || pageIndex >= pageCount)
            return 0;

        if (pageIndex == (pageCount - 1))
        {
            final int left = mItemCount % mItemCountPerPage;
            return left == 0 ? mItemCountPerPage : left;
        } else
        {
            return mItemCountPerPage;
        }
    }

    /**
     * 返回item在第几页
     *
     * @param itemIndex item在总的集合中的位置
     * @return
     */
    public int getPageIndexForItem(int itemIndex)
    {
        if (itemIndex >= 0 && itemIndex < mItemCount)
        {
            return itemIndex / mItemCountPerPage;
        } else
        {
            return -1;
        }
    }

    /**
     * 返回第几页中的第几个item在总的item集合中的位置
     *
     * @param pageIndex
     * @param pageItemIndex
     * @return
     */
    public int getItemIndexForPageItem(int pageIndex, int pageItemIndex)
    {
        final int start = pageIndex * mItemCountPerPage;
        return start + pageItemIndex;
    }
}
