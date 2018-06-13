/*
 * Copyright (C) 2017 zhengjun, fanwe (http://www.fanwe.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fanwe.lib.viewpager.utils;

public class FPageHelper
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
        int page = mItemCount / mItemCountPerPage;
        int left = mItemCount % mItemCountPerPage;
        if (left == 0)
        {
            return page;
        } else
        {
            return page + 1;
        }
    }

    /**
     * 返回该页有几个item
     *
     * @param pageIndex
     * @return
     */
    public int getPageItemCount(int pageIndex)
    {
        int pageCount = getPageCount();
        if (pageCount <= 0)
        {
            return 0;
        }
        if (pageIndex < 0 || pageIndex >= pageCount)
        {
            return 0;
        }

        if (pageIndex == (pageCount - 1))
        {
            int left = mItemCount % mItemCountPerPage;
            if (left == 0)
            {
                return mItemCountPerPage;
            } else
            {
                return left;
            }
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
        int start = pageIndex * mItemCountPerPage;
        return start + pageItemIndex;
    }
}
