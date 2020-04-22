package io.micronaut.circuitbreaker.resilience4j;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.micronaut.context.annotation.EachProperty;
import io.micronaut.context.annotation.Parameter;

import javax.swing.text.html.Option;
import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.util.Optional;

@EachProperty(value = "resilience4j.circuitbreaker.configurations", primary = "default")
public class CircuitBreakerConfiguration {

    private final String name;
    private Float failureRateThreshold;
    private Float slowCallRateThreshold;
    private Duration slowCallDurationThreshold;
    private Integer slidingWindowSize;
    private Integer minimumNumberOfCalls;
    private HalfOpenState halfOpenState;

    private CircuitBreakerConfig.SlidingWindowType windowType;


    public void setFailureRateThreshold(Float failureRateThreshold) {
        this.failureRateThreshold = failureRateThreshold;
    }

    public void setMinimumNumberOfCalls(Integer minimumNumberOfCalls) {
        this.minimumNumberOfCalls = minimumNumberOfCalls;
    }

    public void setSlidingWindowSize(Integer slidingWindowSize) {
        this.slidingWindowSize = slidingWindowSize;
    }

    public void setSlowCallDurationThreshold(Duration slowCallDurationThreshold) {
        this.slowCallDurationThreshold = slowCallDurationThreshold;
    }

    public void setSlowCallRateThreshold(Float slowCallRateThreshold) {
        this.slowCallRateThreshold = slowCallRateThreshold;
    }

    public void setHalfOpenState(HalfOpenState halfOpenState) {
        this.halfOpenState = halfOpenState;
    }

    public Optional<HalfOpenState> getHalfOpenState() {
        return Optional.ofNullable(halfOpenState);
    }

    public void setWindowType(CircuitBreakerConfig.SlidingWindowType windowType) {
        this.windowType = windowType;
    }


    public Optional<Duration> getSlowCallDurationThreshold() {
        return Optional.ofNullable(slowCallDurationThreshold);
    }

    public Optional<Integer> getMinimumNumberOfCalls() {
        return Optional.ofNullable(minimumNumberOfCalls);
    }

    public Optional<Integer> getSlidingWindowSize() {
        return Optional.ofNullable(slidingWindowSize);
    }

    public Optional<Float> getSlowCallRateThreshold() {
        return Optional.ofNullable(slowCallRateThreshold);
    }

    public Optional<CircuitBreakerConfig.SlidingWindowType> getWindowType() {
        return Optional.ofNullable(windowType);
    }

    public Optional<Float> getFailureRateThreshold() {
        return Optional.ofNullable(failureRateThreshold);
    }

    /**
     * @param name The name of the configuration
     */
    public CircuitBreakerConfiguration(@Parameter String name) {
        this.name = name;
    }

    @NotNull
    public String getName() {
        return name;
    }

    public static class HalfOpenState {
        private Integer permittedNumberOfCalls;
        private Boolean automaticallyTransitionToHalfopen;


        public Optional<Boolean> getAutomaticallyTransitionToHalfopen() {
            return Optional.ofNullable(automaticallyTransitionToHalfopen);
        }

        public void setPermittedNumberOfCalls(Integer permittedNumberOfCalls) {
            this.permittedNumberOfCalls = permittedNumberOfCalls;
        }

        public Optional<Integer> getPermittedNumberOfCalls() {
            return Optional.ofNullable(permittedNumberOfCalls);
        }
    }
}
