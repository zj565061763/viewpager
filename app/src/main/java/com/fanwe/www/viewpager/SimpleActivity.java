package com.fanwe.www.viewpager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.fanwe.lib.viewpager.FGridViewPager;
import com.fanwe.www.viewpager.adapter.ItemAdapter;
import com.fanwe.www.viewpager.model.DataModel;

import java.util.ArrayList;
import java.util.List;

public class SimpleActivity extends AppCompatActivity
{
    private FGridViewPager mViewPager;

    private List<DataModel> mListModel = new ArrayList<>();
    private ItemAdapter mItemAdapter;

    @Override
    protected void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_simple);
        initAdapter();

        mViewPager = findViewById(R.id.vpg_content);

        //设置ViewPager参数
        mViewPager.setGridItemCountPerPage(9); //设置每页有几个数据
        mViewPager.setGridColumnCountPerPage(3); //设置每一页有几列
        mViewPager.setGridHorizontalDivider(getResources().getDrawable(R.drawable.divider_horizontal)); //设置横分割线
        mViewPager.setGridVerticalDivider(getResources().getDrawable(R.drawable.divider_vertical)); //设置竖分割线
        mViewPager.setGridAdapter(mItemAdapter); //设置适配器
    }

    private void initAdapter()
    {
        for (int i = 0; i < 50; i++)
        {
            DataModel model = new DataModel();
            model.setName(String.valueOf(i));
            mListModel.add(model);
        }
        mItemAdapter = new ItemAdapter(mListModel, this);
    }

    public void onClickRemove(View view)
    {
        mItemAdapter.removeData(0);
    }
}
