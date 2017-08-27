package com.fanwe.library.viewpager.indicator.model;

public class PositionData
{
    public int left;
    public int top;
    public int right;
    public int bottom;

    public int getWidth()
    {
        return right - left;
    }

    public int getHeight()
    {
        return bottom - top;
    }

    public int getLeftPercent(float percent)
    {
        return (int) (left + getWidth() * percent);
    }
}
