package com.example.myapplication.Model;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class User {
    @SerializedName("username")
    public String username;
    @SerializedName("id")
    public String id;
    @SerializedName("firstName")
    public String firstName = "";
    @SerializedName("lastName")
    public String lastName = "";
    @SerializedName("email")
    public String email = "";

    // Current logged in user
    private static User me;
    public static void setMe(User u) {
        me = u;
    }
    public static User getMe() {
        return me;
    }
}
