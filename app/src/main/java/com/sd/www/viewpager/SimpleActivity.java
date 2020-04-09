package com.sd.www.viewpager;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.sd.lib.adapter.FPagerAdapter;
import com.sd.lib.viewpager.FViewPager;
import com.sd.www.viewpager.view.SimpleTabView;
import com.sd.www.viewpager.view.SimpleTabView0;
import com.sd.www.viewpager.view.SimpleTabView1;
import com.sd.www.viewpager.view.SimpleTabView2;

import java.util.ArrayList;
import java.util.List;

public class SimpleActivity extends AppCompatActivity
{
    private FViewPager view_pager;

    private SimpleTabView0 mSimpleTabView0;
    private SimpleTabView1 mSimpleTabView1;
    private SimpleTabView2 mSimpleTabView2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_simple);
        view_pager = findViewById(R.id.view_pager);

        mSimpleTabView0 = new SimpleTabView0(this);
        mSimpleTabView1 = new SimpleTabView1(this);
        mSimpleTabView2 = new SimpleTabView2(this);

        view_pager.setAdapter(mAdapter);

        final List<String> listData = new ArrayList<>();
        listData.add("");
        listData.add("");
        listData.add("");
        mAdapter.getDataHolder().setData(listData);
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

            simpleTabView.setTag(R.id.view_pager_child_position, position);
            return simpleTabView;
        }
    };
}
