package io.micronaut.circuitbreaker.bucket;

import io.micronaut.http.HttpRequest;

public interface BucketNameResolver {
    String resolve(HttpRequest<?> request);
}
