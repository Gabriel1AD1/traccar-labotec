package com.labotec.traccar.infra.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfig {
    /*
    @Bean
    public Caffeine<Object, Object> optimizedSensorCacheBuilder() {
        return Caffeine.newBuilder()
                .expireAfterWrite(1, TimeUnit.HOURS) // TTL específico: 1 hora
                .maximumSize(1000); // Límite de 1000 entradas
    }

    @Bean("optimizedSensorCacheManager")
    public CaffeineCacheManager optimizedSensorCacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("optimizedSensorCache");
        cacheManager.setCaffeine(optimizedSensorCacheBuilder());
        return cacheManager;
    }*/
}
