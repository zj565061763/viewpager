package com.fanwe.library.viewpager.indicator.adapter;

import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;

import com.fanwe.library.viewpager.indicator.IPagerIndicatorItem;

/**
 * Created by Administrator on 2017/8/25.
 */

public abstract class PagerIndicatorAdapter
{
    private final DataSetObservable mDataSetObservable = new DataSetObservable();

    public final void registerDataSetObserver(DataSetObserver observer)
    {
        mDataSetObservable.registerObserver(observer);
    }

    public final void unregisterDataSetObserver(DataSetObserver observer)
    {
        mDataSetObservable.unregisterObserver(observer);
    }

    public final void notifyDataSetChanged()
    {
        mDataSetObservable.notifyChanged();
    }

    /**
     * 创建ItemView
     *
     * @param position
     * @param viewParent
     * @return
     */
    public final View createPagerIndicatorItem(int position, ViewGroup viewParent)
    {
        IPagerIndicatorItem item = onCreatePagerIndicatorItem(position, viewParent);
        if (item instanceof View)
        {
            return (View) item;
        } else
        {
            throw new IllegalArgumentException("onCreatePagerIndicatorItem method must return instance of View");
        }
    }

    /**
     * 创建ItemView
     *
     * @param position
     * @param viewParent
     * @return
     */
    protected abstract IPagerIndicatorItem onCreatePagerIndicatorItem(int position, ViewGroup viewParent);

}
