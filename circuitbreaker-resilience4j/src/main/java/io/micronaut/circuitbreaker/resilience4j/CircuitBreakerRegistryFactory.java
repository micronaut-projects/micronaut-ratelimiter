package io.micronaut.circuitbreaker.resilience4j;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Singleton
public class CircuitBreakerRegistryFactory {
    @Singleton
    CircuitBreakerRegistry registry (
        CircuitBreakerRegistryConfiguration registryConfiguration,
        List<CircuitBreakerConfiguration> circuitBreakerConfigurations
    ) {
        Map<String, CircuitBreakerConfig> configMap = new HashMap<>(circuitBreakerConfigurations.size());
        for (CircuitBreakerConfiguration config : circuitBreakerConfigurations) {
            CircuitBreakerConfig.Builder builder = CircuitBreakerConfig.custom();
            configMap.put(config.getName(), buildConfig(builder, config));
        }

        io.vavr.collection.Map<String, String> tags = null;
        return CircuitBreakerRegistry.of(configMap, tags);
    }

    private CircuitBreakerConfig buildConfig(CircuitBreakerConfig.Builder  builder, CircuitBreakerConfiguration config) {
        return builder.build();
    }
}
