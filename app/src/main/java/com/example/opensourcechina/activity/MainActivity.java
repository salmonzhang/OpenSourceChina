package com.example.opensourcechina.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.opensourcechina.R;
import com.example.opensourcechina.bean.MainTabs;
import com.example.opensourcechina.utils.ToastUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.fl_main_show_layout)
    FrameLayout mFlMainShowLayout;
    @Bind(R.id.ftab_main_bottom_layout)
    FragmentTabHost mFtabMainBottomLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        //绑定
        mFtabMainBottomLayout.setup(this, getSupportFragmentManager(), R.id.fl_main_show_layout);

        MainTabs[] mainTabses = MainTabs.values();
        for (int i = 0; i < mainTabses.length; i++) {
            //添加页签
            TabHost.TabSpec tabSpec = mFtabMainBottomLayout.newTabSpec(mainTabses[i].mTag);//复用
            //添加指示器
            View view = View.inflate(this, R.layout.tab_indicator, null);

            //标题
            TextView title = (TextView) view.findViewById(R.id.tab_title);
            title.setText(mainTabses[i].mText);

            //图片
            ImageView iv = (ImageView) view.findViewById(R.id.iv_icon);
            iv.setImageResource(mainTabses[i].mIv);

            if (i == 2) {
                iv.setVisibility(View.INVISIBLE);
            }

            tabSpec.setIndicator(view);
            Class<? extends Fragment> clss = mainTabses[i].mClass;
            mFtabMainBottomLayout.addTab(tabSpec, clss, null);

            //去掉分隔线
            mFtabMainBottomLayout.getTabWidget().setDividerDrawable(null);
        }
    }

    @OnClick(R.id.iv_icon_selector)
    public void onClick(View view) {
        ToastUtil.showToast("弹一弹被点击了");
    }
}
