package com.sd.www.viewpager.adapter;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sd.lib.adapter.FSimpleAdapter;
import com.sd.lib.adapter.selectable.FAdapterSelectManager;
import com.sd.lib.selectmanager.SelectManager;
import com.sd.www.viewpager.R;
import com.sd.www.viewpager.model.DataModel;

/**
 * Created by Administrator on 2017/8/10.
 */
public class GridAdapter extends FSimpleAdapter<DataModel>
{
    private final SelectManager<DataModel> mSelectManager = new FAdapterSelectManager<>(this);

    public GridAdapter()
    {
        getSelectManager().setMode(SelectManager.Mode.MULTI);
    }

    public SelectManager<DataModel> getSelectManager()
    {
        return mSelectManager;
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent)
    {
        return R.layout.item_viewpager;
    }

    @Override
    public void onBindData(int position, View convertView, ViewGroup parent, final DataModel model)
    {
        TextView button = get(R.id.btn, convertView);
        button.setText(model.getName());

        if (getSelectManager().isSelected(model))
        {
            button.setBackgroundColor(Color.GREEN);
        } else
        {
            button.setBackgroundColor(Color.GRAY);
        }

        convertView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getSelectManager().performClick(model);
            }
        });
    }
}
