@Configuration
@Requires(property = "resilience4j.ratelimiter.enabled", notEquals = StringUtils.FALSE)
package io.micronaut.ratelimiter.resilience4j;

import io.micronaut.context.annotation.Configuration;
import io.micronaut.context.annotation.Requires;
import io.micronaut.core.util.StringUtils;
