package com.example.version1;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ListJsonAPI {

    @GET("api/v1/test")
    Call<List<ListData>> getListData();
}
