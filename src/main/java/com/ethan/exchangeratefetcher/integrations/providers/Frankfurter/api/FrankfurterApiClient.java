package com.ethan.exchangeratefetcher.integrations.providers.Frankfurter.api;

import com.ethan.exchangeratefetcher.api.contracts.ApiMetricsContract;
import org.springframework.lang.CheckReturnValue;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@Component
public class FrankfurterApiClient implements FrankfurterExchangeRateApiClient {

    private int totalRuntimeRequests = 0;

    private final RestClient restClient;

    public FrankfurterApiClient(final RestClient.Builder restClientBuilder) {
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

        if (params != null && !params.isEmpty()) {
            final UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(path);

            builder.queryParam("base", params.get("baseCurrency"));
            builder.queryParam("symbols", params.get("requestedRates"));

            return this.restClient.get().uri(builder.toUriString()).retrieve().body(responseType);
        }

        return this.restClient.get().uri(path).retrieve().body(responseType);
    }

    public ApiMetricsContract getApiMetrics() {
        final Map<String, String> metrics = new HashMap<>();
        metrics.put("totalRequests", String.valueOf(totalRuntimeRequests));

        final ApiMetricsContract apiMetrics = new ApiMetricsContract();
        apiMetrics.setName("Frankfurter");
        apiMetrics.setMetrics(metrics);

        return apiMetrics;
    }
}
