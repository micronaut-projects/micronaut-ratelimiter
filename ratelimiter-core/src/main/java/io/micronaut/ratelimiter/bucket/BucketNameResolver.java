package io.micronaut.ratelimiter.bucket;

import io.micronaut.http.HttpRequest;

public interface BucketNameResolver {

    String resolve(HttpRequest<?> request);
}
