package com.sohail.events;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by SOHAIL on 10/02/17.
 */

public interface RegistrationApi {

    @GET("/api?id=1eCEO9rrOvpN21tQiSd1OYBMSyQXiebE8KKhss5hQkMA&")
    Call<Registration> getRows(@Query("q") String EventName);
}
