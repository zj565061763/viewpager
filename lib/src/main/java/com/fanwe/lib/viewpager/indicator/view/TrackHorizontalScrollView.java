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
package com.fanwe.lib.viewpager.indicator.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;

public class TrackHorizontalScrollView extends HorizontalScrollView
{
    public TrackHorizontalScrollView(Context context)
    {
        super(context);
    }

    public TrackHorizontalScrollView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public TrackHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    private Runnable mScrollRunnable;

    public void smoothScrollTo(final View child)
    {
        removeScrollRunnable();
        mScrollRunnable = new Runnable()
        {
            public void run()
            {
                int scrollX = child.getLeft() - (getWidth() - child.getWidth()) / 2;
                smoothScrollTo(scrollX, 0);
                mScrollRunnable = null;
            }
        };
        postScrollRunnable();
    }

    private void removeScrollRunnable()
    {
        if (mScrollRunnable != null)
        {
            removeCallbacks(mScrollRunnable);
            mScrollRunnable = null;
        }
    }

    private void postScrollRunnable()
    {
        if (mScrollRunnable != null)
        {
            post(mScrollRunnable);
        }
    }

    @Override
    public void onAttachedToWindow()
    {
        super.onAttachedToWindow();
        postScrollRunnable();
    }

    @Override
    public void onDetachedFromWindow()
    {
        super.onDetachedFromWindow();
        removeScrollRunnable();
    }
}
