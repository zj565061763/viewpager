package com.fanwe.www.viewpager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.fanwe.library.listener.SDSimpleIterateCallback;
import com.fanwe.library.model.SelectableModel;
import com.fanwe.library.utils.SDCollectionUtil;
import com.fanwe.library.viewpager.SDGridViewPager;
import com.fanwe.library.viewpager.indicator.IPagerIndicatorAdapter;
import com.fanwe.library.viewpager.indicator.IPagerIndicatorItemView;
import com.fanwe.library.viewpager.indicator.ViewPagerIndicator;
import com.fanwe.www.viewpager.adapter.ItemAdapter;

import java.util.ArrayList;
import java.util.List;

public class ScrollActivity extends AppCompatActivity
{
    private SDGridViewPager mViewPager;
    private ViewPagerIndicator mViewPagerIndicator;

    private ItemAdapter mItemAdapter;

    @Override
    protected void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_scroll);
        initAdapter();

        mViewPager = (SDGridViewPager) findViewById(R.id.vpg_content);
        mViewPagerIndicator = (ViewPagerIndicator) findViewById(R.id.view_pager_indicator);

        mViewPagerIndicator.setAdapter(new IPagerIndicatorAdapter()
        {
            @Override
            public IPagerIndicatorItemView onCreateView(final int position, ViewGroup viewParent)
            {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                params.leftMargin = 10;
                params.rightMargin = 10;

                CustomItemView customItemView = new CustomItemView(viewParent.getContext());
                customItemView.getTextView().setText(String.valueOf(position));
                customItemView.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        mViewPager.setCurrentItem(position);
                    }
                });
                customItemView.setLayoutParams(params);

                return customItemView;
            }
        });
        mViewPagerIndicator.setViewPager(mViewPager); //给指示器设置ViewPager

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
        SDCollectionUtil.foreach(20, new SDSimpleIterateCallback()
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
