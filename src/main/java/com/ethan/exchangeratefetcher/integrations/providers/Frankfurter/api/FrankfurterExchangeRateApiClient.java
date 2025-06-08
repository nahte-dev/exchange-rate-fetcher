package com.ethan.exchangeratefetcher.integrations.providers.Frankfurter.api;

import com.ethan.exchangeratefetcher.api.ApiConstants;
import com.ethan.exchangeratefetcher.integrations.providers.Frankfurter.api.contracts.FrankfurterExchangeRateContract;
import com.ethan.exchangeratefetcher.integrations.providers.api.ApiClient;

import java.util.Map;

public interface FrankfurterExchangeRateApiClient extends ApiClient {

    String PATH = ApiConstants.FRANKFURTER_API_URL;

    default FrankfurterExchangeRateContract getExchangeRate(final Map<String, String> params) {
        return get(PATH, params, FrankfurterExchangeRateContract.class);
    }
}
