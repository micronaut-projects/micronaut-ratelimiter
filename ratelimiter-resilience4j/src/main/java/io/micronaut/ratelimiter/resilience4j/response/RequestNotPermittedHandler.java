package io.micronaut.ratelimiter.resilience4j.response;

import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.server.exceptions.ExceptionHandler;

import javax.inject.Singleton;

@Singleton
public class RequestNotPermittedHandler implements ExceptionHandler<RequestNotPermitted, HttpResponse<?>> {

    @Override
    public HttpResponse<?> handle(HttpRequest request, RequestNotPermitted exception) {
        return HttpResponse.status(HttpStatus.TOO_MANY_REQUESTS)
                .contentType(MediaType.TEXT_PLAIN_TYPE)
                .body(exception.getMessage());
    }
}
