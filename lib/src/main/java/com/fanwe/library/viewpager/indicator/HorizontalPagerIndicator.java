package com.fanwe.library.viewpager.indicator;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;

import com.fanwe.library.gridlayout.SDGridLayout;
import com.fanwe.library.viewpager.R;

/**
 * Created by Administrator on 2017/8/23.
 */

public class HorizontalPagerIndicator extends PagerIndicatorGroupView
{
    public HorizontalPagerIndicator(Context context)
    {
        super(context);
        init();
    }

    public HorizontalPagerIndicator(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public HorizontalPagerIndicator(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    private HorizontalScrollView mHorizontalScrollView;
    private SDGridLayout mViewItems;

    private void init()
    {
        LayoutInflater.from(getContext()).inflate(R.layout.lib_vpg_horizontal_pager_indicator, this, true);
        mHorizontalScrollView = (HorizontalScrollView) findViewById(R.id.view_scroll);
        mViewItems = (SDGridLayout) findViewById(R.id.view_container_items);
        mViewItems.setOrientation(SDGridLayout.HORIZONTAL);
    }

    @Override
    public void onPageCountChanged(int count)
    {
        final int childCount = mViewItems.getChildCount();
        if (count > childCount)
        {
            for (int i = 0; i < count - childCount; i++)
            {
                IPagerIndicatorItemView itemView = getAdapter().onCreateView();
                if (!(itemView instanceof View))
                {
                    throw new IllegalArgumentException("onCreateView() must return instance of view");
                }
                final View view = (View) itemView;
                if (!view.hasOnClickListeners())
                {
                    view.setOnClickListener(new OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            ViewPager viewPager = getViewPager();
                            if (viewPager != null)
                            {
                                viewPager.setCurrentItem(mViewItems.indexOfChild(view));
                            }
                        }
                    });
                }
                mViewItems.addView(view);
            }
        } else if (count < childCount)
        {
            for (int i = childCount - 1; i >= count; i--)
            {
                mViewItems.removeViewAt(i);
            }
        }
    }

    @Override
    public void onSelectedChanged(int position, boolean selected)
    {
        super.onSelectedChanged(position, selected);

        if (selected)
        {
            IPagerIndicatorItemView itemView = getItemView(position);
            if (itemView != null)
            {
                postScrollRunnable((View) itemView);
            }
        }
    }

    @Override
    public IPagerIndicatorItemView getItemView(int position)
    {
        if (position < 0)
        {
            return null;
        }
        final int childCount = mViewItems.getChildCount();
        if (position >= childCount)
        {
            return null;
        }

        return (IPagerIndicatorItemView) mViewItems.getChildAt(position);
    }

    private Runnable mScrollRunnable;

    private void postScrollRunnable(final View child)
    {
        removeScrollRunnable();
        mScrollRunnable = new Runnable()
        {
            public void run()
            {
                int scrollX = child.getLeft() - (getWidth() - child.getWidth()) / 2;
                mHorizontalScrollView.smoothScrollTo(scrollX, 0);
                mScrollRunnable = null;
            }
        };
        postScrollRunnable();
    }

    private void removeScrollRunnable()
    {
        if (mScrollRunnable != null)
        {
            removeCallbacks(mScrollRunnable);
            mScrollRunnable = null;
        }
    }

    private void postScrollRunnable()
    {
        if (mScrollRunnable != null)
        {
            post(mScrollRunnable);
        }
    }

    @Override
    public void onAttachedToWindow()
    {
        super.onAttachedToWindow();
        postScrollRunnable();
    }

    @Override
    public void onDetachedFromWindow()
    {
        super.onDetachedFromWindow();
        removeScrollRunnable();
    }
}
