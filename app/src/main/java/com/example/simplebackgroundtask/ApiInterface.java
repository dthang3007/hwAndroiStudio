package com.example.simplebackgroundtask;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiInterface {
    @GET("users")
    Call<ArrayList<User>> getAllUsers();

    @GET("users/{id}")
    Call<User> getUsersByID(@Path("id") int id);

    String token = "eaa08b9c5a69d3e2b789545e22db2db77b99f0b0cec01662646bc74a99131c30";

    @POST("users?access-token=" + token)
    Call<User> addUser(@Body() User user);

    @PUT("users/{id}?access-token=" + token)
    Call<User> updateUser(@Path("id") int id, @Body() User user);
}
