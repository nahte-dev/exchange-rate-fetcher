package com.ethan.exchangeratefetcher.services;

import com.ethan.exchangeratefetcher.api.contracts.MetricsContract;
import com.ethan.exchangeratefetcher.integrations.providers.Frankfurter.api.FrankfurterApiClient;
import com.ethan.exchangeratefetcher.integrations.providers.fawazahmed0.api.Fawazahmed0ApiClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MetricsService {

    private int runtimeQueryCount = 0;

    private final FrankfurterApiClient frankfurterApiClient;
    private final Fawazahmed0ApiClient fawazahmed0ApiClient;

    public MetricsService(
        final FrankfurterApiClient frankfurterApiClient,
        final Fawazahmed0ApiClient fawazahmed0ApiClient) {

        this.frankfurterApiClient = frankfurterApiClient;
        this.fawazahmed0ApiClient = fawazahmed0ApiClient;
    }

    public void updateRuntimeQueryCount() {
        this.runtimeQueryCount++;
    }

    public MetricsContract getMetrics() {
        final MetricsContract metricsContract = new MetricsContract();
        metricsContract.setTotalQueries(String.valueOf(runtimeQueryCount));
        metricsContract.setApiMetrics(List.of(
            this.frankfurterApiClient.getApiMetrics(), this.fawazahmed0ApiClient.getApiMetrics()));

        return metricsContract;
    }
}
