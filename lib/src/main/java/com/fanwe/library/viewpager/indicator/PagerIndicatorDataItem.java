package com.fanwe.library.viewpager.indicator;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by Administrator on 2017/8/26.
 */
public abstract class PagerIndicatorDataItem<T> extends PagerIndicatorItem
{
    public PagerIndicatorDataItem(Context context)
    {
        super(context);
    }

    public PagerIndicatorDataItem(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public PagerIndicatorDataItem(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    private T mData;

    public void setData(T data)
    {
        mData = data;
    }

    public T getData()
    {
        return mData;
    }
}
