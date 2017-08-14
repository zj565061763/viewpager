package com.fanwe.library.viewpager.pullcondition;

import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

import com.fanwe.library.viewpager.SDViewPager;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2017/7/27.
 */
public class SimpleIgnorePullCondition implements SDViewPager.IPullCondition
{
    private WeakReference<View> mView;
    private Rect mRect;

    public SimpleIgnorePullCondition(View view)
    {
        mView = new WeakReference<>(view);
    }

    public View getView()
    {
        if (mView != null)
        {
            return mView.get();
        } else
        {
            return null;
        }
    }

    @Override
    public boolean canPull(MotionEvent event)
    {
        final View view = getView();
        if (view != null)
        {
            if (mRect == null)
            {
                mRect = new Rect();
            }
            view.getGlobalVisibleRect(mRect);
            if (mRect.contains((int) event.getRawX(), (int) event.getRawY()))
            {
                return false;
            }
        }
        return true;
    }
}
