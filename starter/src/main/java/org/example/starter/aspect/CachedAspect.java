package org.example.starter.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
public class CachedAspect {
    private final ConcurrentHashMap<Object, CacheEntry> cache = new ConcurrentHashMap<>();
    @Value("${cache.ttl}")
    private long ttl;

    @Around("@annotation(org.example.starter.annotation.Cached)")
    public Object logCache(ProceedingJoinPoint joinPoint) throws Throwable {
        Object key = generateKey(joinPoint);

        if (cache.containsKey(key) && !isExpired(cache.get(key))) {
            return cache.get(key).value();
        }

        Object result = joinPoint.proceed();
        cache.put(key, new CacheEntry(result, System.currentTimeMillis()));

        return result;
    }

    private Object generateKey(ProceedingJoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        return args.length > 0 ? args[0] : joinPoint.getSignature().toString();
    }

    private boolean isExpired(CacheEntry entry) {
        return System.currentTimeMillis() - entry.timestamp() > TimeUnit.SECONDS.toMillis(ttl);
    }

    @Scheduled(fixedRate = 100000)
    private void cleanUpCache() {
        cache.entrySet().removeIf(entry -> isExpired(entry.getValue()));
    }

    private record CacheEntry(Object value, long timestamp) { }
}
