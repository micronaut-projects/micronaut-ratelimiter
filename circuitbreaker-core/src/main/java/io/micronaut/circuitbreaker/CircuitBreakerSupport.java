package io.micronaut.circuitbreaker;

import io.micronaut.circuitbreaker.annotation.CircuitBreaker;
import io.micronaut.circuitbreaker.annotation.NoCircuitBreaker;
import io.micronaut.core.annotation.AnnotationMetadata;
import io.micronaut.http.HttpAttributes;
import io.micronaut.http.HttpRequest;
import io.micronaut.web.router.RouteMatch;

import javax.inject.Singleton;
import java.util.Optional;

@Singleton
public class CircuitBreakerSupport {
    private final CircuitBreakerConfiguration configuration;


    public boolean shouldCircuitbreak(HttpRequest<?> request) {
        return getAnnotationMetadata(request).map(metadata -> !metadata.hasAnnotation(NoCircuitBreaker.class)).orElse(true);
    }

    public CircuitBreakerSupport(CircuitBreakerConfiguration configuration) {
        this.configuration = configuration;
    }

    public Optional<String> getConfigurationName(HttpRequest<?> request) {
        return getAnnotationMetadata(request).flatMap(metadata -> metadata.stringValue(CircuitBreaker.class));
    }

    protected Optional<AnnotationMetadata> getAnnotationMetadata(HttpRequest<?> request) {
        return request.getAttribute(HttpAttributes.ROUTE_MATCH, RouteMatch.class)
            .map(RouteMatch::getAnnotationMetadata);
    }

}
