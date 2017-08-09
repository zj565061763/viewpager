package com.fanwe.library.viewpager;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/9.
 */
public class SDViewPager extends ViewPager
{
    public SDViewPager(Context context)
    {
        super(context);
    }

    public SDViewPager(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    /**
     * 是否锁住ViewPager，锁住后不能拖动
     */
    private boolean mIsLockPull = false;
    private List<PullCondition> mListCondition = new ArrayList<>();
    private DataSetObserver mDataSetObserver;

    private int mPageCount;
    private OnPageCountChangeCallback mOnPageCountChangeCallback;

    /**
     * 设置页数发生改变回调
     *
     * @param onPageCountChangeCallback
     */
    public void setOnPageCountChangeCallback(OnPageCountChangeCallback onPageCountChangeCallback)
    {
        mOnPageCountChangeCallback = onPageCountChangeCallback;
    }

    /**
     * 设置数据改变观察者
     *
     * @param dataSetObserver
     */
    public void setDataSetObserver(DataSetObserver dataSetObserver)
    {
        mDataSetObserver = dataSetObserver;
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
        {
            return;
        }
        if (!mListCondition.contains(condition))
        {
            mListCondition.add(condition);
        }
    }

    /**
     * 移除拖动条件
     *
     * @param condition
     */
    public void removePullCondition(PullCondition condition)
    {
        if (condition == null)
        {
            return;
        }
        if (mListCondition.contains(condition))
        {
            mListCondition.remove(condition);
        }
    }

    private boolean validatePullCondition(MotionEvent event)
    {
        boolean canPull = true;
        for (PullCondition item : mListCondition)
        {
            if (!item.canPull(event))
            {
                canPull = false;
                break;
            }
        }
        return canPull;
    }

    /**
     * 返回一共有几页
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
                mOnPageCountChangeCallback.onPageCountChanged(oldCount, pageCount);
            }
        }
    }

    @Override
    public void setAdapter(PagerAdapter adapter)
    {
        if (getAdapter() != null)
        {
            getAdapter().unregisterDataSetObserver(mInternalDataSetObserver);
        }
        super.setAdapter(adapter);
        if (adapter != null)
        {
            adapter.registerDataSetObserver(mInternalDataSetObserver);

            setPageCount(adapter.getCount());
        } else
        {
            setPageCount(0);
        }
    }

    private DataSetObserver mInternalDataSetObserver = new DataSetObserver()
    {
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
    };

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev)
    {
        if (!mIsLockPull)
        {
            try
            {
                if (validatePullCondition(ev))
                {
                    return super.onInterceptTouchEvent(ev);
                } else
                {
                    return false;
                }
            } catch (IllegalArgumentException e)
            {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if (!mIsLockPull)
        {
            try
            {
                if (validatePullCondition(event))
                {
                    return super.onTouchEvent(event);
                } else
                {
                    return false;
                }
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return false;
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
                View child = getChildAt(i);

                child.measure(widthMeasureSpec, childHeightMeasureSpec);
                final int height = child.getMeasuredHeight();
                if (height > maxHeight)
                {
                    maxHeight = height;
                }
            }

            if (heightMode == MeasureSpec.AT_MOST)
            {
                maxHeight = Math.min(maxHeight, MeasureSpec.getSize(heightMeasureSpec));
            }

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
         * @return
         */
        boolean canPull(MotionEvent event);
    }

    public interface OnPageCountChangeCallback
    {
        /**
         * 页数发生改变回调
         *
         * @param oldCount
         * @param newCount
         */
        void onPageCountChanged(int oldCount, int newCount);
    }
}
