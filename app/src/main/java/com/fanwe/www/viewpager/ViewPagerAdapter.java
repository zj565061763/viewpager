package com.fanwe.www.viewpager;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.fanwe.library.adapter.SDPagerAdapter;
import com.fanwe.library.model.SelectableModel;

import java.util.List;

/**
 * Created by Administrator on 2017/8/9.
 */

public class ViewPagerAdapter extends SDPagerAdapter<SelectableModel>
{
    public ViewPagerAdapter(List<SelectableModel> listModel, Activity activity)
    {
        super(listModel, activity);
    }

    @Override
    public View getView(ViewGroup container, int position)
    {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_viewpager, container, false);
        TextView button = (TextView) view.findViewById(R.id.btn);
        button.setText(String.valueOf(position));
        return view;
    }
}
