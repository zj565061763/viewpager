package com.fanwe.library.viewpager.indicator;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.fanwe.library.viewpager.R;
import com.fanwe.library.viewpager.helper.SDViewPagerInfoListener;
import com.fanwe.library.viewpager.indicator.adapter.PagerIndicatorAdapter;
import com.fanwe.library.viewpager.indicator.view.TrackHorizontalScrollView;

/**
 * Created by Administrator on 2017/8/24.
 */
public class PagerIndicator extends FrameLayout
{
    public PagerIndicator(Context context)
    {
        super(context);
        init();
    }

    public PagerIndicator(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public PagerIndicator(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    private TrackHorizontalScrollView mHorizontalScrollView;

    private PagerIndicatorGroup mPagerIndicatorGroup;
    private ViewGroup mPagerIndicatorTrackContainer;

    private SDViewPagerInfoListener mViewPagerInfoListener = new SDViewPagerInfoListener();

    private void init()
    {
        LayoutInflater.from(getContext()).inflate(R.layout.lib_vpg_pager_indicator, this, true);
        mHorizontalScrollView = (TrackHorizontalScrollView) findViewById(R.id.view_scroll);
        mPagerIndicatorGroup = (PagerIndicatorGroup) findViewById(R.id.view_indicator_group);
        mPagerIndicatorTrackContainer = (ViewGroup) findViewById(R.id.view_indicator_track_container);

        mViewPagerInfoListener.setOnPageSelectedChangeCallback(mInternalOnPageSelectedChangeCallback);
    }

    public void setDebug(boolean debug)
    {
        mPagerIndicatorGroup.setDebug(debug);
    }

    /**
     * 设置ViewPager
     *
     * @param viewPager
     */
    public void setViewPager(ViewPager viewPager)
    {
        mPagerIndicatorGroup.setViewPager(viewPager);
        mViewPagerInfoListener.setViewPager(viewPager);
    }

    /**
     * 设置适配器
     *
     * @param adapter
     */
    public void setAdapter(PagerIndicatorAdapter adapter)
    {
        mPagerIndicatorGroup.setAdapter(adapter);
    }

    /**
     * 返回设置的适配器
     *
     * @return
     */
    public PagerIndicatorAdapter getAdapter()
    {
        return mPagerIndicatorGroup.getAdapter();
    }

    /**
     * 返回position位置对应的Item
     *
     * @param position
     * @return
     */
    public IPagerIndicatorItem getItemView(int position)
    {
        return mPagerIndicatorGroup.getPagerIndicatorItem(position);
    }

    /**
     * 设置当DataSetObserver数据变化的时候是否全部重新创建view，默认true
     *
     * @param fullCreateMode
     */
    public void setFullCreateMode(boolean fullCreateMode)
    {
        mPagerIndicatorGroup.setFullCreateMode(fullCreateMode);
    }

    /**
     * 设置可追踪指示器Item的view
     *
     * @param pagerIndicatorTrack
     */
    public void setPagerIndicatorTrack(IPagerIndicatorTrack pagerIndicatorTrack)
    {
        final IPagerIndicatorTrack oldTrack = mPagerIndicatorGroup.getPagerIndicatorTrack();
        if (oldTrack != pagerIndicatorTrack)
        {
            if (oldTrack != null)
            {
                mPagerIndicatorTrackContainer.removeAllViews();
            }
            mPagerIndicatorGroup.setPagerIndicatorTrack(pagerIndicatorTrack);
            if (pagerIndicatorTrack != null)
            {
                if (pagerIndicatorTrack instanceof View)
                {
                    mPagerIndicatorTrackContainer.addView((View) pagerIndicatorTrack);
                } else
                {
                    throw new IllegalArgumentException("pagerIndicatorView must be instance of view");
                }
            }
        }
    }

    private SDViewPagerInfoListener.OnPageSelectedChangeCallback mInternalOnPageSelectedChangeCallback = new SDViewPagerInfoListener.OnPageSelectedChangeCallback()
    {
        @Override
        public void onSelectedChanged(int position, boolean selected)
        {
            if (selected)
            {
                IPagerIndicatorItem item = mPagerIndicatorGroup.getPagerIndicatorItem(position);
                if (item != null)
                {
                    mHorizontalScrollView.smoothScrollTo((View) item);
                }
            }
        }
    };

}
