package io.micronaut.ratelimiter;

import io.micronaut.core.annotation.AnnotationMetadata;
import io.micronaut.http.HttpAttributes;
import io.micronaut.http.HttpRequest;
import io.micronaut.ratelimiter.annotation.NoRateLimit;
import io.micronaut.ratelimiter.annotation.RateLimit;
import io.micronaut.web.router.RouteMatch;

import javax.inject.Singleton;
import java.util.Optional;

@Singleton
public class RateLimitingSupport {

    private final RateLimitingConfiguration configuration;

    public RateLimitingSupport(RateLimitingConfiguration configuration) {
        this.configuration = configuration;
    }

    public boolean shouldLimit(HttpRequest<?> request) {
        return getAnnotationMetadata(request).map(metadata -> !metadata.hasAnnotation(NoRateLimit.class)).orElse(true);
    }

    public Optional<String> getConfigurationName(HttpRequest<?> request) {
        return getAnnotationMetadata(request).flatMap(metadata -> metadata.stringValue(RateLimit.class));
    }

    protected Optional<AnnotationMetadata> getAnnotationMetadata(HttpRequest<?> request) {
        return request.getAttribute(HttpAttributes.ROUTE_MATCH, RouteMatch.class)
                .map(RouteMatch::getAnnotationMetadata);
    }
}
