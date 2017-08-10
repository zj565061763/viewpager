package com.fanwe.www.viewpager;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.fanwe.library.drawable.SDDrawable;
import com.fanwe.library.listener.SDSimpleIterateCallback;
import com.fanwe.library.model.SelectableModel;
import com.fanwe.library.utils.LogUtil;
import com.fanwe.library.utils.SDCollectionUtil;
import com.fanwe.library.viewpager.SDGridViewPager;
import com.fanwe.library.viewpager.SDViewPager;
import com.fanwe.library.viewpager.SDViewPagerPlayer;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    private SDGridViewPager mViewPager;
    private ViewPagerAdapter mAdapter;
    private ItemAdapter mItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager = (SDGridViewPager) findViewById(R.id.vpg_content);
        mViewPager.setOnPageCountChangeCallback(new SDViewPager.OnPageCountChangeCallback()
        {
            @Override
            public void onPageCountChanged(int oldCount, int newCount)
            {
                LogUtil.i("onPageCountChanged:" + oldCount + "," + newCount);
            }
        });

        final List<SelectableModel> listModel = new ArrayList<>();
        SDCollectionUtil.foreach(50, new SDSimpleIterateCallback()
        {
            @Override
            public boolean next(int i)
            {
                listModel.add(new SelectableModel());
                return false;
            }
        });
        mAdapter = new ViewPagerAdapter(listModel, this);
        mItemAdapter = new ItemAdapter(listModel, this);

//        mViewPager.setAdapter(mAdapter);

        mViewPager.setItemCountPerPage(9);
        mViewPager.setColumnCountPerPage(3);
        mViewPager.setHorizontalDivider(new SDDrawable().color(Color.RED).size(10));
        mViewPager.setVerticalDivider(new SDDrawable().color(Color.RED).size(10));
        mViewPager.setGridAdapter(mItemAdapter);

//        testAutoPlay();
    }

    private SDViewPagerPlayer mPlayer = new SDViewPagerPlayer();

    private void testAutoPlay()
    {
        mPlayer.setViewPager(mViewPager);
        mPlayer.startPlay(3 * 1000);
    }
}
