package com.example.opensourcechina.gloab;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

/**
 * author:salmonzhang
 * Description:定义一个全局的Application
 * Date:2017/8/8 0008 10:05
 */

public class Oschina extends Application {
    public static Context context;
    public static Handler mainHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        mainHandler = new Handler();
    }
}
