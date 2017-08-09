package com.fanwe.library.viewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2017/8/9.
 */
public class SDAutoPlayViewPager extends SDViewPager
{
    public SDAutoPlayViewPager(Context context)
    {
        super(context);
        init();
    }

    public SDAutoPlayViewPager(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    private SDViewPagerPlayer mAutoPlayHelper = new SDViewPagerPlayer();

    private void init()
    {
        mAutoPlayHelper.setViewPager(this);
    }

    /**
     * 是否正在轮播中
     *
     * @return
     */
    public boolean isPlaying()
    {
        return mAutoPlayHelper.isPlaying();
    }

    /**
     * 开始轮播
     */
    public void startPlay()
    {
        mAutoPlayHelper.startPlay();
    }

    /**
     * 开始轮播
     *
     * @param playSpan 轮播间隔(毫秒)
     */
    public void startPlay(long playSpan)
    {
        mAutoPlayHelper.startPlay(playSpan);
    }

    /**
     * 停止轮播
     */
    public void stopPlay()
    {
        mAutoPlayHelper.stopPlay();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        mAutoPlayHelper.processTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDetachedFromWindow()
    {
        super.onDetachedFromWindow();
        stopPlay();
    }
}
