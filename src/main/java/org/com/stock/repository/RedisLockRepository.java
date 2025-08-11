package org.com.stock.repository;

import lombok.RequiredArgsConstructor;
import org.com.stock.service.StockService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class RedisLockRepository {
    private final RedisTemplate<String, String> redisTemplate;

    public Boolean lock(Long key) {
        return redisTemplate
                .opsForValue()
                .setIfAbsent(String.valueOf(key), "lock", Duration.ofMillis(3000));
    }

    public Boolean unlock(Long key) {
        return redisTemplate.delete(String.valueOf(key));
    }
}
