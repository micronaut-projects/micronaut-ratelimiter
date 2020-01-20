package io.micronaut.ratelimiter.resilience4j;

import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.micronaut.context.annotation.Factory;

import javax.inject.Singleton;
import java.util.*;

@Factory
class RateLimiterRegistryFactory {

    @Singleton
    RateLimiterRegistry registry(List<RateLimiterConfiguration> rateLimiterConfigurations) {
        Map<String, RateLimiterConfig> configMap = new HashMap<>(rateLimiterConfigurations.size());
        for (RateLimiterConfiguration config: rateLimiterConfigurations) {
            RateLimiterConfig.Builder builder = RateLimiterConfig.custom();
            configMap.put(config.getName(), buildConfig(builder, config));
        }

        if (configMap.isEmpty()) {
            return RateLimiterRegistry.ofDefaults();
        } else {
            return RateLimiterRegistry.of(configMap);
        }
    }

    private RateLimiterConfig buildConfig(RateLimiterConfiguration config) {
        RateLimiterConfig.Builder builder = RateLimiterConfig.custom();
        return buildConfig(builder, config);
    }

    private RateLimiterConfig buildConfig(RateLimiterConfig.Builder builder, RateLimiterConfiguration config) {
        config.getLimit().map(Long::intValue).ifPresent(builder::limitForPeriod);
        config.getPeriod().ifPresent(builder::limitRefreshPeriod);
        config.getTimeout().ifPresent(builder::timeoutDuration);
        return builder.build();
    }

}
