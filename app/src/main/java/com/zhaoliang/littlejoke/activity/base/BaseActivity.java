package com.zhaoliang.littlejoke.activity.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import org.xutils.x;

/**
 * Activity基类
 * Created by pro on 16/1/29.
 */
public class BaseActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initXutils3();
    }

    /**
     * 初始化Xutils3
     */
    private void initXutils3() {
        x.view().inject(this);
    }
}
