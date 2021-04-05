package demo;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.atomic.AtomicInteger;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
public class CircuitBreakerController {
    private final Logger logger = getLogger(getClass());
    private final AtomicInteger counter = new AtomicInteger();

    // No need to make CircuitBreaker singleton, i.e. it is ok to call CircuitBreakerFactory.create every time
    @Autowired
    private CircuitBreakerFactory<?, ?> circuitBreakerFactory;

    @Autowired
    private RestTemplate restTemplate;

    // for (var i = 0; i < 10; i++) fetch('/').then(r => r.text()).then(console.log)
    @GetMapping
    public String index() {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("demo");
        String url = "http://this.url.does.not.exist";
        return circuitBreaker.run(() -> restTemplate.getForObject(url, String.class),
                throwable -> fallback());
    }

    // for (var i = 0; i < 3; i++) fetch('/sleep/1').then(r => r.text()).then(console.log)
    // for (var i = 0; i < 3; i++) fetch('/sleep/3').then(r => r.text()).then(console.log)
    @GetMapping("/sleep/{seconds}")
    public String timeout(@PathVariable long seconds) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("demo");
        String url = "http://this.url.does.not.exist";
        return circuitBreaker.run(() -> {
            sleep(seconds);
            return "OK";
        }, throwable -> fallback());
    }

    private String run() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "OK";
    }

    private String fallback() {
        logger.info("fallback invoked");
        return "Try later";
    }

    private void sleep(long seconds) {
        try {
            Thread.sleep(1000 * seconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
