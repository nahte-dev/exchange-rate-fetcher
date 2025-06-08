package com.ethan.exchangeratefetcher.api.contracts;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class MetricsContract {

    @JsonProperty("totalQueries")
    private String totalQueries;

    @JsonProperty("apis")
    private List<ApiMetricsContract> apiMetrics;

    public String getTotalQueries() {
        return totalQueries;
    }

    public void setTotalQueries(final String totalQueries) {
        this.totalQueries = totalQueries;
    }

    public List<ApiMetricsContract> getApiMetrics() {
        return apiMetrics;
    }

    public void setApiMetrics(final List<ApiMetricsContract> apiMetrics) {
        this.apiMetrics = apiMetrics;
    }
}
