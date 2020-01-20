package io.micronaut.ratelimiter.resilience4j;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.context.annotation.EachProperty;
import io.micronaut.core.convert.format.MapFormat;
import io.micronaut.core.naming.conventions.StringConvention;
import io.micronaut.core.util.Toggleable;
import io.vavr.collection.Map;

import java.util.Optional;

@ConfigurationProperties("resilience4j.ratelimiter")
public class RateLimiterRegistryConfiguration implements Toggleable {

    private boolean enabled;
    private Map<String, String> tags;

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Optional<Map<String, String>> getTags() {
        return Optional.ofNullable(tags);
    }

    public void setTags(@MapFormat(keyFormat = StringConvention.RAW) Map<String, String> tags) {
        this.tags = tags;
    }

}
