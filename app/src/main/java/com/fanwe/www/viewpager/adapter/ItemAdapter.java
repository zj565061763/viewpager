package com.fanwe.www.viewpager.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.model.SelectableModel;
import com.fanwe.www.viewpager.R;
import com.fanwe.www.viewpager.model.DataModel;

import java.util.List;

/**
 * Created by Administrator on 2017/8/10.
 */
public class ItemAdapter extends SDSimpleAdapter<DataModel>
{
    public ItemAdapter(List<DataModel> listModel, Activity activity)
    {
        super(listModel, activity);
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent)
    {
        return R.layout.item_viewpager;
    }

    @Override
    public void bindData(int position, View convertView, ViewGroup parent, DataModel model)
    {
        TextView button = get(R.id.btn, convertView);
        button.setText(String.valueOf(position));

        if (model.isSelected())
        {
            button.setBackgroundColor(Color.GREEN);
        } else
        {
            button.setBackgroundColor(Color.GRAY);
        }

        convertView.setOnClickListener(this);
    }

    @Override
    public void onItemClick(int position, DataModel model, View view)
    {
        super.onItemClick(position, model, view);
        getSelectManager().performClick(model);
    }
}
