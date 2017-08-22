package com.fanwe.library.viewpager;

import android.content.Context;
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
        init();
    }

    public SDViewPager(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    /**
     * 是否锁住ViewPager，锁住后不能拖动
     */
    private boolean mIsLockPull = false;
    private List<IPullCondition> mListCondition = new ArrayList<>();
    private SDViewPagerInfoListener mViewPagerInfoListener = new SDViewPagerInfoListener();

    private void init()
    {
        mViewPagerInfoListener.listen(this);
    }

    /**
     * 返回ViewPager信息监听对象
     *
     * @return
     */
    public SDViewPagerInfoListener getViewPagerInfoListener()
    {
        return mViewPagerInfoListener;
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
    public void addPullCondition(IPullCondition condition)
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
    public void removePullCondition(IPullCondition condition)
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
        for (IPullCondition item : mListCondition)
        {
            if (!item.canPull(event))
            {
                canPull = false;
                break;
            }
        }
        return canPull;
    }

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

    public interface IPullCondition
    {
        /**
         * 返回是否可以触发拖动
         *
         * @param event
         * @return
         */
        boolean canPull(MotionEvent event);
    }
}
