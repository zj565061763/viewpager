package com.fanwe.library.viewpager;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;

/**
 * Created by Administrator on 2017/8/9.
 */
public class FWAutoPlayViewPager extends FWViewPager
{
    public FWAutoPlayViewPager(Context context)
    {
        super(context);
    }

    public FWAutoPlayViewPager(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    /**
     * 默认轮播间隔
     */
    private static final long DEFAULT_PLAY_SPAN = 1000 * 5;
    private long mPlaySpan = DEFAULT_PLAY_SPAN;
    private boolean mIsNeedPlay = false;
    private boolean mIsPlaying = false;
    private CountDownTimer mTimer;

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
        if (getAdapter() == null)
        {
            stopPlay();
            return false;
        }
        if (getAdapter().getCount() <= 1)
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
        if (!canPlay())
        {
            return;
        }
        if (!mIsNeedPlay)
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
                        int current = getCurrentItem();
                        current++;
                        if (current >= getAdapter().getCount())
                        {
                            current = 0;
                        }
                        setCurrentItem(current, true);
                    }
                }

                @Override
                public void onFinish()
                {
                }
            };
            postDelayed(mStartTimerRunnable, mPlaySpan);
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
        removeCallbacks(mStartTimerRunnable);
        if (mTimer != null)
        {
            mTimer.cancel();
            mTimer = null;
            mIsPlaying = false;
        }
    }

    @Override
    protected void onDetachedFromWindow()
    {
        super.onDetachedFromWindow();
        stopPlay();
    }
}
