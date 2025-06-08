package com.ethan.exchangeratefetcher.api.contracts;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class ApiMetricsContract {

    @JsonProperty("name")
    private String name;

    @JsonProperty("metrics")
    private Map<String, String> metrics;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Map<String, String> getMetrics() {
        return metrics;
    }

    public void setMetrics(final Map<String, String> metrics) {
        this.metrics = metrics;
    }
}
