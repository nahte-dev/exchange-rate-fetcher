package com.ethan.exchangeratefetcher.api.contracts;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class AbstractContract {

    @JsonProperty(value = "date")
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
