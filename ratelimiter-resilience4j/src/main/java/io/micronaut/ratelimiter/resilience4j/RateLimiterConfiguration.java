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

import io.micronaut.context.annotation.EachProperty;
import io.micronaut.context.annotation.Parameter;

import javax.annotation.Nonnull;
import java.time.Duration;
import java.util.Optional;

/**
 * Configuration for pre-defined rate limiters.
 *
 * @author James Kleeh
 * @since 1.0.0
 */
@EachProperty(value = "resilience4j.ratelimiter.configurations", primary = "default")
public class RateLimiterConfiguration {

    private final String name;
    private Duration period;
    private Long limit;
    private Duration timeout;

    /**
     * @param name The name of the configuration
     */
    public RateLimiterConfiguration(@Parameter String name) {
        this.name = name;
    }

    /**
     * @return The name of the configuration
     */
    @Nonnull
    public String getName() {
        return name;
    }

    /**
     * @return The optional rate limit duration
     */
    @Nonnull
    public Optional<Duration> getPeriod() {
        return Optional.ofNullable(period);
    }

    /**
     * @param period The refresh period of the rate limit
     */
    public void setPeriod(Duration period) {
        this.period = period;
    }

    /**
     * @return The optional request count limit
     */
    @Nonnull
    public Optional<Long> getLimit() {
        return Optional.ofNullable(limit);
    }

    /**
     * @param limit How many requests are allowed in the period
     */
    public void setLimit(Long limit) {
        this.limit = limit;
    }

    /**
     * @return The timeout to retrieve rate limit permission
     */
    @Nonnull
    public Optional<Duration> getTimeout() {
        return Optional.ofNullable(timeout);
    }

    /**
     * @param timeout How long to wait for rate limit permission
     */
    public void setTimeout(Duration timeout) {
        this.timeout = timeout;
    }

}
