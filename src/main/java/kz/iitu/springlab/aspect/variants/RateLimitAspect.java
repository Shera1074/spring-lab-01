package kz.iitu.springlab.aspect.variants;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

@Aspect
@Component
public class RateLimitAspect {

    private final Map<String, Queue<Long>> requestHistory = new ConcurrentHashMap<>();

    @Around("@annotation(rateLimited)")
    public Object limitRate(ProceedingJoinPoint pjp, RateLimited rateLimited) throws Throwable {
        String methodName = pjp.getSignature().toShortString();
        long now = System.currentTimeMillis();
        long window = rateLimited.timeWindowMs();
        int max = rateLimited.maxRequests();

        requestHistory.putIfAbsent(methodName, new ConcurrentLinkedQueue<>());
        Queue<Long> timestamps = requestHistory.get(methodName);

        while (!timestamps.isEmpty() && (now - timestamps.peek() > window)) {
            timestamps.poll();
        }

        if (timestamps.size() >= max) {
            throw new IllegalStateException("Rate limit exceeded for " + methodName + ". Allowed: " + max + " requests per " + (window / 1000) + "s.");
        }

        timestamps.add(now);
        return pjp.proceed();
    }
}