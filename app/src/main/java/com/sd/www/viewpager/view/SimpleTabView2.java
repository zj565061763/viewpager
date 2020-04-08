package com.sd.www.viewpager.view;

import android.content.Context;
import android.util.AttributeSet;

public class SimpleTabView2 extends SimpleTabView
{
    public SimpleTabView2(Context context)
    {
        super(context);
    }

    public SimpleTabView2(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public SimpleTabView2(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init()
    {
        super.init();
        getTv_content().setText("2");
    }
}
