package demo;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.concurrent.atomic.AtomicInteger;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
public class CircuitBreakerController {
    private final Logger logger = getLogger(getClass());
    private final AtomicInteger counter = new AtomicInteger();

    @Autowired
    private CircuitBreakerFactory<?, ?> circuitBreakerFactory;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping
    public String index() {
        return "OK";
    }

    private CircuitBreaker failCircuitBreaker;
    private CircuitBreaker delayCircuitBreaker;

    @PostConstruct
    public void init() {
        // It turned out that CircuitBreaker need to be a singleton to make Spring Retry based implementation work as
        // expected, while Resilience4j would be fine even calling CircuitBreakerFactory.create per each request.
        // Creating separate fail/delay CircuitBreaker instances to make sure their state is shared during between
        // two independent scenarios.
        failCircuitBreaker = circuitBreakerFactory.create("demo-fail");
        delayCircuitBreaker = circuitBreakerFactory.create("demo-delay");
    }

    // for (var i = 0; i < 10; i++) fetch('/fail').then(r => r.text()).then(console.log)
    @GetMapping("/fail")
    public String fail() {
        return failCircuitBreaker.run(
                () -> query("http://this.url.does.not.exist"),
                throwable -> fallback()
        );
    }

    // TODO: Spring Retry does not seem to support timeouts, i.e. all queries pass no matter how much time they take
    // for (var i = 0; i < 3; i++) fetch('/delay/1').then(r => r.text()).then(console.log)
    // for (var i = 0; i < 3; i++) fetch('/delay/3').then(r => r.text()).then(console.log)
    @GetMapping("/delay/{seconds}")
    public String delay(@PathVariable long seconds) {
        return delayCircuitBreaker.run(
                () -> query("https://httpbin.org/delay/" + seconds),
                throwable -> fallback()
        );
    }

    private String query(String url) {
        logger.info("query invoked: {}", counter.incrementAndGet());
        return restTemplate.getForObject(url, String.class);
    }

    private String fallback() {
        logger.info("fallback invoked");
        return "Try later";
    }
}
