package example

import io.micronaut.http.annotation.Controller
import io.micronaut.ratelimiter.annotation.NoRateLimit

@NoRateLimit
@Controller("/api")
class NoRateLimitController {
}
