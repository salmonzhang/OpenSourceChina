package com.example.opensourcechina.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TabHost;

import com.example.opensourcechina.R;
import com.example.opensourcechina.fragment.NewsFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

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
        mFtabMainBottomLayout.setup(this,getSupportFragmentManager(),R.id.fl_main_show_layout);

        for (int i = 0; i < 5; i++) {
            //添加页签
            TabHost.TabSpec tabSpec = mFtabMainBottomLayout.newTabSpec("tag");//复用
            //添加指示器
            View view = View.inflate(this, R.layout.tab_indicator, null);
            tabSpec.setIndicator(view);
            Class<? extends Fragment> clss = NewsFragment.class;
            mFtabMainBottomLayout.addTab(tabSpec, clss, null);
        }
    }
}
