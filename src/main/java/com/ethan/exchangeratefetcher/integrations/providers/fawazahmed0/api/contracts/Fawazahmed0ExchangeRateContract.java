package com.ethan.exchangeratefetcher.integrations.providers.fawazahmed0.api.contracts;

import com.ethan.exchangeratefetcher.api.contracts.AbstractContract;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.math.BigDecimal;
import java.util.Map;

public class Fawazahmed0ExchangeRateContract extends AbstractContract {

    @JsonAnyGetter
    @JsonAnySetter
    private Map<String, Map<String, BigDecimal>> convertedRates;

    public Map<String, Map<String, BigDecimal>> getConvertedRates() {
        return convertedRates;
    }

    public void setConvertedRates(final Map<String, Map<String, BigDecimal>> convertedRates) {
        this.convertedRates = convertedRates;
    }
}
