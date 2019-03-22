package com.example.aupairapp.Services;

import com.example.aupairapp.Model.UserDto;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UserService {

    @GET("/users/{email}")
    Call<UserDto> getOneUser(@Path("email") String email);

    @GET("/users/me")
    Call<UserDto> getMe();

}
