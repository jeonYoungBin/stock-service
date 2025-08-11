package org.com.stock.facade;

import lombok.RequiredArgsConstructor;
import org.com.stock.repository.RedisLockRepository;
import org.com.stock.service.StockService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LettuceLcokStockFacade {
    private final RedisLockRepository redisLockRepository;
    private final StockService stockService;

    public void decrease(Long id, Long quantity) throws InterruptedException {
        while(!redisLockRepository.lock(id)) {
            Thread.sleep(100);
        }
        try {
            stockService.decrease(id, quantity);
        } finally {
            redisLockRepository.unlock(id);
        }
    }
}
