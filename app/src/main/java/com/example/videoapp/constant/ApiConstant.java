package com.example.videoapp.constant;

import android.content.Context;


public class ApiConstant {
    public String API;
    private static com.example.videoapp.constant.ApiConstant sInstance;

    public ApiConstant(Context context) {
        API = BaseConstant.BASE_URL;
    }

    public static com.example.videoapp.constant.ApiConstant getsInstance(Context context) {
        synchronized (com.example.videoapp.constant.ApiConstant.class) {
            if (sInstance == null) {
                sInstance = new com.example.videoapp.constant.ApiConstant(context);
            }
        }
        return sInstance;
    }
}
