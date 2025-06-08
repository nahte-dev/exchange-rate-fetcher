package com.ethan.exchangeratefetcher.services;

import com.ethan.exchangeratefetcher.api.contracts.ExchangeRateContract;
import com.ethan.exchangeratefetcher.integrations.providers.Frankfurter.api.FrankfurterApiClient;
import com.ethan.exchangeratefetcher.integrations.providers.Frankfurter.api.contracts.FrankfurterExchangeRateContract;
import com.ethan.exchangeratefetcher.integrations.providers.fawazahmed0.api.Fawazahmed0ApiClient;
import com.ethan.exchangeratefetcher.integrations.providers.fawazahmed0.api.contracts.Fawazahmed0ExchangeRateContract;
import org.springframework.lang.CheckReturnValue;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ExchangeRateService {

    private final FrankfurterApiClient frankfurterApiClient;
    private final Fawazahmed0ApiClient fawazahmed0ApiClient;
    private final MetricsService metricsService;

    private final Map<Map<String, String>, ExchangeRateContract> cachedExchangeRates;

    public ExchangeRateService(
        final FrankfurterApiClient frankfurterApiClient,
        final Fawazahmed0ApiClient fawazahmed0ApiClient,
        final MetricsService metricsService) {

        this.frankfurterApiClient = frankfurterApiClient;
        this.fawazahmed0ApiClient = fawazahmed0ApiClient;
        this.metricsService = metricsService;

        this.cachedExchangeRates = new HashMap<>();
    }

    @CheckReturnValue
    public ExchangeRateContract getExchangeRate(
        @Nullable
        final String baseCurrency,
        @Nullable
        final List<String> requestedRates) {

        final Map<String, String> params = parseRequestedRates(baseCurrency, requestedRates);

        if (this.cachedExchangeRates.containsKey(params)) {
            this.metricsService.updateRuntimeQueryCount();
            return this.cachedExchangeRates.get(params);
        }

        final FrankfurterExchangeRateContract frankfurterExchangeRate = this.frankfurterApiClient.getExchangeRate(params);
        final Fawazahmed0ExchangeRateContract fawazahmed0ExchangeRate = this.fawazahmed0ApiClient.getExchangeRate(params);

        final Map<String, List<BigDecimal>> exchangeRatesByLabel = Stream.of(
                frankfurterExchangeRate.getConvertedRates(), fawazahmed0ExchangeRate.getConvertedRates().get(baseCurrency))
                .flatMap(map -> map.entrySet().stream())
                .collect(Collectors.groupingBy(
                    e -> e.getKey().toLowerCase(), Collectors.mapping(Map.Entry::getValue, Collectors.toList())));

        final Map<String, BigDecimal> averageExchangeRatesByLabel = exchangeRatesByLabel.entrySet().stream()
            .filter(k -> {
                if (params.containsKey("requestedRates")) {
                    return Arrays.stream(params.get("requestedRates").split(",")).anyMatch(r -> Objects.equals(r, k.getKey()));
                } else {
                    return true;
                }
            })
            .collect(Collectors.toMap(Map.Entry::getKey, m -> calculateAverageRate(m.getValue())));

        final ExchangeRateContract exchangeRateContract = new ExchangeRateContract();
        exchangeRateContract.setProvidedBaseCurrency(baseCurrency);
        exchangeRateContract.setConvertedRates(averageExchangeRatesByLabel);

        this.cachedExchangeRates.put(params, exchangeRateContract);
        this.metricsService.updateRuntimeQueryCount();

        return exchangeRateContract;
    }

    @CheckReturnValue
    private BigDecimal calculateAverageRate(
        final List<BigDecimal> rates) {

        final BigDecimal sum = rates.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        return sum.divide(BigDecimal.valueOf(rates.size()), RoundingMode.HALF_UP);
    }

    @CheckReturnValue
    private Map<String, String> parseRequestedRates(final String baseCurrency, final List<String> requestedRates) {
        final Map<String, String> params = new HashMap<>();

        params.put("baseCurrency", baseCurrency);

        if (requestedRates != null && !requestedRates.isEmpty()) {
            params.put("requestedRates", requestedRates.stream().map(
                String::toLowerCase).collect(Collectors.joining(",")));
        }

        return params;
    }
}
