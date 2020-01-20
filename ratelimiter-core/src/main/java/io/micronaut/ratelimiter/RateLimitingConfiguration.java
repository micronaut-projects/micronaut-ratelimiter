package io.micronaut.ratelimiter;

import io.micronaut.context.annotation.ConfigurationProperties;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ConfigurationProperties("micronaut.ratelimiting")
public class RateLimitingConfiguration {

    public static final String PATH_KEY = "micronaut.ratelimiting.paths";

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
