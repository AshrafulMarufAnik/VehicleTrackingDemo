package com.anik.armr.demoapptask.Interface;

import com.anik.armr.demoapptask.ModelClass.User;
import com.anik.armr.demoapptask.ModelClass.Vehicle;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface RetrofitInterface {

    @GET("yuma/location/query/user/677d3c5b-14de-4e38-a767-9678fc2f5c9d/last")
    Call<Vehicle> getVehicleData();

    @POST ("yuma/access/login")
    Call<User> UserLogInPost();

}
