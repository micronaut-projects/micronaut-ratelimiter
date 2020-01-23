/*
 * Copyright 2017-2019 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

/**
 * The server filter to limit requests using Resilience4j.
 *
 * @author James Kleeh
 * @since 1.0.0
 */
@Internal
@Filter("${" + PATH_KEY + ":/**}")
class RateLimitingFilter extends OncePerRequestHttpServerFilter {

    public static final Integer ORDER = 1000;
    private final BucketNameResolver bucketNameResolver;
    private final RateLimiterRegistry registry;
    private final RateLimitingSupport rateLimitingSupport;

    /**
     * @param bucketNameResolver  The bucket name resolver
     * @param registry            The rate limiter registry
     * @param rateLimitingSupport The rate limiting support
     */
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
