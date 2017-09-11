package com.clemente.danilo.androiddevelopment.service;

import com.clemente.danilo.androiddevelopment.model.User;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by HP on 23/07/2017.
 */

public interface APIDataBase {
    @GET("58b9b1740f0000b614f09d2f​")
    Call<User> getUser();
}
