package io.micronaut.circuitbreaker.resilience4j;

import io.micronaut.context.annotation.EachProperty;
import io.micronaut.context.annotation.Parameter;

import javax.validation.constraints.NotNull;

@EachProperty(value = "resilience4j.circuitbreaker.configurations", primary = "default")
public class CircuitBreakerConfiguration {

    private final String name;

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
}
