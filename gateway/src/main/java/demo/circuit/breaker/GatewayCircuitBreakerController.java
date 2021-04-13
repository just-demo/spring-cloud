package demo.circuit.breaker;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.atomic.AtomicInteger;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
public class GatewayCircuitBreakerController {
    private final Logger logger = getLogger(getClass());
    private final AtomicInteger counter = new AtomicInteger();

    @Autowired
    private CircuitBreakerFactory<?, ?> circuitBreakerFactory;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Environment environment;

    @GetMapping("/test")
    public String test() {
        return circuitBreakerFactory.create("test").run(
                () -> query(environment.getProperty("test.url")),
                this::fallback);
    }

    private String query(String url) {
        logger.info("query invoked: {}", counter.incrementAndGet());
        return restTemplate.getForObject(url, String.class);
    }

    private String fallback(Throwable error) {
        logger.info("fallback invoked: {}", error.getMessage());
        return "Try later";
    }
}