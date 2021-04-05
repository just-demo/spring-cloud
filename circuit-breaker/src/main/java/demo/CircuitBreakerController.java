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

    @GetMapping
    public String index() {
        return "OK";
    }

    // for (var i = 0; i < 10; i++) fetch('/fail').then(r => r.text()).then(console.log)
    @GetMapping("/fail")
    public String fail() {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("demo-fail");
        return circuitBreaker.run(
                () -> query("http://this.url.does.not.exist"),
                throwable -> fallback()
        );
    }

    // for (var i = 0; i < 3; i++) fetch('/delay/1').then(r => r.text()).then(console.log)
    // for (var i = 0; i < 3; i++) fetch('/delay/3').then(r => r.text()).then(console.log)
    @GetMapping("/delay/{seconds}")
    public String timeout(@PathVariable long seconds) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("demo-delay");
        return circuitBreaker.run(
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
