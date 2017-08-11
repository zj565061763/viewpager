package com.fanwe.www.viewpager;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.fanwe.library.listener.SDSimpleIterateCallback;
import com.fanwe.library.model.SelectableModel;
import com.fanwe.library.utils.LogUtil;
import com.fanwe.library.utils.SDCollectionUtil;
import com.fanwe.library.viewpager.SDViewPagerInfoListener;
import com.fanwe.library.viewpager.SDViewPagerPlayer;
import com.fanwe.library.viewpager.extend.SDSimpleIndicatorViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    private SDSimpleIndicatorViewPager mViewPager;
    private ItemAdapter mItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager = (SDSimpleIndicatorViewPager) findViewById(R.id.vpg_content);

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

        mViewPager.getViewPager().getViewPagerInfoListener().setOnPageCountChangeCallback(new SDViewPagerInfoListener.OnPageCountChangeCallback()
        {
            @Override
            public void onPageCountChanged(int oldCount, int newCount, ViewPager viewPager)
            {
                LogUtil.i("onPageCountChanged:" + oldCount + "," + newCount);
            }
        });
        mViewPager.getViewPager().getViewPagerInfoListener().setDataSetObserver(new DataSetObserver()
        {
            @Override
            public void onChanged()
            {
                super.onChanged();
                LogUtil.i("onChanged");
            }
        });

        //设置ViewPager参数
        mViewPager.getViewPager().setItemCountPerPage(9); //设置每页有9个数据
        mViewPager.getViewPager().setColumnCountPerPage(3); //设置每一页有3列
        mViewPager.getViewPager().setHorizontalDivider(getResources().getDrawable(R.drawable.divider_horizontal)); //设置横分割线
        mViewPager.getViewPager().setVerticalDivider(getResources().getDrawable(R.drawable.divider_vertical)); //设置竖分割线
        mViewPager.getViewPager().setGridAdapter(mItemAdapter); //设置适配器

        //设置指示器相关配置，以下为默认配置，可以覆盖库中的默认配置
        mViewPager.getIndicatorConfig().imageResIdNormal = R.drawable.lib_vpg_ic_indicator_normal; //指示器正常状态图片
        mViewPager.getIndicatorConfig().imageResIdSelected = R.drawable.lib_vpg_ic_indicator_selected; //指示器选中状态图片
        mViewPager.getIndicatorConfig().width = getResources().getDimensionPixelSize(R.dimen.lib_vpg_indicator_width); //指示器图片宽度
        mViewPager.getIndicatorConfig().height = getResources().getDimensionPixelSize(R.dimen.lib_vpg_indicator_height); //指示器图片高度
        mViewPager.getIndicatorConfig().margin = getResources().getDimensionPixelSize(R.dimen.lib_vpg_indicator_margin); //指示器图片间隔

    }

    private void testPlayer()
    {
        //设置轮播
        mPlayer.setViewPager(mViewPager.getViewPager()); //给播放者设置要轮播的ViewPager对象
        mPlayer.startPlay(2 * 1000); //每隔2秒切换一次
    }

    private SDViewPagerPlayer mPlayer = new SDViewPagerPlayer();
}
