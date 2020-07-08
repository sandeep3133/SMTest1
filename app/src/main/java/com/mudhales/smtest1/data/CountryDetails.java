package com.mudhales.smtest1.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CountryDetails {
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("rows")
    @Expose
    private List<CountryDescription> countryDescription = null;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<CountryDescription> getCountryDescription() {
        return countryDescription;
    }

    public void setCountryDescription(List<CountryDescription> countryDescription) {
        this.countryDescription = countryDescription;
    }
}
