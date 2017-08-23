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

    public int getCenterLeft()
    {
        return left + getWidth() / 2;
    }

    public int getCenterTop()
    {
        return top + getHeight() / 2;
    }
}
