package com.sd.lib.viewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class FViewPager extends ViewPager
{
    public FViewPager(Context context)
    {
        super(context);
    }

    public FViewPager(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    private List<PullCondition> mListCondition;

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
     * 是否包含某个拖动条件
     *
     * @param condition
     * @return
     */
    public boolean containsPullCondition(PullCondition condition)
    {
        if (condition == null || mListCondition == null)
            return false;

        return mListCondition.contains(condition);
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
        if (!canPull(ev))
            return false;

        try
        {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException ex)
        {
            ex.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        boolean result = false;

        try
        {
            result = super.onTouchEvent(event);
        } catch (IllegalArgumentException ex)
        {
            ex.printStackTrace();
        }

        if (!canPull(event))
            return false;

        return result;
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
