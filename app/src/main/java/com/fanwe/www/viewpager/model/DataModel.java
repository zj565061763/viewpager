package com.fanwe.www.viewpager.model;

import com.fanwe.lib.selectmanager.FSelectManager;

/**
 * Created by Administrator on 2017/8/25.
 */

public class DataModel extends FSelectManager.SelectableModel
{
    private String name;

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
}
