/*
 * Copyright 2017-2019 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.micronaut.ratelimiter;

import io.micronaut.core.annotation.AnnotationMetadata;
import io.micronaut.http.HttpAttributes;
import io.micronaut.http.HttpRequest;
import io.micronaut.ratelimiter.annotation.NoRateLimit;
import io.micronaut.ratelimiter.annotation.RateLimit;
import io.micronaut.web.router.RouteMatch;

import javax.inject.Singleton;
import java.util.Optional;

/**
 * Contains common business logic for rate limiting implementations.
 *
 * @author James Kleeh
 * @since 1.0.0
 */
@Singleton
public class RateLimitingSupport {

    private final RateLimitingConfiguration configuration;

    /**
     * Default constructor.
     *
     * @param configuration The rate limiting configuration
     */
    public RateLimitingSupport(RateLimitingConfiguration configuration) {
        this.configuration = configuration;
    }

    /**
     * @param request The request
     * @return True if the request should be rate limited
     */
    public boolean shouldLimit(HttpRequest<?> request) {
        return getAnnotationMetadata(request).map(metadata -> !metadata.hasAnnotation(NoRateLimit.class)).orElse(true);
    }

    /**
     * @param request The request
     * @return The rate limiting configuration to be used for this request. Returns
     * empty if the default configuration should apply
     */
    public Optional<String> getConfigurationName(HttpRequest<?> request) {
        return getAnnotationMetadata(request).flatMap(metadata -> metadata.stringValue(RateLimit.class));
    }

    /**
     * @param request The request
     * @return The annotation metadata to resolve rate limit annotations
     */
    protected Optional<AnnotationMetadata> getAnnotationMetadata(HttpRequest<?> request) {
        return request.getAttribute(HttpAttributes.ROUTE_MATCH, RouteMatch.class)
                .map(RouteMatch::getAnnotationMetadata);
    }
}
