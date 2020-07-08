package com.mudhales.smtest1.remote;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public class RetrofitInterfaces {
    // Created interface to fetch list data by SM
    public interface IGetInfoData {
        @GET("IGetInfoData")
        Call<ResponseBody> getInfo();
    }
}
