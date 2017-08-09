package com.fanwe.www.viewpager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

import com.fanwe.library.listener.SDSimpleIterateCallback;
import com.fanwe.library.model.SelectableModel;
import com.fanwe.library.utils.SDCollectionUtil;
import com.fanwe.library.viewpager.SDAutoPlayViewPager;
import com.fanwe.library.viewpager.SDViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    private SDAutoPlayViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager = (SDAutoPlayViewPager) findViewById(R.id.vpg_content);

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

        ViewPagerAdapter adapter = new ViewPagerAdapter(listModel, this);
        mViewPager.setAdapter(adapter);

        mViewPager.addPullCondition(new SDViewPager.PullCondition()
        {
            @Override
            public boolean canPull(MotionEvent event)
            {
                if (event.getY() > mViewPager.getHeight() / 2)
                {
                    return true;
                }
                return false;
            }
        });
        mViewPager.startPlay(3 * 1000);
    }
}
