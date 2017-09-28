package com.fanwe.www.viewpager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.fanwe.library.listener.SDSimpleIterateCallback;
import com.fanwe.library.utils.SDCollectionUtil;
import com.fanwe.lib.viewpager.SDGridViewPager;
import com.fanwe.lib.viewpager.indicator.impl.PagerIndicator;
import com.fanwe.www.viewpager.adapter.ItemAdapter;
import com.fanwe.www.viewpager.model.DataModel;

import java.util.ArrayList;
import java.util.List;

public class SimpleActivity extends AppCompatActivity
{
    private SDGridViewPager mViewPager;
    private PagerIndicator mPagerIndicator;

    private List<DataModel> mListModel = new ArrayList<>();
    private ItemAdapter mItemAdapter;

    @Override
    protected void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_simple);
        initAdapter();

        mViewPager = (SDGridViewPager) findViewById(R.id.vpg_content);
        mPagerIndicator = (PagerIndicator) findViewById(R.id.view_indicator);

        mPagerIndicator.setDebug(true);
        mPagerIndicator.setViewPager(mViewPager); //给指示器设置ViewPager

        //设置ViewPager参数
        mViewPager.setGridItemCountPerPage(1); //设置每页有几个数据
        mViewPager.setGridColumnCountPerPage(1); //设置每一页有几列
        mViewPager.setGridHorizontalDivider(getResources().getDrawable(R.drawable.divider_horizontal)); //设置横分割线
        mViewPager.setGridVerticalDivider(getResources().getDrawable(R.drawable.divider_vertical)); //设置竖分割线
        mViewPager.setGridAdapter(mItemAdapter); //设置适配器

        mViewPager.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                mViewPager.setCurrentItem(10);
            }
        }, 3000);
    }

    private void initAdapter()
    {
        SDCollectionUtil.foreach(5, new SDSimpleIterateCallback()
        {
            @Override
            public boolean next(int i)
            {
                DataModel model = new DataModel();
                model.setName(String.valueOf(i));
                mListModel.add(model);
                return false;
            }
        });
        mItemAdapter = new ItemAdapter(mListModel, this);
    }

    public void onClickRemove(View view)
    {
        mItemAdapter.removeData(0);
    }
}
