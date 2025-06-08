package com.ethan.exchangeratefetcher.api.contracts;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Map;

public class ExchangeRateContract {

    @JsonProperty(value = "base")
    private String providedBaseCurrency;

    @JsonProperty(value = "rates")
    private Map<String, BigDecimal> convertedRates;

    public String getProvidedBaseCurrency() {
        return providedBaseCurrency;
    }

    public void setProvidedBaseCurrency(final String providedBaseCurrency) {
        this.providedBaseCurrency = providedBaseCurrency;
    }

    public Map<String, BigDecimal> getConvertedRates() {
        return convertedRates;
    }

    public void setConvertedRates(final Map<String, BigDecimal> convertedRates) {
        this.convertedRates = convertedRates;
    }
}
