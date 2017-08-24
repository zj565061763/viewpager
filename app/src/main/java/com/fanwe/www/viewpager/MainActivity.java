package com.fanwe.www.viewpager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.fanwe.library.SDLibrary;
import com.fanwe.library.listener.SDSimpleIterateCallback;
import com.fanwe.library.model.SelectableModel;
import com.fanwe.library.utils.SDCollectionUtil;
import com.fanwe.library.viewpager.SDGridViewPager;
import com.fanwe.library.viewpager.indicator.IPagerIndicatorAdapter;
import com.fanwe.library.viewpager.indicator.IPagerIndicatorItemView;
import com.fanwe.library.viewpager.indicator.impl.ViewPagerIndicator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    private SDGridViewPager mViewPager;
    private ViewPagerIndicator mViewPagerIndicator;
    private ItemAdapter mItemAdapter;

    @Override
    protected void onCreate(final Bundle savedInstanceState)
    {
        SDLibrary.getInstance().init(getApplication());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager = (SDGridViewPager) findViewById(R.id.vpg_content);
        mViewPagerIndicator = (ViewPagerIndicator) findViewById(R.id.view_indicator);

        mViewPagerIndicator.setViewPager(mViewPager);
        mViewPagerIndicator.setAdapter(new IPagerIndicatorAdapter()
        {
            @Override
            public IPagerIndicatorItemView onCreateView()
            {
                return new ImageIndicatorItemView(MainActivity.this);
            }
        });

        final List<SelectableModel> listModel = new ArrayList<>();
        SDCollectionUtil.foreach(5, new SDSimpleIterateCallback()
        {
            @Override
            public boolean next(int i)
            {
                listModel.add(new SelectableModel());
                return false;
            }
        });
        mItemAdapter = new ItemAdapter(listModel, this);

        //设置ViewPager参数
        mViewPager.setGridItemCountPerPage(1); //设置每页有9个数据
        mViewPager.setGridColumnCountPerPage(1); //设置每一页有3列
        mViewPager.setGridHorizontalDivider(getResources().getDrawable(R.drawable.divider_horizontal)); //设置横分割线
        mViewPager.setGridVerticalDivider(getResources().getDrawable(R.drawable.divider_vertical)); //设置竖分割线
        mViewPager.setGridAdapter(mItemAdapter); //设置适配器


    }
}
