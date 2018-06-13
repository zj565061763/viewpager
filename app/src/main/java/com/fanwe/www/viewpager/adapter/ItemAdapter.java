package com.fanwe.www.viewpager.adapter;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fanwe.lib.adapter.selectable.FSelectableSimpleAdapter;
import com.fanwe.www.viewpager.R;
import com.fanwe.www.viewpager.model.DataModel;

/**
 * Created by Administrator on 2017/8/10.
 */
public class ItemAdapter extends FSelectableSimpleAdapter<DataModel>
{
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

        if (model.isSelected())
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
