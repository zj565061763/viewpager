package com.sd.lib.viewpager.utils;

import android.os.Build;
import android.view.View;
import android.view.ViewParent;

import androidx.viewpager.widget.ViewPager;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.WeakHashMap;

public abstract class FViewPagerChildListener
{
    private final Map<View, String> mMapParent = new WeakHashMap<>();

    private final View mView;
    private WeakReference<ViewPager> mViewPager;

    private boolean mSelected;

    public FViewPagerChildListener(View view)
    {
        if (view == null)
            throw new NullPointerException("view is null");
        mView = view;
    }

    public final View getView()
    {
        return mView;
    }

    public final boolean start()
    {
        final View view = getView();
        if (!isAttached(view))
            return false;

        final ViewPager viewPager = findViewPager(view);
        setViewPager(viewPager);
        isSelected();

        return viewPager != null;
    }

    public final void stop()
    {
        setViewPager(null);
    }

    private ViewPager getViewPager()
    {
        return mViewPager == null ? null : mViewPager.get();
    }

    private void setViewPager(ViewPager viewPager)
    {
        final ViewPager old = getViewPager();
        if (old != viewPager)
        {
            if (old != null)
                old.removeOnPageChangeListener(mOnPageChangeListener);

            mViewPager = viewPager == null ? null : new WeakReference<>(viewPager);

            if (viewPager != null)
            {
                viewPager.addOnPageChangeListener(mOnPageChangeListener);
            } else
            {
                mMapParent.clear();
                setSelected(false);
            }
        }
    }

    private final ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener()
    {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
        {
        }

        @Override
        public void onPageSelected(int position)
        {
            isSelected();
        }

        @Override
        public void onPageScrollStateChanged(int state)
        {
        }
    };

    public boolean isSelected()
    {
        final View child = getSelectedChild();
        if (child == null)
            return false;

        final boolean isSelected = mMapParent.containsKey(child);
        setSelected(isSelected);
        return isSelected;
    }

    private View getSelectedChild()
    {
        final ViewPager viewPager = getViewPager();
        if (viewPager == null)
            return null;

        final int count = viewPager.getChildCount();
        if (count <= 0)
            return null;

        final int currentItem = viewPager.getCurrentItem();
        for (int i = 0; i < count; i++)
        {
            final View child = viewPager.getChildAt(i);
            final ViewPager.LayoutParams lp = (ViewPager.LayoutParams) child.getLayoutParams();
            try
            {
                final Field field = lp.getClass().getDeclaredField("position");
                field.setAccessible(true);
                final int position = field.getInt(lp);
                if (position == currentItem)
                    return child;
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return null;
    }

    private ViewPager findViewPager(View view)
    {
        ViewPager viewPager = null;

        mMapParent.clear();
        mMapParent.put(view, "");

        ViewParent parent = view.getParent();
        while (true)
        {
            if (parent == null)
                break;

            if (!(parent instanceof View))
                break;

            if (parent instanceof ViewPager)
            {
                viewPager = (ViewPager) parent;
                break;
            }

            mMapParent.put((View) parent, "");
            parent = parent.getParent();
        }

        if (viewPager == null)
            mMapParent.clear();

        return viewPager;
    }

    private void setSelected(boolean selected)
    {
        if (mSelected != selected)
        {
            mSelected = selected;
            onPageSelectChanged(selected);
        }
    }

    protected abstract void onPageSelectChanged(boolean selected);

    private static boolean isAttached(View view)
    {
        if (view == null)
            return false;

        if (Build.VERSION.SDK_INT >= 19)
            return view.isAttachedToWindow();
        else
            return view.getWindowToken() != null;
    }
}
