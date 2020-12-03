package com.example.senierproject.Retrofit;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitClient {
    private static Retrofit instance;

    public static Retrofit getInstance() {
       if(instance == null)
           instance = new Retrofit.Builder()
                   .baseUrl("http://18.224.2.233:3306/")
                   .addConverterFactory(ScalarsConverterFactory.create())
                   .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                   .build();
        return instance;
    }
}
