package com.zhaoliang.littlejoke.application;

import android.app.Application;

import org.xutils.x;

/**
 * 应用程序
 * Created by pro on 16/1/29.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        initXutils3();
    }

    /**
     * 初始化Xtuils3
     */
    private void initXutils3() {
        x.Ext.init(this);
        x.Ext.setDebug(true);
    }
}
