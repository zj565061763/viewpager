package com.fanwe.www.viewpager;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.model.SelectableModel;

import java.util.List;

/**
 * Created by Administrator on 2017/8/10.
 */
public class ItemAdapter extends SDSimpleAdapter<SelectableModel>
{
    public ItemAdapter(List<SelectableModel> listModel, Activity activity)
    {
        super(listModel, activity);
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent)
    {
        return R.layout.item_viewpager;
    }

    @Override
    public void bindData(int position, View convertView, ViewGroup parent, SelectableModel model)
    {
        TextView button = get(R.id.btn, convertView);
        button.setText(String.valueOf(position));
    }
}
