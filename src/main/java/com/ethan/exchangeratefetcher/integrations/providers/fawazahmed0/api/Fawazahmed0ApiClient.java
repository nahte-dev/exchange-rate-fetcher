package com.ethan.exchangeratefetcher.integrations.providers.fawazahmed0.api;

import com.ethan.exchangeratefetcher.api.contracts.ApiMetricsContract;
import org.springframework.lang.CheckReturnValue;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.HashMap;
import java.util.Map;

@Component
public class Fawazahmed0ApiClient implements Fawazahmed0ExchangeRateApiClient {

    private int totalRuntimeRequests = 0;

    private final RestClient restClient;

    public Fawazahmed0ApiClient(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder.baseUrl(PATH).build();
    }

    @Override
    @CheckReturnValue
    @Nullable
    public <T> T get(
        @NonNull
        final String path,
        @Nullable
        final Map<String, String> params,
        @NonNull
        final Class<T> responseType) {
        totalRuntimeRequests++;
        return this.restClient.get().uri(path).retrieve().body(responseType);
    }

    public ApiMetricsContract getApiMetrics() {
        final Map<String, String> metrics = new HashMap<>();
        metrics.put("totalRequests", String.valueOf(totalRuntimeRequests));

        final ApiMetricsContract apiMetrics = new ApiMetricsContract();
        apiMetrics.setName("fawazahmed0");
        apiMetrics.setMetrics(metrics);

        return apiMetrics;
    }
}
