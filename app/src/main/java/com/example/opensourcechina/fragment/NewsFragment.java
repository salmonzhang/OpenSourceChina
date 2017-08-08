package com.example.opensourcechina.fragment;

import android.view.View;
import android.widget.TextView;

/**
 * author:salmonzhang
 * Description:综合界面的Fragment
 * Date:2017/8/8 0008 16:12
 */

public class NewsFragment extends BaseFragment{
    @Override
    protected View getSuccessView() {
        TextView textView = new TextView(getContext());
        textView.setText("综合");
        return textView;
    }

    @Override
    protected Object rquestData() {
        return "";
    }
}
