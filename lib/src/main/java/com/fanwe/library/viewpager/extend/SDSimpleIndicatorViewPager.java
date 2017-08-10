package com.fanwe.library.viewpager.extend;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.fanwe.library.gridlayout.SDGridLayout;
import com.fanwe.library.viewpager.R;
import com.fanwe.library.viewpager.SDGridViewPager;
import com.fanwe.library.viewpager.SDViewPager;

/**
 * 简单的带ViewPager和指示器的控件
 */
public class SDSimpleIndicatorViewPager extends FrameLayout
{
    public SDSimpleIndicatorViewPager(@NonNull Context context)
    {
        super(context);
        init();
    }

    public SDSimpleIndicatorViewPager(@NonNull Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public SDSimpleIndicatorViewPager(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    private SDGridViewPager mViewPager;
    private SDGridLayout mIndicatorView;

    private IndicatorConfig mIndicatorConfig;

    private void init()
    {
        LayoutInflater.from(getContext()).inflate(R.layout.lib_vpg_view_simple_indicator_viewpager, this, true);
        mViewPager = (SDGridViewPager) findViewById(R.id.lib_vpg_viewpager);
        mIndicatorView = (SDGridLayout) findViewById(R.id.lib_vpg_indicator);

        initViewPager();
        initViewPagerIndicator();
    }

    private void initViewPager()
    {
        mViewPager.setOnPageCountChangeCallback(new SDViewPager.OnPageCountChangeCallback()
        {
            @Override
            public void onPageCountChanged(int oldCount, int newCount, ViewPager viewPager)
            {
                getIndicatorAdapter().notifyDataSetChanged();
            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {
            }

            @Override
            public void onPageSelected(int position)
            {
                getIndicatorAdapter().notifyDataSetChanged();
            }

            @Override
            public void onPageScrollStateChanged(int state)
            {
            }
        });
    }

    private void initViewPagerIndicator()
    {
        mIndicatorView.setOrientation(SDGridLayout.HORIZONTAL);
        mIndicatorView.setAdapter(getIndicatorAdapter());
    }

    /**
     * 返回ViewPager
     *
     * @return
     */
    public SDGridViewPager getViewPager()
    {
        return mViewPager;
    }

    /**
     * 返回指示器view
     *
     * @return
     */
    public View getIndicatorView()
    {
        return mIndicatorView;
    }

    /**
     * 返回指示器的配置
     *
     * @return
     */
    public IndicatorConfig getIndicatorConfig()
    {
        if (mIndicatorConfig == null)
        {
            mIndicatorConfig = new IndicatorConfig();
        }
        return mIndicatorConfig;
    }

    private BaseAdapter getIndicatorAdapter()
    {
        return mIndicatorAdapter;
    }

    private BaseAdapter mIndicatorAdapter = new BaseAdapter()
    {
        @Override
        public int getCount()
        {
            return mViewPager.getPageCount();
        }

        @Override
        public Object getItem(int position)
        {
            return null;
        }

        @Override
        public long getItemId(int position)
        {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            ImageView imageView = new ImageView(getContext());
            if (position == mViewPager.getCurrentItem())
            {
                imageView.setImageResource(getIndicatorConfig().imageResIdSelected);
            } else
            {
                imageView.setImageResource(getIndicatorConfig().imageResIdNormal);
            }

            final int width = getIndicatorConfig().width;
            final int height = getIndicatorConfig().height;
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(width, height);
            imageView.setLayoutParams(params);

            final int margint = getIndicatorConfig().margin;
            if (mIndicatorView.getVerticalSpacing() != margint)
            {
                mIndicatorView.setVerticalSpacing(margint);
            }

            return imageView;
        }
    };

    private class IndicatorConfig
    {
        public int imageResIdNormal;
        public int imageResIdSelected;
        public int width;
        public int height;
        public int margin;

        public IndicatorConfig()
        {
            this.imageResIdNormal = R.drawable.lib_vpg_ic_indicator_normal;
            this.imageResIdSelected = R.drawable.lib_vpg_ic_indicator_selected;
            this.width = getResources().getDimensionPixelSize(R.dimen.lib_vpg_indicator_width);
            this.height = getResources().getDimensionPixelSize(R.dimen.lib_vpg_indicator_height);
            this.margin = getResources().getDimensionPixelSize(R.dimen.lib_vpg_indicator_margin);
        }
    }
}
