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
package io.micronaut.ratelimiter.resilience4j;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.core.convert.format.MapFormat;
import io.micronaut.core.naming.conventions.StringConvention;
import io.micronaut.core.util.Toggleable;

import java.util.Map;
import java.util.Optional;

/**
 * Configuration for the rate limiter registry.
 *
 * @author James Kleeh
 * @since 1.0.0
 */
@ConfigurationProperties("resilience4j.ratelimiter")
public class RateLimiterRegistryConfiguration implements Toggleable {

    private boolean enabled;
    private Map<String, String> tags;

    /**
     * @return True if the rate limiter should be enabled
     */
    @Override
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * @param enabled True if the resilience4j rate limiter should be enabled
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * @return The registry tags
     */
    public Optional<Map<String, String>> getTags() {
        return Optional.ofNullable(tags);
    }

    /**
     * @param tags The registry tags
     */
    public void setTags(@MapFormat(keyFormat = StringConvention.RAW) Map<String, String> tags) {
        this.tags = tags;
    }

}
