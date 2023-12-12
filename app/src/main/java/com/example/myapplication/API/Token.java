package com.example.myapplication.API;

import com.google.gson.annotations.SerializedName;

public class Token {
    @SerializedName("access_token")
    public String access_token;
    @SerializedName("token_type")
    public String token_type;

    public String getAccess_token() {
        return access_token;
    }
}
