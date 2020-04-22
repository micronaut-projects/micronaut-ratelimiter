package io.micronaut.circuitbreaker.resilience4j.filter;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.circuitbreaker.operator.CircuitBreakerOperator;
import io.micronaut.circuitbreaker.CircuitBreakerSupport;
import io.micronaut.circuitbreaker.bucket.BucketNameResolver;
import io.micronaut.core.annotation.Internal;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Filter;
import io.micronaut.http.filter.OncePerRequestHttpServerFilter;
import io.micronaut.http.filter.ServerFilterChain;
import io.reactivex.Flowable;
import org.reactivestreams.Publisher;

import java.util.Optional;

import static io.micronaut.circuitbreaker.CircuitBreakerConfiguration.PATH_KEY;


@Internal
@Filter("${"+ PATH_KEY +"}")
public class CircuitBreakerFilter extends OncePerRequestHttpServerFilter {

    public static final Integer ORDER = 1000;
    private final CircuitBreakerRegistry registry;
    private final CircuitBreakerSupport circuitBreakerSupport;
    private final BucketNameResolver bucketNameResolver;

    CircuitBreakerFilter(BucketNameResolver bucketNameResolver, CircuitBreakerRegistry registry, CircuitBreakerSupport circuitBreakerSupport) {
        this.registry = registry;
        this.circuitBreakerSupport = circuitBreakerSupport;
        this.bucketNameResolver = bucketNameResolver;
    }

    @Override
    protected Publisher<MutableHttpResponse<?>> doFilterOnce(HttpRequest<?> request, ServerFilterChain chain) {
        if (circuitBreakerSupport.shouldCircuitbreak(request)) {
            String key = bucketNameResolver.resolve(request);
            Optional<String> configName = circuitBreakerSupport.getConfigurationName(request);
            CircuitBreaker circuitBreaker = configName.map(name -> registry.circuitBreaker(key, name)).orElseGet(() -> registry.circuitBreaker(key));

            return Flowable.fromPublisher(chain.proceed(request))
                .compose(CircuitBreakerOperator.of(circuitBreaker));

        } else {
            return chain.proceed(request);
        }
    }
}
