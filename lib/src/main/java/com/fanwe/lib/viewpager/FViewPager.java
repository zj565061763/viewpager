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
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class FViewPager extends ViewPager
{
    public FViewPager(Context context)
    {
        super(context);
        init();
    }

    public FViewPager(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    /**
     * 是否锁住ViewPager，锁住后不能拖动
     */
    private boolean mIsLockPull = false;
    private List<PullCondition> mListCondition;

    private void init()
    {
    }

    /**
     * 设置是否锁住ViewPager，锁住后不能拖动
     *
     * @param lockPull
     */
    public void setLockPull(boolean lockPull)
    {
        mIsLockPull = lockPull;
    }

    /**
     * ViewPager是否被锁住不能拖动
     *
     * @return
     */
    public boolean isLockPull()
    {
        return mIsLockPull;
    }

    /**
     * 添加拖动条件
     *
     * @param condition
     */
    public void addPullCondition(PullCondition condition)
    {
        if (condition == null)
            return;

        if (mListCondition == null)
            mListCondition = new CopyOnWriteArrayList<>();

        if (mListCondition.contains(condition))
            return;

        mListCondition.add(condition);
    }

    /**
     * 移除拖动条件
     *
     * @param condition
     */
    public void removePullCondition(PullCondition condition)
    {
        if (condition == null || mListCondition == null)
            return;

        mListCondition.remove(condition);
        if (mListCondition.isEmpty())
            mListCondition = null;
    }

    private boolean canPull(MotionEvent event)
    {
        if (mListCondition == null || mListCondition.isEmpty())
            return true;

        for (PullCondition item : mListCondition)
        {
            if (!item.canPull(event, this))
                return false;
        }

        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev)
    {
        if (mIsLockPull || !canPull(ev))
            return false;

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if (mIsLockPull || !canPull(event))
            return false;

        return super.onTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (heightMode != MeasureSpec.EXACTLY)
        {
            int maxHeight = 0;
            final int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
            final int count = getChildCount();
            for (int i = 0; i < count; i++)
            {
                final View child = getChildAt(i);
                child.measure(widthMeasureSpec, childHeightMeasureSpec);

                final int height = child.getMeasuredHeight();
                if (height > maxHeight)
                    maxHeight = height;
            }

            if (heightMode == MeasureSpec.AT_MOST)
                maxHeight = Math.min(maxHeight, MeasureSpec.getSize(heightMeasureSpec));

            heightMeasureSpec = MeasureSpec.makeMeasureSpec(maxHeight, MeasureSpec.EXACTLY);
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public interface PullCondition
    {
        /**
         * 返回是否可以触发拖动
         *
         * @param event
         * @param viewPager
         * @return
         */
        boolean canPull(MotionEvent event, FViewPager viewPager);
    }
}
