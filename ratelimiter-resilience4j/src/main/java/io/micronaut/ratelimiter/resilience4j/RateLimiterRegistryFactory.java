/*
 * Copyright 2017-2019 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.micronaut.ratelimiter.resilience4j;

import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.micronaut.context.annotation.Factory;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A factory to create the {@link RateLimiterRegistry}.
 *
 * @author James Kleeh
 * @since 1.0.0
 */
@Factory
class RateLimiterRegistryFactory {

    /**
     * The registry factory method.
     *
     * @param registryConfiguration The registry configuration
     * @param rateLimiterConfigurations All rate limiter configurations
     * @return The rate limiter registry
     */
    @Singleton
    RateLimiterRegistry registry(
            RateLimiterRegistryConfiguration registryConfiguration,
            List<RateLimiterConfiguration> rateLimiterConfigurations) {
        Map<String, RateLimiterConfig> configMap = new HashMap<>(rateLimiterConfigurations.size());
        for (RateLimiterConfiguration config: rateLimiterConfigurations) {
            RateLimiterConfig.Builder builder = RateLimiterConfig.custom();
            configMap.put(config.getName(), buildConfig(builder, config));
        }

        io.vavr.collection.Map<String, String> tags = registryConfiguration.getTags().map(io.vavr.collection.HashMap::ofAll).orElse(io.vavr.collection.HashMap.empty());
        return RateLimiterRegistry.of(configMap, tags);
    }

    private RateLimiterConfig buildConfig(RateLimiterConfig.Builder builder, RateLimiterConfiguration config) {
        config.getLimit().map(Long::intValue).ifPresent(builder::limitForPeriod);
        config.getPeriod().ifPresent(builder::limitRefreshPeriod);
        config.getTimeout().ifPresent(builder::timeoutDuration);
        return builder.build();
    }

}
