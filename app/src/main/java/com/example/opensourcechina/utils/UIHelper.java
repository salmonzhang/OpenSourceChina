package com.example.opensourcechina.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import java.net.URLDecoder;

/**
 * 界面帮助类
 *
 * @author FireAnt（http://my.oschina.net/LittleDY）
 * @version 创建时间：2014年10月10日 下午3:33:36
 */
public class UIHelper {

    /**
     * 全局web样式
     */
    // 链接样式文件，代码块高亮的处理
    public final static String linkCss = "<script type=\"text/javascript\" " +
            "src=\"file:///android_asset/shCore.js\"></script>"
            + "<script type=\"text/javascript\" src=\"file:///android_asset/brush.js\"></script>"
            + "<script type=\"text/javascript\" src=\"file:///android_asset/client.js\"></script>"
            + "<script type=\"text/javascript\" src=\"file:///android_asset/detail_page" +
            ".js\"></script>"
            + "<script type=\"text/javascript\">SyntaxHighlighter.all();</script>"
            + "<script type=\"text/javascript\">function showImagePreview(var url){window" +
            ".location.url= url;}</script>"
            + "<link rel=\"stylesheet\" type=\"text/css\" " +
            "href=\"file:///android_asset/shThemeDefault.css\">"
            + "<link rel=\"stylesheet\" type=\"text/css\" href=\"file:///android_asset/shCore" +
            ".css\">"
            + "<link rel=\"stylesheet\" type=\"text/css\" href=\"file:///android_asset/css/common" +
            ".css\">";
    public final static String WEB_STYLE = linkCss;

    public static final String WEB_LOAD_IMAGES = "<script type=\"text/javascript\"> var " +
            "allImgUrls = getAllImgSrc(document.body.innerHTML);</script>";

    private static final String SHOWIMAGE = "ima-api:action=showImage&data=";

    //这个超莲接点击后的地址
    public static void showLinkRedirect(Context context, int objType,
                                        long objId, String objKey) {
        ToastUtil.showToast("打开相应的类"+objKey+"context:"+context+"objType:"+objType);
        /*switch (objType) {
            case URLsUtils.URL_OBJ_TYPE_NEWS:
                showNewsDetail(context, objId, -1);
                break;
            case URLsUtils.URL_OBJ_TYPE_QUESTION:
                showPostDetail(context, objId, 0);
                break;
            case URLsUtils.URL_OBJ_TYPE_QUESTION_TAG:
                showPostListByTag(context, objKey);
                break;
            case URLsUtils.URL_OBJ_TYPE_SOFTWARE:
                SoftwareDetailActivity.show(context, objKey);
                //showSoftwareDetail(context, objKey);
                break;
            case URLsUtils.URL_OBJ_TYPE_ZONE:
                showUserCenter(context, objId, objKey);
                break;
            case URLsUtils.URL_OBJ_TYPE_TWEET:
                showTweetDetail(context, null, objId);
                break;
            case URLsUtils.URL_OBJ_TYPE_BLOG:
                showBlogDetail(context, objId);
                break;
            case URLsUtils.URL_OBJ_TYPE_OTHER:
                openBrowser(context, objKey);
                break;
            case URLsUtils.URL_OBJ_TYPE_TEAM:
                openSysBrowser(context, objKey);
                break;
            case URLsUtils.URL_OBJ_TYPE_GIT:
                openSysBrowser(context, objKey);
                break;
        }*/
    }

    /**
     * 打开内置浏览器
     *
     * @param context
     * @param url
     */
    public static void openBrowser(Context context, String url) {

        if (StringUtils.isImgUrl(url)) {
           ToastUtil.showToast("显示图片"+url);
            return;
        }

        if (url.startsWith("http://www.oschina.net/tweet-topic/")) {
            Bundle bundle = new Bundle();
            int i = url.lastIndexOf("/");
            if (i != -1) {
                bundle.putString("topic",
                        URLDecoder.decode(url.substring(i + 1)));
            }
         /*   UIHelper.showSimpleBack(context, SimpleBackPage.TWEET_TOPIC_LIST,
                    bundle);*/
            ToastUtil.showToast("开启回退");
            return;
        }
        try {
            // 启用外部浏览器
             Uri uri = Uri.parse(url);
             Intent it = new Intent(Intent.ACTION_VIEW, uri);
             context.startActivity(it);
        /*    Bundle bundle = new Bundle();
            bundle.putString(BrowserFragment.BROWSER_KEY, url);
            showSimpleBack(context, SimpleBackPage.BROWSER, bundle);*/
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtil.showToast("无法浏览此网页zzz");
        }
    }

    /**
     * 显示用户中心页面
     *
     * @param context
     * @param hisuid
     * @param hisuid
     * @param hisname
     */
    public static void showUserCenter(Context context, long hisuid,
                                      String hisname) {
        ToastUtil.showToast("显示用户中心");
      /*  if (hisuid == 0 && hisname.equalsIgnoreCase("匿名")) {
            AppContext.showToast("提醒你，该用户为非会员");
            return;
        }
        User user = new User();
        user.setId((int) hisuid);
        user.setName(hisname);
        OtherUserHomeActivity.show(context, user);*/
    }

}
