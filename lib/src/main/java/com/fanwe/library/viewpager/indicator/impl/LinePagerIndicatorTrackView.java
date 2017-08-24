package com.fanwe.library.viewpager.indicator.impl;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

import com.fanwe.library.viewpager.indicator.IPagerIndicatorTrackView;
import com.fanwe.library.viewpager.indicator.model.PositionData;

/**
 * Created by Administrator on 2017/8/24.
 */

public class LinePagerIndicatorTrackView extends View implements IPagerIndicatorTrackView
{
    public LinePagerIndicatorTrackView(Context context)
    {
        super(context);
        init();
    }

    private static final int DEFAULT_LINE_HEIGHT = 2;
    private static final int DEFAULT_LINE_COLOR = Color.parseColor("#d43d3d");
    private static final int DEFAULT_LINE_CORNER = 1;

    private int mLineColor;
    private float mLineHeight;
    private float mLineCorner;
    private RectF mLineRect = new RectF();

    private Paint mPaint;

    private void init()
    {
        mLineColor = DEFAULT_LINE_COLOR;
        mLineHeight = getResources().getDisplayMetrics().density * DEFAULT_LINE_HEIGHT;
        mLineCorner = getResources().getDisplayMetrics().density * DEFAULT_LINE_CORNER;

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mLineColor);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        canvas.drawRoundRect(mLineRect, mLineCorner, mLineCorner, mPaint);
    }

    @Override
    public void onPageCountChanged(int count)
    {
        if (count <= 0)
        {
            setVisibility(View.GONE);
        } else
        {
            setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onShowPercent(int position, float showPercent, boolean isEnter, boolean leftToRight, PositionData positionData)
    {
        if (positionData == null)
        {
            return;
        }
    }
}
