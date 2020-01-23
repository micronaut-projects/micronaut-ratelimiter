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
package io.micronaut.ratelimiter.bucket;

import io.micronaut.context.annotation.Requires;
import io.micronaut.core.util.StringUtils;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.server.util.HttpClientAddressResolver;

import javax.inject.Singleton;

/**
 * A basic {@link BucketNameResolver} implementation that resolves
 * the bucket name based off of the client IP address.
 *
 * @author James Kleeh
 * @since 1.0.0
 */
@Singleton
@Requires(property = "micronaut.ratelimiter.ip-address-resolver", value = StringUtils.TRUE)
class IpAddressBucketNameResolver implements BucketNameResolver {

    private final HttpClientAddressResolver clientAddressResolver;

    IpAddressBucketNameResolver(HttpClientAddressResolver clientAddressResolver) {
        this.clientAddressResolver = clientAddressResolver;
    }

    @Override
    public String resolve(HttpRequest<?> request) {
        return clientAddressResolver.resolve(request);
    }
}
