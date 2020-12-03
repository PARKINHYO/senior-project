package com.example.senierproject.Retrofit;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface INodeJS {
    @POST("register")
    @FormUrlEncoded
    io.reactivex.Observable<String> registerUser(@Field("email") String email,
                                                 @Field("name") String name,
                                                 @Field("password") String password);
    @POST("login")
    @FormUrlEncoded
    io.reactivex.Observable<String> loginUser(@Field("email") String email,
                                              @Field("password") String password);

    @POST("registerChild")
    @FormUrlEncoded
    io.reactivex.Observable<String> registerChild(@Field("email") String email,
                                                  @Field("device_name") String device_name,
                                                  @Field("c_name") String c_name,
                                                  @Field("c_sex") String c_sex,
                                                  @Field("c_height") String c_height,
                                                  @Field("c_weight") String c_weight,
                                                  @Field("c_age") String c_age);
    //@POST("checkDevice")
    //@FormUrlEncoded
    //io.reactivex.Observable<String> checkDevice(@SafeParcelable.Field("email") String email);
}
