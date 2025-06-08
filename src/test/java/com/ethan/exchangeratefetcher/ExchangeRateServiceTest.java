package com.ethan.exchangeratefetcher;

import com.ethan.exchangeratefetcher.api.contracts.ExchangeRateContract;
import com.ethan.exchangeratefetcher.integrations.providers.Frankfurter.api.FrankfurterApiClient;
import com.ethan.exchangeratefetcher.integrations.providers.Frankfurter.api.contracts.FrankfurterExchangeRateContract;
import com.ethan.exchangeratefetcher.integrations.providers.fawazahmed0.api.Fawazahmed0ApiClient;
import com.ethan.exchangeratefetcher.integrations.providers.fawazahmed0.api.contracts.Fawazahmed0ExchangeRateContract;
import com.ethan.exchangeratefetcher.services.ExchangeRateService;
import com.ethan.exchangeratefetcher.services.MetricsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.when;

@SpringJUnitConfig(ExchangeRateService.class)
public class ExchangeRateServiceTest {

    private static final Map<String, BigDecimal> CONVERSION_RATES = createConversionRates();
    private static final Map<String, BigDecimal> CONVERSION_RATES2 = createConversionRates2();

    @Autowired
    private ExchangeRateService exchangeRateService;

    @MockitoBean
    private FrankfurterApiClient frankfurterApiClient;

    @MockitoBean
    private Fawazahmed0ApiClient fawazahmed0ApiClient;

    @MockitoBean
    private MetricsService metricsService;

    @Test
    public void testGetExchangeRate_ShouldReturnDefaultExchangeRates() {
        final FrankfurterExchangeRateContract frankFurterContract = createFrankfurterContract(CONVERSION_RATES);
        final Fawazahmed0ExchangeRateContract fawazahmed0Contract = createFawazahmed0Contract("nzd", CONVERSION_RATES);

        when(this.frankfurterApiClient.getExchangeRate(anyMap())).thenReturn(frankFurterContract);
        when(this.fawazahmed0ApiClient.getExchangeRate(anyMap())).thenReturn(fawazahmed0Contract);

        final ExchangeRateContract actual = this.exchangeRateService.getExchangeRate("nzd", null);

        assertThat(actual.getProvidedBaseCurrency()).isEqualTo("nzd");
        assertThat(actual.getConvertedRates()).isEqualTo(CONVERSION_RATES);
    }

    @Test
    public void testGetExchangeRateWithMultipleRates_ShouldReturnAveragedExchangeRates() {
        final FrankfurterExchangeRateContract frankFurterContract = createFrankfurterContract(CONVERSION_RATES);
        final Fawazahmed0ExchangeRateContract fawazahmed0Contract = createFawazahmed0Contract("btc", CONVERSION_RATES2);

        when(this.frankfurterApiClient.getExchangeRate(anyMap())).thenReturn(frankFurterContract);
        when(this.fawazahmed0ApiClient.getExchangeRate(anyMap())).thenReturn(fawazahmed0Contract);

        final ExchangeRateContract actual = this.exchangeRateService.getExchangeRate("btc", null);

        assertThat(actual.getProvidedBaseCurrency()).isEqualTo("btc");
        assertThat(actual.getConvertedRates().get("aud")).isEqualTo(BigDecimal.valueOf(1.4733));
        assertThat(actual.getConvertedRates().get("usd")).isEqualTo(BigDecimal.valueOf(1.5033));
        assertThat(actual.getConvertedRates().get("sek")).isEqualTo(BigDecimal.valueOf(1.9135));
        assertThat(actual.getConvertedRates().get("can")).isEqualTo(BigDecimal.valueOf(3.225));
    }

    @Test
    public void testGetExchangeRateWithSpecifiedRates_ShouldOnlyReturnRequestedExchangeRates() {
        final FrankfurterExchangeRateContract frankFurterContract = createFrankfurterContract(CONVERSION_RATES);
        final Fawazahmed0ExchangeRateContract fawazahmed0Contract = createFawazahmed0Contract("btc", CONVERSION_RATES2);

        final List<String> requestedRates = new ArrayList<>();
        requestedRates.add("aud");
        requestedRates.add("usd");

        when(this.frankfurterApiClient.getExchangeRate(anyMap())).thenReturn(frankFurterContract);
        when(this.fawazahmed0ApiClient.getExchangeRate(anyMap())).thenReturn(fawazahmed0Contract);

        final ExchangeRateContract actual = this.exchangeRateService.getExchangeRate("btc", requestedRates);

        assertThat(actual.getConvertedRates().size()).isEqualTo(2);
        assertThat(actual.getProvidedBaseCurrency()).isEqualTo("btc");
        assertThat(actual.getConvertedRates().get("aud")).isEqualTo(BigDecimal.valueOf(1.4733));
        assertThat(actual.getConvertedRates().get("usd")).isEqualTo(BigDecimal.valueOf(1.5033));
    }

    private static Map<String, BigDecimal> createConversionRates() {
        final Map<String, BigDecimal> conversionRates = new HashMap<>();
        conversionRates.put("aud", new BigDecimal("1.3315"));
        conversionRates.put("usd", new BigDecimal("1.715"));
        conversionRates.put("sek", new BigDecimal("1.4135"));
        conversionRates.put("can", new BigDecimal("1.425"));

        return conversionRates;
    }

    private static Map<String, BigDecimal> createConversionRates2() {
        final Map<String, BigDecimal> conversionRates = new HashMap<>();
        conversionRates.put("aud", new BigDecimal("1.615"));
        conversionRates.put("usd", new BigDecimal("1.2915"));
        conversionRates.put("sek", new BigDecimal("2.4135"));
        conversionRates.put("can", new BigDecimal("5.025"));

        return conversionRates;
    }

    private FrankfurterExchangeRateContract createFrankfurterContract(final Map<String, BigDecimal> conversionRates) {
        final FrankfurterExchangeRateContract contract = new FrankfurterExchangeRateContract();
        contract.setDate("2020-05-28");
        contract.setConvertedRates(conversionRates);

        return contract;
    }

    private Fawazahmed0ExchangeRateContract createFawazahmed0Contract(final String baseCurrency, final Map<String, BigDecimal> conversionRates) {
        final Map<String, Map<String, BigDecimal>> rates = new HashMap<>();
        rates.put(baseCurrency, conversionRates);

        final Fawazahmed0ExchangeRateContract contract = new Fawazahmed0ExchangeRateContract();
        contract.setDate("2020-05-28");
        contract.setConvertedRates(rates);

        return contract;
    }
}
