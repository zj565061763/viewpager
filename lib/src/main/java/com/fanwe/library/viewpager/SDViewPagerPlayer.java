package com.fanwe.library.viewpager;

import android.os.CountDownTimer;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2017/8/9.
 */
public class SDViewPagerPlayer
{
    /**
     * 默认轮播间隔
     */
    private static final long DEFAULT_PLAY_SPAN = 1000 * 5;
    private long mPlaySpan = DEFAULT_PLAY_SPAN;
    private boolean mIsNeedPlay = false;
    private boolean mIsPlaying = false;
    private CountDownTimer mTimer;

    private WeakReference<ViewPager> mViewPager;

    /**
     * 设置要播放的ViewPager
     *
     * @param viewPager
     */
    public void setViewPager(ViewPager viewPager)
    {
        if (getViewPager() != viewPager)
        {
            if (viewPager != null)
            {
                mViewPager = new WeakReference<>(viewPager);
                viewPager.setOnTouchListener(mInternalOnTouchListener);
            } else
            {
                mViewPager = null;
            }
        }
    }

    public ViewPager getViewPager()
    {
        if (mViewPager != null)
        {
            return mViewPager.get();
        } else
        {
            return null;
        }
    }

    private View.OnTouchListener mInternalOnTouchListener = new View.OnTouchListener()
    {
        @Override
        public boolean onTouch(View v, MotionEvent event)
        {
            processTouchEvent(event);
            return false;
        }
    };

    /**
     * 是否正在轮播中
     *
     * @return
     */
    public boolean isPlaying()
    {
        return mIsPlaying;
    }

    /**
     * 是否可以轮播
     *
     * @return
     */
    private boolean canPlay()
    {
        if (getViewPager() == null
                || getViewPager().getAdapter() == null
                || getViewPager().getAdapter().getCount() <= 1)
        {
            stopPlay();
            return false;
        }
        return true;
    }

    /**
     * 开始轮播
     */
    public void startPlay()
    {
        startPlay(DEFAULT_PLAY_SPAN);
    }

    /**
     * 开始轮播
     *
     * @param playSpan 轮播间隔(毫秒)
     */
    public void startPlay(long playSpan)
    {
        if (!canPlay())
        {
            return;
        }
        if (playSpan < 1000)
        {
            playSpan = DEFAULT_PLAY_SPAN;
        }

        mPlaySpan = playSpan;
        mIsNeedPlay = true;
        startPlayInternal();
    }

    private void startPlayInternal()
    {
        if (mIsPlaying)
        {
            return;
        }
        if (!mIsNeedPlay)
        {
            return;
        }
        if (!canPlay())
        {
            return;
        }

        if (mTimer == null)
        {
            mTimer = new CountDownTimer(Long.MAX_VALUE, mPlaySpan)
            {
                @Override
                public void onTick(long millisUntilFinished)
                {
                    if (canPlay())
                    {
                        int current = getViewPager().getCurrentItem();
                        current++;
                        if (current >= getViewPager().getAdapter().getCount())
                        {
                            current = 0;
                        }
                        getViewPager().setCurrentItem(current, true);
                    }
                }

                @Override
                public void onFinish()
                {
                }
            };
            getViewPager().postDelayed(mStartTimerRunnable, mPlaySpan);
            mIsPlaying = true;
        }
    }

    private Runnable mStartTimerRunnable = new Runnable()
    {
        @Override
        public void run()
        {
            if (mTimer != null)
            {
                mTimer.start();
            }
        }
    };

    /**
     * 停止轮播
     */
    public void stopPlay()
    {
        stopPlayInternal();
        mIsNeedPlay = false;
    }

    private void stopPlayInternal()
    {
        if (getViewPager() != null)
        {
            getViewPager().removeCallbacks(mStartTimerRunnable);
        }

        if (mTimer != null)
        {
            mTimer.cancel();
            mTimer = null;
            mIsPlaying = false;
        }
    }

    /**
     * 调用此方法处理触摸事件，会根据事件暂停和恢复播放
     *
     * @param event
     */
    public void processTouchEvent(MotionEvent event)
    {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                stopPlayInternal();
                break;
            case MotionEvent.ACTION_UP:
                startPlayInternal();
                break;
        }
    }
}
