package io.micronaut.ratelimiter.resilience4j

import io.micronaut.context.annotation.Property
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.ratelimiter.annotation.NoRateLimit
import io.micronaut.ratelimiter.annotation.RateLimit
import io.micronaut.test.annotation.MicronautTest
import spock.lang.Specification

import javax.inject.Inject

@MicronautTest
@Property(name = "resilience4j.ratelimiter.configurations.low.limit", value = "1")
@Property(name = "resilience4j.ratelimiter.configurations.low.period", value = "10s")
@Property(name = "micronaut.ratelimiting.paths", value = "/api/**")
class RateLimitSpec extends Specification {

    @Inject @Client("/") HttpClient client

    void "test basic rate limit functionality"() {
        when:
        HttpResponse<String> response = client.toBlocking().exchange("/api/limited", String.class)

        then:
        noExceptionThrown()
        response.body() == "ok"

        when:
        client.toBlocking().exchange("/api/limited", String.class)

        then:
        def ex = thrown(HttpClientResponseException)
        ex.response.code() == HttpStatus.TOO_MANY_REQUESTS.code
        ex.message == "RateLimiter 'test' does not permit further calls"

        when:
        response = client.toBlocking().exchange("/not-limited", String.class)

        then:
        noExceptionThrown()
        response.body() == "not-limited"

        when:
        response = client.toBlocking().exchange("/api/not-limited", String.class)

        then:
        noExceptionThrown()
        response.body() == "not-limited"
    }

    @Controller("/")
    @RateLimit("low")
    static class RateLimitedController {

        @Get("/api/limited")
        String limited() {
            "ok"
        }

        @Get("/not-limited")
        String notLimited() {
            "not-limited"
        }

        @Get("/api/not-limited")
        @NoRateLimit
        String notLimitedByAnnotation() {
            "not-limited"
        }
    }
}
