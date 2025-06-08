package com.ethan.exchangeratefetcher.integrations.providers.fawazahmed0.api;

import com.ethan.exchangeratefetcher.api.ApiConstants;
import com.ethan.exchangeratefetcher.integrations.providers.api.ApiClient;
import com.ethan.exchangeratefetcher.integrations.providers.fawazahmed0.api.contracts.Fawazahmed0ExchangeRateContract;

import java.util.Map;

import static com.ethan.exchangeratefetcher.api.ApiConstants.DEFAULT_BASE_CURRENCY;

public interface Fawazahmed0ExchangeRateApiClient extends ApiClient {

    String PATH = ApiConstants.FAWAZAHMED0_API_URL;

    default Fawazahmed0ExchangeRateContract getExchangeRate(final Map<String, String> requestedRate) {
        final String baseCurrency = requestedRate.getOrDefault("baseCurrency", DEFAULT_BASE_CURRENCY);
        return get(PATH + baseCurrency + ".json", null, Fawazahmed0ExchangeRateContract.class);
    }
}
