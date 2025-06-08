package com.ethan.exchangeratefetcher.api.controllers;

import com.ethan.exchangeratefetcher.api.contracts.ExchangeRateContract;
import com.ethan.exchangeratefetcher.services.ExchangeRateService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.ethan.exchangeratefetcher.api.ApiConstants.DEFAULT_BASE_CURRENCY;

@RestController
public class ExchangeRateController {

    private static final String EXCHANGE_RATES_PATH = "/exchangeRates";
    private static final String EXCHANGE_RATE_PATH = EXCHANGE_RATES_PATH + "/{baseCurrency}";

    private final ExchangeRateService exchangeRateService;

    public ExchangeRateController(final ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    @RequestMapping(value = EXCHANGE_RATES_PATH, method = RequestMethod.GET)
    public ExchangeRateContract getExchangeRates() {
        return this.exchangeRateService.getExchangeRate(DEFAULT_BASE_CURRENCY, null);
    }

    @RequestMapping(value = EXCHANGE_RATE_PATH, method = RequestMethod.GET)
    public ExchangeRateContract getExchangeRate(
        @PathVariable(name = "baseCurrency")
        final String baseCurrency,
        @RequestParam(name = "symbols", required = false)
        final List<String> symbols) {

        return this.exchangeRateService.getExchangeRate(baseCurrency.toLowerCase(), symbols);
    }
}
