package com.sd.www.viewpager.view;

import android.content.Context;
import android.util.AttributeSet;

public class SimpleTabView1 extends SimpleTabView
{
    public SimpleTabView1(Context context)
    {
        super(context);
    }

    public SimpleTabView1(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public SimpleTabView1(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init()
    {
        super.init();
        getTv_content().setText("1");
    }
}
