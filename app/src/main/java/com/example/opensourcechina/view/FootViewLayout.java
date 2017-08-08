package com.example.opensourcechina.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.opensourcechina.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * author:salmonzhang
 * Description:条目底部管理类
 * Date:2017/8/8 0008 11:06
 */

public class FootViewLayout extends FrameLayout{
    @Bind(R.id.ll_foot_layout_loading)
    LinearLayout mLlFootLayoutLoading;
    @Bind(R.id.ll_foot_layout_no_more)
    LinearLayout mLlFootLayoutNoMore;

    public FootViewLayout(Context context) {
        this(context, null);
    }

    public FootViewLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FootViewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView(context);
    }

    private void initView(Context context) {
        //把多种状态的底部都加入
        View layoutView = LayoutInflater.from(context).inflate(R.layout.item_foot_layout,this,false);
        //添加
        addView(layoutView);
        //查找控件
        ButterKnife.bind(this, layoutView);
    }

    //定义状态
    public enum FOOTSTAUTS {
        LOADING, NOMORE;
    }

    //根据状态去切换view
    public void changeView(FOOTSTAUTS footstauts) {
        //先全部隐藏
        mLlFootLayoutLoading.setVisibility(View.GONE);
        mLlFootLayoutNoMore.setVisibility(View.GONE);

        switch (footstauts) {
            case LOADING:
                //加载中
                mLlFootLayoutLoading.setVisibility(View.VISIBLE);
                break;
            case NOMORE:
                //没有更多
                mLlFootLayoutNoMore.setVisibility(View.VISIBLE);
                break;
        }
    }
}
