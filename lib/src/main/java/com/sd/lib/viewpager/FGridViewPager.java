package com.sd.lib.viewpager;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.viewpager.widget.PagerAdapter;

import com.sd.lib.viewpager.utils.FGridPageHelper;

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
    private final FGridPageHelper mHelper = new FGridPageHelper();

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
        mHelper.setItemCountPerPage(count);
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
            mGridAdapter.unregisterDataSetObserver(mGridDataSetObserver);

        mGridAdapter = adapter;
        updateGridItemCount();

        if (adapter != null)
        {
            adapter.registerDataSetObserver(mGridDataSetObserver);
            updateGridUI();
        } else
        {
            if (getAdapter() == mInternalPagerAdapter)
                setAdapter(null);
        }
    }

    @Override
    public void setAdapter(PagerAdapter adapter)
    {
        super.setAdapter(adapter);

        if (adapter != mInternalPagerAdapter)
            setGridAdapter(null);
    }

    private final DataSetObserver mGridDataSetObserver = new DataSetObserver()
    {
        @Override
        public void onChanged()
        {
            super.onChanged();
            updateGridItemCount();
            updateGridUI();
        }

        @Override
        public void onInvalidated()
        {
            super.onInvalidated();
        }
    };

    private void updateGridItemCount()
    {
        final int count = mGridAdapter == null ? 0 : mGridAdapter.getCount();
        mHelper.setItemCount(count);
    }

    private void updateGridUI()
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
    private final PagerAdapter mInternalPagerAdapter = new PagerAdapter()
    {
        @Override
        public int getItemPosition(Object object)
        {
            return POSITION_NONE;
        }

        @Override
        public int getCount()
        {
            return mHelper.getPageCount();
        }

        @Override
        public boolean isViewFromObject(View view, Object object)
        {
            return view == object;
        }

        public Object instantiateItem(ViewGroup container, final int pageIndex)
        {
            final FGridLayout gridLayout = new FGridLayout(getContext());
            gridLayout.setSpanCount(mGridColumnCountPerPage);

            if (mGridHorizontalDivider != null)
                gridLayout.setHorizontalDivider(mGridHorizontalDivider);

            if (mGridVerticalDivider != null)
                gridLayout.setVerticalDivider(mGridVerticalDivider);

            final BaseAdapter baseAdapter = new BaseAdapter()
            {
                @Override
                public int getCount()
                {
                    return mHelper.getPageItemCount(pageIndex);
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
                    final int index = mHelper.getItemIndexForPageItem(pageIndex, pageItemIndex);
                    return mGridAdapter.getView(index, convertView, parent);
                }
            };

            gridLayout.setAdapter(baseAdapter);

            container.addView(gridLayout);
            return gridLayout;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object)
        {
            container.removeView((View) object);
        }
    };
}
