package com.example.opensourcechina.cachemanager;

import android.text.TextUtils;

import com.example.opensourcechina.utils.GsonUtil;

import java.lang.reflect.Type;
import java.util.List;

/**
 * author:salmonzhang
 * Description:一个activity显示所有的fragment
 * Date:2017/8/8 0008 10:05
 */
//可以看做一个工具类
public class LoadData {

    private LoadData () {

    }

    private static LoadData sLoadData = new LoadData();


    public static LoadData getInstance() {
        return sLoadData;
    }

    //获取json对象的方法
    public <T> T getBeanData(String url, Class<T> clazz, String cookie) {

        //1. 去网络获取数据
        String content = HttpManager.getInstance().dataGet(url, cookie);
        System.out.println("网络获取的数据:"+content);
        //2. 判断当前的数据是否为空
        if (TextUtils.isEmpty(content)) {
            //如果是空
            //去缓存类中去取数据
            content = CacheManger.getInstance().getCacheData(url);

//            System.out.println("当前缓存数据:"+content.length());
        } else {
            //不为空
            //保存数据,刷新缓存数据
            CacheManger.getInstance().saveData(content,url);
        }

        //到这一步为止,我们已经得到数据了
        //解析一把
        return parseJson(content,clazz);





        //走到这一步就可以去解析了
//        return null;
    }

    //解析json数据
    private<T> T parseJson(String content,Class<T> clazz) {

        return GsonUtil.parseJsonToBean(content,clazz);
    }

    //获取json对象的方法
    public <T> List<T> getListData(String url, Type type, String cookie) {

        //1. 去网络获取数据
        String content = HttpManager.getInstance().dataGet(url, cookie);
        //2. 判断当前的数据是否为空
        if (TextUtils.isEmpty(content)) {
            //如果是空
            //去缓存类中去取数据
            content = CacheManger.getInstance().getCacheData(url);

//            System.out.println("当前缓存数据:"+content.length());
        } else {
            //不为空
            //保存数据,刷新缓存数据
            CacheManger.getInstance().saveData(content,url);
        }

        //到这一步为止,我们已经得到数据了
        //解析一把
        return parseJsonList(content,type);





        //走到这一步就可以去解析了
        //        return null;
    }

    private <T> List<T> parseJsonList(String content, Type type) {
        return GsonUtil.parseJsonToList(content,type);
    }


}
