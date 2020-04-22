package io.micronaut.circuitbreaker.resilience4j;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.core.convert.format.MapFormat;
import io.micronaut.core.naming.conventions.StringConvention;
import io.micronaut.core.util.Toggleable;

import java.util.Map;

@ConfigurationProperties("resilience4j.circuitbreaker")
public class CircuitBreakerRegistryConfiguration implements Toggleable {

    protected boolean enabled;
    private Map<String, String> tags;

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setTags(@MapFormat(keyFormat = StringConvention.RAW) Map<String, String> tags) {
        this.tags = tags;
    }
}
