package com.fanwe.www.viewpager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.fanwe.library.listener.SDSimpleIterateCallback;
import com.fanwe.library.model.SelectableModel;
import com.fanwe.library.utils.SDCollectionUtil;
import com.fanwe.library.viewpager.SDGridViewPager;
import com.fanwe.library.viewpager.indicator.IPagerIndicatorAdapter;
import com.fanwe.library.viewpager.indicator.IPagerIndicatorItemView;
import com.fanwe.library.viewpager.indicator.impl.PagerIndicatorGroupView;
import com.fanwe.www.viewpager.adapter.ItemAdapter;

import java.util.ArrayList;
import java.util.List;

public class ScrollActivity extends AppCompatActivity
{
    private static final String[] ARR_VERSION = new String[]{
            "CUPCAKE",
            "DONUT",
            "ECLAIR",
            "GINGERBREAD",
            "HONEYCOMB",
            "ICE_CREAM_SANDWICH",
            "JELLY_BEAN",
            "KITKAT",
            "LOLLIPOP",
            "M",
            "NOUGAT"};

    private SDGridViewPager mViewPager;
    private PagerIndicatorGroupView mPagerIndicatorGroupView;

    private ItemAdapter mItemAdapter;

    @Override
    protected void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_scroll);
        initAdapter();

        mViewPager = (SDGridViewPager) findViewById(R.id.vpg_content);
        mPagerIndicatorGroupView = (PagerIndicatorGroupView) findViewById(R.id.view_pager_indicator);

        mPagerIndicatorGroupView.setDebug(true);
        mPagerIndicatorGroupView.setAdapter(new IPagerIndicatorAdapter()
        {
            @Override
            public IPagerIndicatorItemView onCreateView(final int position, ViewGroup viewParent)
            {
                TextViewItemView textView = new TextViewItemView(viewParent.getContext());
                textView.setText(ARR_VERSION[position]);
                textView.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        mViewPager.setCurrentItem(position);
                    }
                });
                return textView;
            }
        });
        mPagerIndicatorGroupView.setViewPager(mViewPager); //给指示器设置ViewPager

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
        final List<SelectableModel> listModel = new ArrayList<>();
        SDCollectionUtil.foreach(10, new SDSimpleIterateCallback()
        {
            @Override
            public boolean next(int i)
            {
                listModel.add(new SelectableModel());
                return false;
            }
        });
        mItemAdapter = new ItemAdapter(listModel, this);
    }
}
