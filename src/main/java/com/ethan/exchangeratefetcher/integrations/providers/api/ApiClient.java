package com.ethan.exchangeratefetcher.integrations.providers.api;

import org.springframework.lang.CheckReturnValue;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Map;

public interface ApiClient {

    @CheckReturnValue
    @Nullable
    <T> T get(
        @NonNull
        final String path,
        @Nullable
        final Map<String, String> params,
        @NonNull
        final Class<T> responseType);
}
