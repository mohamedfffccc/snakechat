package com.example.snakechat.data.local;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientApi {
    public static  String BASE_URL = "http://ipda3-tech.com/blood-bank/api/v1/";
    private static Retrofit retrofit = null;
    private static Retrofit retrofit2 = null;

    public static Retrofit GetClient()
    {
        if (retrofit==null)
        {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
    public static Retrofit GetNews()
    {
        if (retrofit2==null)
        {
            retrofit2 = new Retrofit.Builder().baseUrl("https://newsapi.org/v2/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit2;
    }


}
