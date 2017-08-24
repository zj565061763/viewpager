package com.fanwe.library.viewpager.helper;

import android.database.DataSetObserver;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * 监听ViewPager的一些重要数据，比如总页数变化和数据集发生变化
 */
public class SDViewPagerInfoListener
{
    private WeakReference<ViewPager> mViewPager;
    private PagerAdapterDataSetObserver mInternalDataSetObserver = new PagerAdapterDataSetObserver();
    private InternalOnPageChangeListener mInternalOnPageChangeListener = new InternalOnPageChangeListener();
    private int mPageCount;

    private ArrayList<OnPageCountChangeCallback> mListOnPageCountChangeCallback;
    private ArrayList<OnScrolledPercentChangeCallback> mListOnScrolledPercentChangeCallback;
    private ArrayList<OnSelectedChangeCallback> mListOnSelectedChangeCallback;


    private ArrayList<DataSetObserver> mListDataSetObserver;
    private ArrayList<ViewPager.OnPageChangeListener> mListOnPageChangeListener;

    /**
     * 添加页数变化回调
     *
     * @param onPageCountChangeCallback
     */
    public void addOnPageCountChangeCallback(OnPageCountChangeCallback onPageCountChangeCallback)
    {
        if (onPageCountChangeCallback == null)
        {
            return;
        }
        if (mListOnPageCountChangeCallback == null)
        {
            mListOnPageCountChangeCallback = new ArrayList<>();
        }
        if (!mListOnPageCountChangeCallback.contains(onPageCountChangeCallback))
        {
            mListOnPageCountChangeCallback.add(onPageCountChangeCallback);
        }
    }

    /**
     * 移除页数变化回调
     *
     * @param onPageCountChangeCallback
     */
    public void removeOnPageCountChangeCallback(OnPageCountChangeCallback onPageCountChangeCallback)
    {
        if (mListOnPageCountChangeCallback != null)
        {
            mListOnPageCountChangeCallback.remove(onPageCountChangeCallback);
        }
    }

    public void addOnSelectedChangeCallback(OnSelectedChangeCallback onSelectedChangeCallback)
    {
        if (onSelectedChangeCallback == null)
        {
            return;
        }
        if (mListOnSelectedChangeCallback == null)
        {
            mListOnSelectedChangeCallback = new ArrayList<>();
        }
        if (!mListOnSelectedChangeCallback.contains(onSelectedChangeCallback))
        {
            mListOnSelectedChangeCallback.add(onSelectedChangeCallback);
        }
    }

    public void removeOnSelectedChangeCallback(OnSelectedChangeCallback onSelectedChangeCallback)
    {
        if (mListOnSelectedChangeCallback != null)
        {
            mListOnSelectedChangeCallback.remove(onSelectedChangeCallback);
        }
    }

    /**
     * 添加滚动百分比回调
     *
     * @param onScrolledPercentChangeCallback
     */
    public void addOnScrolledPercentChangeCallback(OnScrolledPercentChangeCallback onScrolledPercentChangeCallback)
    {
        if (onScrolledPercentChangeCallback == null)
        {
            return;
        }
        if (mListOnScrolledPercentChangeCallback == null)
        {
            mListOnScrolledPercentChangeCallback = new ArrayList<>();
        }
        if (!mListOnScrolledPercentChangeCallback.contains(onScrolledPercentChangeCallback))
        {
            mListOnScrolledPercentChangeCallback.add(onScrolledPercentChangeCallback);
        }
    }

    /**
     * 移除滚动百分比回调
     *
     * @param onScrolledPercentChangeCallback
     */
    public void removeOnScrolledPercentChangeCallback(OnScrolledPercentChangeCallback onScrolledPercentChangeCallback)
    {
        if (mListOnScrolledPercentChangeCallback != null)
        {
            mListOnScrolledPercentChangeCallback.remove(onScrolledPercentChangeCallback);
        }
    }

    /**
     * 添加数据发生变化回调
     *
     * @param dataSetObserver
     */
    public void addDataSetObserver(DataSetObserver dataSetObserver)
    {
        if (dataSetObserver == null)
        {
            return;
        }
        if (mListDataSetObserver == null)
        {
            mListDataSetObserver = new ArrayList<>();
        }
        if (!mListDataSetObserver.contains(dataSetObserver))
        {
            mListDataSetObserver.add(dataSetObserver);
        }
    }

    /**
     * 移除数据发生变化回调
     *
     * @param dataSetObserver
     */
    public void removeDataSetObserver(DataSetObserver dataSetObserver)
    {
        if (mListDataSetObserver != null)
        {
            mListDataSetObserver.remove(dataSetObserver);
        }
    }

    /**
     * 添加页面变化回调
     *
     * @param onPageChangeListener
     */
    public void addOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener)
    {
        if (onPageChangeListener == null)
        {
            return;
        }
        if (mListOnPageChangeListener == null)
        {
            mListOnPageChangeListener = new ArrayList<>();
        }
        if (!mListOnPageChangeListener.contains(onPageChangeListener))
        {
            mListOnPageChangeListener.add(onPageChangeListener);
        }
    }

    /**
     * 移除页面变化回调
     *
     * @param onPageChangeListener
     */
    public void removeOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener)
    {
        if (mListOnPageChangeListener != null)
        {
            mListOnPageChangeListener.remove(onPageChangeListener);
        }
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

            initLeavedPercent();

            if (mListOnPageCountChangeCallback != null)
            {
                for (OnPageCountChangeCallback item : mListOnPageCountChangeCallback)
                {
                    item.onPageCountChanged(pageCount);
                }
            }
        }
    }

    /**
     * 设置要监听的ViewPager
     *
     * @param viewPager
     */
    public void listen(ViewPager viewPager)
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

    private SparseArray<Float> mArrLeavedPercent = new SparseArray<>();

    private void initLeavedPercent()
    {
        mArrLeavedPercent.clear();
        final int pageCount = getPageCount();
        if (pageCount <= 0)
        {
            return;
        }
        for (int i = 0; i < pageCount; i++)
        {
            mArrLeavedPercent.put(i, 1.0f);
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
            if (mListOnSelectedChangeCallback != null)
            {
                for (OnSelectedChangeCallback item : mListOnSelectedChangeCallback)
                {
                    item.onSelectedChanged(position, selected);
                }
            }
        }

        private void notifyLeave(int position, float leavePercent, boolean leftToRight)
        {
            if (position < 0 || position >= getPageCount())
            {
                return;
            }

            if (mListOnScrolledPercentChangeCallback != null)
            {
                for (OnScrolledPercentChangeCallback item : mListOnScrolledPercentChangeCallback)
                {
                    item.onLeave(position, leavePercent, leftToRight);
                }
            }
            mArrLeavedPercent.put(position, leavePercent);
        }

        private void notifyEnter(int position, float enterPercent, boolean leftToRight)
        {
            if (position < 0 || position >= getPageCount())
            {
                return;
            }

            if (mListOnScrolledPercentChangeCallback != null)
            {
                for (OnScrolledPercentChangeCallback item : mListOnScrolledPercentChangeCallback)
                {
                    item.onEnter(position, enterPercent, leftToRight);
                }
            }
            mArrLeavedPercent.put(position, 1 - enterPercent);
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
                final boolean leftToRight = currentPositionOffsetSum >= mLastPositionOffsetSum;

                int leavePosition = 0;
                int enterPosition = 0;
                if (leftToRight)
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
                        Float leavedPercent = mArrLeavedPercent.get(i, 0.0f);
                        if (leavedPercent != 1.0f)
                        {
                            notifyLeave(i, 1.0f, leftToRight);
                        }
                    }
                }

                if (leftToRight)
                {
                    notifyLeave(leavePosition, positionOffset, leftToRight);
                    notifyEnter(enterPosition, positionOffset, leftToRight);
                } else
                {
                    notifyLeave(leavePosition, 1.0f - positionOffset, leftToRight);
                    notifyEnter(enterPosition, 1.0f - positionOffset, leftToRight);
                }

                mLastPositionOffsetSum = currentPositionOffsetSum;
            }

            if (mListOnPageChangeListener != null)
            {
                for (ViewPager.OnPageChangeListener item : mListOnPageChangeListener)
                {
                    item.onPageScrolled(position, positionOffset, positionOffsetPixels);
                }
            }
        }

        @Override
        public void onPageSelected(int position)
        {
            setSelected(position);

            if (mListOnPageChangeListener != null)
            {
                for (ViewPager.OnPageChangeListener item : mListOnPageChangeListener)
                {
                    item.onPageSelected(position);
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int state)
        {
            mScrollState = state;

            if (mListOnPageChangeListener != null)
            {
                for (ViewPager.OnPageChangeListener item : mListOnPageChangeListener)
                {
                    item.onPageScrollStateChanged(state);
                }
            }
        }
    }

    private ViewPager.OnAdapterChangeListener mInternalOnAdapterChangeListener = new ViewPager.OnAdapterChangeListener()
    {
        @Override
        public void onAdapterChanged(ViewPager viewPager, PagerAdapter oldAdapter, PagerAdapter newAdapter)
        {
            mInternalDataSetObserver.register(newAdapter);
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

                    mInternalOnPageChangeListener.setSelected(getViewPager().getCurrentItem());
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

            if (mListDataSetObserver != null)
            {
                for (DataSetObserver item : mListDataSetObserver)
                {
                    item.onChanged();
                }
            }
        }

        @Override
        public void onInvalidated()
        {
            super.onInvalidated();

            if (mListDataSetObserver != null)
            {
                for (DataSetObserver item : mListDataSetObserver)
                {
                    item.onInvalidated();
                }
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

    public interface OnScrolledPercentChangeCallback
    {
        void onEnter(int position, float enterPercent, boolean leftToRight);

        void onLeave(int position, float leavePercent, boolean leftToRight);
    }

    public interface OnSelectedChangeCallback
    {
        void onSelectedChanged(int position, boolean selected);
    }
}
