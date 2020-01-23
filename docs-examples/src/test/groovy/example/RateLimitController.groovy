package example

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.ratelimiter.annotation.NoRateLimit
import io.micronaut.ratelimiter.annotation.RateLimit

@RateLimit("high") // <1>
@Controller("/api") // <2>
class RateLimitController {

    @NoRateLimit // <3>
    HttpResponse<?> someMethod() {
        HttpResponse.ok()
    }
}
