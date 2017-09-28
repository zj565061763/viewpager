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
package com.fanwe.lib.viewpager.indicator;

import com.fanwe.lib.viewpager.indicator.model.PositionData;

/**
 * ViewPager指示器，可追踪指示器Item的view
 */
public interface IPagerIndicatorTrack
{
    /**
     * ViewPager页数变化回调
     *
     * @param count
     */
    void onPageCountChanged(int count);

    /**
     * ViewPager页面显示的百分比回调
     *
     * @param position     第几页
     * @param showPercent  显示的百分比[0-1]
     * @param isEnter      true-当前页面处于进入状态，false-当前页面处于离开状态
     * @param isMoveLeft   true-ViewPager内容向左移动，false-ViewPager内容向右移动
     * @param positionData 当前position对应Item的位置信息
     */
    void onShowPercent(int position, float showPercent, boolean isEnter, boolean isMoveLeft, PositionData positionData);

    /**
     * ViewPager某一页选中或者非选中回调
     *
     * @param position     第几页
     * @param selected     true-选中，false-未选中
     * @param positionData 当前position对应Item的位置信息
     */
    void onSelectedChanged(int position, boolean selected, PositionData positionData);
}
