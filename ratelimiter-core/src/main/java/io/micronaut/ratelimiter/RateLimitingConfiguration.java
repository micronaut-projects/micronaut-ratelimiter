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

import io.micronaut.context.annotation.ConfigurationProperties;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

/**
 * Configuration for generic rate limiting options.
 *
 * @author James Kleeh
 * @since 1.0.0
 */
@ConfigurationProperties("micronaut.ratelimiting")
public class RateLimitingConfiguration {

    public static final String PATH_KEY = "micronaut.ratelimiting.paths";

    private List<String> paths = Collections.emptyList();
    private boolean ipAddressResolver = false;

    /**
     * @return The paths to rate limit
     */
    @Nonnull
    public List<String> getPaths() {
        return paths;
    }

    /**
     * @param paths The ANT style paths that rate limiting should be applied to
     */
    public void setPaths(@Nonnull List<String> paths) {
        this.paths = paths;
    }

    /**
     * @return True if the ip address bucket name resolver should be used
     */
    public boolean isIpAddressResolver() {
        return ipAddressResolver;
    }

    /**
     * @param ipAddressResolver If true, the provided IP address {@link io.micronaut.ratelimiter.bucket.BucketNameResolver}
     *                          will be enabled.
     */
    public void setIpAddressResolver(boolean ipAddressResolver) {
        this.ipAddressResolver = ipAddressResolver;
    }
}
