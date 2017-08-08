package com.example.opensourcechina.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.opensourcechina.activity.ShowActivity;
import com.example.opensourcechina.view.LoaderPager;

/**
 * author:salmonzhang
 * Description:使用ui框架创建一个BaseFragment
 * Date:2017/8/8 0008 11:23
 */

public abstract class BaseFragment extends Fragment{

    //使用ui框架进行切换
    private LoaderPager mLoaderPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //复用的问题,listview数据会重复添加的问题
        if (mLoaderPager == null) {
            mLoaderPager = new LoaderPager(getContext()) {
                @Override
                protected Object getNetData() {
                    return rquestData();//不能跟系统名或者已有的方法名重了
                }

                @Override
                protected View createSuccessView() {
                    return getSuccessView();
                }
            };
        }


        return mLoaderPager;
    }

    //得到一个成功的view
    protected abstract View getSuccessView();

    //这个请求数据的方法,带了线程了
    protected abstract Object rquestData();

    //提取一个刷新数据的方法
    public void refresh() {
        mLoaderPager.loadData();
    }

    //开启fragment
    public void startFragment(Class<? extends Fragment> fragmentClass, Bundle bundle, boolean isShowActionbar, String title, boolean isArrow, int titleLayout) {
        Intent intent = new Intent(getContext(), ShowActivity.class);
        intent.putExtra("classname", fragmentClass);
        intent.putExtra("bundle", bundle);
        intent.putExtra("isShowActionbar", isShowActionbar);
        intent.putExtra("title", title);
        intent.putExtra("isArrow", isArrow);
        intent.putExtra("view", titleLayout);
        startActivity(intent);
    }

    public void startFragment(Class<? extends Fragment> fragmentClass) {
        startFragment(fragmentClass, new Bundle(), false, "", false,0);
    }

    public void startFragment(Class<? extends Fragment> fragmentClass,String title) {
        startFragment(fragmentClass, new Bundle(), true, title, false,0);
    }

    public void startFragment(Class<? extends Fragment> fragmentClass,String title,Bundle bundle) {
        startFragment(fragmentClass, bundle, true, title, false,0);
    }
}
