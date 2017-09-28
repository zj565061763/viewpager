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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.fanwe.lib.viewpager.R;
import com.fanwe.lib.viewpager.helper.SDViewPagerInfoListener;
import com.fanwe.lib.viewpager.indicator.IPagerIndicatorItem;
import com.fanwe.lib.viewpager.indicator.IPagerIndicatorTrack;
import com.fanwe.lib.viewpager.indicator.PagerIndicatorGroup;
import com.fanwe.lib.viewpager.indicator.adapter.PagerIndicatorAdapter;
import com.fanwe.lib.viewpager.indicator.view.TrackHorizontalScrollView;

public class PagerIndicator extends FrameLayout
{
    public PagerIndicator(Context context)
    {
        super(context);
        init();
    }

    public PagerIndicator(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public PagerIndicator(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    private TrackHorizontalScrollView mHorizontalScrollView;

    private PagerIndicatorGroup mPagerIndicatorGroup;
    private ViewGroup mPagerIndicatorTrackContainer;

    private SDViewPagerInfoListener mViewPagerInfoListener = new SDViewPagerInfoListener();

    private void init()
    {
        LayoutInflater.from(getContext()).inflate(R.layout.lib_vpg_pager_indicator, this, true);
        mHorizontalScrollView = (TrackHorizontalScrollView) findViewById(R.id.view_scroll);
        mPagerIndicatorGroup = (PagerIndicatorGroup) findViewById(R.id.view_indicator_group);
        mPagerIndicatorTrackContainer = (ViewGroup) findViewById(R.id.view_indicator_track_container);

        mViewPagerInfoListener.setOnPageSelectedChangeCallback(mInternalOnPageSelectedChangeCallback);
    }

    public void setDebug(boolean debug)
    {
        mPagerIndicatorGroup.setDebug(debug);
    }

    /**
     * 设置ViewPager
     *
     * @param viewPager
     */
    public void setViewPager(ViewPager viewPager)
    {
        mPagerIndicatorGroup.setViewPager(viewPager);
        mViewPagerInfoListener.setViewPager(viewPager);
    }

    /**
     * 设置适配器
     *
     * @param adapter
     */
    public void setAdapter(PagerIndicatorAdapter adapter)
    {
        mPagerIndicatorGroup.setAdapter(adapter);
    }

    /**
     * 返回设置的适配器
     *
     * @return
     */
    public PagerIndicatorAdapter getAdapter()
    {
        return mPagerIndicatorGroup.getAdapter();
    }

    /**
     * 返回position位置对应的Item
     *
     * @param position
     * @return
     */
    public IPagerIndicatorItem getPagerIndicatorItem(int position)
    {
        return mPagerIndicatorGroup.getPagerIndicatorItem(position);
    }

    /**
     * 设置当DataSetObserver数据变化的时候是否全部重新创建view，默认true
     *
     * @param fullCreateMode
     */
    public void setFullCreateMode(boolean fullCreateMode)
    {
        mPagerIndicatorGroup.setFullCreateMode(fullCreateMode);
    }

    /**
     * 设置可追踪指示器Item的view
     *
     * @param pagerIndicatorTrack
     */
    public void setPagerIndicatorTrack(IPagerIndicatorTrack pagerIndicatorTrack)
    {
        final IPagerIndicatorTrack oldTrack = mPagerIndicatorGroup.getPagerIndicatorTrack();
        if (oldTrack != pagerIndicatorTrack)
        {
            if (oldTrack != null)
            {
                mPagerIndicatorTrackContainer.removeAllViews();
            }
            mPagerIndicatorGroup.setPagerIndicatorTrack(pagerIndicatorTrack);
            if (pagerIndicatorTrack != null)
            {
                if (pagerIndicatorTrack instanceof View)
                {
                    mPagerIndicatorTrackContainer.addView((View) pagerIndicatorTrack);
                } else
                {
                    throw new IllegalArgumentException("pagerIndicatorView must be instance of view");
                }
            }
        }
    }

    private SDViewPagerInfoListener.OnPageSelectedChangeCallback mInternalOnPageSelectedChangeCallback = new SDViewPagerInfoListener.OnPageSelectedChangeCallback()
    {
        @Override
        public void onSelectedChanged(int position, boolean selected)
        {
            if (selected)
            {
                IPagerIndicatorItem item = mPagerIndicatorGroup.getPagerIndicatorItem(position);
                if (item != null)
                {
                    mHorizontalScrollView.smoothScrollTo((View) item);
                }
            }
        }
    };

}
