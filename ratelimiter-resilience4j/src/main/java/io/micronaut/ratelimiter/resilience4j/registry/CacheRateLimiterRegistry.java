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
package io.micronaut.ratelimiter.resilience4j.registry;

import io.github.resilience4j.core.ConfigurationNotFoundException;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.micronaut.cache.SyncCache;
import io.micronaut.core.annotation.Experimental;
import io.micronaut.core.annotation.Internal;
import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import io.vavr.collection.Seq;

import java.util.Optional;
import java.util.function.Supplier;

@Experimental
@Internal
@SuppressWarnings("all")
public class CacheRateLimiterRegistry implements RateLimiterRegistry {

    private final Map<String, String> registryTags;
    private final SyncCache<?> cache;
    private final RateLimiterConfig defaultConfig;

    public CacheRateLimiterRegistry(SyncCache<?> cache, RateLimiterConfig defaultConfig) {
        this(cache, defaultConfig, HashMap.empty());
    }

    public CacheRateLimiterRegistry(SyncCache<?> cache, RateLimiterConfig defaultConfig, Map<String, String> registryTags) {
        this.cache = cache;
        this.defaultConfig = defaultConfig;
        this.registryTags = registryTags;
    }

    public void addRateLimiters(java.util.Map<String, RateLimiterConfig> rateLimiterMap) {
        for (java.util.Map.Entry<String, RateLimiterConfig> entry: rateLimiterMap.entrySet()) {
            String name = entry.getKey();
            cache.put(name, RateLimiter.of(name, entry.getValue()));
        }
    }

    @Override
    public Seq<RateLimiter> getAllRateLimiters() {
        throw new UnsupportedOperationException("The sync cache API cannot retrieve all values");
    }

    @Override
    public RateLimiter rateLimiter(String name) {
        return cache.putIfAbsent(name, () -> RateLimiter.of(name, defaultConfig));
    }

    @Override
    public RateLimiter rateLimiter(String name, Map<String, String> tags) {
        return cache.putIfAbsent(name, () -> RateLimiter.of(name, defaultConfig, tags.merge(registryTags)));
    }

    @Override
    public RateLimiter rateLimiter(String name, RateLimiterConfig rateLimiterConfig) {
        return cache.putIfAbsent(name, () -> RateLimiter.of(name, rateLimiterConfig));
    }

    @Override
    public RateLimiter rateLimiter(String name, RateLimiterConfig rateLimiterConfig, Map<String, String> tags) {
        return cache.putIfAbsent(name, () -> RateLimiter.of(name, rateLimiterConfig, tags.merge(registryTags)));
    }

    @Override
    public RateLimiter rateLimiter(String name, Supplier<RateLimiterConfig> rateLimiterConfigSupplier) {
        return cache.putIfAbsent(name, () -> RateLimiter.of(name, rateLimiterConfigSupplier));
    }

    @Override
    public RateLimiter rateLimiter(String name, Supplier<RateLimiterConfig> rateLimiterConfigSupplier, Map<String, String> tags) {
        return cache.putIfAbsent(name, () -> RateLimiter.of(name, rateLimiterConfigSupplier, tags.merge(registryTags)));
    }

    @Override
    public RateLimiter rateLimiter(String name, String configName) {
        return cache.putIfAbsent(name, () -> {
            RateLimiter rateLimiter = cache.get(configName, RateLimiter.class).orElseThrow(() -> new ConfigurationNotFoundException(configName));
            return RateLimiter.of(name, rateLimiter.getRateLimiterConfig());
        });
    }

    @Override
    public RateLimiter rateLimiter(String name, String configName, Map<String, String> tags) {
        return cache.putIfAbsent(name, () -> {
            RateLimiter rateLimiter = cache.get(configName, RateLimiter.class).orElseThrow(() -> new ConfigurationNotFoundException(configName));
            return RateLimiter.of(name, rateLimiter.getRateLimiterConfig(), tags.merge(registryTags));
        });
    }

    @Override
    public void addConfiguration(String configName, RateLimiterConfig configuration) {
        cache.put(configName, RateLimiter.of(configName, configuration));
    }

    @Override
    public Optional<RateLimiter> find(String name) {
        return cache.get(name, RateLimiter.class);
    }

    @Override
    public Optional<RateLimiter> remove(String name) {
        Optional<RateLimiter> rateLimiter = find(name);
        rateLimiter.ifPresent(limiter -> cache.invalidate(name));
        return rateLimiter;
    }

    @Override
    public Optional<RateLimiter> replace(String name, RateLimiter newEntry) {
        Optional<RateLimiter> rateLimiter = find(name);
        cache.put(name, newEntry);
        return rateLimiter;
    }

    @Override
    public Optional<RateLimiterConfig> getConfiguration(String configName) {
        return find(configName).map(RateLimiter::getRateLimiterConfig);
    }

    @Override
    public RateLimiterConfig getDefaultConfig() {
        return defaultConfig;
    }

    @Override
    public EventPublisher<RateLimiter> getEventPublisher() {
        throw new UnsupportedOperationException("Not supported due to resilience4j api limitations");
    }
}
