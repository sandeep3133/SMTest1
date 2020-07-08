package com.mudhales.smtest1.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mudhales.smtest1.BuildConfig;
import com.mudhales.smtest1.data.CountryDetails;
import com.mudhales.smtest1.remote.RetrofitClient;
import com.mudhales.smtest1.remote.RetrofitInterfaces;

import java.io.IOException;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListRepository {
    private static final String TAG = ListRepository.class.getSimpleName();
    private RetrofitInterfaces.IGetInfoData apiRequest;
    public ListRepository() {
        apiRequest = RetrofitClient.getClient(BuildConfig.SERVER_URL).create(RetrofitInterfaces.IGetInfoData.class);
    }

    // To get data from server by SM 201912111435
    public LiveData<CountryDetails> checkInfoDetails() {
        final MutableLiveData<CountryDetails> data = new MutableLiveData<>();
        apiRequest.getInfo()
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.body() != null) {
                            try {
                                ResponseBody rb = response.body();
                                String result = rb.string();
                                //  Log.e("====result===","===result===="+result);
                                CountryDetails countryDetails = new Gson().fromJson(result, new TypeToken<CountryDetails>() {
                                }.getType());
                                data.setValue(countryDetails);
                            } catch (IOException e) {
                                e.printStackTrace();
                                data.setValue(null);
                            }

                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        data.setValue(null);
                    }
                });
        return data;
    }
}
