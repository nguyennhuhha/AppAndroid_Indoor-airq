package com.example.myapplication.RestAPI;

import com.google.gson.annotations.SerializedName;

public class tokenResponse {
    @SerializedName("access_token")
    public String access_token;
    @SerializedName("token_type")
    public String token_type;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }
}
