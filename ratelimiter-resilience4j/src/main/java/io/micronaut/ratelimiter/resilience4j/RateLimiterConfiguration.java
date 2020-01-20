package io.micronaut.ratelimiter.resilience4j;

import io.micronaut.context.annotation.EachProperty;
import io.micronaut.context.annotation.Parameter;

import javax.annotation.Nonnull;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@EachProperty(value = "resilience4j.ratelimiter.configurations", primary = "default")
public class RateLimiterConfiguration {

    private final String name;
    private Duration period;
    private Long limit;
    private Duration timeout;
    private List<String> tags = Collections.emptyList();

    public RateLimiterConfiguration(@Parameter String name) {
        this.name = name;
    }

    @Nonnull
    public String getName() {
        return name;
    }

    public boolean isPrimary() {
        return name.equals("default");
    }

    @Nonnull
    public Optional<Duration> getPeriod() {
        return Optional.ofNullable(period);
    }

    public void setPeriod(Duration period) {
        this.period = period;
    }

    @Nonnull
    public Optional<Long> getLimit() {
        return Optional.ofNullable(limit);
    }

    public void setLimit(Long limit) {
        this.limit = limit;
    }

    @Nonnull
    public Optional<Duration> getTimeout() {
        return Optional.ofNullable(timeout);
    }

    public void setTimeout(Duration timeout) {
        this.timeout = timeout;
    }

    @Nonnull
    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
