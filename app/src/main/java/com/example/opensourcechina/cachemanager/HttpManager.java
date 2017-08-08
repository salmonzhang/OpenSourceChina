package com.example.opensourcechina.cachemanager;

import android.text.TextUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
/**
 * author:salmonzhang
 * Description:网络请求
 * Date:2017/8/8 0008 10:05
 */

public class HttpManager {

    private HttpManager() {

    }

    private static HttpManager sHttpManager = new HttpManager();

    public static HttpManager getInstance() {
        return sHttpManager;
    }

    //去网络获取数据
    public String dataGet(String url, String cookie) {

        try {
            OkHttpClient okHttpClient = new OkHttpClient();

          /*  Request request = new Request.Builder()
                    .url(url)
                    .build();*/
            Request.Builder builder = new Request.Builder();
            if (!TextUtils.isEmpty(cookie)) {
                builder.addHeader("cookie", cookie);
            }
            Request request = builder.url(url).build();


            Response response = okHttpClient.newCall(request).execute();

            //获取头
            Headers headers = response.headers();
            for (int i = 0; i < headers.size(); i++) {
                String name = headers.name(i);
                String value = headers.get(name);
                System.out.println("name:" + name + "---->value" + value);
                //Content-Type---->valuetext/html;charset=UTF-8
                //判断是否网页
            }

            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return null;

        }
    }

    public Response postData(String url, HashMap<String, String> params, String cookie) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        FormBody.Builder builder = new FormBody.Builder();
        for (String name : params.keySet()) {
            String value = params.get(name);
            builder.add(name, value);
        }

        FormBody formBody = builder.build();

        Request.Builder requestBuild = new Request.Builder();
        if (!TextUtils.isEmpty(cookie)) {
            requestBuild.addHeader("cookie", cookie);
        }
        Request request = requestBuild.url(url).post(formBody).build();


      /*  Request request = new Request.Builder()
                .addHeader("cookie",cookie)
                .url(url)
                .post(formBody)
                .build();*/
        try {
            return okHttpClient.newCall(request).execute();
        } catch (IOException e) {

            e.printStackTrace();
            return null;
        }
    }

    public String postFile(String url, String key, File file, String cookie) {
        try {
            OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

            RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)//传送的类型
                    .addFormDataPart(key, file.getName(), MultipartBody.create(MediaType.parse("application/octet-stream"), file)).build();
            Request.Builder builder = new Request.Builder();
            if (!TextUtils.isEmpty(cookie)) {
                builder.addHeader("cookie", cookie);
            }

            Request request = builder.post(body).url(url).build();

            Response response = okHttpClient.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return  null;
        }
    }


}
