package com.fanwe.www.viewpager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.fanwe.library.SDLibrary;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(final Bundle savedInstanceState)
    {
        SDLibrary.getInstance().init(getApplication());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void onClickSimple(View view)
    {
        startActivity(new Intent(this, SimpleActivity.class));
    }

}
