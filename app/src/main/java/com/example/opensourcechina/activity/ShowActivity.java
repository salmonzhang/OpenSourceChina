package com.example.opensourcechina.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.example.opensourcechina.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * author:salmonzhang
 * Description:一个activity显示所有的fragment
 * Date:2017/8/8 0008 10:05
 */
public class ShowActivity extends BaseActivity {

    @Bind(R.id.ll_show_main)
    LinearLayout mLlShowMain;
    private Class<Fragment> mClassname;
    private Bundle          mBundle;
    private boolean         mIsShowActionbar;
    private boolean         mIsArrow;
    private String          mTitle;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        ButterKnife.bind(this);

        mClassname = (Class<Fragment>) getIntent().getSerializableExtra("classname");
        mBundle = getIntent().getBundleExtra("bundle");
        mIsShowActionbar = getIntent().getBooleanExtra("isShowActionbar", false);
        mIsArrow = getIntent().getBooleanExtra("isArrow", false);
        mTitle = getIntent().getStringExtra("title");
        int view = getIntent().getIntExtra("view", 0);
        if (view >  0) {
            View layout = View.inflate(getApplicationContext(), view, null);
            mLlShowMain.addView(layout,0);
        }

        init();
    }

    //初始化
    private void init() {

        try {

            Fragment tweetFragment = mClassname.newInstance();

            tweetFragment.setArguments(mBundle);

            getSupportFragmentManager().beginTransaction().replace(R.id.fl_show_show_layout, tweetFragment).commit();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("请传入有效的fragment");
            finish();
        }


        //得到actionbar
        ActionBar supportActionBar = getSupportActionBar();

        if (mIsShowActionbar) {
            supportActionBar.show();//显示
        } else {
            supportActionBar.hide();//隐藏
        }

        if (mIsArrow) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowHomeEnabled(true);
        }

        if (!TextUtils.isEmpty(mTitle)) {
            supportActionBar.setTitle(mTitle);
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();

                break;

            default:
                break;

        }

        return super.onOptionsItemSelected(item);
    }

}
