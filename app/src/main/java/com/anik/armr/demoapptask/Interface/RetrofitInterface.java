package com.anik.armr.demoapptask.Interface;

import com.anik.armr.demoapptask.ModelClass.User;
import com.anik.armr.demoapptask.ModelClass.Vehicle;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitInterface {

    @GET("yuma/location/query/user/677d3c5b-14de-4e38-a767-9678fc2f5c9d/last")
    Call<ArrayList<Vehicle>> getVehicleLocationData(
            @Header("Content-Type") String contentType,
            @Header("YumaSession") String sessionID,
            @Query("Lon") double lat,
            @Query("lat") double lon,
            @Query("heading") double heading,
            @Query("Speed") double speed,
            @Query("location_name") String location_name
    );

    @POST ("yuma/access/login")
    Call<User> UserLogInPost();

}
