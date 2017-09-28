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
package com.fanwe.lib.viewpager.indicator.impl;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.fanwe.lib.viewpager.indicator.IPagerIndicatorItem;
import com.fanwe.lib.viewpager.indicator.PagerIndicatorGroup;
import com.fanwe.lib.viewpager.indicator.adapter.PagerIndicatorAdapter;

/**
 * 线性的ViewPager指示器Group
 */
public class LinearPagerIndicatorGroup extends PagerIndicatorGroup
{
    public LinearPagerIndicatorGroup(Context context)
    {
        super(context);
        init();
    }

    public LinearPagerIndicatorGroup(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public LinearPagerIndicatorGroup(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init()
    {
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);
    }

    @Override
    public IPagerIndicatorItem getPagerIndicatorItem(int position)
    {
        if (position < 0)
        {
            return null;
        }
        final int childCount = getChildCount();
        if (position >= childCount)
        {
            return null;
        }

        return (IPagerIndicatorItem) getChildAt(position);
    }

    @Override
    public void onPageCountChanged(int count)
    {
        if (!isFullCreateMode())
        {
            final int childCount = getChildCount();
            if (count > childCount)
            {
                final int createCount = count - childCount;
                onAddPagerIndicatorItem(createCount);
            } else if (count < childCount)
            {
                final int removeCount = childCount - count;
                onRemovePagerIndicatorItem(removeCount);
            }
        }
        super.onPageCountChanged(count);
    }

    @Override
    protected void onDataSetChanged()
    {
        if (isFullCreateMode())
        {
            removeAllViews();
            onAddPagerIndicatorItem(getPageCount());
        }
    }

    /**
     * 添加Item
     *
     * @param count 要添加的数量
     */
    protected void onAddPagerIndicatorItem(int count)
    {
        if (count <= 0)
        {
            return;
        }
        final PagerIndicatorAdapter adapter = getAdapter();
        if (adapter == null)
        {
            return;
        }
        final int childCount = getChildCount();
        for (int i = 0; i < count; i++)
        {
            View view = adapter.createPagerIndicatorItem(childCount + i, this);

            ViewGroup.LayoutParams params = view.getLayoutParams();
            if (params == null)
            {
                params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
                view.setLayoutParams(params);
            }

            if (!view.hasOnClickListeners())
            {
                view.setOnClickListener(new OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        ViewPager viewPager = getViewPager();
                        if (viewPager != null)
                        {
                            viewPager.setCurrentItem(indexOfChild(v));
                        }
                    }
                });
            }

            addView(view, params);
        }
    }

    /**
     * 移除Item
     *
     * @param count 要移除的数量
     */
    protected void onRemovePagerIndicatorItem(int count)
    {
        if (count <= 0)
        {
            return;
        }
        final int childCount = getChildCount();
        if (childCount <= 0)
        {
            return;
        }
        if (count > childCount)
        {
            count = childCount;
        }
        final int leftCount = childCount - count;
        //倒序移除
        for (int i = childCount - 1; i >= leftCount; i--)
        {
            removeViewAt(i);
        }
    }
}
