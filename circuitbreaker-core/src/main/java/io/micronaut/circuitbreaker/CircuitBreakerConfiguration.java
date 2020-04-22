package io.micronaut.circuitbreaker;

import io.micronaut.context.annotation.ConfigurationProperties;

import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;


@ConfigurationProperties("micronaut.circuitbreaker")
public class CircuitBreakerConfiguration {
    public static final String PATH_KEY = "micronaut.circuitbreaker.paths";

    private List<String> paths = Collections.emptyList();
    private boolean ipAddressResolver = false;

    @Nonnull
    public List<String> getPaths() {
        return paths;
    }

    public void setPaths(@Nonnull List<String> paths) {
        this.paths = paths;
    }

    public boolean isIpAddressResolver() {
        return ipAddressResolver;
    }

    public void setIpAddressResolver(boolean ipAddressResolver) {
        this.ipAddressResolver = ipAddressResolver;
    }
}
