package com.mudhales.smtest1.fragment;

import android.app.Application;

import com.mudhales.smtest1.data.CountryDetails;
import com.mudhales.smtest1.repository.ListRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class ListDataViewModel extends AndroidViewModel {
    private ListRepository listRepository;
    private LiveData<CountryDetails> listResponseLiveData;
    public ListDataViewModel(@NonNull Application application) {
        super(application);
        listRepository = new ListRepository();
    }
    public LiveData<CountryDetails> getListResponseLiveData() {
        listResponseLiveData = listRepository.checkInfoDetails();
        return listResponseLiveData;
    }
}
