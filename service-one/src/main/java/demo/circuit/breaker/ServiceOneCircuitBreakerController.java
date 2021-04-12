package demo.circuit.breaker;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.atomic.AtomicInteger;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
public class ServiceOneCircuitBreakerController {
    private final Logger logger = getLogger(getClass());
    private final AtomicInteger counter = new AtomicInteger();

    @Autowired
    private CircuitBreakerFactory<?, ?> circuitBreakerFactory;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/fail")
    public String fail() {
        return circuitBreakerFactory.create("demo-fail").run(
                () -> query("http://this.url.does.not.exist"),
                this::fallback);
    }

    @GetMapping("/delay/{seconds}")
    public String delay(@PathVariable long seconds) {
        return circuitBreakerFactory.create("demo-delay").run(
                () -> query("https://httpbin.org/delay/" + seconds),
                this::fallback);
    }

    private String query(String url) {
        logger.info("query invoked: {}", counter.incrementAndGet());
        return restTemplate.getForObject(url, String.class);
    }

    private String fallback(Throwable error) {
        logger.info("fallback invoked");
        return "Try later";
    }
}