package com.fanwe.lib.viewpager.pullcondition;

import android.view.MotionEvent;

import com.fanwe.lib.viewpager.FViewPager;

public class LockPullCondition implements FViewPager.PullCondition
{
    @Override
    public boolean canPull(MotionEvent event, FViewPager viewPager)
    {
        return false;
    }
}
