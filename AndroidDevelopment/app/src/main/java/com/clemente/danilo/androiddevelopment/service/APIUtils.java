package com.clemente.danilo.androiddevelopment.service;

import retrofit2.Retrofit;

/**
 * Created by HP on 23/07/2017.
 */

public class APIUtils {
    public static final String BASE_URL = "http://www.mocky.io/v2/";

    public static APIDataBase getAPIDataBaseVersion() {

        return RetrofitClient.getClient(BASE_URL).create(APIDataBase.class);
    }

}
