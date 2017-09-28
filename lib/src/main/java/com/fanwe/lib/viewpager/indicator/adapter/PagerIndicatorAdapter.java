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
package com.fanwe.lib.viewpager.indicator.adapter;

import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;

import com.fanwe.lib.viewpager.indicator.IPagerIndicatorItem;

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
     * 创建Item
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
     * 创建Item
     *
     * @param position
     * @param viewParent
     * @return
     */
    protected abstract IPagerIndicatorItem onCreatePagerIndicatorItem(int position, ViewGroup viewParent);

}
