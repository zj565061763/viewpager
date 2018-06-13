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
package com.fanwe.lib.viewpager;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.fanwe.lib.gridlayout.FGridLayout;
import com.fanwe.lib.viewpager.utils.FGridPageHelper;

/**
 * 可以设置每一页有多少item和多少列的ViewPager
 */
public class FGridViewPager extends FViewPager
{
    public FGridViewPager(Context context)
    {
        super(context);
    }

    public FGridViewPager(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    private BaseAdapter mGridAdapter;
    /**
     * 横分割线
     */
    private Drawable mGridHorizontalDivider;
    /**
     * 竖分割线
     */
    private Drawable mGridVerticalDivider;

    /**
     * 每页的数据要按几列展示
     */
    private int mGridColumnCountPerPage = 1;
    private FGridPageHelper mPageHelper = new FGridPageHelper();

    /**
     * 设置横分割线
     *
     * @param gridHorizontalDivider
     */
    public void setGridHorizontalDivider(Drawable gridHorizontalDivider)
    {
        mGridHorizontalDivider = gridHorizontalDivider;
    }

    /**
     * 设置竖分割线
     *
     * @param gridVerticalDivider
     */
    public void setGridVerticalDivider(Drawable gridVerticalDivider)
    {
        mGridVerticalDivider = gridVerticalDivider;
    }

    /**
     * 设置每页要显示的item数量
     *
     * @param count
     */
    public void setGridItemCountPerPage(int count)
    {
        mPageHelper.setItemCountPerPage(count);
    }

    /**
     * 设置每页的数据要按几列展示
     *
     * @param count
     */
    public void setGridColumnCountPerPage(int count)
    {
        mGridColumnCountPerPage = count;
    }

    /**
     * 返回设置的适配器
     *
     * @return
     */
    public BaseAdapter getGridAdapter()
    {
        return mGridAdapter;
    }

    /**
     * 设置适配器
     *
     * @param adapter
     */
    public void setGridAdapter(BaseAdapter adapter)
    {
        if (mGridAdapter != null)
        {
            mGridAdapter.unregisterDataSetObserver(mInternalGridDataSetObserver);
        }
        mGridAdapter = adapter;
        updateItemCount();
        if (adapter != null)
        {
            adapter.registerDataSetObserver(mInternalGridDataSetObserver);
            dealAdapterByGrid();
        } else
        {
            PagerAdapter pagerAdapter = getAdapter();
            if (pagerAdapter == mInternalPagerAdapter)
            {
                setAdapter(null);
            }
        }
    }

    private void updateItemCount()
    {
        int count = 0;
        if (mGridAdapter != null)
        {
            count = mGridAdapter.getCount();
        }
        mPageHelper.setItemCount(count);
    }

    @Override
    public void setAdapter(PagerAdapter adapter)
    {
        super.setAdapter(adapter);

        if (adapter != mInternalPagerAdapter)
        {
            setGridAdapter(null);
        }
    }

    private DataSetObserver mInternalGridDataSetObserver = new DataSetObserver()
    {
        @Override
        public void onChanged()
        {
            super.onChanged();
            updateItemCount();
            dealAdapterByGrid();
        }

        @Override
        public void onInvalidated()
        {
            super.onInvalidated();
        }
    };

    private void dealAdapterByGrid()
    {
        if (getAdapter() != mInternalPagerAdapter)
        {
            setAdapter(mInternalPagerAdapter);
        } else
        {
            mInternalPagerAdapter.notifyDataSetChanged();
        }
    }

    /**
     * ViewPager默认适配器
     */
    private PagerAdapter mInternalPagerAdapter = new PagerAdapter()
    {
        @Override
        public int getItemPosition(Object object)
        {
            return POSITION_NONE;
        }

        @Override
        public int getCount()
        {
            int count = mPageHelper.getPageCount();
            return count;
        }

        @Override
        public boolean isViewFromObject(View view, Object object)
        {
            return view == object;
        }

        public Object instantiateItem(ViewGroup container, final int pageIndex)
        {
            View pageView = null;

            FGridLayout gridLayout = new FGridLayout(getContext());
            gridLayout.setSpanCount(mGridColumnCountPerPage);

            if (mGridHorizontalDivider != null)
            {
                gridLayout.setHorizontalDivider(mGridHorizontalDivider);
            }
            if (mGridVerticalDivider != null)
            {
                gridLayout.setVerticalDivider(mGridVerticalDivider);
            }

            BaseAdapter pageAdapter = new BaseAdapter()
            {
                @Override
                public int getCount()
                {
                    int count = mPageHelper.getPageItemCount(pageIndex);
                    return count;
                }

                @Override
                public Object getItem(int pageItemIndex)
                {
                    return null;
                }

                @Override
                public long getItemId(int position)
                {
                    return mGridAdapter.getItemId(position);
                }

                @Override
                public View getView(int pageItemIndex, View convertView, ViewGroup parent)
                {
                    int index = mPageHelper.getItemIndexForPageItem(pageIndex, pageItemIndex);
                    return mGridAdapter.getView(index, convertView, parent);
                }
            };

            gridLayout.setAdapter(pageAdapter);

            pageView = gridLayout;
            container.addView(pageView);
            return pageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object)
        {
            container.removeView((View) object);
        }
    };
}
