package io.micronaut.circuitbreaker.resilience4j;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.DEFAULT_MINIMUM_NUMBER_OF_CALLS;
import static io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.DEFAULT_SLIDING_WINDOW_SIZE;

@Singleton
public class CircuitBreakerRegistryFactory {
    @Singleton
    CircuitBreakerRegistry registry(
        CircuitBreakerRegistryConfiguration registryConfiguration,
        List<CircuitBreakerConfiguration> circuitBreakerConfigurations
    ) {
        Map<String, CircuitBreakerConfig> configMap = new HashMap<>(circuitBreakerConfigurations.size());
        for (CircuitBreakerConfiguration config : circuitBreakerConfigurations) {
            CircuitBreakerConfig.Builder builder = CircuitBreakerConfig.custom();
            configMap.put(config.getName(), buildConfig(builder, config));
        }

        io.vavr.collection.Map<String, String> tags = registryConfiguration.getTags().map(io.vavr.collection.HashMap::ofAll).orElse(io.vavr.collection.HashMap.empty());
        return CircuitBreakerRegistry.of(configMap, tags);
    }

    private CircuitBreakerConfig buildConfig(CircuitBreakerConfig.Builder builder, CircuitBreakerConfiguration config) {
        config.getWindowType().ifPresent(c -> builder.slidingWindow(
            config.getSlidingWindowSize().orElse(DEFAULT_SLIDING_WINDOW_SIZE),
            config.getMinimumNumberOfCalls().orElse(DEFAULT_MINIMUM_NUMBER_OF_CALLS), c));

        config.getHalfOpenState().ifPresent(h -> {
            h.getAutomaticallyTransitionToHalfopen().ifPresent(builder::automaticTransitionFromOpenToHalfOpenEnabled);
            h.getPermittedNumberOfCalls().ifPresent(builder::permittedNumberOfCallsInHalfOpenState);
        });
        config.getSlowCallRateThreshold().ifPresent(builder::slowCallRateThreshold);
        config.getSlowCallDurationThreshold().ifPresent(builder::slowCallDurationThreshold);
        config.getFailureRateThreshold().ifPresent(builder::failureRateThreshold);
        return builder.build();
    }
}
