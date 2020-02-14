package com.example.snakechat.data.local;

import com.example.snakechat.data.model.login.Login;
import com.example.snakechat.data.model.news.News;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserApi {
    @POST("login")
    @FormUrlEncoded
    Call<Login> LoginUser(@Field("phone") String phone,
                          @Field("password") String password);

    @POST("signup")
    @FormUrlEncoded
    Call<Login> SignUp(@Field("name") String name, @Field("email") String email,
                       @Field("birth_date") String birth_date, @Field("city_id") int city_id,
                       @Field("phone") String phone, @Field("donation_last_date") String donation_last_date,
                       @Field("password") String password, @Field("password_confirmation") String password_confirmation,
                       @Field("blood_type_id") int blood_type_id);
    @POST("reset-password")
    @FormUrlEncoded
    Call<Login> restrPass(@Field("phone") String phone
                      );
    @GET("top-headlines")
    Call<News> getNews(@Query("country") String country ,
                       @Query("apiKey") String apiKey);
    @GET("everything")
    Call<News> filterNews(@Query("q") String query ,
                       @Query("from") String from ,
                       @Query("sortBy") String sortBy ,
                       @Query("apikey") String apikey);






}
