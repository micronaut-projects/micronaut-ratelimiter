package io.micronaut.circuitbreaker.bucket;

import io.micronaut.context.annotation.Requires;
import io.micronaut.core.util.StringUtils;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.server.util.HttpClientAddressResolver;

import javax.inject.Singleton;

@Singleton
@Requires(property = "micronaut.circuitbreaker.ip-address-resolver", value = StringUtils.TRUE)
public class IpAddressBucketNameResolver implements BucketNameResolver {
    private final HttpClientAddressResolver clientAddressResolver;

    IpAddressBucketNameResolver(HttpClientAddressResolver clientAddressResolver){
        this.clientAddressResolver = clientAddressResolver;
    }

    @Override
    public String resolve(HttpRequest<?> request) {
        return clientAddressResolver.resolve(request);
    }
}
