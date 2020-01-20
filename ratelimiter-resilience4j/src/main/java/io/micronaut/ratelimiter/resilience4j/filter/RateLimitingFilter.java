package io.micronaut.ratelimiter.resilience4j.filter;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.github.resilience4j.ratelimiter.operator.RateLimiterOperator;
import io.micronaut.core.annotation.Internal;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Filter;
import io.micronaut.http.filter.OncePerRequestHttpServerFilter;
import io.micronaut.http.filter.ServerFilterChain;
import io.micronaut.ratelimiter.RateLimitingSupport;
import io.micronaut.ratelimiter.bucket.BucketNameResolver;
import io.reactivex.Flowable;
import org.reactivestreams.Publisher;

import java.util.Optional;

import static io.micronaut.ratelimiter.RateLimitingConfiguration.PATH_KEY;

@Internal
@Filter("${" + PATH_KEY + ":/**}")
class RateLimitingFilter extends OncePerRequestHttpServerFilter {

    public static final Integer ORDER = 1000;
    private final BucketNameResolver bucketNameResolver;
    private final RateLimiterRegistry registry;
    private final RateLimitingSupport rateLimitingSupport;

    RateLimitingFilter(BucketNameResolver bucketNameResolver,
                       RateLimiterRegistry registry,
                       RateLimitingSupport rateLimitingSupport) {
        this.bucketNameResolver = bucketNameResolver;
        this.registry = registry;
        this.rateLimitingSupport = rateLimitingSupport;
    }

    @Override
    public int getOrder() {
        return ORDER;
    }

    @Override
    protected Publisher<MutableHttpResponse<?>> doFilterOnce(HttpRequest<?> request, ServerFilterChain chain) {
        if (rateLimitingSupport.shouldLimit(request)) {
            String key = bucketNameResolver.resolve(request);
            Optional<String> configName = rateLimitingSupport.getConfigurationName(request);
            RateLimiter rateLimiter = configName.map(name -> registry.rateLimiter(key, name)).orElseGet(() -> registry.rateLimiter(key));

            return Flowable.fromPublisher(chain.proceed(request))
                    .compose(RateLimiterOperator.of(rateLimiter));
        } else {
            return chain.proceed(request);
        }
    }
}
