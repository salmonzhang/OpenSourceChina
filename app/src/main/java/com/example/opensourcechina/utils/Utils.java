package com.example.opensourcechina.utils;

import android.content.res.Resources;
import android.text.Html;
import android.text.Spanned;

import com.example.opensourcechina.gloab.Oschina;
import com.example.opensourcechina.view.tweetview.MyLinkMovementMethod;
import com.example.opensourcechina.view.tweetview.MyURLSpan;
import com.example.opensourcechina.view.tweetview.TweetTextView;

/**
 * Created by sy_heima on 2016/10/8.
 */

public class Utils {
    //这个是在主线程去更新ui,在没有上下文的环境,
    public static void runOnUIThread(Runnable runnable)
    {
        Oschina.mainHandler.post(runnable);
    }

    //得到资源管理的类
    public static Resources getResources()
    {
        return Oschina.context.getResources();
    }

    //在屏幕适配时候使用,让代码中使用dip属性
    public static int getDimens(int resId)
    {

        return getResources().getDimensionPixelSize(resId);
    }

    //得到颜色
    public static int getColor(int resId)
    {
        return getResources().getColor(resId);
    }

    //得到字符串数组信息
    public static String[] getStringArray(int resId)
    {
        return getResources().getStringArray(resId);
    }

    //富文本,内容
    //显示富文本的方法
    public static void setContent(TweetTextView contentView, String content) {
        contentView.setMovementMethod(MyLinkMovementMethod.a());
        contentView.setFocusable(false);
        contentView.setDispatchToParent(true);
        contentView.setLongClickable(false);
        Spanned span = Html.fromHtml(TweetTextView.modifyPath(content));
        span = InputHelper.displayEmoji(contentView.getResources(), span.toString());
        contentView.setText(span);
        MyURLSpan.parseLinkText(contentView, span);
    }
}
