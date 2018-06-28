/*
 * Copyright (C) 2017 zhengjun, fanwe (http://www.fanwe.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fanwe.lib.viewpager.pullcondition;

import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

import com.fanwe.lib.viewpager.FViewPager;

import java.lang.ref.WeakReference;

public class SimpleIgnoredPullCondition implements FViewPager.PullCondition
{
    private final WeakReference<View> mView;
    private Rect mRect;

    public SimpleIgnoredPullCondition(View view)
    {
        mView = new WeakReference<>(view);
    }

    public final View getView()
    {
        return mView == null ? null : mView.get();
    }

    @Override
    public boolean canPull(MotionEvent event, FViewPager viewPager)
    {
        final View view = getView();
        if (view == null)
        {
            viewPager.removePullCondition(this);
            return true;
        }

        if (mRect == null)
            mRect = new Rect();

        view.getGlobalVisibleRect(mRect);
        if (mRect.contains((int) event.getRawX(), (int) event.getRawY()))
            return false;

        return true;
    }
}
