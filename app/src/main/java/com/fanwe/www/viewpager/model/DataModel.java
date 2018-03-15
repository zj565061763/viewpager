package com.fanwe.www.viewpager.model;

import com.fanwe.lib.selectmanager.FSelectManager;

import java.util.ArrayList;
import java.util.List;

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

    public static List<DataModel> get(int count)
    {
        final List<DataModel> listModel = new ArrayList<>();
        for (int i = 0; i < count; i++)
        {
            DataModel model = new DataModel();
            model.setName(String.valueOf(i));
            listModel.add(model);
        }
        return listModel;
    }
}
