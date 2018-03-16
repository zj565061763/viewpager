package com.fanwe.www.viewpager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.fanwe.lib.viewpager.FGridViewPager;
import com.fanwe.www.viewpager.adapter.ItemAdapter;
import com.fanwe.www.viewpager.model.DataModel;

public class SimpleActivity extends AppCompatActivity
{
    private FGridViewPager mViewPager;
    private ItemAdapter mAdapter;

    @Override
    protected void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_simple);
        mViewPager = findViewById(R.id.vpg_content);

        //设置ViewPager参数
        mViewPager.setGridItemCountPerPage(9); //设置每页有几个数据
        mViewPager.setGridColumnCountPerPage(3); //设置每一页有几列
        mViewPager.setGridHorizontalDivider(getResources().getDrawable(R.drawable.divider_horizontal)); //设置横分割线
        mViewPager.setGridVerticalDivider(getResources().getDrawable(R.drawable.divider_vertical)); //设置竖分割线

        mAdapter = new ItemAdapter(DataModel.get(50), this);
        mViewPager.setGridAdapter(mAdapter); //设置适配器
    }

    public void onClickRemove(View view)
    {
        mAdapter.removeData(0);

        final String selected = mAdapter.getSelectManager().isSingleMode() ?
                String.valueOf(mAdapter.getSelectManager().getSelectedItem()) :
                String.valueOf(mAdapter.getSelectManager().getSelectedItems());

        Toast.makeText(this, selected, Toast.LENGTH_SHORT).show();
    }
}
