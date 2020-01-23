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
package io.micronaut.ratelimiter.resilience4j.response;

import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.server.exceptions.ExceptionHandler;

import javax.inject.Singleton;

/**
 * Exception handler for {@link RequestNotPermitted}.
 *
 * @author James Kleeh
 * @since 1.0.0
 */
@Singleton
public class RequestNotPermittedHandler implements ExceptionHandler<RequestNotPermitted, HttpResponse<?>> {

    @Override
    public HttpResponse<?> handle(HttpRequest request, RequestNotPermitted exception) {
        return HttpResponse.status(HttpStatus.TOO_MANY_REQUESTS)
                .contentType(MediaType.TEXT_PLAIN_TYPE)
                .body(exception.getMessage());
    }
}
