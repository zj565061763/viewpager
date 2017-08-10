package com.fanwe.www.viewpager;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.fanwe.library.drawable.SDDrawable;
import com.fanwe.library.listener.SDSimpleIterateCallback;
import com.fanwe.library.model.SelectableModel;
import com.fanwe.library.utils.SDCollectionUtil;
import com.fanwe.library.viewpager.extend.SDIndicatorViewPager;
import com.fanwe.library.viewpager.SDViewPagerPlayer;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    private SDIndicatorViewPager mViewPager;
    private ItemAdapter mItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager = (SDIndicatorViewPager) findViewById(R.id.vpg_content);

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
        mItemAdapter = new ItemAdapter(listModel, this);

        mViewPager.getViewPager().setItemCountPerPage(9);
        mViewPager.getViewPager().setColumnCountPerPage(3);
        mViewPager.getViewPager().setHorizontalDivider(new SDDrawable().color(Color.RED).size(10));
        mViewPager.getViewPager().setVerticalDivider(new SDDrawable().color(Color.RED).size(10));
        mViewPager.getViewPager().setGridAdapter(mItemAdapter);

//        testAutoPlay();
    }

    private SDViewPagerPlayer mPlayer = new SDViewPagerPlayer();

    private void testAutoPlay()
    {
        mPlayer.setViewPager(mViewPager.getViewPager());
        mPlayer.startPlay(3 * 1000);
    }
}
