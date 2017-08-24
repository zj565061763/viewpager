package com.fanwe.www.viewpager;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.fanwe.library.viewpager.indicator.IPagerIndicatorItemView;
import com.fanwe.library.viewpager.indicator.model.PositionData;

/**
 * Created by Administrator on 2017/8/24.
 */

public class CustomItemView extends FrameLayout implements IPagerIndicatorItemView
{
    public CustomItemView(Context context)
    {
        super(context);
        init();
    }

    private int mColorNormal = Color.parseColor("#616161");
    private int mColorSelected = Color.parseColor("#f57c00");

    private TextView mTextView;
    private View mViewUnderline;

    private void init()
    {
        LayoutInflater.from(getContext()).inflate(R.layout.view_custom_item_view, this, true);
        mTextView = (TextView) findViewById(R.id.tv);
        mViewUnderline = findViewById(R.id.view_underline);

        mViewUnderline.setBackgroundColor(mColorSelected);
        mViewUnderline.setZ(20);
        onSelectedChanged(false);
    }

    public TextView getTextView()
    {
        return mTextView;
    }

    @Override
    public void onSelectedChanged(boolean selected)
    {
        if (selected)
        {
//            mViewUnderline.setVisibility(View.VISIBLE);
            getTextView().setTextColor(mColorSelected);
        } else
        {
//            mViewUnderline.setVisibility(View.INVISIBLE);
            getTextView().setTextColor(mColorNormal);
        }
    }

    @Override
    public void onShowPercent(float showPercent, boolean isEnter, boolean isMoveLeft)
    {
    }

    private PositionData mPositionData = new PositionData();

    @Override
    public PositionData getPositionData()
    {
        return mPositionData;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom)
    {
        super.onLayout(changed, left, top, right, bottom);
        getPositionData().left = getLeft();
        getPositionData().top = getTop();
        getPositionData().right = getRight();
        getPositionData().bottom = getBottom();
    }
}
