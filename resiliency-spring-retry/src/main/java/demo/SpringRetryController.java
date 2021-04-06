package demo;

import org.slf4j.Logger;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
public class SpringRetryController {
    private final Logger logger = getLogger(getClass());
    private final AtomicInteger counter = new AtomicInteger();

    @GetMapping
    public String index() {
        return "OK";
    }

    @GetMapping("/retry")
    @Retryable(
            // recover = "fallback", // Not needed for single @Recover method
            value = Exception.class,
            maxAttempts = 5,
            backoff = @Backoff(delay = 100))
    public String retry() {
        logger.info("retry invoked: {}", counter.incrementAndGet());
        throw new RuntimeException("Dummy failure");
    }

    @Recover
    public String fallback(Exception e) {
        logger.warn("fallback invoked");
        return "Try later: " + counter.get();
    }
}
