package demo.resilient;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
public class ResilientController {
    private final Logger logger = getLogger(getClass());
    private final AtomicInteger counter = new AtomicInteger();

    @Retry(name = "demoFallback", fallbackMethod = "fallback")
    @GetMapping("/fallback")
    public String demoFallback() {
        logger.info("demoFallback: {}", counter.incrementAndGet());
        throw new RuntimeException("demoFallback");
    }

    @Retry(name = "demoRetry", fallbackMethod = "fallback")
    @GetMapping("/retry")
    public String demoRetry() {
        logger.info("demoRetry: {}", counter.incrementAndGet());
        throw new RuntimeException("demoRetry");
    }

    // for (var i = 0; i < 1000; i++) fetch('/resilient/circuit-breaker').then(r => r.text()).then(console.log);
    @CircuitBreaker(name = "demoCircuitBreaker", fallbackMethod = "fallback")
    @RequestMapping("/circuit-breaker")
    public String demoCircuitBreaker() {
        logger.info("demoCircuitBreaker: {}", counter.incrementAndGet());
        throw new RuntimeException("demoCircuitBreaker");
    }

    // for (var i = 0; i < 10; i++) fetch('/resilient/rate-limiter').then(r => r.text()).then(console.log);
    @RateLimiter(name = "demoRateLimiter", fallbackMethod = "fallback")
    @RequestMapping("/rate-limiter")
    public String demoRateLimiter() {
        logger.info("demoRateLimiter: {}", counter.incrementAndGet());
        return "Success demoRateLimiter";
    }

    // TODO: does it really work?
    // for (var i = 0; i < 100; i++) fetch('/resilient/bulk-head').then(r => r.text()).then(console.log);
    @Bulkhead(name = "demoBulkHead", fallbackMethod = "fallback")
    @RequestMapping("/bulk-head")
    public String demoBulkHead() {
        logger.info("demoBulkHead: {}", counter.incrementAndGet());
        return "Success demoBulkHead";
    }

    public String fallback(Exception e) {
        return "Fallback for " + e.getMessage();
    }
}
