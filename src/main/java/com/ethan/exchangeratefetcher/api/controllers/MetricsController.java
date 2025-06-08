package com.ethan.exchangeratefetcher.api.controllers;

import com.ethan.exchangeratefetcher.api.contracts.MetricsContract;
import com.ethan.exchangeratefetcher.services.MetricsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MetricsController {

    private final static String METRICS_PATH = "/metrics";

    private final MetricsService metricsService;

    public MetricsController(final MetricsService metricsService) {
        this.metricsService = metricsService;
    }

    @RequestMapping(value = METRICS_PATH, method = RequestMethod.GET)
    public MetricsContract getExchangeRates() {
        return this.metricsService.getMetrics();
    }
}
