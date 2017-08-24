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
import com.fanwe.library.viewpager.helper.SDViewPagerInfoListener;

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
    private SDViewPagerInfoListener mViewPagerInfoListener = new SDViewPagerInfoListener();

    private IndicatorConfig mIndicatorConfig;

    /**
     * 设置只有一页的时候是否显示指示器
     */
    private boolean mShowIndicatorWhenOnlyOnePage = false;

    private void init()
    {
        LayoutInflater.from(getContext()).inflate(R.layout.lib_vpg_view_simple_indicator_viewpager, this, true);
        mViewPager = (SDGridViewPager) findViewById(R.id.lib_vpg_viewpager);
        mIndicatorView = (SDGridLayout) findViewById(R.id.lib_vpg_indicator);

        mViewPagerInfoListener.listen(mViewPager);
        initViewPager();
        initViewPagerIndicator();
    }

    private void initViewPager()
    {
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

        mViewPagerInfoListener.setOnPageCountChangeCallback(new SDViewPagerInfoListener.OnPageCountChangeCallback()
        {
            @Override
            public void onPageCountChanged(int count)
            {
                getIndicatorAdapter().notifyDataSetChanged();
            }
        });
    }

    private void initViewPagerIndicator()
    {
        mIndicatorView.setOrientation(SDGridLayout.HORIZONTAL);
        mIndicatorView.setAdapter(getIndicatorAdapter());
    }

    /**
     * 设置只有一张广告图的时候是否显示指示器（默认不展示）
     *
     * @param showIndicatorWhenOnlyOnePage
     */
    public void setShowIndicatorWhenOnlyOnePage(boolean showIndicatorWhenOnlyOnePage)
    {
        mShowIndicatorWhenOnlyOnePage = showIndicatorWhenOnlyOnePage;
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
            int count = mViewPagerInfoListener.getPageCount();
            if (count <= 1)
            {
                if (!mShowIndicatorWhenOnlyOnePage)
                {
                    count = 0;
                }
            }
            return count;
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

            int width = 0;
            int height = 0;
            if (position == mViewPager.getCurrentItem())
            {
                imageView.setImageResource(getIndicatorConfig().imageResIdSelected);
                width = getIndicatorConfig().widthSelected;
                height = getIndicatorConfig().heightSelected;
            } else
            {
                imageView.setImageResource(getIndicatorConfig().imageResIdNormal);
                width = getIndicatorConfig().widthNormal;
                height = getIndicatorConfig().heightNormal;
            }

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

    public class IndicatorConfig
    {
        public int imageResIdNormal;
        public int imageResIdSelected;

        public int widthNormal;
        public int heightNormal;

        public int widthSelected;
        public int heightSelected;

        public int margin;

        public IndicatorConfig()
        {
            this.imageResIdNormal = R.drawable.lib_vpg_ic_indicator_normal;
            this.imageResIdSelected = R.drawable.lib_vpg_ic_indicator_selected;

            this.widthNormal = getResources().getDimensionPixelSize(R.dimen.lib_vpg_indicator_width_normal);
            this.heightNormal = getResources().getDimensionPixelSize(R.dimen.lib_vpg_indicator_height_normal);

            this.widthSelected = getResources().getDimensionPixelSize(R.dimen.lib_vpg_indicator_width_selected);
            this.heightSelected = getResources().getDimensionPixelSize(R.dimen.lib_vpg_indicator_height_selected);

            this.margin = getResources().getDimensionPixelSize(R.dimen.lib_vpg_indicator_margin);
        }
    }
}
