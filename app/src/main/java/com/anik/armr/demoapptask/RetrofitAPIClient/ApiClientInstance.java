package com.anik.armr.demoapptask.RetrofitAPIClient;

import com.anik.armr.demoapptask.Interface.RetrofitInterface;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClientInstance {
    private final String BASE_URL = "https://prod.yuma-technology.co.uk:8443/";
    private ApiClientInstance clientInstance;
    private Retrofit retrofit;
    private final String CONTENT_TYPE = "application/json";
    private String SESSION_ID = "73e2f077-042a-4b03-8248-d1c6b47d691e";

    public ApiClientInstance() {

        OkHttpClient httpClient = new OkHttpClient();
        httpClient.networkInterceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder requestBuilder = chain.request().newBuilder();
                requestBuilder.header("Content-Type",CONTENT_TYPE);
                requestBuilder.header("YumaSession",SESSION_ID);
                return chain.proceed(requestBuilder.build());
            }
        });

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public ApiClientInstance getInstance(){
        if(clientInstance == null){
            clientInstance = new ApiClientInstance();
        }
        return  clientInstance;
    }

    public RetrofitInterface getApiData(){
        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);
        return retrofitInterface;
    }
}
