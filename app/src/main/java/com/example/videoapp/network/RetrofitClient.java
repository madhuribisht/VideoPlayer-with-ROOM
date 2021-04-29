package com.example.videoapp.network;

import android.content.Context;
import com.example.videoapp.constant.ApiConstant;
import com.example.videoapp.daoInterface.RestApi;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static RetrofitClient instance = null;
    private RestApi restApi;
    private static HttpLoggingInterceptor loggingInterceptor;

    private RetrofitClient(final Context context) {
        try {
            loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();
            Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiConstant.getsInstance(context).API)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
            restApi = retrofit.create(RestApi.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static synchronized RetrofitClient getInstance(final Context context) {
        if (instance == null) {
            instance = new RetrofitClient(context);
        }
        return instance;
    }

    public RestApi getRestApi() {
        return restApi;
    }
}
