package com.fanwe.www.viewpager;

import android.content.Context;
import android.widget.ImageView;

import com.fanwe.library.viewpager.indicator.IPagerIndicatorItemView;
import com.fanwe.library.viewpager.indicator.impl.PagerIndicatorItemView;

/**
 * Created by Administrator on 2017/8/23.
 */

public class ImageIndicatorItemView extends PagerIndicatorItemView implements IPagerIndicatorItemView
{
    public ImageIndicatorItemView(Context context)
    {
        super(context);
        mImageView = new ImageView(context);
        addView(mImageView);

        onSelectedChanged(false);
        onShowPercent(0, false, false);
    }

    private ImageView mImageView;

    @Override
    public void onSelectedChanged(boolean selected)
    {
        if (selected)
        {
            mImageView.getLayoutParams().width = 30;
            mImageView.getLayoutParams().height = 30;

            mImageView.setImageResource(R.drawable.ic_lib_indicator_selected);
        } else
        {
            mImageView.getLayoutParams().width = 15;
            mImageView.getLayoutParams().height = 15;

            mImageView.setImageResource(R.drawable.ic_lib_indicator_normal);
        }

        mImageView.setLayoutParams(mImageView.getLayoutParams());
    }

    @Override
    public void onShowPercent(float showPercent, boolean isEnter, boolean leftToRight)
    {
        super.onShowPercent(showPercent, isEnter, leftToRight);

        float alpha = showPercent;
        if (alpha < 0.5f)
        {
            alpha = 0.5f;
        }

        mImageView.setAlpha(alpha);
    }
}
