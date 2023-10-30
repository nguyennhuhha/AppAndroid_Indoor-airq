package com.example.myapplication.RestAPI;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIInterface {
    @FormUrlEncoded
    @POST("auth/realms/master/protocol/openid-connect/token")
    @Headers({
            "Accept: application/json",
            "Content-Type: application/x-www-form-urlencoded",
    })
    Call<ResponseBody> getToken(@Field("client_id") String client_id,
                                @Field("username") String username,
                                @Field("password") String password,
                                @Field("grant_type") String grant_type);
}
