package com.fanwe.www.viewpager;

import android.content.Context;
import android.view.ViewGroup;
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
        addView(mImageView, new ViewGroup.LayoutParams(100, 100));
        onSelectedChanged(false);
    }

    private ImageView mImageView;

    @Override
    public void onSelectedChanged(boolean selected)
    {
        if (selected)
        {
            mImageView.setImageResource(R.drawable.ic_lib_indicator_selected);
        } else
        {
            mImageView.setImageResource(R.drawable.ic_lib_indicator_normal);
        }
    }
}
