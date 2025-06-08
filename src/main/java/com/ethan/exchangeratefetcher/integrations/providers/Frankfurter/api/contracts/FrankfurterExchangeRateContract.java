package com.ethan.exchangeratefetcher.integrations.providers.Frankfurter.api.contracts;

import com.ethan.exchangeratefetcher.api.contracts.AbstractContract;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Map;

public class FrankfurterExchangeRateContract extends AbstractContract {

    @JsonProperty(value = "base")
    private String baseCurrency;

    @JsonProperty(value = "rates")
    private Map<String, BigDecimal> convertedRates;

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(final String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public Map<String, BigDecimal> getConvertedRates() {
        return convertedRates;
    }

    public void setConvertedRates(final Map<String, BigDecimal> convertedRates) {
        this.convertedRates = convertedRates;
    }
}
