package com.sd.www.viewpager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.sd.lib.adapter.FPagerAdapter;
import com.sd.lib.viewpager.FViewPager;
import com.sd.lib.viewpager.utils.FViewPagerChildListener;
import com.sd.www.viewpager.view.SimpleTabView;

import java.util.ArrayList;
import java.util.List;

public class SimpleActivity extends AppCompatActivity
{
    public static final String TAG = SimpleActivity.class.getSimpleName();

    private FViewPager view_pager;

    private SimpleTabView mSimpleTabView0;
    private SimpleTabView mSimpleTabView1;
    private SimpleTabView mSimpleTabView2;

    private FViewPagerChildListener mViewPagerChildListener;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_simple);
        view_pager = findViewById(R.id.view_pager);

        mAdapter.setAutoCacheView(true);
        view_pager.setAdapter(mAdapter);

        mSimpleTabView0 = new SimpleTabView(this);
        mSimpleTabView1 = new SimpleTabView(this);
        mSimpleTabView2 = new SimpleTabView(this);

        mSimpleTabView0.getTv_content().setText("0");
        mSimpleTabView1.getTv_content().setText("1");
        mSimpleTabView2.getTv_content().setText("2");

        final List<String> listData = new ArrayList<>();
        listData.add("0");
        listData.add("1");
        listData.add("2");
        mAdapter.getDataHolder().setData(listData);

        initViewPagerChildListener();
    }

    private void initViewPagerChildListener()
    {
        mViewPagerChildListener = new FViewPagerChildListener(mSimpleTabView0)
        {
            @Override
            protected void onPageSelectChanged(boolean selected)
            {
                Log.i(TAG, "onPageSelectChanged:" + selected);
            }
        };
        mSimpleTabView0.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener()
        {
            @Override
            public void onViewAttachedToWindow(View v)
            {
                mViewPagerChildListener.start();
            }

            @Override
            public void onViewDetachedFromWindow(View v)
            {
                mViewPagerChildListener.stop();
            }
        });
    }

    private final FPagerAdapter<String> mAdapter = new FPagerAdapter<String>()
    {
        @Override
        public View getView(ViewGroup container, int position)
        {
            SimpleTabView simpleTabView = null;

            if (position == 0)
                simpleTabView = mSimpleTabView0;
            else if (position == 1)
                simpleTabView = mSimpleTabView1;
            else if (position == 2)
                simpleTabView = mSimpleTabView2;
            else
                simpleTabView = new SimpleTabView(SimpleActivity.this);

            return simpleTabView;
        }
    };
}
