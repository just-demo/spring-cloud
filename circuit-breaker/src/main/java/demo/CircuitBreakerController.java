package demo;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.atomic.AtomicInteger;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromServletMapping;

@RestController
public class CircuitBreakerController {
    private final Logger logger = getLogger(getClass());
    private final AtomicInteger counter = new AtomicInteger();

    @Autowired
    private CircuitBreakerFactory<?, ?> circuitBreakerFactory;

    @Autowired
    private RestTemplate restTemplate;

    // for (var i = 0; i < 10; i++) fetch('/').then(r => r.text()).then(console.log)
    @GetMapping
    public String index(HttpServletRequest request) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("demo");
        String url = fromServletMapping(request).path("/unstable").build().toString();
        return circuitBreaker.run(() -> restTemplate.getForObject(url, String.class),
                throwable -> fallback());
    }

    @GetMapping("/unstable")
    @ResponseStatus(code = NOT_FOUND, reason = "Dummy failure")
    public String unstable() {
        logger.info("unstable invoked: {}", counter.incrementAndGet());
        return "OK";
    }

    private String fallback() {
        logger.info("fallback invoked");
        return "Try later";
    }
}
