package com.clemente.danilo.androiddevelopment.service;

import com.clemente.danilo.androiddevelopment.model.PlaceMarket;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by HP on 26/08/2017.
 */

public interface RetrofitMaps {
    /*
  * Retrofit get annotation with our URL
  * And our method that will return us details of student.
  */
    @GET("api/place/nearbysearch/json?sensor=true&key=AIzaSyDYdFIKFjUyUIt623xUutXjmE9IHr1nEPo")
    Call<PlaceMarket> getNearbyPlaces(@Query("type") String type, @Query("location") String location, @Query("radius") int radius);

}
