package com.fanwe.library.viewpager;

import android.database.DataSetObserver;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import java.lang.ref.WeakReference;

/**
 * 监听ViewPager的一些重要数据，比如总页数变化和数据集发生变化
 */
public class SDViewPagerInfoListener
{
    private WeakReference<ViewPager> mViewPager;
    private PagerAdapterDataSetObserver mInternalDataSetObserver = new PagerAdapterDataSetObserver();
    private int mPageCount;

    private OnPageCountChangeCallback mOnPageCountChangeCallback;
    private DataSetObserver mDataSetObserver;
    private ViewPager.OnPageChangeListener mOnPageChangeListener;

    /**
     * 设置监听回调
     *
     * @param onPageCountChangeCallback
     */
    public void setOnPageCountChangeCallback(OnPageCountChangeCallback onPageCountChangeCallback)
    {
        mOnPageCountChangeCallback = onPageCountChangeCallback;
    }

    /**
     * 设置数据发生变化回调
     *
     * @param dataSetObserver
     */
    public void setDataSetObserver(DataSetObserver dataSetObserver)
    {
        mDataSetObserver = dataSetObserver;
    }

    /**
     * 设置页面变化回调
     *
     * @param onPageChangeListener
     */
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener)
    {
        mOnPageChangeListener = onPageChangeListener;
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
            final int oldCount = mPageCount;
            mPageCount = pageCount;

            if (mOnPageCountChangeCallback != null)
            {
                mOnPageCountChangeCallback.onPageCountChanged(oldCount, pageCount, getViewPager());
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

            if (viewPager != null)
            {
                mViewPager = new WeakReference<>(viewPager);

                viewPager.addOnPageChangeListener(mInternalOnPageChangeListener);
                viewPager.addOnAdapterChangeListener(mInternalOnAdapterChangeListener);
                mInternalDataSetObserver.register(viewPager.getAdapter());
            } else
            {
                mViewPager = null;

                setPageCount(0);
            }
        }
    }

    private ViewPager.OnPageChangeListener mInternalOnPageChangeListener = new ViewPager.OnPageChangeListener()
    {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
        {
            if (mOnPageChangeListener != null)
            {
                mOnPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        }

        @Override
        public void onPageSelected(int position)
        {
            if (mOnPageChangeListener != null)
            {
                mOnPageChangeListener.onPageSelected(position);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state)
        {
            if (mOnPageChangeListener != null)
            {
                mOnPageChangeListener.onPageScrollStateChanged(state);
            }
        }
    };

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
         * @param oldCount
         * @param newCount
         * @param viewPager
         */
        void onPageCountChanged(int oldCount, int newCount, ViewPager viewPager);
    }
}
