package com.demo.demotest.base;

import android.os.Bundle;

/**
 * Created by sun on 2018/1/17 9:06
 */
public abstract class BaseCommonActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadData();
    }
    protected abstract void loadData();
}
