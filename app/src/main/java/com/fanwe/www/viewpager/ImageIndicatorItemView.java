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
        onLeave(1, true);
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
    public void onEnter(float enterPercent, boolean leftToRight)
    {
        super.onEnter(enterPercent, leftToRight);

        float alpha = enterPercent;
        if (alpha < 0.5f)
        {
            alpha = 0.5f;
        }

        mImageView.setAlpha(alpha);
    }

    @Override
    public void onLeave(float leavePercent, boolean leftToRight)
    {
        super.onLeave(leavePercent, leftToRight);

        float alpha = 1 - leavePercent;
        if (alpha < 0.5f)
        {
            alpha = 0.5f;
        }

        mImageView.setAlpha(alpha);
    }
}
