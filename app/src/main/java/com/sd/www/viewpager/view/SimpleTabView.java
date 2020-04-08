package com.sd.www.viewpager.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

public class SimpleTabView extends FrameLayout
{
    public SimpleTabView(Context context)
    {
        super(context);
        init();
    }

    public SimpleTabView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public SimpleTabView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    private TextView tv_content;

    protected void init()
    {
        tv_content = new TextView(getContext());

        final FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        addView(tv_content, params);
    }

    public TextView getTv_content()
    {
        return tv_content;
    }
}
