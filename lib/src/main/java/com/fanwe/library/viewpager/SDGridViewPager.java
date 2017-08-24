package com.fanwe.library.viewpager;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.fanwe.library.gridlayout.SDGridLayout;

/**
 * 可以设置每一页有多少item和多少列的ViewPager
 */
public class SDGridViewPager extends SDViewPager
{
    public SDGridViewPager(Context context)
    {
        super(context);
    }

    public SDGridViewPager(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    /**
     * 每页要显示的item数量
     */
    private int mGridItemCountPerPage = 1;
    /**
     * 每页的数据要按几列展示
     */
    private int mGridColumnCountPerPage = 1;
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
     * @param gridItemCountPerPage
     */
    public void setGridItemCountPerPage(int gridItemCountPerPage)
    {
        mGridItemCountPerPage = gridItemCountPerPage;
    }

    /**
     * 返回每页要显示的item数量
     *
     * @return
     */
    public int getGridItemCountPerPage()
    {
        return mGridItemCountPerPage;
    }

    /**
     * 设置每页的数据要按几列展示
     *
     * @param gridColumnCountPerPage
     */
    public void setGridColumnCountPerPage(int gridColumnCountPerPage)
    {
        mGridColumnCountPerPage = gridColumnCountPerPage;
    }

    /**
     * 返回每页的数据按几列展示
     *
     * @return
     */
    public int getGridColumnCountPerPage()
    {
        return mGridColumnCountPerPage;
    }

    /**
     * 返回一共有几页
     *
     * @return
     */
    private int getGridPageCount()
    {
        if (mGridAdapter != null)
        {
            int left = mGridAdapter.getCount() % getGridItemCountPerPage();
            int page = mGridAdapter.getCount() / getGridItemCountPerPage();
            if (left == 0)
            {
                return page;
            } else
            {
                return page + 1;
            }
        } else
        {
            return 0;
        }
    }

    /**
     * 返回该页有几个item
     *
     * @param pageIndex
     * @return
     */
    public int getGridPageItemCount(int pageIndex)
    {
        int pageCount = getGridPageCount();
        if (pageCount <= 0)
        {
            return 0;
        }
        if (pageIndex < 0 || pageIndex >= pageCount)
        {
            return 0;
        }

        int start = pageIndex * getGridItemCountPerPage();
        int end = start + getGridItemCountPerPage() - 1;
        if (end < mGridAdapter.getCount())
        {
            return getGridItemCountPerPage();
        } else
        {
            return mGridAdapter.getCount() - start;
        }
    }

    /**
     * 返回itemPosition在第几页
     *
     * @param itemPosition
     * @return
     */
    public int indexOfGridPage(int itemPosition)
    {
        if (itemPosition >= 0 && mGridAdapter != null && itemPosition < mGridAdapter.getCount())
        {
            return itemPosition / getGridItemCountPerPage();
        } else
        {
            return -1;
        }
    }

    /**
     * 设置适配器
     *
     * @param adapter
     */
    public void setGridAdapter(BaseAdapter adapter)
    {
        if (mGridAdapter != adapter)
        {
            if (mGridAdapter != null)
            {
                mGridAdapter.unregisterDataSetObserver(mInternalDataSetObserver);
            }
            mGridAdapter = adapter;
            if (adapter != null)
            {
                adapter.registerDataSetObserver(mInternalDataSetObserver);
                dealAdapter();
            }
        }
    }

    @Override
    public void setAdapter(PagerAdapter adapter)
    {
        if (adapter != mInternalPagerAdapter)
        {
            setGridAdapter(null);
        }
        super.setAdapter(adapter);
    }

    private DataSetObserver mInternalDataSetObserver = new DataSetObserver()
    {
        @Override
        public void onChanged()
        {
            super.onChanged();
            dealAdapter();
        }

        @Override
        public void onInvalidated()
        {
            super.onInvalidated();
        }
    };

    private void dealAdapter()
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
            return getGridPageCount();
        }

        @Override
        public boolean isViewFromObject(View view, Object object)
        {
            return view == object;
        }

        public Object instantiateItem(ViewGroup container, final int position)
        {
            View pageView = null;

            final int startPosition = position * getGridItemCountPerPage();

            SDGridLayout gridLayout = new SDGridLayout(getContext());
            gridLayout.setSpanCount(getGridColumnCountPerPage());

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
                    return getGridPageItemCount(position);
                }

                @Override
                public Object getItem(int position)
                {
                    return mGridAdapter.getItem(startPosition + position);
                }

                @Override
                public long getItemId(int position)
                {
                    return mGridAdapter.getItemId(position);
                }

                @Override
                public View getView(int position, View convertView, ViewGroup parent)
                {
                    int calPosition = startPosition + position;
                    return mGridAdapter.getView(calPosition, convertView, parent);
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
