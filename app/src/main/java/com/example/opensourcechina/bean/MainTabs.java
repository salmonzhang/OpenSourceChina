package com.example.opensourcechina.bean;

import com.example.opensourcechina.R;
import com.example.opensourcechina.fragment.NewsFragment;

/**
 * author:salmonzhang
 * Description:用枚举封装底部数据
 * Date:2017/8/8 0008 16:46
 */

public enum MainTabs {

    //数据
    News("News", R.drawable.tab_icon_new,"综合", NewsFragment.class),
    TWEET("TWEET", R.drawable.tab_icon_tweet,"动弹", NewsFragment.class),
    SELECT("SELECT", R.drawable.tab_icon_selector,"弹一弹", NewsFragment.class),
    FIND("FIND", R.drawable.tab_icon_explore,"发现", NewsFragment.class),
    ME("ME", R.drawable.tab_icon_me,"我的", NewsFragment.class);

    //构造
    public String mTag;//复用的标记
    public int mIv;//图片
    public String mText;//文字
    public Class mClass;//类

    MainTabs(String tag,int iv,String text,Class clss) {
        this.mTag = tag;
        this.mIv = iv;
        this.mText = text;
        this.mClass = clss;
    }
}
