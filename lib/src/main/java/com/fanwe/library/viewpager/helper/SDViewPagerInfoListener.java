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
package com.fanwe.library.viewpager.helper;

import android.database.DataSetObserver;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;

import java.lang.ref.WeakReference;

/**
 * 监听ViewPager的一些重要数据，比如总页数变化，数据集变化，选中变化，滚动百分比变化等
 */
public class SDViewPagerInfoListener
{
    private WeakReference<ViewPager> mViewPager;
    private PagerAdapterDataSetObserver mInternalDataSetObserver = new PagerAdapterDataSetObserver();
    private InternalOnPageChangeListener mInternalOnPageChangeListener = new InternalOnPageChangeListener();
    private int mPageCount;

    private DataSetObserver mDataSetObserver;
    private ViewPager.OnPageChangeListener mOnPageChangeListener;
    private ViewPager.OnAdapterChangeListener mOnAdapterChangeListener;

    private OnPageCountChangeCallback mOnPageCountChangeCallback;
    private OnPageSelectedChangeCallback mOnPageSelectedChangeCallback;
    private OnPageScrolledPercentChangeCallback mOnPageScrolledPercentChangeCallback;

    /**
     * 设置数据变化回调
     *
     * @param dataSetObserver
     */
    public void setDataSetObserver(DataSetObserver dataSetObserver)
    {
        mDataSetObserver = dataSetObserver;
    }

    /**
     * 设置页面变化监听
     *
     * @param onPageChangeListener
     */
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener)
    {
        mOnPageChangeListener = onPageChangeListener;
    }

    /**
     * 设置Adapter变化监听
     *
     * @param onAdapterChangeListener
     */
    public void setOnAdapterChangeListener(ViewPager.OnAdapterChangeListener onAdapterChangeListener)
    {
        mOnAdapterChangeListener = onAdapterChangeListener;
    }

    /**
     * 设置页数变化回调
     *
     * @param onPageCountChangeCallback
     */
    public void setOnPageCountChangeCallback(OnPageCountChangeCallback onPageCountChangeCallback)
    {
        mOnPageCountChangeCallback = onPageCountChangeCallback;
    }

    /**
     * 设置选中变化回调
     *
     * @param onPageSelectedChangeCallback
     */
    public void setOnPageSelectedChangeCallback(OnPageSelectedChangeCallback onPageSelectedChangeCallback)
    {
        mOnPageSelectedChangeCallback = onPageSelectedChangeCallback;
    }

    /**
     * 设置滚动百分比回调
     *
     * @param onPageScrolledPercentChangeCallback
     */
    public void setOnPageScrolledPercentChangeCallback(OnPageScrolledPercentChangeCallback onPageScrolledPercentChangeCallback)
    {
        mOnPageScrolledPercentChangeCallback = onPageScrolledPercentChangeCallback;
    }

    /**
     * 返回要监听的ViewPager对象
     *
     * @return
     */
    public ViewPager getViewPager()
    {
        if (mViewPager != null)
        {
            return mViewPager.get();
        } else
        {
            return null;
        }
    }

    /**
     * 返回当前ViewPager一共有几页
     *
     * @return
     */
    public int getPageCount()
    {
        return mPageCount;
    }

    private void setPageCount(int pageCount)
    {
        if (mPageCount != pageCount)
        {
            mPageCount = pageCount;

            initShowPercent();

            if (mOnPageCountChangeCallback != null)
            {
                mOnPageCountChangeCallback.onPageCountChanged(pageCount);
            }
        }
    }

    /**
     * 设置要监听的ViewPager
     *
     * @param viewPager
     */
    public void setViewPager(ViewPager viewPager)
    {
        final ViewPager oldView = getViewPager();
        if (oldView != viewPager)
        {
            if (oldView != null)
            {
                //如果旧的对象不为空先取消监听
                oldView.removeOnPageChangeListener(mInternalOnPageChangeListener);
                oldView.removeOnAdapterChangeListener(mInternalOnAdapterChangeListener);
                mInternalDataSetObserver.unregister();
            }

            mInternalOnPageChangeListener.resetPosition();

            if (viewPager != null)
            {
                mViewPager = new WeakReference<>(viewPager);

                viewPager.addOnPageChangeListener(mInternalOnPageChangeListener);
                viewPager.addOnAdapterChangeListener(mInternalOnAdapterChangeListener);
                mInternalDataSetObserver.register(viewPager.getAdapter());

                mInternalOnPageChangeListener.setSelected(viewPager.getCurrentItem());
            } else
            {
                mViewPager = null;

                setPageCount(0);
            }
        }
    }

    private SparseArray<Float> mArrShowPercent = new SparseArray<>();

    private void initShowPercent()
    {
        mArrShowPercent.clear();
        final int pageCount = getPageCount();
        if (pageCount <= 0)
        {
            return;
        }
        for (int i = 0; i < pageCount; i++)
        {
            mArrShowPercent.put(i, 0f);
        }
    }

    /**
     * 通知选中
     */
    public void notifySelected()
    {
        final ViewPager viewPager = getViewPager();
        if (viewPager != null)
        {
            mInternalOnPageChangeListener.notifySelectedChanged(viewPager.getCurrentItem(), true);
        }
    }

    private class InternalOnPageChangeListener implements ViewPager.OnPageChangeListener
    {
        private int mScrollState = ViewPager.SCROLL_STATE_IDLE;
        private float mLastPositionOffsetSum = -1;

        private int mCurrentPosition = -1;
        private int mLastPosition = -1;

        private void resetPosition()
        {
            mCurrentPosition = -1;
            mLastPosition = -1;
        }

        private void setSelected(int position)
        {
            if (position < 0)
            {
                return;
            }

            mLastPosition = mCurrentPosition;
            mCurrentPosition = position;

            notifySelectedChanged(mLastPosition, false);
            notifySelectedChanged(mCurrentPosition, true);
        }

        private void notifySelectedChanged(int position, boolean selected)
        {
            if (position < 0 || position >= getPageCount())
            {
                return;
            }
            if (mOnPageSelectedChangeCallback != null)
            {
                mOnPageSelectedChangeCallback.onSelectedChanged(position, selected);
            }
        }

        private void notifyShowPercent(int position, float percent, boolean isEnter, boolean isMoveLeft)
        {
            if (position < 0 || position >= getPageCount())
            {
                return;
            }

            if (mOnPageScrolledPercentChangeCallback != null)
            {
                mOnPageScrolledPercentChangeCallback.onShowPercent(position, percent, isEnter, isMoveLeft);
            }

            mArrShowPercent.put(position, percent);
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
        {
            final float currentPositionOffsetSum = position + positionOffset;
            if (mLastPositionOffsetSum < 0)
            {
                mLastPositionOffsetSum = currentPositionOffsetSum;
            }

            boolean processScrolledPercentLogic = true;
            if (mScrollState != ViewPager.SCROLL_STATE_IDLE)
            {
                if (currentPositionOffsetSum == mLastPositionOffsetSum)
                {
                    processScrolledPercentLogic = false;
                }
            }
            if (processScrolledPercentLogic)
            {
                final boolean isMoveLeft = currentPositionOffsetSum >= mLastPositionOffsetSum;

                int leavePosition = 0;
                int enterPosition = 0;
                if (isMoveLeft)
                {
                    //手指向左
                    leavePosition = position;
                    enterPosition = position + 1;

                    if (positionOffset == 0)
                    {
                        leavePosition--;
                        enterPosition--;
                        positionOffset = 1.0f;
                    }
                } else
                {
                    //手指向右
                    leavePosition = position + 1;
                    enterPosition = position;
                }

                if (mScrollState != ViewPager.SCROLL_STATE_IDLE)
                {
                    final int pageCount = getPageCount();
                    for (int i = 0; i < pageCount; i++)
                    {
                        if (i == leavePosition || i == enterPosition)
                        {
                            continue;
                        }
                        Float showPercent = mArrShowPercent.get(i, -1f);
                        if (showPercent != 0f)
                        {
                            notifyShowPercent(i, 0, false, isMoveLeft);
                        }
                    }
                }

                if (isMoveLeft)
                {
                    notifyShowPercent(leavePosition, 1 - positionOffset, false, isMoveLeft);
                    notifyShowPercent(enterPosition, positionOffset, true, isMoveLeft);
                } else
                {
                    notifyShowPercent(leavePosition, positionOffset, false, isMoveLeft);
                    notifyShowPercent(enterPosition, 1 - positionOffset, true, isMoveLeft);
                }

                mLastPositionOffsetSum = currentPositionOffsetSum;
            }

            if (mOnPageChangeListener != null)
            {
                mOnPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        }

        @Override
        public void onPageSelected(int position)
        {
            setSelected(position);

            if (mOnPageChangeListener != null)
            {
                mOnPageChangeListener.onPageSelected(position);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state)
        {
            mScrollState = state;

            if (mOnPageChangeListener != null)
            {
                mOnPageChangeListener.onPageScrollStateChanged(state);
            }
        }
    }

    private ViewPager.OnAdapterChangeListener mInternalOnAdapterChangeListener = new ViewPager.OnAdapterChangeListener()
    {
        @Override
        public void onAdapterChanged(ViewPager viewPager, PagerAdapter oldAdapter, PagerAdapter newAdapter)
        {
            mInternalDataSetObserver.register(newAdapter);

            if (mOnAdapterChangeListener != null)
            {
                mOnAdapterChangeListener.onAdapterChanged(viewPager, oldAdapter, newAdapter);
            }

            mInternalOnPageChangeListener.setSelected(getViewPager().getCurrentItem());
        }
    };

    private class PagerAdapterDataSetObserver extends DataSetObserver
    {
        private WeakReference<PagerAdapter> mAdapter;

        private PagerAdapter getAdapter()
        {
            if (mAdapter != null)
            {
                return mAdapter.get();
            } else
            {
                return null;
            }
        }

        /**
         * 把当前对象注册到Adapter
         *
         * @param adapter
         */
        public void register(PagerAdapter adapter)
        {
            final PagerAdapter oldAdapter = getAdapter();
            if (oldAdapter != adapter)
            {
                if (oldAdapter != null)
                {
                    // 如果旧对象存在先取消注册
                    oldAdapter.unregisterDataSetObserver(this);
                }

                if (adapter != null)
                {
                    mAdapter = new WeakReference<>(adapter);

                    adapter.registerDataSetObserver(this);
                    setPageCount(adapter.getCount());
                } else
                {
                    mAdapter = null;
                }
            }

            if (adapter == null)
            {
                setPageCount(0);
            }
        }

        /**
         * 取消当前对象注册到Adapter
         */
        public void unregister()
        {
            final PagerAdapter oldAdapter = getAdapter();
            if (oldAdapter != null)
            {
                oldAdapter.unregisterDataSetObserver(this);
                mAdapter = null;
            }
        }

        @Override
        public void onChanged()
        {
            super.onChanged();
            setPageCount(getAdapter().getCount());

            if (mDataSetObserver != null)
            {
                mDataSetObserver.onChanged();
            }
        }

        @Override
        public void onInvalidated()
        {
            super.onInvalidated();

            if (mDataSetObserver != null)
            {
                mDataSetObserver.onInvalidated();
            }
        }
    }

    public interface OnPageCountChangeCallback
    {
        /**
         * 页数发生改变回调
         *
         * @param count
         */
        void onPageCountChanged(int count);
    }

    public interface OnPageScrolledPercentChangeCallback
    {
        /**
         * ViewPager页面显示的百分比回调
         *
         * @param position    第几页
         * @param showPercent 显示的百分比[0-1]
         * @param isEnter     true-当前页面处于进入状态，false-当前页面处于离开状态
         * @param isMoveLeft  true-ViewPager内容向左移动，false-ViewPager内容向右移动
         */
        void onShowPercent(int position, float showPercent, boolean isEnter, boolean isMoveLeft);
    }

    public interface OnPageSelectedChangeCallback
    {
        /**
         * ViewPager某一页选中或者非选中回调
         *
         * @param position 第几页
         * @param selected true-选中，false-未选中
         */
        void onSelectedChanged(int position, boolean selected);
    }
}
