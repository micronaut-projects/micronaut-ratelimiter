package io.micronaut.ratelimiter.annotation;

import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimit {

    /**
     * @return The name of the configuration to control rate limits.
     */
    String value();
}
