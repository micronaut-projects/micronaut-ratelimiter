package io.micronaut.ratelimiter.resilience4j

import io.micronaut.http.HttpRequest
import io.micronaut.ratelimiter.bucket.BucketNameResolver

import javax.inject.Singleton

@Singleton
class TestBucketNameResolver implements BucketNameResolver {

    @Override
    String resolve(HttpRequest<?> request) {
        "test"
    }
}
